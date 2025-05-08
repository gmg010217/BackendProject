package BackendProject.service;

import BackendProject.domain.ChatMessage;
import BackendProject.domain.Diary;
import BackendProject.domain.Exercise;
import BackendProject.domain.Member;
import BackendProject.dto.AiChatRequest;
import BackendProject.dto.GeminiRequestDto;
import BackendProject.dto.GeminiResponseDto;
import BackendProject.dto.QuizListDto;
import BackendProject.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeminiService {

    private final GeminiRepository geminiRepository;
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;
    private final ExerciseRepository exerciseRepository;
    private final QuizRepository quizRepository;

    @Qualifier("geminiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public String addGemini(Long memberId, AiChatRequest requestDto) {
        ChatMessage userChatMessage = new ChatMessage();
        userChatMessage.setContent(requestDto.getQuestion());
        userChatMessage.setSender("USER");
        userChatMessage.setWriterId(memberId);
        geminiRepository.save(userChatMessage);

        String requestUrl = apiUrl + "?key=" + geminiApiKey;
        Member member = memberRepository.findById(memberId);
        String pass = "안녕 내 닉네임은 " + member.getNickName() + "이고 나이는"
                + member.getAge() + "살이야. 성별은"
                + member.getGender() + "이고. 나를 간략히 소개하자면 나는 \""
                + member.getAboutMe() + "\"라고 할 수 있어.";

        List<Diary> diarys = diaryRepository.findAll(memberId);

        for (Diary diary : diarys) {
            pass += "내가 쓴 일기 제목은 \""
                    + diary.getTitle() + "\"이고. 일기 내용은 \""
                    + diary.getContent() + "\"이야.";
        }

        pass += "여기까지가 나의 소개이고 질문은 \"" + requestDto.getQuestion() + "\"이야.";

        GeminiRequestDto request = new GeminiRequestDto(pass);
        GeminiResponseDto response = restTemplate.postForObject(requestUrl, request, GeminiResponseDto.class);

        String rawText = response.getCandidates().get(0).getContent().getParts().get(0).getText().toString();
        String message = rawText.replace("*", "");

        ChatMessage aiChatMessage = new ChatMessage();
        aiChatMessage.setContent(message);
        aiChatMessage.setSender("AI");
        aiChatMessage.setWriterId(memberId);
        geminiRepository.save(aiChatMessage);

        return message;
    }

    public List<ChatMessage> makeResult(Long memberId) {
        return geminiRepository.findAll(memberId);
    }

    public String getExerciseRecommand(Long memberId) {
        String requestUrl = apiUrl + "?key=" + geminiApiKey;
        List<Exercise> exercises = exerciseRepository.findAll(memberId);

        String pass = "";

        for (Exercise exercise : exercises) {
            pass += "내가 쓴 운동 제목은 \""
                    + exercise.getTitle() + "\"이고. 운동 내용은 \""
                    + exercise.getContent() + "\"이야.";
        }

        pass += "여기까지가 나의 운동 기록이고 이에 맞추어서 운동 루틴을 추천해줘, 단 운동 루틴은 3문장 이내로 추천해줘";

        GeminiRequestDto request = new GeminiRequestDto(pass);
        GeminiResponseDto response = restTemplate.postForObject(requestUrl, request, GeminiResponseDto.class);

        String rawText = response.getCandidates().get(0).getContent().getParts().get(0).getText().toString();
        String message = rawText.replace("*", "");

        return message;
    }

    public Exercise getFirstExercise(Long memberId, LocalDate date) {
        String requestUrl = apiUrl + "?key=" + geminiApiKey;
        List<Exercise> exercises = exerciseRepository.findAll(memberId);

        if (exercises.isEmpty()) {
            return null;
        }

        String pass = "";

        for (Exercise exercise : exercises) {
            pass += "내가 쓴 운동 제목은 \""
                    + exercise.getTitle() + "\"이고. 운동 내용은 \""
                    + exercise.getContent() + "\"이야.";
        }

        pass += "여기까지가 나의 운동 기록이고 내가 작성한 운동 기록과 동일한 운동으로 \"운동 제목\"과 \"운동 내용\"만 하나씩 작성해줘 " +
                "(단 운동 제목과 운동 내용 문구는 작성하지 말고 운동 제목과 운동 내용을 &로 구분해서 작성해줘";

        GeminiRequestDto request = new GeminiRequestDto(pass);
        GeminiResponseDto response = restTemplate.postForObject(requestUrl, request, GeminiResponseDto.class);

        String rawText = response.getCandidates().get(0).getContent().getParts().get(0).getText().toString();
        String message = rawText.replace("*", "");

        String[] split = message.split("&");

        Exercise exercise = new Exercise();
        exercise.setTitle(split[0]);
        exercise.setContent(split[1]);
        exercise.setMemberId(memberId);
        exercise.setExerciseDate(date);
        Exercise savedExercises = exerciseRepository.save(exercise);

        return savedExercises;
    }

    public List<QuizListDto> getQuiz(Long memberId) {

        if (quizRepository.existsByMemberIdAndDate(memberId, LocalDate.now())) {
            throw new IllegalStateException("오늘은 이미 퀴즈를 진행했습니다");
        }

        String requestUrl = apiUrl + "?key=" + geminiApiKey;
        Member member = memberRepository.findById(memberId);
        String pass = "안녕 내 닉네임은 " + member.getNickName() + "이고 나이는"
                + member.getAge() + "살이야. 성별은"
                + member.getGender() + "이고. 나를 간략히 소개하자면 나는 \""
                + member.getAboutMe() + "\"라고 할 수 있어.";

        List<Diary> diarys = diaryRepository.findAll(memberId);

        for (Diary diary : diarys) {
            pass += "내가 쓴 일기 제목은 \""
                    + diary.getTitle() + "\"이고. 일기 내용은 \""
                    + diary.getContent() + "\"이야.";
        }

        List<Exercise> exercises = exerciseRepository.findAll(memberId);

        for (Exercise exercise : exercises) {
            pass += "내가 쓴 운동 제목은 \""
                    + exercise.getTitle() + "\"이고. 운동 내용은 \""
                    + exercise.getContent() + "\"이야.";
        }

        pass += "여기까지가 나의 소개이고 \"건강 관련 퀴즈\"를 사지선다 객관식으로 10개 내줘" +
                "단 \"question\": \"1+1=?\", \"options\": [\"1\", \"2\", \"3\"], \"answer\": \"2\" 형식으로 작성해 (JSON 데이터 이외의 문구는 작성하지 말고)";

        GeminiRequestDto request = new GeminiRequestDto(pass);
        GeminiResponseDto response = restTemplate.postForObject(requestUrl, request, GeminiResponseDto.class);

        String rawText = response.getCandidates().get(0).getContent().getParts().get(0).getText().toString();
        String message = rawText.replace("*", "")
                .replace("```json", "")
                .replace("```", "");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<QuizListDto> quizList = objectMapper.readValue(message, new TypeReference<List<QuizListDto>>() {});
            return quizList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("퀴즈 JSON 파싱 실패", e);
        }
    }
}
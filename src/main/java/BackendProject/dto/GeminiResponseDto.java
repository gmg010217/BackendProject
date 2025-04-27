package BackendProject.dto;

import lombok.Data;

import java.util.List;

@Data
public class GeminiResponseDto {
    private List<Candidate> candidates;

    @Data
    public static class Candidate {
        private Content content;
    }

    @Data
    public static class Content {
        private List<Parts> parts;
    }

    @Data
    public static class Parts {
        private String text;
    }
}
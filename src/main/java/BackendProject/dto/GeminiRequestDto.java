package BackendProject.dto;

import lombok.Data;

import java.util.List;

@Data
public class GeminiRequestDto {

    private List<Content> contents;

    public GeminiRequestDto(String text) {
        Parts parts = new Parts();
        parts.setText(text);

        Content content = new Content();
        content.setParts(List.of(parts));

        this.contents = List.of(content);
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
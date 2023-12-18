package raspi.youtube;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class YoutubeResponse {
    private final JsonNode jsonNode;

    public YoutubeResponse(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.jsonNode = objectMapper.readTree(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("given json string not parsable");
        }
    }

    public String getVideoID() {
        String videoId;
        videoId = jsonNode.get("items")
                .get(0)
                .get("id")
                .get("videoId")
                .asText();
        return videoId;
    }

    public String getVideoTitle() {
        String videoTitle;
        videoTitle = jsonNode.get("items")
                .get(0)
                .get("snippet")
                .get("title")
                .asText();
        return videoTitle;
    }
}

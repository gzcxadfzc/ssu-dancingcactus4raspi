package raspi.youtube;

//import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raspi.AppConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class YoutubeRequest {
    private static final String RESULT_COUNT = "1";
    private static final String BLANK = " ";
    private static final String BLANK_REPLACEMENT_FOR_URL = "%20";
    private static final String ADDITIONAL_KEYWORD = "|official";
    private final String apiKey;

    @Autowired
    public YoutubeRequest(AppConfig appConfig) {
        this.apiKey = appConfig.getApiKey();
    }

    public YoutubeResponse request(String searchTarget) {
        return new YoutubeResponse(requestJson(searchTarget));
    }

    public String requestJson(String searchTarget) {
        try {
            String apiUrl = createUrl(searchTarget);
            URL url = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            return response.toString();
        } catch (IOException e) {
            System.out.println("IOException Occurs, Maybe out of credits for youtube api");
            return "";
        }
    }

    private String createUrl(String searchTarget) {
        searchTarget = searchTarget.replace(BLANK, BLANK_REPLACEMENT_FOR_URL);
        StringBuilder apiUrl = new StringBuilder("https://www.googleapis.com/youtube/v3/search");
        apiUrl.append("?key=");
        apiUrl.append(apiKey);
        apiUrl.append("&part=snippet&type=video&maxResults=");
        apiUrl.append(RESULT_COUNT);
        apiUrl.append("&videoEmbeddable=true&q=");
        apiUrl.append(searchTarget);
        apiUrl.append(ADDITIONAL_KEYWORD);
        return apiUrl.toString();
    }
}

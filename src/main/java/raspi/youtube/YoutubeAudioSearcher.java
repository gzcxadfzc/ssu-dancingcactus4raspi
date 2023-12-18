package raspi.youtube;

import org.springframework.stereotype.Component;

@Component
public class YoutubeAudioSearcher {
    private final YoutubeRequest request;

    public YoutubeAudioSearcher(YoutubeRequest request) {
        this.request = request;
    }

    public YoutubeAudio getYoutubeAudioFrom(String searchTarget) {
        YoutubeResponse response = request.request(searchTarget);
        return new YoutubeAudio(response);
    }
}

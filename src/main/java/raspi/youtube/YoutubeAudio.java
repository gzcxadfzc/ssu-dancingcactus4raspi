package raspi.youtube;

public class YoutubeAudio {
    private static final String EXT = ".wav";
    private final String title;
    private final String id;
    private final String audioFileName;

    public YoutubeAudio(YoutubeResponse response) {
        this.title = response.getVideoTitle();
        this.id = response.getVideoID();
        this.audioFileName = response.getVideoTitle() + EXT;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getAudioFileName() {
        return audioFileName;
    }
}

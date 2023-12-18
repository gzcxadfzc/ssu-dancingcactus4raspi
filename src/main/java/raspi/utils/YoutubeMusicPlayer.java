package raspi.utils;

import org.springframework.stereotype.Component;
import raspi.AppConfig;
import raspi.youtube.YoutubeAudio;

@Component
public class YoutubeMusicPlayer extends AudioPlayer {
    private final AudioDownloader downloader;

    public YoutubeMusicPlayer(AppConfig appConfig, AudioDownloader downloader) {
        super(appConfig);
        this.downloader = downloader;
    }

    public void downLoadAndPlay(YoutubeAudio youtubeAudio) {
        downloader.download(youtubeAudio);
        super.playMusic(youtubeAudio);
    }
}

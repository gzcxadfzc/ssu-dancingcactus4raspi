package raspi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raspi.utils.gpio.CactusController;
import raspi.youtube.YoutubeAudio;
import raspi.youtube.YoutubeAudioSearcher;
import raspi.utils.YoutubeMusicPlayer;

@Component
public class StateMessageHandler {
    private final YoutubeMusicPlayer musicPlayer;
    private final YoutubeAudioSearcher searcher;
    private final CactusController cactus;

    @Autowired
    public StateMessageHandler(YoutubeMusicPlayer musicPlayer, YoutubeAudioSearcher searcher, CactusController cactus) {
        this.musicPlayer = musicPlayer;
        this.searcher = searcher;
        this.cactus = cactus;
    }

    public boolean handle(StateMessage stateMessage) {
        State state = stateMessage.getState();
        String value = stateMessage.getValue();
        System.out.println(value);
        switch (state) {
            case PLAY -> {
                YoutubeAudio audio = searcher.getYoutubeAudioFrom(value);
                musicPlayer.downLoadAndPlay(audio);
                cactus.dance();
            }
            case STOP -> {
                musicPlayer.stopMusic();
                cactus.stop();
            }
            case PAUSE -> {
                musicPlayer.pauseMusic();
                cactus.stop();
            }
            case RESUME -> {
                musicPlayer.resumeMusic();
                cactus.dance();
            }
            case VOLUME -> {
                if (value.equals("up")) {
                    musicPlayer.volumeUp();
                } else if (value.equals("down")) {
                    musicPlayer.volumeDown();
                } else {
                    musicPlayer.setVolumeLevel(value);
                }
            }
            case EXIT -> {
                musicPlayer.stopMusic();
                cactus.stop();
                cactus.close();
                return false;
            }
        }
        return true;
    }
}

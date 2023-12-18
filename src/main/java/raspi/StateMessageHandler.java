package raspi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raspi.youtube.YoutubeAudio;
import raspi.youtube.YoutubeAudioSearcher;
import raspi.utils.YoutubeMusicPlayer;

@Component
public class StateMessageHandler {
    private final YoutubeMusicPlayer musicPlayer;
    private final YoutubeAudioSearcher searcher;

    @Autowired
    public StateMessageHandler(YoutubeMusicPlayer musicPlayer, YoutubeAudioSearcher searcher) {
        this.musicPlayer = musicPlayer;
        this.searcher = searcher;
    }

    public boolean handle(StateMessage stateMessage) {
        State state = stateMessage.getState();
        String value = stateMessage.getValue();
        System.out.println(value);
        switch (state) {
            case PLAY -> {
                YoutubeAudio audio = searcher.getYoutubeAudioFrom(value);
                musicPlayer.downLoadAndPlay(audio);
            }
            case STOP -> musicPlayer.stopMusic();
            case PAUSE -> musicPlayer.pauseMusic();
            case RESUME -> musicPlayer.resumeMusic();
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
                return false;
            }
        }
        return true;
    }
}

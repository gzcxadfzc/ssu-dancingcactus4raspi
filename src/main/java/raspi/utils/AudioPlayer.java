package raspi.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raspi.AppConfig;
import raspi.youtube.YoutubeAudio;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

@Component
public class AudioPlayer {
    private static final String DEFAULT_VOLUME_LEVEL = "4";
    private final AppConfig config;
    private Clip clip;
    private long microSecondPosition = 0;
    private VolumeLevel currentVolume;

    @Autowired
    public AudioPlayer(AppConfig appConfig) {
        this.config = appConfig;
    }

    public void playMusic(YoutubeAudio youtubeAudio) {
        if (isPlaying()) {
            clip.close();
        }
        String audioFilePath = config.getMusicDirectory() + youtubeAudio.getAudioFileName();
        File audio = new File(audioFilePath);
        try {
            initClip(audio);
            clip.start();
            System.out.println("now playing....  " + youtubeAudio.getAudioFileName());
            Thread.sleep(1000);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initClip(File audio) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream stream = AudioSystem.getAudioInputStream(audio);
        AudioFormat format = stream.getFormat();
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(stream);
        setVolumeLevel(DEFAULT_VOLUME_LEVEL);
    }

    public void stopMusic() {
        if (isPlaying()) {
            clip.stop();
            clip.setMicrosecondPosition(0);
            System.out.println("music stops....");
        }
    }

    public void pauseMusic() {
        if (isPlaying()) {
            microSecondPosition = clip.getMicrosecondPosition();
            clip.stop();
        }
    }

    public void resumeMusic() {
        if (clip.isOpen() && !clip.isRunning()) {
            clip.setMicrosecondPosition(microSecondPosition);
            clip.start();
        }
    }

    public void setVolumeLevel(String level) {
        currentVolume = VolumeLevel.getLevel(Integer.parseInt(level));
        setClipVolumeToCurrentVolume();
    }

    public void volumeUp() {
        currentVolume = currentVolume.up();
        setClipVolumeToCurrentVolume();
        System.out.println(currentVolume.getDbValue());
    }

    public void volumeDown() {
        currentVolume = currentVolume.down();
        setClipVolumeToCurrentVolume();
        System.out.println(currentVolume.getDbValue());
    }

    private void setClipVolumeToCurrentVolume() {
        if (clip.isOpen()) {
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            if (volume != null) {
                volume.setValue(currentVolume.getDbValue());
            }
        }
    }

    private boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}

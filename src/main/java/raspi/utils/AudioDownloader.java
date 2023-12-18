package raspi.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raspi.youtube.YoutubeAudio;

@Component
public class AudioDownloader {
    private static final String EXECUTABLE_FILE_NAME = "audioExtractor.py";
    private final PythonExecutor executor;

    @Autowired
    public AudioDownloader(PythonExecutor executor) {
        this.executor = executor;
    }

    public void download(YoutubeAudio youtubeAudio) {
        executor.exec(EXECUTABLE_FILE_NAME, youtubeAudio.getId());
    }
}

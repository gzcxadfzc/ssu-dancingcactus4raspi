package raspi;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource("classpath:app.properties")
public class AppConfig {
    private final String apiKey;
    private final String musicDirectory;
    private final String audioDirectory;
    private final String pythonScriptsDirectory;
    public AppConfig(
            @Value("${api.key}") String apiKey,
            @Value("${music.directory}") String musicDirectory,
            @Value("${audio.directory}") String audioDirectory,
            @Value("${pythonScript.directory}") String pythonScriptsDirectory
    ) {
        this.apiKey = apiKey;
        this.musicDirectory = musicDirectory;
        this.audioDirectory = audioDirectory;
        this.pythonScriptsDirectory = pythonScriptsDirectory;
    }
}
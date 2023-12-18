package raspi.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raspi.AppConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

@Component
public class PythonExecutor {
    private final String path;

    @Autowired
    public PythonExecutor(AppConfig appConfig) {
        path = appConfig.getPythonScriptsDirectory();
    }

    public void exec(String scriptName, String argument) {
        try {
            scriptName = path + scriptName;
            ProcessBuilder processBuilder = new ProcessBuilder("python", scriptName, argument);
            execProcess(processBuilder);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void exec(String scriptName) {
        try {
            scriptName = path + scriptName;
            ProcessBuilder processBuilder = new ProcessBuilder("python", scriptName);
            execProcess(processBuilder);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void execProcess(ProcessBuilder processBuilder) throws IOException, InterruptedException {
        Process process = processBuilder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        Logger logger = Logger.getLogger(PythonExecutor.class.getName());
        while ((line = reader.readLine()) != null) {
            logger.info(line); // 파이썬 실행 결과 출력
        }
        int exitCode = process.waitFor();
        String logMessage = "python script exit with status" + exitCode;
        logger.info(logMessage);
    }
}

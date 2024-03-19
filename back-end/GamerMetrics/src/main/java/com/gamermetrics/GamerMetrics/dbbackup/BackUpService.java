package com.gamermetrics.GamerMetrics.dbbackup;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BackUpService {

    public void backupDatabase() throws IOException, InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        String fileName = "C:\\Projects\\GamerMetrics\\back-end\\GamerMetrics\\infrastructure\\db\\backups\\" +
                "backup-" + now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth() + "-" + now.getHour() +
                "-" + now.getMinute() + "-" + now.getSecond() + ".sql";

        String backUp = "cmd /c start /wait ./infrastructure/db/backups/backupp.lnk " + fileName;

        int exitCode = Runtime.getRuntime().exec(backUp).waitFor();
        if (exitCode == 0) {
            System.out.println("Backup successful");
        } else {
            throw new RuntimeException("Backup failed");
        }
    }

    public BackUpResponse[] getAllBackUps() {
        List<BackUpResponse> backupResponses = new ArrayList<>();
        String directoryPath = "C:\\Projects\\GamerMetrics\\back-end\\GamerMetrics\\infrastructure\\db\\backups\\";

        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String fileName = file.getName();
                        if (fileName.endsWith(".sql")) {
                            backupResponses.add(BackUpResponse.builder().fileName(fileName).build());
                        }
                    }
                }
            }
        }

        return backupResponses.toArray(BackUpResponse[]::new);
    }
}

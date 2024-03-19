package com.gamermetrics.GamerMetrics.dbbackup;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.loader.ResourceEntry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/backups")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class BackUpController {

    private final BackUpService backUpService;

    @PostMapping
    public ResponseEntity<String> backupDatabase() {
        try {
            backUpService.backupDatabase();
        } catch (IOException | InterruptedException e) {
            return new ResponseEntity<>("Backup failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Backup successful", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<BackUpResponse[]> getBackup() {
        return new ResponseEntity<>(backUpService.getAllBackUps(), HttpStatus.OK);
    }

}


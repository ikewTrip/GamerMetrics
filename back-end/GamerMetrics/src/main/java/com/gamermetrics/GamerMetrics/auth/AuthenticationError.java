package com.gamermetrics.GamerMetrics.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class AuthenticationError {
    private int status;
    private String message;
    private Date timeStamp;

    public AuthenticationError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timeStamp = new Date();
    }
}

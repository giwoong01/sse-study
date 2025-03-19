package com.sse.sse_study_backend.global.config;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ServerIdProvider {

    private final String SERVER_ID;

    public ServerIdProvider() {
        this.SERVER_ID = UUID.randomUUID().toString();
    }

    public String getServerId() {
        return SERVER_ID;
    }

}

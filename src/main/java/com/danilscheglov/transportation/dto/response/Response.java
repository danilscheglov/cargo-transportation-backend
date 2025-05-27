package com.danilscheglov.transportation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class Response {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime timestamp;
    protected String message;

    public Response() {
        this.timestamp = LocalDateTime.now();
    }

    public Response(String message) {
        this();
        this.message = message;
    }
}
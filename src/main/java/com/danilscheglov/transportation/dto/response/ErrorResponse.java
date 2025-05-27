package com.danilscheglov.transportation.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ErrorResponse extends Response {
    private HttpStatus status;
    private String error;

    public ErrorResponse() {
        super();
    }

    public ErrorResponse(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.error = status.getReasonPhrase();
    }
}
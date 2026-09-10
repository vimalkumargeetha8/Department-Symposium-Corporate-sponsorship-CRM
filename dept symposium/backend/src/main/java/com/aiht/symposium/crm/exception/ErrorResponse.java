package com.aiht.symposium.crm.exception;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {
    private boolean success;
    private String message;
    private List<String> errors;
    private int statusCode;
    private LocalDateTime timestamp;

    public ErrorResponse() {
    }

    public ErrorResponse(boolean success, String message, List<String> errors, int statusCode, LocalDateTime timestamp) {
        this.success = success;
        this.message = message;
        this.errors = errors;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }

    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public static class Builder {
        private boolean success;
        private String message;
        private List<String> errors;
        private int statusCode;
        private LocalDateTime timestamp;

        public Builder success(boolean success) { this.success = success; return this; }
        public Builder message(String message) { this.message = message; return this; }
        public Builder errors(List<String> errors) { this.errors = errors; return this; }
        public Builder statusCode(int statusCode) { this.statusCode = statusCode; return this; }
        public Builder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }

        public ErrorResponse build() {
            return new ErrorResponse(success, message, errors, statusCode, timestamp);
        }
    }
}

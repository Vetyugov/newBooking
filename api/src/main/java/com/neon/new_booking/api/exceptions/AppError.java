package com.neon.new_booking.api.exceptions;

public class AppError extends Throwable {
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AppError() {
    }

    public AppError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

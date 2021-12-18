package com.agus.dot.exception;

public class ProvinceNotFoundException extends Exception {
    private int code;
    private String message;

    public ProvinceNotFoundException(int code, String message){
        super();
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

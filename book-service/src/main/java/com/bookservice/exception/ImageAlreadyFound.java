package com.bookservice.exception;

public class ImageAlreadyFound extends RuntimeException {
    public ImageAlreadyFound(String message) {
        super(message);
    }
}

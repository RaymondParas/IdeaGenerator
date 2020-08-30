package com.IdeaGenerator.IdeaGenerator.exceptionHandle;

public class RecordNotFoundException extends RuntimeException{

    public RecordNotFoundException(String message){
        super(message);
    }

    public RecordNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}

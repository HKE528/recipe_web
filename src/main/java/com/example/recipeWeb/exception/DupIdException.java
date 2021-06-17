package com.example.recipeWeb.exception;

public class DupIdException extends IllegalStateException{
    public DupIdException() {
        super();
    }

    public DupIdException(String s) {
        super(s);
    }

    public DupIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public DupIdException(Throwable cause) {
        super(cause);
    }
}

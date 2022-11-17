package jk.program.library_manager.exception;

import java.util.List;

public class InvalidWriterException extends RuntimeException{

    private List<String> errors;

    public InvalidWriterException(String message,  List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}

package jk.program.library_manager.exception;

import java.util.List;

public class InvalidBookException extends RuntimeException {

    private List<String> errors;

    public InvalidBookException(String message,  List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}

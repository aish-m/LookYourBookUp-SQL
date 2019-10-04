package ADBproject.LookYourBookUp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// This exception is thrown when there are no ratings yet for a book that the user selected to check

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoRatingsException extends RuntimeException {
    private String errorMessage;

    public NoRatingsException( String errorMessage) {
        super(String.format("%s", errorMessage));
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

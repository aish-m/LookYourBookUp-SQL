package ADBproject.LookYourBookUp.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// This exception is thrown by code when the user inputs a user id or bibNum that is  not already present in the respective table

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ForeignKeyConstraintException extends RuntimeException {
    private String errorMessage;

    public ForeignKeyConstraintException(String errorMessage) {
        super(String.format("%s", errorMessage));
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

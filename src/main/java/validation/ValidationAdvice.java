package validation;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ValidationAdvice {
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler
    public ValidationErrors handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception) {

        ValidationErrors errors = new ValidationErrors();

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            errors.addError(fieldError);
        }
        return errors;
    }
}

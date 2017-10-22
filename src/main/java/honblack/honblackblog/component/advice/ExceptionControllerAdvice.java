package honblack.honblackblog.component.advice;

import honblack.honblackblog.exception.ResourceNotFoundException;
import honblack.honblackblog.exception.UnexpectedCountException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ResourceNotFoundException.class})
    public String resourceNotFound() {
        return "error/404";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({UnexpectedCountException.class})
    public String unexpectedCount() {
        return "error/500";
    }
}

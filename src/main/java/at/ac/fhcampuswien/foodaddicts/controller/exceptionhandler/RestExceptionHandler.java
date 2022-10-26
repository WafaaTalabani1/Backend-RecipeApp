package at.ac.fhcampuswien.foodaddicts.controller.exceptionhandler;

import at.ac.fhcampuswien.foodaddicts.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorDto> handleCustomException(Exception exception) {
        String message = exception.getMessage();
        return ResponseEntity.badRequest().body(new ErrorDto(message));
    }
}

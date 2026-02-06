package ek.osnb.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFoundException(NotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemDetail pd = ProblemDetail.forStatus(status);
        pd.setTitle("Resource Not Found");
        return ResponseEntity.status(status).body(pd);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationException(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemDetail pd = ProblemDetail.forStatus(status);
        pd.setTitle("Validation Failed");
        Map<String, String> validationErrors = new HashMap<>();
        for (var error : ex.getBindingResult().getFieldErrors()) {
            validationErrors.put(error.getField(), error.getDefaultMessage());
        }
        pd.setProperty("validationErrors", validationErrors);
        return ResponseEntity.status(status).body(pd);
    }

//    CUSTOM REPONSE EXAMPLE
//    public record ApiErrorResponse(String message) {}

//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<ApiErrorResponse> handleNotFoundException(NotFoundException ex) {
//        ApiErrorResponse response = new ApiErrorResponse(ex.getMessage());
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//    }
}


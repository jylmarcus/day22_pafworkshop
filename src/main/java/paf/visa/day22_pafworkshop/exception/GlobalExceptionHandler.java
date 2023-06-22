package paf.visa.day22_pafworkshop.exception;

import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException ex,
            WebRequest request) {
        ErrorMessage errMsg = new ErrorMessage(HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        LOGGER.error(ex.getMessage());

        return new ResponseEntity<ErrorMessage>(errMsg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> handleIOException(HttpServletRequest req, Exception ex) {
        ErrorMessage errMsg = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
        new Date(),
        ex.getMessage(),
        req.getRequestURI());

        LOGGER.info("Enter handle IOException");

        return ResponseEntity.internalServerError().body(errMsg);
    }

    @ExceptionHandler(CannotGetJdbcConnectionException.class)
    public ResponseEntity<ErrorMessage> handleDBException(Exception ex, HttpServletRequest req) {
        ErrorMessage errMsg = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                req.getRequestURI());

        return new ResponseEntity<ErrorMessage>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception ex, HttpServletRequest request) {
        ErrorMessage errMsg = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(), request.getRequestURL().toString());

        LOGGER.error(ex.getMessage());

        return new ResponseEntity<ErrorMessage>(errMsg, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package tul.swiercz.thesis.bookmind.exception;

import org.hibernate.StaleStateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tul.swiercz.thesis.bookmind.dto.exception.ExceptionInfo;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static tul.swiercz.thesis.bookmind.exception.ExceptionMessages.OPTIMISTIC_LOCK;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    protected ExceptionInfo handleNotFound(Exception ex, HttpServletRequest request) {
        return mapException(ex, request);
    }

    @ExceptionHandler(value = InternalException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    protected ExceptionInfo handleInternalServerError(Exception ex, HttpServletRequest request) {
        return mapException(ex, request);
    }

    @ExceptionHandler(value = StaleStateException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    protected ExceptionInfo handleStaleStateException(Exception ex, HttpServletRequest request) {
        return mapException(new OptimisticLockException(OPTIMISTIC_LOCK, ex), request);
    }

    protected ExceptionInfo mapException(Exception ex, HttpServletRequest request) {
        ExceptionInfo exceptionInfo = new ExceptionInfo();
        exceptionInfo.setMessage(ex.getMessage());
        exceptionInfo.setTimestamp(LocalDateTime.now());
        exceptionInfo.setPath(request.getRequestURI());
        return exceptionInfo;
    }

}

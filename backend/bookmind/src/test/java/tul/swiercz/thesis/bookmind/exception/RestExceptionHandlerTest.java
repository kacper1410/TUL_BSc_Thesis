package tul.swiercz.thesis.bookmind.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tul.swiercz.thesis.bookmind.dto.exception.ExceptionInfo;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RestExceptionHandlerTest {

    private RestExceptionHandler restExceptionHandler;

    @Mock
    private Exception exception;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void initFields() {
        restExceptionHandler = new RestExceptionHandler();
    }

    @Test
    void mapException() {
        String uri = "uri";
        String message = "message";
        when(request.getRequestURI()).thenReturn(uri);
        when(exception.getMessage()).thenReturn(message);

        LocalDateTime before = LocalDateTime.now().minus(1, ChronoUnit.MILLIS);
        ExceptionInfo exceptionInfo = restExceptionHandler.mapException(exception, request);
        LocalDateTime after = LocalDateTime.now().plus(1, ChronoUnit.MILLIS);

        assertEquals(uri, exceptionInfo.getPath());
        assertEquals(message, exceptionInfo.getMessage());
        assertTrue(exceptionInfo.getTimestamp().isAfter(before));
        assertTrue(exceptionInfo.getTimestamp().isBefore(after));

    }

}

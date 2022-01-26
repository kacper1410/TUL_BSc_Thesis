package tul.swiercz.thesis.bookmind.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import tul.swiercz.thesis.bookmind.dto.exception.ExceptionInfo;
import tul.swiercz.thesis.bookmind.exception.ExceptionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String requestURI = request.getRequestURI();
        String message;
        message = requestURI.startsWith("/auth") ? ExceptionMessages.UNAUTHORIZED : ExceptionMessages.NOT_AUTHENTICATED;
        ExceptionInfo httpResponse = new ExceptionInfo(message, LocalDateTime.now(), requestURI);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(UNAUTHORIZED.value());
        OutputStream outputStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.writeValue(outputStream, httpResponse);
        outputStream.flush();
    }
}

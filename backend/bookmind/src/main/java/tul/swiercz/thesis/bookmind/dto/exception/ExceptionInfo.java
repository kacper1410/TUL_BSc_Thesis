package tul.swiercz.thesis.bookmind.dto.exception;

import java.time.LocalDateTime;

public class ExceptionInfo {

    private String message;

    private LocalDateTime timestamp;

    private String path;

    //region Accessors
    public ExceptionInfo() {
    }

    public ExceptionInfo(String message, LocalDateTime timestamp, String path) {
        this.message = message;
        this.timestamp = timestamp;
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    //endregion

}

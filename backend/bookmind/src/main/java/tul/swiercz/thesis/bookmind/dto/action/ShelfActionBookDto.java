package tul.swiercz.thesis.bookmind.dto.action;

import javax.validation.constraints.NotNull;

public class ShelfActionBookDto extends ShelfActionDto {

    private Long bookId;

    private Long connectionVersion;

    private String connectionVersionSignature;

    @NotNull
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getConnectionVersion() {
        return connectionVersion;
    }

    public void setConnectionVersion(Long connectionVersion) {
        this.connectionVersion = connectionVersion;
    }

    public String getConnectionVersionSignature() {
        return connectionVersionSignature;
    }

    public void setConnectionVersionSignature(String connectionVersionSignature) {
        this.connectionVersionSignature = connectionVersionSignature;
    }
}

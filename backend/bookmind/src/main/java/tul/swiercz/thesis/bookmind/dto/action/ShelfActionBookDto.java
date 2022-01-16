package tul.swiercz.thesis.bookmind.dto.action;

import com.sun.istack.NotNull;

public class ShelfActionBookDto extends ShelfActionDto {

    private Long bookId;

    @NotNull
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}

package tul.swiercz.thesis.bookmind.dto.action;

import com.sun.istack.NotNull;
import tul.swiercz.thesis.bookmind.dto.shelf.CreateShelf;

public class ShelfActionCreateDto extends ShelfActionDto {

    private CreateShelf createShelf;

    @NotNull
    public CreateShelf getCreateShelf() {
        return createShelf;
    }

    public void setCreateShelf(CreateShelf createShelf) {
        this.createShelf = createShelf;
    }
}

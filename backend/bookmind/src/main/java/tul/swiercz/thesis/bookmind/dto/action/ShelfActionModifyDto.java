package tul.swiercz.thesis.bookmind.dto.action;

import com.sun.istack.NotNull;
import tul.swiercz.thesis.bookmind.dto.shelf.ModifyShelf;

public class ShelfActionModifyDto extends ShelfActionDto {

    private ModifyShelf modifyShelf;

    @NotNull
    public ModifyShelf getModifyShelf() {
        return modifyShelf;
    }

    public void setModifyShelf(ModifyShelf modifyShelf) {
        this.modifyShelf = modifyShelf;
    }
}

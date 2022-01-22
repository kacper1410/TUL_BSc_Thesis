package tul.swiercz.thesis.bookmind.dto.action;

import tul.swiercz.thesis.bookmind.dto.shelf.ModifyShelf;

import javax.validation.constraints.NotNull;

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

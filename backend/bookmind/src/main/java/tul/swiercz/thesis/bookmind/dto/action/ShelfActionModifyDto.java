package tul.swiercz.thesis.bookmind.dto.action;

import tul.swiercz.thesis.bookmind.dto.shelf.ModifyShelf;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ShelfActionModifyDto extends ShelfActionDto {

    private ModifyShelf shelf;

    @NotNull
    @Valid
    public ModifyShelf getShelf() {
        return shelf;
    }

    public void setShelf(ModifyShelf shelf) {
        this.shelf = shelf;
    }
}

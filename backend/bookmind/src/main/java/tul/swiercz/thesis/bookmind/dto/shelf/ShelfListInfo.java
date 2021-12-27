package tul.swiercz.thesis.bookmind.dto.shelf;

import tul.swiercz.thesis.bookmind.dto.InfoDto;

public class ShelfListInfo extends InfoDto {

    private String name;

    //region Accessors
    public ShelfListInfo() {
    }

    public ShelfListInfo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //endregion

}

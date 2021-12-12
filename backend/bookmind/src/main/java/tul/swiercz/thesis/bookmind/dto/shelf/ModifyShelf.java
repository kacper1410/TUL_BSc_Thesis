package tul.swiercz.thesis.bookmind.dto.shelf;

public class ModifyShelf {

    private String name;

    //region Accessors
    public ModifyShelf() {
    }

    public ModifyShelf(String name) {
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

package tul.swiercz.thesis.bookmind.dto.shelf;

public class CreateShelf {

    private String name;

    //region Accessors
    public CreateShelf() {
    }

    public CreateShelf(String name) {
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

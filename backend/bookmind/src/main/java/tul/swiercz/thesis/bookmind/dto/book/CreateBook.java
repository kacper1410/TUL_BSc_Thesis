package tul.swiercz.thesis.bookmind.dto.book;

public class CreateBook {

    private String title;

    public CreateBook() {
    }

    public CreateBook(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
package tul.swiercz.thesis.bookmind.dto.book;

public class CreateBook {

    private String title;

    private String author;

    //region Accessors
    public CreateBook() {
    }

    public CreateBook(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    //endregion

}

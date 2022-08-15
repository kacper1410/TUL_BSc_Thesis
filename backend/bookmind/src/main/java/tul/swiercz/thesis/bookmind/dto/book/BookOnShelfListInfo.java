package tul.swiercz.thesis.bookmind.dto.book;

public class BookOnShelfListInfo extends BookListInfo {

    private Long connectionVersion;

    private boolean active;

    public BookOnShelfListInfo(String title, String author, Long connectionVersion) {
        super(title, author);
        this.connectionVersion = connectionVersion;
    }

    public Long getConnectionVersion() {
        return connectionVersion;
    }

    public void setConnectionVersion(Long connectionVersion) {
        this.connectionVersion = connectionVersion;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

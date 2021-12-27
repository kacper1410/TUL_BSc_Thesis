package tul.swiercz.thesis.bookmind.dto;

public abstract class InfoDto {

    private Long id;

    private Long version;

    //region Accessors
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
    //endregion

}

package tul.swiercz.thesis.bookmind.dto;

public abstract class AbstractDto {

    private Long id;

    //region Accessors
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    //endregion

}

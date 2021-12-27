package tul.swiercz.thesis.bookmind.dto;

public abstract class ModifyDto {

    private Long version;

    public ModifyDto() {
    }

    public ModifyDto(Long version) {
        this.version = version;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}

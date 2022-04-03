package tul.swiercz.thesis.bookmind.dto;

import javax.validation.constraints.NotNull;

public abstract class ModifyDto {

    private Long version;

    public ModifyDto() {
    }

    public ModifyDto(Long version) {
        this.version = version;
    }

    @NotNull
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}

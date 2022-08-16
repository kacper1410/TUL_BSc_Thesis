package tul.swiercz.thesis.bookmind.dto;

import tul.swiercz.thesis.bookmind.security.SignatureUtil;

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

    public String getVersionSignature() {
        return SignatureUtil.calcSignature(version);
    }
}

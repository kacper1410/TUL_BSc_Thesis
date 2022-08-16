package tul.swiercz.thesis.bookmind.dto.shelf;

import tul.swiercz.thesis.bookmind.security.SignatureUtil;

public class ShelfForBookListInfo extends ShelfListInfo {

    private Long connectionVersion;

    private boolean active;

    public ShelfForBookListInfo() {
    }

    public ShelfForBookListInfo(String name, Long connectionVersion, boolean active) {
        super(name);
        this.connectionVersion = connectionVersion;
        this.active = active;
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

    public String getConnectionVersionSignature() {
        return SignatureUtil.calcSignature(connectionVersion);
    }
}

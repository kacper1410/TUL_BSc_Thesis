package tul.swiercz.thesis.bookmind.dto.user;

public class AccessLevelInfo {

    private String authority;

    public AccessLevelInfo(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}

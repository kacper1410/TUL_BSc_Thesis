package tul.swiercz.thesis.bookmind.dto.user;

import tul.swiercz.thesis.bookmind.dto.InfoDto;

import java.util.List;

public class UserListInfo extends InfoDto {

    private String username;

    private String email;

    private boolean enabled;

    private List<AccessLevelInfo> authorities;

    public UserListInfo() {
    }

    public UserListInfo(String username, String email, boolean enabled, List<AccessLevelInfo> authorities) {
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<AccessLevelInfo> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<AccessLevelInfo> authorities) {
        this.authorities = authorities;
    }
}

package tul.swiercz.thesis.bookmind.domain;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="bookmind_user")
public class User extends AbstractIdDomain implements UserDetails {

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<AccessLevel> authorities;

    @ColumnDefault("false")
    private boolean enabled;

    //region Accessors
    public User() {
    }

    public User(String username, String password, String email, Set<AccessLevel> authorities, boolean enabled) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
        this.enabled = enabled;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Set<AccessLevel> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AccessLevel> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //endregion
}

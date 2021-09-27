package tul.swiercz.thesis.bookmind.domain;

import org.springframework.security.core.userdetails.UserDetails;

public abstract class AbstractUserDetails extends AbstractDomain implements UserDetails {

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

    @Override
    public boolean isEnabled() {
        return true;
    }

}

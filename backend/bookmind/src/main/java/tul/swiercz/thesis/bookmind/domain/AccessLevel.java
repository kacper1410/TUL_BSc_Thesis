package tul.swiercz.thesis.bookmind.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="bookmind_access_level")
public class AccessLevel extends AbstractDomain implements GrantedAuthority {

    private String authority;

    //region Accessors
    public AccessLevel() {
    }

    public AccessLevel(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
    //endregion

}

package tul.swiercz.thesis.bookmind.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="bookmind_access_level")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessLevel extends AbstractDomain implements GrantedAuthority {

    private String authority;

}

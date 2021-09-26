package tul.swiercz.thesis.bookmind.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="bookmind_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User extends AbstractDomain {

    private String login;
    private String password;
    private String email;
    @ManyToMany
    private List<AccessLevel> accessLevels;

}

package tul.swiercz.thesis.bookmind.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="bookmind_user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User extends AbstractUserDetails {

    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AccessLevel> authorities;

}

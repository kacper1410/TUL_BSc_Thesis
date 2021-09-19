package tul.swiercz.thesis.bookmind.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

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

}

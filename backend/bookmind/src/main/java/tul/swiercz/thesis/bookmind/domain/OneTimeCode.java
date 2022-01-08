package tul.swiercz.thesis.bookmind.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="bookmind_one_time_code")
public class OneTimeCode extends AbstractDomain {

    @Column(unique = true)
    private String code;

    @ManyToOne
    private User user;

    private LocalDateTime expirationDate;

    //region Accessors
    public OneTimeCode(String code, User user, LocalDateTime expirationDate) {
        this.code = code;
        this.user = user;
        this.expirationDate = expirationDate;
    }

    public OneTimeCode() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String url) {
        this.code = url;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
    //endregion
}

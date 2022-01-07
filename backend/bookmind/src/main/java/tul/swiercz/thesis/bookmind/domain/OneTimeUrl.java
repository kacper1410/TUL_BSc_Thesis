package tul.swiercz.thesis.bookmind.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="bookmind_one_time_url")
public class OneTimeUrl extends AbstractDomain {

    @Column(unique = true)
    private String url;

    @ManyToOne
    private User user;

    private LocalDateTime expirationDate;

    //region Accessors
    public OneTimeUrl(String url, User user, LocalDateTime expirationDate) {
        this.url = url;
        this.user = user;
        this.expirationDate = expirationDate;
    }

    public OneTimeUrl() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

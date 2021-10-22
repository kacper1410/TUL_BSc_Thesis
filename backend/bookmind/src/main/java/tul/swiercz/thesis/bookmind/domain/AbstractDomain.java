package tul.swiercz.thesis.bookmind.domain;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @ColumnDefault("0")
    private Long version;

    public Long getId() {
        return id;
    }

}

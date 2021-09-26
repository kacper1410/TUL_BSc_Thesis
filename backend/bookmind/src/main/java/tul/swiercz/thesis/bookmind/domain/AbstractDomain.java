package tul.swiercz.thesis.bookmind.domain;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    @Version
    @Column(columnDefinition = "bigint default 0")
    private Long version = 0L;

}

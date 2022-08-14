package tul.swiercz.thesis.bookmind.domain;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractVersionDomain {

    @Version
    @ColumnDefault("0")
    private Long version;

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}

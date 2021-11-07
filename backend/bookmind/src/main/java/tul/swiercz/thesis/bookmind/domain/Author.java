package tul.swiercz.thesis.bookmind.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="bookmind_author")
public class Author extends AbstractDomain {

    private String name;

    //region Accessors
    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //endregion

}

package tul.swiercz.thesis.bookmind.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="bookmind_book")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book extends AbstractDomain {

    private String title;

}

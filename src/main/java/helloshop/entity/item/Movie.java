package helloshop.entity.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("C")
public class Movie extends Item {

    private String director;
    private String actor;
}

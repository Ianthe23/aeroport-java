package domain;

import java.io.Serializable;

public class Entity<ID>  implements Serializable {

    /**
     * The id of the entity
     */
    private ID id;

    /**
     * Returns the id of the entity
     * @return id - the id of the entity
     */
    public ID getId() {
        return id;
    }

    /**
     * Sets the id of the entity
     * @param id - the new id
     */
    public void setId(ID id) {
        this.id = id;
    }
}

package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Created by AnhQuan on 8/10/2016.
 */
@Entity
public class Node extends Model {
    public String name;
    public String description;
    @ManyToOne
    public Root root;
}

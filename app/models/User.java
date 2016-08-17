package models;

import play.db.jpa.Model;

import javax.persistence.Entity;

/**
 * Created by AnhQuan on 8/14/2016.
 */
@Entity
public class User extends Model {
    public String username;
    public String password;
    public String name;
    public String address;
    public boolean isManager;

    public User() {
    }

    public User(String username, String password, String name, String address, boolean isManager) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.isManager = isManager;
    }
}

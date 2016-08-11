package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by AnhQuan on 8/11/2016.
 */
@Entity
public class DataTransfer extends Model {
    public float value;
    public Date time;
    @ManyToOne
    public Node node;
    @ManyToOne
    public Sensor sensor;

    public DataTransfer() {
    }

    public DataTransfer(float value,  Long node, Long sensor) {
        this.value = value;
        this.time = new Date();
        this.node = Node.findById(node);
        this.sensor = Sensor.findById(sensor);
    }
}

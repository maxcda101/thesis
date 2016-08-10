package models;

import play.db.jpa.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by AnhQuan on 8/7/2016.
 */
@Entity
public class Data extends Model {
    public float value;
    public Date time;
    @ManyToOne
    public Node node;
    @ManyToOne
    public Sensor sensor;
    public Data() {
    }

    public Data(float value, Date time, Node node, Sensor sensor) {
        this.value = value;
        this.time = time;
        this.node = node;
        this.sensor = sensor;
    }
    public Data(Long idNode, Long idSensor, float value) {
        this.value = value;
        this.time = new Date();
        this.node = Node.findById(idNode);
        this.sensor = Sensor.findById(idSensor);
    }
    public boolean pushData(){
        if(null==this.node||null==this.sensor){
            return false;
        }else{
            this.save();
            return true;
        }
    }
    public static List<Data> getDatas(int start, int limit){
        if(limit<=0||limit >10){
            limit=10;
        }
        if(start<0){
            start=0;
        }
        return Data.find("order by time desc").from(start).fetch(limit);
    }
}

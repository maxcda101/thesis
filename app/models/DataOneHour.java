package models;

import play.db.jpa.JPA;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by AnhQuan on 8/11/2016.
 */
@Entity
public class DataOneHour extends Model {
    public float value;
    public Date time;
    @ManyToOne
    public Node node;
    @ManyToOne
    public Sensor sensor;
    public DataOneHour(float value,  Long node, Long sensor) {
        this.value = value;
        this.time = new Date();
        this.node = Node.findById(node);
        this.sensor = Sensor.findById(sensor);
    }
    public static List<DataOneHour> getDataByDay( int day, int month, int year){
        String first=year+"-"+month+"-"+day;
        String last=year+"-"+month+"-"+(day+1);

        EntityManager em = JPA.em();
        String sql="SELECT * FROM DataOneHour where '"+first+"' <time and time < '"+last+"'";
        Query query = em.createNativeQuery(sql,DataOneHour.class);
        List<DataOneHour> result = (List<DataOneHour>) query.getResultList();
        return result;
    }
}

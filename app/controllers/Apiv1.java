package controllers;

import models.DataOneHour;
import models.DataTransfer;
import models.Node;
import models.Sensor;
import play.Logger;
import play.data.validation.Required;
import play.db.jpa.JPA;
import play.mvc.Controller;
import ultis.Data;
import ultis.DataNow;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AnhQuan on 8/18/2016.
 */
public class Apiv1 extends Controller {
    public static void getSensor(int start, int limit) {
        renderJSON(Sensor.getSensors(start, limit));
    }

    public static void getDataByDay(int day, int month, int year, @Required Long idSensor) {
        if (day == 0 || month == 0 || year == 0) {
            renderJSON("Date format exception");
        }
        renderJSON(DataOneHour.getDataByDay(day, month, year, idSensor));
    }

    public static void getMediumDataNow(@Required Long idLocation) {
        EntityManager em = JPA.em();

        List<Sensor> listSensor = Sensor.getSensors(0, 10);
        List<Node> listNode = Node.getAllNodeByLocation(idLocation);
        String result = "{";
        for (Sensor sensor : listSensor) {
            float total = 0;
            int i = 0;
            for (Node node : listNode) {
                String sql = "SELECT * FROM DataTransfer WHERE node_id=" + node.id + " and sensor_id=" + sensor.id + " order by time desc limit 1";
                Query query = em.createNativeQuery(sql, DataTransfer.class);
                total += ((DataTransfer) query.getSingleResult()).value;
                i++;
            }
            result += "\"" + sensor.name + "\":" + total / i + ",";
        }
        result = result.substring(0, result.length() - 1);
        result += "}";
        renderJSON(result);
    }

    public static void getAllNode(@Required Long idLocation) {
        renderJSON(Node.getAllNodeByLocation(idLocation));
    }

    public static void getValueByNodeNow(@Required Long idNode, @Required Long idSensor) {
        EntityManager em = JPA.em();
        String sql = "SELECT * FROM DataTransfer WHERE node_id=" + idNode + " and sensor_id=" + idSensor + " order by time desc limit 1";
        Query query = em.createNativeQuery(sql, DataTransfer.class);
        DataTransfer value = ((DataTransfer) query.getSingleResult());
        renderJSON(value);
    }

    public static void getDataNodeBySensorNow(@Required Long idLocation) {
        EntityManager em = JPA.em();
        List<Node> listNode = Node.getAllNodeByLocation(idLocation);
        List<Sensor> listSensor = Sensor.getSensors(0, 10);

        List<DataNow> listData = new ArrayList<DataNow>();
        for (Node node : listNode) {
            DataNow dataNow=new DataNow();
            dataNow.setNode(node);
            for (Sensor sensor : listSensor) {
                String sql = "SELECT * FROM DataTransfer WHERE node_id=" + node.id + " and sensor_id=" + sensor.id + " order by time desc limit 1";
                Query query = em.createNativeQuery(sql, DataTransfer.class);
                DataTransfer dataTransfer = (DataTransfer) query.getSingleResult();
                Data data=new Data(sensor, dataTransfer.value);
                dataNow.addData(data);
            }
            listData.add(dataNow);
        }
        renderJSON(listData);
    }
}

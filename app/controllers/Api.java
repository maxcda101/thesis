package controllers;

import models.Data;
import models.DataTransfer;
import models.Location;
import models.Sensor;
import play.Logger;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.db.jpa.JPA;
import play.mvc.Controller;
import play.mvc.Http;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created by AnhQuan on 02/08/2016.
 */
public class Api extends Controller {
    public static void index() {
        renderJSON("hello Api");
    }
    public static void getLocations(int start, int limit){
        renderJSON(Location.getLocations(start,limit));
    }
    public static void addLocation(@Required String name, String address, float latitude, float longitude, String description){
        if(validation.hasErrors()){
            renderJSON(validation.errorsMap());
        }
        Location location=new Location(name,address,latitude,longitude,description);
        if(location.save()!=null){
            renderJSON(location);
            Logger.info("Add loaction: ",location.name);
        }else{
            renderJSON("{\"error\":\" can not add location\"}");
            Logger.info("Error: ","can not add location");
        }
    }
    public static void pushData(@Required Long idNode, @Required Long idSensor, @Required float value){
        if (validation.hasErrors()) {
            renderJSON(validation.errorsMap());
        }
        Data data=new Data(idNode, idSensor,value);
        if(data.pushData()){
            renderJSON(data);
        }else{
            renderJSON("{\"error\":\"ID Node or Sensor not validation\"}");
            Logger.info("Error: ","ID Node or Sensor not validation");
        }
    }
    public static void getData(int start, int limit){
        renderJSON(Data.getDatas(start,limit));
    }
    public static void addSensor(@Required String name, String description){
        if (validation.hasErrors()) {
            renderJSON(validation.errorsMap());
        }
        Sensor sensor=new Sensor(name,description);
        if(null!=sensor.save()){
            renderJSON(sensor);
        }else{
            renderJSON("{\"error\":\" can not add sensor\"}");
        }
    }
    public static void getSensor(int start, int limit){
            renderJSON(Sensor.getSensors(start,limit));
    }
    public static void test(){
      //  String sql=;
        EntityManager em = JPA.em();
        Query query= em.createNativeQuery("SELECT distinct sensor_id FROM Data where '"+"2016:08:11 17:20:00"+"' < time and time < :timeOld");
        query.setParameter("timeOld",new Date(),TemporalType.DATE);
        renderJSON(query.getResultList());
    }

}

package jobs;

import models.Data;
import models.DataOneHour;
import models.DataTransfer;
import play.Logger;
import play.db.jpa.JPA;
import play.jobs.Every;
import play.jobs.Job;
import play.jobs.On;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * Created by AnhQuan on 8/11/2016.
 */
@On("0 0 0 01 * ?")//every hour h:01:ss
public class TransferOneHour extends Job{

    @Override
    public void doJob(){
        Logger.info("doJob: ",formatDate(new Date()));
        Date date;
        String timeOld,timeNew;
        Long time;
        date=new Date();
        timeNew=formatDate(date);
        time=date.getTime()-1000*3600;
        date=new Date(time);
        timeOld=formatDate(date);

        //  Data.find()
        EntityManager em = JPA.em();
        Query query= em.createNativeQuery("SELECT distinct sensor_id FROM DataTransfer where '"+timeOld+"' < time and time < ?");
        query.setParameter(1, new Date(), TemporalType.DATE);

        List<Number> listSensor=(List<Number>)query.getResultList();
        //Long a= listSensor.get(1);
        query=em.createNativeQuery("SELECT Distinct node_id FROM DataTransfer where '"+timeOld+"' <time and time < ?");
        query.setParameter(1, new Date(), TemporalType.DATE);

        List<Number> listNode=(List<Number>)query.getResultList();

        for(Number i:listSensor){
            Long idNode=i.longValue();
            for(Number j:listSensor){
                Long idSensor=j.longValue();

                query=em.createNativeQuery("SELECT * FROM DataTransfer where node_id=:idNode and sensor_id=:idSensor :timeOld<time and time <:timeNew");
            //    query.setParameter(idNode,idNode)
                List<DataTransfer> listData=DataTransfer.find("byNode_idAndSensor_id",idNode,idSensor).fetch();
                DataOneHour dataOneHour=new DataOneHour(calculateMedium(listData),idNode,idSensor);
                dataOneHour.save();
            }
        }
    }

    private  float calculateMedium(List<DataTransfer> list){
        float value=0;
        for(DataTransfer data:list){
            value+=data.value;
        }
        if(list.size()>0){
            return value/list.size();
        }else{
            return 0;
        }
    }
    private  String formatDate(Date date){
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(date);
        return currentTime;
    }
}

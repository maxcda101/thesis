package ultis;

import models.Sensor;

/**
 * Created by AnhQuan on 8/19/2016.
 */
public class Data {
    private Sensor sensor;
    private float value;

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Data() {

    }

    public Data(Sensor sensor, float value) {

        this.sensor = sensor;
        this.value = value;
    }
}

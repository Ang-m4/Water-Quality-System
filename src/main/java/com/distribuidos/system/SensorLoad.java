package com.distribuidos.system;
import com.distribuidos.model.Sensor;

public class SensorLoad {

    public static void main(String[] args) {

        Sensor sensorT = new Sensor(args[0], args[1],"config_Temp.txt", Integer.parseInt(args[2]));

        try {
            sensorT.loadConfiguration(sensorT.getConfiguration_path());
        } catch (Exception e) {
        }

        sensorT.sendMeasures();

    }
    
}

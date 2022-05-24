package com.distribuidos.system;

import com.distribuidos.model.Sensor;

public class SensorLoad {

    public static void main(String[] args) {

        Sensor sensorT = new Sensor(args[0], args[1], args[3]+".txt", Integer.parseInt(args[2]));
        sensorT.loadConfiguration();
        sensorT.sendMeasures();

    }

    

}

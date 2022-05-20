package com.distribuidos.model;

import java.io.FileWriter;
import java.io.IOException;


import org.zeromq.ZMQ;

public class MonitorReceiver extends Thread {

    private String topic;
    private ZMQ.Socket subscriber;
    private ZMQ.Socket sender;
    private Double min;
    private Double max;

    MonitorReceiver(ZMQ.Socket subscriber, String topic, Double min, Double max, ZMQ.Socket sender) {
        this.subscriber = subscriber;
        this.topic = topic;
        this.min = min;
        this.max = max;
        this.sender = sender;
    }

    @Override
    public void run() {


        this.subscriber.subscribe(this.topic.getBytes(ZMQ.CHARSET));
        
        while (true) {

            String string = subscriber.recvStr(0).trim();
            System.out.println(string);
            Double measure = Double.parseDouble(string.split(" ")[1]);
            String type = string.split(" ")[0];

            if (measure >= 0) {
                saveData(string, type);

                if (measure > max || measure < min) { // codigo Fuera de rango

                   sender.send("Alert " + measure + " " + type);
    
                }

            }

        }

    }

    public void saveData(String string, String type) {

        try {

            FileWriter file = new FileWriter("Db/" + type + ".txt", true);
            file.write(string + '\n');
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

package com.distribuidos.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.sound.midi.Receiver;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Monitor {

    private double value;
    private String database_path;
    private String type;

    private double max;
    private double min;

    ArrayList<MonitorReceiver> connections;

    public Monitor() {
    }

    public Monitor(double value, String database_path) {
        this.value = value;
        this.database_path = database_path;
    }

    public Monitor(String type, double max, double min) {

        this.type = type;
        this.max = max;
        this.min = min;
        this.connections = new ArrayList<>();

    }

    public void getMeasures(){

        try (ZContext context = new ZContext()) {

            System.out.println("Recibiendo Medidas... ");

            this.generateConnections(context);

            for (MonitorReceiver receiver : this.connections) {

                receiver.start();

            }
        }

    }

    public void generateConnections(ZContext context) {

        File doc = new File("configuration/ports.txt");
        Scanner obj;
        try {
            obj = new Scanner(doc);
            while (obj.hasNextLine()) {

                String line = obj.nextLine();
    
                if (line.contains(this.getType())) {
    
                    String port = line.split(" ")[0];
                    ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
                    subscriber.connect("tcp://localhost:" + port);
                    MonitorReceiver mr = new MonitorReceiver(subscriber,this.getType());
    
                    this.connections.add(mr);
                }
            }

            obj.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDatabase_path() {
        return database_path;
    }

    public void setDatabase_path(String database_path) {
        this.database_path = database_path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

}

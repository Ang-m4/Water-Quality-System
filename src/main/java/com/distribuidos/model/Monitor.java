package com.distribuidos.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Monitor {

    private double value;
    private String database_path;
    private String type;

    private double max;
    private double min;

    ArrayList<String> connections;

    public Monitor() {
    }

    public Monitor(double value, String database_path) {
        this.value = value;
        this.database_path = database_path;
    }

    public Monitor(String type) {

        this.type = type;
        this.connections = new ArrayList<>();

        if(type.equals("temperature")){
            this.min = 68;
            this.max = 89;
        }

        if(type.equals("ph")){
            this.min = 6;
            this.max = 8;
        }

        if(type.equals("oxygen")){
            this.min = 2;
            this.max = 11;
        }

    }

    public void getMeasures() {

        ZContext context = new ZContext();
        System.out.println("Recibiendo Medidas... ");
        updateState("Up");
        this.generateConnections(context);
    }

    public void updateState(String state){

        FileWriter file;

        try {

            file = new FileWriter("configuration/state.txt", true);
            file.write(this.getType() + " " + state +" "+ LocalTime.now() +'\n');
            file.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
        ;

    }

    public void generateConnections(ZContext context) {

        Scanner obj;

        try {

            while (true) {

                File doc = new File("configuration/ports.txt");
                obj = new Scanner(doc);

                while (obj.hasNextLine()) {

                    String line = obj.nextLine();

                    if (line.contains(this.getType())) {

                        String port = line.split(" ")[0];

                        if (!connections.contains(port)) {
                            System.out.println("New Sensor Connected !");
                            prepareSocket(context, port);
                        }
                    }

                }

                obj.close();

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void prepareSocket(ZContext context, String port) {

        ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
        subscriber.connect("tcp://localhost:" + port);
        MonitorReceiver mr = new MonitorReceiver(subscriber, this.getType(),this.getMin(),this.getMax());
        this.connections.add(port);
        mr.start();
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

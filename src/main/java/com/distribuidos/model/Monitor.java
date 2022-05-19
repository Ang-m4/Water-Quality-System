package com.distribuidos.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.naming.Context;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Monitor {

    private double value;
    private String database_path;
    private String type;

    private double max;
    private double min;
    ArrayList<ZMQ.Socket> connections;

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

    public void getMeasures() throws FileNotFoundException {

        try (ZContext context = new ZContext()) {

            System.out.println("Recibiendo Medidas... ");

            this.generateConnections(context);

            while (true) {

                for (ZMQ.Socket socket : this.connections) {
                    
                    socket.subscribe(this.getType().getBytes(ZMQ.CHARSET));
                    String string = socket.recvStr(0).trim();
                    System.out.println("Monitor: "+string);

                }
              
            }
        }

    }

    public void generateConnections(ZContext context) throws FileNotFoundException {

        File doc = new File("configuration/ports.txt");
        Scanner obj = new Scanner(doc);

        while(obj.hasNextLine()){

            String line = obj.nextLine();

            if(line.contains(this.getType())){

                String port = line.split(" ")[0];

                ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
                subscriber.connect("tcp://localhost:"+ port);
                this.connections.add(subscriber);
            }
        }
        obj.close();
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

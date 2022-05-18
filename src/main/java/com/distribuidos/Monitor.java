package com.distribuidos;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Monitor {

    private double value;
    private String database_path;
    private String type;

    private double max;
    private double min;


    public static void main(String[] args) {


        Monitor monitorT = new Monitor(args[1],10,20);
        monitorT.getMeasures();

    }

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
    }

    public void getMeasures() {

        try (ZContext context = new ZContext()) {

            System.out.println("Recibiendo Medidas... ");

            while (true) {

                ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
                subscriber.connect("tcp://localhost:5558");
                subscriber.subscribe(this.getType().getBytes(ZMQ.CHARSET));
                String string = subscriber.recvStr(0).trim();
                System.out.println(string);
            }
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

package com.distribuidos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Sensor {

    private String type;
    private double value;
    private String configuration_path;
    private int frequency;

    private double correct;
    private double incorrect;
    private double out;

    public static void main(String[] args) {

        Sensor sensorT = new Sensor(args[1], "config_Temp.txt", 300);

        try {
            sensorT.loadConfiguration(sensorT.configuration_path);
        } catch (FileNotFoundException e) {
        }

        sensorT.sendMeasures();
    }

    public Sensor() {
    }

    public Sensor(String type, String configuration_path, int frecuency) {
        this.type = type;
        this.configuration_path = configuration_path;
        this.frequency = frecuency;
    }

    public Sensor(String type, double value, String configuration_path, int frecuency) {
        this.type = type;
        this.value = value;
        this.configuration_path = configuration_path;
        this.frequency = frecuency;
    }

    public void loadConfiguration(String file) throws FileNotFoundException {

        File doc = new File("proyecto/" + file);
        Scanner obj;

        obj = new Scanner(doc);
        int i = 0;

        while (obj.hasNextLine()) {

            if (i == 0) {
                this.setCorrect(Double.parseDouble(obj.nextLine()));
            }
            if (i == 1) {
                this.setIncorrect(Double.parseDouble(obj.nextLine()));
            }
            if (i == 2) {
                this.setOut(Double.parseDouble(obj.nextLine()));
            }

            i++;
        }

        obj.close();
    }

    public void sendMeasures() {

        Random generator = new Random(System.currentTimeMillis());

        try (ZContext context = new ZContext()) {

            ZMQ.Socket publisher = context.createSocket(SocketType.PUB);

            publisher.bind("tcp://*:5558");
            publisher.bind("ipc://" + this.getType());

            while (!Thread.currentThread().isInterrupted()) {

                this.setValue(generator.nextInt(20));
                String update = String.format("%s %f", this.getType(), this.getValue());
                publisher.send(update, 0);
                System.out.println(update);

            }
        }
    }

    public double getCorrect() {
        return correct;
    }

    public void setCorrect(double correct) {
        this.correct = correct;
    }

    public double getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(double incorrect) {
        this.incorrect = incorrect;
    }

    public double getOut() {
        return out;
    }

    public void setOut(double out) {
        this.out = out;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getConfiguration_path() {
        return configuration_path;
    }

    public void setConfiguration_path(String configuration_path) {
        this.configuration_path = configuration_path;
    }

    public int getFrecuency() {
        return frequency;
    }

    public void setFrecuency(int frecuency) {
        this.frequency = frecuency;
    }

}

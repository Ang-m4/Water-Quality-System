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

public class Sensor {

    private String type;
    private double value;
    private ArrayList<Double> configuration;
    public String configuration_path;
    private int frequency;
    private String port;

    private int minValue;
    private int maxValue;

    public Sensor() {
    }

    public Sensor(String type, String port, String configuration_path, int frecuency) {
        this.type = type;
        this.configuration_path = configuration_path;
        this.frequency = frecuency;
        this.port = port;
        this.configuration = new ArrayList<>();

        if(type.equals("temperature")){
            this.minValue = 68;
            this.maxValue = 89;
        }

        if(type.equals("ph")){
            this.minValue = 6;
            this.maxValue = 8;
        }

        if(type.equals("oxygen")){
            
            this.minValue = 2;
            this.maxValue = 11;
        }

    }

    public Sensor(String type, double value, String configuration_path, int frecuency) {
        this.type = type;
        this.value = value;
        this.configuration_path = configuration_path;
        this.frequency = frecuency;
    }

    public void loadConfiguration(String file){

        File doc = new File("configuration/" + file);
        Scanner obj;

        try {
            obj = new Scanner(doc);
            while (obj.hasNextLine()) {
                String data = obj.nextLine();
                configuration.add(Double.parseDouble(data));
            }
    
            obj.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void sendMeasures() {

        try (ZContext context = new ZContext()) {

            ZMQ.Socket publisher = context.createSocket(SocketType.PUB);

            publisher.bind("tcp://*:" + this.getPort());
            this.savePort();

            System.out.println("Sending Measures...");

            while (!Thread.currentThread().isInterrupted()) {

                Thread.sleep(this.getFrecuency());

                this.setValue(percentageRandom());

                String update = String.format("%s %f %s", this.getType(), this.getValue(),LocalTime.now());
                publisher.send(update, 0);


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savePort() throws IOException {

        FileWriter file = new FileWriter("configuration/ports.txt", true);
        file.write(this.getPort() + " " + this.getType() + '\n');
        file.close();

    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
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

    public ArrayList<Double> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(ArrayList<Double> configuration) {
        this.configuration = configuration;
    }

    public int percentageRandom() {

        double rate0 = this.configuration.get(0);
        double rate1 = this.configuration.get(1);
        double rate2 = this.configuration.get(2);

        double randomNumber;
        randomNumber = Math.random();

        if (randomNumber >= 0 && randomNumber <= rate0) {
            return (int) (Math.random() * (this.maxValue - this.minValue)) + this.minValue;

        } else if (randomNumber >= rate0 / 100 && randomNumber <= rate0 + rate1) {
            return (int) (Math.random() * (this.minValue - 1));

        } else if (randomNumber >= rate0 + rate1 && randomNumber <= rate0 + rate1 + rate2) {
            return (int) ((Math.random() * (this.maxValue - this.minValue)) + this.minValue) * -1;
        }

        return -1;
    }
}

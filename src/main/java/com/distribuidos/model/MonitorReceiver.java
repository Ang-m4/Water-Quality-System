package com.distribuidos.model;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.PrintWriter;

import static java.time.temporal.ChronoUnit.NANOS;
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

            String time = LocalTime.now().toString();

            if (measure >= 0) {
                saveData(string, type);

                if (measure > max || measure < min) {

                    sender.send("Alert " + measure + " " + type + " " + time);

                }

            }

        }

    }

    public void saveData(String string, String type) {

        try {

            // -- Write on DB -- //

            String path = "Db/" + type + ".json";
            FileWriter file = new FileWriter(path, true);
            JSONObject json = new JSONObject();

            try {

                json.put("type", string.split(" ")[0]);
                json.put("measure", string.split(" ")[1]);
                json.put("time", string.split(" ")[2]);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            file.write(json.toString()+",\n");
            file.close();


            // --- BenchMark -- //

            String time = string.split(" ")[2];
            LocalTime timeSended = LocalTime.parse(time);
            LocalTime timeSaved = LocalTime.now();
            FileWriter benchmark = new FileWriter("Benchmark/db-saving-time.txt", true);
            benchmark.write(NANOS.between(timeSended, timeSaved) + "\n");
            benchmark.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}

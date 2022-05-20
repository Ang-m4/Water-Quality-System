package com.distribuidos.model;


import org.zeromq.ZMQ;

public class MonitorReceiver extends Thread {

    private String topic;
    private ZMQ.Socket subscriber;
    private Double min;
    private Double max;

    MonitorReceiver(ZMQ.Socket subscriber, String topic,Double min,Double max) {
        this.subscriber = subscriber;
        this.topic = topic;
        this.min = min;
        this.max = max;
    }

    @Override
    public void run() {

        this.subscriber.subscribe(this.topic.getBytes(ZMQ.CHARSET));
        
        while (true) {
            
            String string = subscriber.recvStr(0).trim();
            System.out.println(string);

        }

    }



}

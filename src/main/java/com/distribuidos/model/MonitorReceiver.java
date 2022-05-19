package com.distribuidos.model;

import org.zeromq.ZMQ;

public class MonitorReceiver extends Thread {

    private String topic;
    private ZMQ.Socket subscriber;

    MonitorReceiver(ZMQ.Socket subscriber, String topic) {
        this.subscriber = subscriber;
        this.topic = topic;
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

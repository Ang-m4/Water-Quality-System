package com.distribuidos.model;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class QAReceiver extends Thread{
    
    private String port;
    private String topic;

    public QAReceiver(String port){
        this.port = port;
        this.topic = "Alert";
    }

    @Override
    public void run() {
    
        ZContext context = new ZContext();
        ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);

        subscriber.connect("tcp://localhost:"+this.port);
        subscriber.subscribe(topic.getBytes(ZMQ.CHARSET));

        while(true){

            String string = subscriber.recvStr(0).trim();
            System.out.println(string);

        }

        

    }


}

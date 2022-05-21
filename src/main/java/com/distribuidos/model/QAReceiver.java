package com.distribuidos.model;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.NANOS;
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

            String time = string.split(" ")[3];
            LocalTime timeSended = LocalTime.parse(time);
            LocalTime timeReceived = LocalTime.now();

            FileWriter benchmark;

            try {

                benchmark = new FileWriter("Benchmark/alert-promp-time.txt", true);
                benchmark.write(NANOS.between(timeSended, timeReceived)+"\n");
                benchmark.close();
                
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


}

package com.distribuidos.system;
import com.distribuidos.model.QAReceiver;

public class QALoad {

    public static void main(String[] args) {

        QAReceiver QAReceiverT = new QAReceiver("3001");
        QAReceiver QAReceiverP = new QAReceiver("3002");
        QAReceiver QAReceiverO = new QAReceiver("3003");

        System.out.println("Waiting for issues...");

        QAReceiverO.start();
        QAReceiverP.start();
        QAReceiverT.start();

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

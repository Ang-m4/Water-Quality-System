package com.distribuidos.system;

import java.io.FileNotFoundException;

import com.distribuidos.model.Monitor;

public class MonitorLoad {
    
    public static void main(String[] args) {

        Monitor monitorT = new Monitor(args[0],10,20);
        
        try {
            monitorT.getMeasures();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}

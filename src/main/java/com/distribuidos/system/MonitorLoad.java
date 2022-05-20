package com.distribuidos.system;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

import com.distribuidos.model.Monitor;


public class MonitorLoad {

    
    public static void main(String[] args) {

        

        Monitor monitorT = new Monitor(args[0],10,20);
        handleCtrlC(args[0]);
        monitorT.getMeasures();
    }


    public static void handleCtrlC(String type){

        if(type.equals("ph")){
            
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() { 

                    try {
                        Thread.sleep(200);
                        FileWriter file = new FileWriter("configuration/state.txt", true);
                        file.write("ph" + " " + "Down" +" "+ LocalTime.now() +'\n');
                        file.close();
                    } catch (InterruptedException | IOException e) {
                        
                        e.printStackTrace();
                    }
                    
                }
             });

        }

        if(type.equals("temperature")){

            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() { 
                    try {

                        Thread.sleep(200);
                        FileWriter file = new FileWriter("configuration/state.txt", true);
                        file.write("temperature" + " " + "Down" +" "+ LocalTime.now() +'\n');
                        file.close();

                    } catch (InterruptedException | IOException e) {
                        
                        e.printStackTrace();
                    }
                    
                }
             });
        }

        if(type.equals("oxygen")){

            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() { 
                    try {
                        Thread.sleep(200);
                        FileWriter file = new FileWriter("configuration/state.txt", true);
                        file.write("oxygen" + " " + "Down" +" "+ LocalTime.now() +'\n');
                        file.close();
                    } catch (InterruptedException | IOException e) {
                        
                        e.printStackTrace();
                    }
                    
                }
             });
        }

    }

}

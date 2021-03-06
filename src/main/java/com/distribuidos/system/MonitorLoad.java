package com.distribuidos.system;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;

import com.distribuidos.model.Monitor;

public class MonitorLoad {

    public static void main(String[] args) {

        Monitor monitorT = new Monitor(args[0]);
        handleShutdown(args[0]);
        monitorT.getMeasures();
    }

    public static void handleShutdown(String type) {

        if (type.equals("ph")) {

            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {

                    try {
                        FileWriter file = new FileWriter("configuration/state.txt", true);
                        file.write("ph" + " " + "Down" + " " + LocalTime.now() + '\n');
                        file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });

        }

        if (type.equals("temperature")) {

            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    try {

                        FileWriter file = new FileWriter("configuration/state.txt", true);
                        file.write("temperature" + " " + "Down" + " " + LocalTime.now() + '\n');
                        file.close();

                    } catch (IOException e) {

                        e.printStackTrace();
                    }

                }
            });
        }

        if (type.equals("oxygen")) {

            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    try {

                        FileWriter file = new FileWriter("configuration/state.txt", true);
                        file.write("oxygen" + " " + "Down" + " " + LocalTime.now() + '\n');
                        file.close();
                        
                    } catch (IOException e) {

                        e.printStackTrace();
                    }

                }
            });
        }

    }

}

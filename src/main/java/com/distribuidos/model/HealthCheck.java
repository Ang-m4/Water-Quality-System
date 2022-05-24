package com.distribuidos.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HealthCheck {

    private Boolean onUse;

    public HealthCheck() {
        this.onUse = false;
    }

    public void checkMonitors() {

        try {

            Scanner obj;

            System.out.println("Waiting For Updates...");

            while (true) {

                File doc = new File("configuration/state.txt");
                obj = new Scanner(doc);

                while (obj.hasNextLine()) {

                    String line = obj.nextLine();
                    String type = line.split(" ")[0];

                    if (line.contains("Down")) {
                        
                        this.setOnUse(true);
                        Monitor extra = new Monitor(type);
                        extra.getMeasures();
                    }

                }

                obj.close();

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Boolean getOnUse() {
        return onUse;
    }

    public void setOnUse(Boolean onUse) {
        this.onUse = onUse;
    }

}

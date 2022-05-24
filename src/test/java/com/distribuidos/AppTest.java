package com.distribuidos;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.distribuidos.model.Sensor;

import org.junit.Test;

public class AppTest {

    @Test
    public void dataGeneratingTest() {

        Sensor sensor = new Sensor("temperature", "1111", "config_Temp.txt", 50);
        sensor.loadConfiguration();

        int errorV = 0;
        int correctV = 0;

        for (Double value : sensor.generateMeasures(100)) {

            if (value < 0) {
                errorV++;
            }

            if (value > sensor.getMinValue()) {
                correctV++;
            }

        }

        assertTrue(Math.abs(errorV - 10) < 3 && Math.abs(correctV - 60) < 5);
    }

    @Test
    public void QAAlertTest() {

        Boolean check = true;

        File doc = new File("Test/QAtest.txt");
        Scanner obj;

        try {
            obj = new Scanner(doc);
            while (obj.hasNextLine()) {

                String data = obj.nextLine();
                Double value = Double.parseDouble(data.split(" ")[0]);

                if (data.split(" ")[1].equals("temperature")) {

                    if (value > 68) {
                        check = false;
                    }

                }

                if (data.split(" ")[1].equals("oxygen")) {

                    if (value > 2) {
                        check = false;
                    }

                }

                if (data.split(" ")[1].equals("ph")) {

                    if (value > 6) {
                        check = false;
                    }

                }

            }

            obj.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assertTrue(check);

    }

}

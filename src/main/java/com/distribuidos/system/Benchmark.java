package com.distribuidos.system;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Benchmark {

    public static void main(String[] args) {

        doDdBenchmark();
        doAlertBenchmark();
    }

    public static void doAlertBenchmark() {

        try {
            Scanner obj;
            File doc = new File("Benchmark/alert-promp-time.txt");
            obj = new Scanner(doc);

            long timeElapsed = 0;
            int lines = 0;

            while (obj.hasNextLine()) {

                timeElapsed = timeElapsed + Long.parseLong(obj.nextLine());
                lines++;

            }

            System.out.println("The average alert promp time is: " + timeElapsed / lines +" NanoSeconds");
            obj.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void doDdBenchmark() {

        try {

            Scanner obj;
            File doc = new File("Benchmark/db-saving-time.txt");
            obj = new Scanner(doc);

            long timeElapsed = 0;
            int lines = 0;

            while (obj.hasNextLine()) {

                timeElapsed = timeElapsed + Long.parseLong(obj.nextLine());
                lines++;
            }

            System.out.println("The average Saving Data time is: " + timeElapsed / lines +" NanoSeconds");
            obj.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}

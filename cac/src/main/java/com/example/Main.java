

package com.example;

import com.opencsv.CSVReader;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try (CSVReader reader = new CSVReader(new FileReader("C:\\Users\\sabya\\OneDrive\\Documents\\3rd trimester\\java\\cac\\src\\main\\resources\\EPL DATA.csv"))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // Process each line of the CSV dataset
                for (String value : nextLine) {
                    System.out.print(value + " \t");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package com.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.inference.TestUtils;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StatisticalAnalysis {

    private static List<Double> fetchData(String csvFile, String columnName) throws IOException {
        List<Double> data = new ArrayList<>();
        try (Reader reader = new FileReader(csvFile);
             CSVParser csv = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : csv) {
                String valueStr = record.get(columnName);
                // Remove commas from the string if present
                valueStr = valueStr.replaceAll(",", "");
                double value = Double.parseDouble(valueStr);
                data.add(value);
            }
        }
        return data;
    }

    public static void main(String[] args) {
        String csvFile = "C:\\Users\\sabya\\OneDrive\\Documents\\3rd trimester\\java\\cac\\src\\main\\resources\\EPL DATA.csv"; // Path to your CSV file
        Scanner scanner = new Scanner(System.in);

        try {
            boolean exit = false;
            while (!exit) {
                System.out.println("");
                System.out.println("1. Mean");
                System.out.println("------------------------------");
                System.out.println("2. Median");
                System.out.println("------------------------------");
                System.out.println("3. Mode");
                System.out.println("------------------------------");
                System.out.println("4. Standard Deviation");
                System.out.println("------------------------------");
                System.out.println("5. Range");
                System.out.println("------------------------------");
                System.out.println("6. Interquartile Range");
                System.out.println("------------------------------");
                System.out.println("7. Coefficient of Variation");
                System.out.println("------------------------------");
                System.out.println("8. Skewness");
                System.out.println("------------------------------");
                System.out.println("9. Kurtosis");
                System.out.println("------------------------------");
                System.out.println("10. Correlation");
                System.out.println("------------------------------");
                System.out.println("11. Hypothesis Testing");
                System.out.println("------------------------------");
                System.out.println("12. Exit");

                System.out.print("Enter your choice:\n ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        calculateMean(fetchData(csvFile, "current_value"));
                        break;
                    case 2:
                        calculateMedian(fetchData(csvFile, "current_value"));
                        break;
                    case 3:
                        calculateMode(fetchData(csvFile, "current_value"));
                        break;
                    case 4:
                        calculateStandardDeviation(fetchData(csvFile, "current_value"));
                        break;
                    case 5:
                        calculateRange(fetchData(csvFile, "current_value"));
                        break;
                    case 6:
                        calculateInterquartileRange(fetchData(csvFile, "current_value"));
                        break;
                    case 7:
                        calculateCoefficientOfVariation(fetchData(csvFile, "current_value"));
                        break;
                    case 8:
                        calculateSkewness(fetchData(csvFile, "current_value"));
                        break;
                    case 9:
                        calculateKurtosis(fetchData(csvFile, "current_value"));
                        break;
                    case 10:
                        calculateCorrelation(fetchData(csvFile, "winger"), fetchData(csvFile, "assists"));
                        break;
                    case 11:
                        performHypothesisTesting(fetchData(csvFile, "winger"), fetchData(csvFile, "assists"));
                        break;
                    case 12:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice! Please enter a number between 1 and 12.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Double calculateMean(List<Double> data) {
        double sum = 0;
        for (double value : data) {
            sum += value;
        }
        double mean = sum / data.size();
        System.out.println("Mean: " + mean);

        return mean;
    }


    public static Double calculateMedian(List<Double> data) {
        // Sort the list
        Collections.sort(data);
        double median;
        int size = data.size();
        if (size % 2 == 0) {
            median = (data.get(size / 2 - 1) + data.get(size / 2)) / 2.0;
        } else {
            median = data.get(size / 2);
        }
        System.out.println("Median: " + median);

        return median;
    }


    public static Double calculateMode(List<Double> data) {
        Map<Double, Integer> frequencyMap = new HashMap<>();
        for (double value : data) {
            frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
        }
        double mode = 0;
        int maxFrequency = 0;
        for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                mode = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }
        System.out.println("Mode: " + mode);

        return mode;
    }


    public static Double calculateStandardDeviation(List<Double> data) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (double value : data) {
            stats.addValue(value);
        }
        System.out.println("Standard Deviation: " + stats.getStandardDeviation());

        return stats.getStandardDeviation();
    }


    public static void calculateRange(List<Double> data) {
        double min = Collections.min(data);
        double max = Collections.max(data);
        double range = max - min;
        System.out.println("Range: " + range);
    }


    public static void calculateInterquartileRange(List<Double> data) {
        Collections.sort(data);
        int size = data.size();
        double lowerQuartile, upperQuartile;
        if (size % 2 == 0) {
            lowerQuartile = calculateMedian(data.subList(0, size / 2));
            upperQuartile = calculateMedian(data.subList(size / 2, size));
        } else {
            lowerQuartile = calculateMedian(data.subList(0, size / 2));
            upperQuartile = calculateMedian(data.subList(size / 2 + 1, size));
        }
        double interquartileRange = upperQuartile - lowerQuartile;
        System.out.println("Interquartile Range: " + interquartileRange);
    }


    public static void calculateCoefficientOfVariation(List<Double> data) {
        double mean = calculateMean(data);
        double standardDeviation = calculateStandardDeviation(data);
        double coefficientOfVariation = (standardDeviation / mean) * 100.0;
        System.out.println("coefficientOfVariation:" + coefficientOfVariation);
    }


    private static void calculateSkewness(List<Double> data) {
        double mean = calculateMean(data);
        double standardDeviation = calculateStandardDeviation(data);
        double skewness = 0;
        for (double value : data) {
            skewness += Math.pow((value - mean) / standardDeviation, 3);
        }
        skewness /= data.size();
        System.out.println("skewness:" + skewness);
    }

    private static void calculateKurtosis(List<Double> data) {
        double mean = calculateMean(data);
        double standardDeviation = calculateStandardDeviation(data);
        double kurtosis = 0;
        for (double value : data) {
            kurtosis += Math.pow((value - mean) / standardDeviation, 4);
        }
        kurtosis = (kurtosis / data.size()) - 3;
        System.out.println("kurtosis:" + kurtosis) ;
    }

    private static void calculateCorrelation(List<Double> data1, List<Double> data2) {
        // Convert lists to arrays
        double[] array1 = data1.stream().mapToDouble(Double::doubleValue).toArray();
        double[] array2 = data2.stream().mapToDouble(Double::doubleValue).toArray();
        
        // Compute Pearson's correlation coefficient
        PearsonsCorrelation correlation = new PearsonsCorrelation();
        double correlationCoefficient = correlation.correlation(array1, array2);
        
        System.out.println("Correlation coefficient between 'winger' and 'assists': " + correlationCoefficient);
    }
    public static void performHypothesisTesting(List<Double> wingerData, List<Double> assistsData) {
        double[] wingerArray = wingerData.stream().mapToDouble(Double::doubleValue).toArray();
        double[] assistsArray = assistsData.stream().mapToDouble(Double::doubleValue).toArray();

        // Perform independent t-test
        double pValue = TestUtils.tTest(wingerArray, assistsArray);

        // Set significance level
        double significanceLevel = 0.05;

        // Print results
        System.out.println("Hypothesis Testing Results:");
        System.out.println("Null Hypothesis (H0): There is no significant difference in assists between players who play as wingers and those who do not.");
        System.out.println("Alternative Hypothesis (H1): There is a significant difference in assists between players who play as wingers and those who do not.");
        System.out.println("Significance Level: " + significanceLevel);
        System.out.println("p-value: " + pValue);

        // Compare p-value with significance level
        if (pValue < significanceLevel) {
            System.out.println("Result: Reject Null Hypothesis");
            System.out.println("Conclusion: There is enough evidence to suggest a significant difference in assists between players who play as wingers and those who do not.");
        } else {
            System.out.println("Result: Fail to Reject Null Hypothesis");
            System.out.println("Conclusion: There is not enough evidence to suggest a significant difference in assists between players who play as wingers and those who do not.");
        }
    }
    }

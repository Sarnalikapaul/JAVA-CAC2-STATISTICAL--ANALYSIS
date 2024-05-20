


package com.example;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class DataVisualization {

    public static void main(String[] args) {
        String csvFile = "C:\\Users\\sabya\\OneDrive\\Documents\\3rd trimester\\java\\cac\\src\\main\\resources\\EPL DATA.csv";
        String line = "";
        String csvSplitBy = ",";
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                if (data.length >= 22) { // Ensure there are at least 22 columns
                    try {
                        String position = data[3]; // Player position
                        String playerName = data[2]; // Player name
                        double goals = Double.parseDouble(data[7]); // Goals scored (as double)
    double assists = Double.parseDouble(data[8]); // Assists provided (as double)
                        double currentValue = Double.parseDouble(data[17]); // Current value
                        System.out.println("Player: " + playerName + ", Goals: " + goals + ", Assists: " + assists);

                        pieDataset.setValue(position, currentValue); // Set the current value for the corresponding player position
                        barDataset.addValue(goals, "Goals", playerName); // Add goals for the corresponding player
                        barDataset.addValue(assists, "Assists", playerName); // Add assists for the corresponding player
                    } catch (NumberFormatException e) {
                        // Handle non-integer values in the columns
                        System.err.println("Invalid data format for row: " + line);
                    }
                } else {
                    // Handle rows with insufficient data
                    System.err.println("Incomplete data for row: " + line);
                }
            }
        } catch (IOException e) {
            // Handle file IO errors
            e.printStackTrace();
        }

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Player Position Analysis",
                pieDataset,
                true,
                true,
                false);

        pieChart.setBackgroundPaint(Color.white);

        JFreeChart barChart = ChartFactory.createBarChart(
        "Player Performance Analysis",
        "Player",
        "Goals",
        barDataset,
        PlotOrientation.VERTICAL,
        true,
        true,
        false);


        barChart.setBackgroundPaint(Color.white);

        JFrame frame = new JFrame(" Analysis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Select Chart");
        JMenuItem pieMenuItem = new JMenuItem("Pie Chart");
        JMenuItem barMenuItem = new JMenuItem("Bar Graph");
        menu.add(pieMenuItem);
        menu.add(barMenuItem);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
        
        // Add both chart panels to the frame initially
        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        pieChartPanel.setPreferredSize(new java.awt.Dimension(400, 300));
        frame.add(pieChartPanel);
        
        ChartPanel barChartPanel = new ChartPanel(barChart);
        barChartPanel.setPreferredSize(new java.awt.Dimension(400, 300));
        frame.add(barChartPanel);
        
        // Set layout to display both charts side by side
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));

        pieMenuItem.addActionListener(e -> {
            barChartPanel.setVisible(false);
            pieChartPanel.setVisible(true);
        });

        barMenuItem.addActionListener(e -> {
            pieChartPanel.setVisible(false);
            barChartPanel.setVisible(true);
        });

        frame.pack();
        frame.setVisible(true);
    }
}

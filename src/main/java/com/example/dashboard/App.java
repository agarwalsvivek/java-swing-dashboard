package com.example.dashboard;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::createAndShow);
    }

    private static void createAndShow() {
        useNimbusLookAndFeel();

        JFrame frame = new JFrame("Stock Ticker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // ---- Header ----
        JLabel title = new JLabel("Stock Ticker (simulated prices)", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        title.setBorder(BorderFactory.createEmptyBorder(16, 16, 0, 16));
        frame.add(title, BorderLayout.NORTH);

        // ---- Table ----
        StockTableModel model = new StockTableModel();
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);   // click a column header to sort
        table.setRowHeight(24);

        JPanel center = new JPanel(new BorderLayout());
        center.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
        center.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(center, BorderLayout.CENTER);

        // ---- Footer ----
        JLabel lastUpdate = new JLabel();
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBorder(BorderFactory.createEmptyBorder(0, 16, 12, 16));
        footer.add(lastUpdate, BorderLayout.EAST);
        frame.add(footer, BorderLayout.SOUTH);

        // ---- Data updates ----
        QuoteSimulator simulator = new QuoteSimulator(startingPrices());
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");

        Runnable refresh = () -> {
            model.setQuotes(simulator.nextQuotes());
            lastUpdate.setText("Last update: " + LocalTime.now().format(fmt));
        };
        refresh.run();   // initial load

        // Runs on the EDT every second, so updating the model here is safe
        Timer timer = new Timer(1000, e -> refresh.run());
        timer.start();

        frame.setSize(640, 360);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /** Arbitrary starting values for the demo, not real market prices. */
    private static Map<String, Double> startingPrices() {
        Map<String, Double> prices = new LinkedHashMap<>();
        prices.put("AAPL", 200.00);
        prices.put("MSFT", 400.00);
        prices.put("GOOG", 150.00);
        prices.put("AMZN", 180.00);
        prices.put("TSLA", 250.00);
        return prices;
    }

    private static void useNimbusLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }
        } catch (Exception ignored) {
            // fall back to the default look and feel
        }
    }
}
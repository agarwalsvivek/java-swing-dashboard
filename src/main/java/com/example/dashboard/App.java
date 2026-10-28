package com.example.dashboard;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class App {

    private static int clicks = 0;   // only touched on the Swing thread

    public static void main(String[] args) {
        // All Swing UI work must happen on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(App::createAndShow);
    }

    private static void createAndShow() {
        useNimbusLookAndFeel();

        JFrame frame = new JFrame("Hello Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // ---- Header ----
        JLabel title = new JLabel("Hello, Dashboard", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 24f));
        title.setBorder(BorderFactory.createEmptyBorder(16, 16, 0, 16));
        frame.add(title, BorderLayout.NORTH);

        // ---- Cards ----
        JLabel statusValue = new JLabel("Running");
        JLabel javaValue = new JLabel(System.getProperty("java.version"));
        JLabel clicksValue = new JLabel("0");

        JPanel cards = new JPanel(new GridLayout(1, 3, 12, 12));
        cards.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        cards.add(card("Status", statusValue));
        cards.add(card("Java version", javaValue));
        cards.add(card("Clicks", clicksValue));
        frame.add(cards, BorderLayout.CENTER);

        // ---- Footer: button + live clock ----
        JButton clickMe = new JButton("Click me");
        clickMe.addActionListener(e -> clicksValue.setText(String.valueOf(++clicks)));

        JLabel clock = new JLabel();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");

        // javax.swing.Timer fires on the EDT, so updating UI here is safe.
        // This is the same pattern a stock ticker will use later.
        Timer timer = new Timer(1000, e -> clock.setText("Time: " + LocalTime.now().format(fmt)));
        timer.setInitialDelay(0);
        timer.start();

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBorder(BorderFactory.createEmptyBorder(0, 16, 12, 16));
        footer.add(clickMe, BorderLayout.WEST);
        footer.add(clock, BorderLayout.EAST);
        frame.add(footer, BorderLayout.SOUTH);

        frame.setSize(640, 300);
        frame.setLocationRelativeTo(null);   // center on screen
        frame.setVisible(true);
    }

    private static JPanel card(String heading, JLabel value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(heading));
        value.setHorizontalAlignment(SwingConstants.CENTER);
        value.setFont(value.getFont().deriveFont(Font.BOLD, 18f));
        panel.add(value, BorderLayout.CENTER);
        return panel;
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
package com.example.dashboard;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class QuoteSimulator {

    private final Map<String, Double> openPrices = new LinkedHashMap<>();
    private final Map<String, Double> currentPrices = new LinkedHashMap<>();

    public QuoteSimulator(Map<String, Double> startingPrices) {
        openPrices.putAll(startingPrices);
        currentPrices.putAll(startingPrices);
    }

    /** Moves each price randomly by up to ±1% and returns the new quotes. */
    public List<StockQuote> nextQuotes() {
        List<StockQuote> quotes = new ArrayList<>();
        for (var entry : currentPrices.entrySet()) {
            String symbol = entry.getKey();
            double movePct = ThreadLocalRandom.current().nextDouble(-0.01, 0.01);
            double newPrice = round2(entry.getValue() * (1 + movePct));
            entry.setValue(newPrice);

            double change = round2(newPrice - openPrices.get(symbol));
            quotes.add(new StockQuote(symbol, newPrice, change));
        }
        return quotes;
    }

    private static double round2(double value) {
        return Math.round(value * 100) / 100.0;
    }
}
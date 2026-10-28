package com.example.dashboard;

public record StockQuote(String symbol, double price, double change) {

    public double changePercent() {
        double previous = price - change;
        return previous == 0 ? 0 : (change / previous) * 100;
    }
}
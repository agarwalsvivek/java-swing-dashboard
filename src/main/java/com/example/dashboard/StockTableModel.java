package com.example.dashboard;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class StockTableModel extends AbstractTableModel {

    private static final String[] COLUMNS = {"Symbol", "Price", "Change", "Change %"};

    private List<StockQuote> quotes = new ArrayList<>();

    /** Must be called on the Swing thread (EDT). */
    public void setQuotes(List<StockQuote> newQuotes) {
        boolean sameRows = newQuotes.size() == quotes.size();
        quotes = new ArrayList<>(newQuotes);

        if (sameRows && !quotes.isEmpty()) {
            fireTableRowsUpdated(0, quotes.size() - 1);   // keeps the user's row selection
        } else {
            fireTableDataChanged();                       // full refresh
        }
    }

    @Override
    public int getRowCount() {
        return quotes.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    @Override
    public Class<?> getColumnClass(int column) {
        // Tells JTable how to align and sort: numbers right-aligned, text left
        return column == 0 ? String.class : Double.class;
    }

    @Override
    public Object getValueAt(int row, int column) {
        StockQuote q = quotes.get(row);
        return switch (column) {
            case 0 -> q.symbol();
            case 1 -> q.price();
            case 2 -> q.change();
            case 3 -> q.changePercent();
            default -> throw new IllegalArgumentException("Unknown column: " + column);
        };
    }
}
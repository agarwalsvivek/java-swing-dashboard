# Java Swing Dashboard

A small desktop stock ticker built with Java Swing, written as a learning project. The prices are **simulated**, not real market data.

## What it does

The app opens a window with a table of five stocks (AAPL, MSFT, GOOG, AMZN, TSLA):

| Column       | Meaning                                       |
|--------------|-----------------------------------------------|
| **Symbol**   | Ticker symbol                                 |
| **Price**    | Current simulated price                       |
| **Change**   | Price change since the app started            |
| **Change %** | That change as a percentage of the start price |

- Prices move randomly by up to ±1% every second.
- Click a column header to sort by that column.
- When the data refreshes, your row selection stays where it was.
- The footer shows the time of the last update.

Starting prices are arbitrary demo values. The window uses the Nimbus look and feel when it's available, and the system default otherwise.

## Requirements

- JDK 21 or newer
- A graphical desktop. On WSL2 this means WSLg, which Windows 11 includes.

You don't need to install Gradle. The included wrapper (`gradlew`) downloads it the first time you build.

## Running

```bash
./gradlew run
```

On Windows (outside WSL):

```bat
gradlew.bat run
```

To build without running:

```bash
./gradlew build
```

## Project structure

```
src/main/java/com/example/dashboard/
├── App.java              # Entry point: builds the window and starts the 1-second timer
├── StockQuote.java       # Record holding symbol, price and change; computes change %
├── QuoteSimulator.java   # Produces the next set of randomly moved prices
└── StockTableModel.java  # Table model that feeds quotes to the JTable
```

The data flows like this:

```
Timer (every 1s, on EDT)
  → QuoteSimulator.nextQuotes()
  → StockTableModel.setQuotes(...)
  → JTable repaints
```

## Swing concepts used

- **Event Dispatch Thread (EDT)**: all UI is created via `SwingUtilities.invokeLater`, because Swing is not thread-safe.
- **`javax.swing.Timer`**: fires its callback on the EDT, so the table model can be updated without extra synchronization.
- **`AbstractTableModel`**: separates the data from the `JTable` that displays it.
  - `getColumnClass` tells the table which columns are numbers. Numbers are then right-aligned and sorted numerically.
  - `fireTableRowsUpdated` is used instead of `fireTableDataChanged` when the row count is unchanged, so the user's selection is kept.
- **Row sorting**: `table.setAutoCreateRowSorter(true)` makes the column headers sortable.
- **Layout managers**: `BorderLayout` arranges the header, table and footer.
- **Records**: `StockQuote` is a Java `record`.

## Roadmap

- [x] Live (simulated) stock ticker table
- [ ] Format prices and percentages to two decimals
- [ ] Colour gains green and losses red with a custom cell renderer
- [ ] Price history chart
- [ ] Fetch real quotes from a market data API, off the EDT using `SwingWorker`

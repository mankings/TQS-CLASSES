package mankings.tqs;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    private List<Stock> stocks;
    private IStockmarketService stockmarket;

    public StocksPortfolio(IStockmarketService stockmarket) {
        this.stocks = new ArrayList<>();
        this.stockmarket = stockmarket;
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }

    public double getTotalValue() {
        double total = 0;
        for (Stock s : stocks)
            total += s.getQuantity() * stockmarket.lookUpPrice(s.getLabel());
        return total;
    }
}

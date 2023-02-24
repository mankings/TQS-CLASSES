package mankings.tqs;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StockTests {
    @Mock
    IStockmarketService stockmarket;

    @InjectMocks
    StocksPortfolio portfolio;

    @Test
    public void testGetTotalValue() {
        when(stockmarket.lookUpPrice("MANK")).thenReturn(5.0);
        when(stockmarket.lookUpPrice("INGS")).thenReturn(3.0);

        portfolio.addStock(new Stock("MANK", 4));
        portfolio.addStock(new Stock("INGS", 2));

        assertEquals(26, portfolio.getTotalValue());
    }
}

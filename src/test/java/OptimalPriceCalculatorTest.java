import order.Order;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static order.Orders.buy;
import static order.Orders.sell;
import static org.testng.Assert.assertEquals;

/**
 * Created by takoe on 18.04.17.
 */
public class OptimalPriceCalculatorTest {

    private OptimalPriceCalculator calculator;

    @BeforeMethod
    public void reset() {
        calculator = new OptimalPriceCalculator();
    }

    @Test(dataProvider = "samples")
    public void testSamples(Iterable<Order> orders, String expected) {
        orders.forEach(calculator::place);
        assertEquals(calculator.calculate().toString(), expected);
    }

    @DataProvider(name = "samples")
    public static Object[][] samples() {
        return new Object[][]{
                {
                        Arrays.asList(
                                buy(100, new BigDecimal("10.00")),
                                sell(150, new BigDecimal("10.10"))
                        ),
                        "0 n/a"
                },
                {
                        Arrays.asList(
                                buy(100, new BigDecimal("15.40")),
                                buy(100, new BigDecimal("15.30")),
                                sell(150, new BigDecimal("15.30"))
                        ),
                        "150 15.30"
                }
        };
    }

}
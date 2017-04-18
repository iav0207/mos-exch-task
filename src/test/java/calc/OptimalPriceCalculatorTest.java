package calc;

import order.Order;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static calc.OptimalPriceCalculatorTestData.largeSet;
import static org.testng.Assert.assertEquals;

public class OptimalPriceCalculatorTest {

    private OptimalPriceCalculator calculator;

    @BeforeMethod
    public void reset() {
        calculator = new OptimalPriceCalculator();
    }

    @Test(dataProvider = "samples", dataProviderClass = OptimalPriceCalculatorTestData.class)
    public void testSamples(Iterable<Order> orders, String expected) {
        runTest(orders, expected);
    }

    @Test(dataProvider = "extra", dataProviderClass = OptimalPriceCalculatorTestData.class)
    public void testExtra(Iterable<Order> orders, String expected) {
        runTest(orders, expected);
    }

    private void runTest(Iterable<Order> orders, String expected) {
        orders.forEach(calculator::place);
        assertEquals(calculator.calculate().toString(), expected);
    }

    @Test
    public void testPerformance() {
        long tic = now();
        largeSet().forEach(calculator::place);
        System.out.println(calculator.calculate());
        long toc = now();

        System.out.printf("Time elapsed: %d ms\n", (toc - tic));
    }

    private static long now() {
        return System.currentTimeMillis();
    }

}
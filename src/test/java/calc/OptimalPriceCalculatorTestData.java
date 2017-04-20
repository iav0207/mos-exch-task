package calc;

import order.Order;
import org.testng.annotations.DataProvider;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static order.Orders.buy;
import static order.Orders.sell;

public class OptimalPriceCalculatorTestData {

    private static Random random = ThreadLocalRandom.current();

    @DataProvider(name = "samples")
    public static Object[][] samples() {
        return new Object[][]{
                {
                        Arrays.asList(
                                buy(100, price("10.00")),
                                sell(150, price("10.10"))
                        ),
                        "0 n/a"
                },
                {
                        Arrays.asList(
                                buy(100, price("15.40")),
                                buy(100, price("15.30")),
                                sell(150, price("15.30"))
                        ),
                        "150 15.30"
                }
        };
    }

    @DataProvider(name = "extra")
    public static Object[][] extra() {
        return new Object[][] {
                {
                        Arrays.asList(
                                buy(1, price("10.00")),
                                sell(1, price("10.01"))
                        ),
                        "0 n/a"
                },
                {
                        Arrays.asList(
                                buy(1, price("10.00")),
                                sell(1, price("10.00"))
                        ),
                        "1 10.00"
                },
                {
                        Arrays.asList(
                                buy(300, price("10.00")),
                                sell(1, price("10.00")),
                                sell(1, price("10.00"))
                        ),
                        "2 10.00"
                },
                {
                        Arrays.asList(
                                buy(1, price("10.00")),
                                buy(1, price("20.00")),
                                buy(1, price("30.00")),
                                sell(1, price("10.00")),
                                sell(1, price("20.00")),
                                sell(1, price("30.00"))
                        ),
                        "2 20.00"
                },
                {
                        Arrays.asList(
                                buy(1, price("10.00")),
                                buy(1, price("20.00")),
                                buy(1, price("29.50")),
                                buy(1, price("31.00")),
                                sell(1, price("10.00")),
                                sell(1, price("20.00")),
                                sell(1, price("30.00")),
                                sell(1, price("32.00"))
                        ),
                        "2 24.75"   // случай с усреднением двух корректных оптимальных цен
                }
        };
    }

    static Stream<Order> largeSet() {
        return Stream.generate(OptimalPriceCalculatorTestData::randomOrder).limit(1_000_000);
    }

    private static Order randomOrder() {

        return random.nextBoolean() ? buy(intFromOneTo(1000), new BigDecimal(intFromOneTo(100)))
                : sell(intFromOneTo(1000), new BigDecimal(intFromOneTo(100)));
    }

    private static int intFromOneTo(int endInclusive) {
        return 1 + random.nextInt(endInclusive - 1);
    }

    private static BigDecimal price(String s) {
        return new BigDecimal(s);
    }

}

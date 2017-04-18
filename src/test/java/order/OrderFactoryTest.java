package order;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

public class OrderFactoryTest {

    private OrderFactory factory = new OrderFactory();

    @Test(dataProvider = "valid")
    public void shouldCreateOrderOfSpecifiedTypeIfInputIsValid(Integer amount, BigDecimal price) {
        assertEquals(factory.buy(amount, price).getType(), OrderType.BUY);
        assertEquals(factory.sell(amount, price).getType(), OrderType.SELL);
    }

    @Test(dataProvider = "invalid", expectedExceptions = OrderValueException.class)
    public void shouldBuyThrowExceptionIfInputIsInvalid(Integer amount, BigDecimal price) {
        factory.buy(amount, price);
    }

    @Test(dataProvider = "invalid", expectedExceptions = OrderValueException.class)
    public void shouldSellThrowExceptionIfInputIsInvalid(Integer amount, BigDecimal price) {
        factory.sell(amount, price);
    }

    @Test(dataProvider = "nulls", expectedExceptions = NullPointerException.class)
    public void shouldBuyThrowNpeIfAnyArgumentIsNull(Integer amount, BigDecimal price) {
        factory.buy(amount, price);
    }

    @Test(dataProvider = "nulls", expectedExceptions = NullPointerException.class)
    public void shouldSellThrowNpeIfAnyArgumentIsNull(Integer amount, BigDecimal price) {
        factory.sell(amount, price);
    }

    @DataProvider(name = "valid")
    public static Object[][] valid() {
        return new Object[][] {
                {1,             price("1.00")           },
                {1_000,         price("100.00")         },
                {1_000,         price("100.00")         },
                {321,           price("12.3215146548")  }
        };
    }

    @DataProvider(name = "invalid")
    public static Object[][] invalid() {
        return new Object[][] {

                // invalid amount
                {-1,            price("1.00")           },
                {0,             price("1.00")           },
                {1_001,         price("1.00")           },
                {2_000,         price("1.00")           },


                // invalid price
                {1,             price("0.99")           },
                {1,             price("0.999999999")    },
                {1,             price("100.00000001")   },
                {1,             price("-50.0")          },
                {1,             price("-50.0")          }
        };
    }

    @DataProvider(name = "nulls")
    public static Object[][] nulls() {
        return new Object[][] {
                {null,          price("1.00")           },
                {1,             null                    }
        };
    }

    private static BigDecimal price(String s) {
        return new BigDecimal(s);
    }

}
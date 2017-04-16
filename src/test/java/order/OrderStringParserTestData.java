package order;

import org.testng.annotations.DataProvider;

/**
 * Created by takoe on 16.04.17.
 */
public class OrderStringParserTestData {

    @DataProvider(name = "valid")
    public static Object[][] valid() {
        return new Object[][] {
                {"B 10 10.0",                       OrderType.BUY},
                {"B 032154688 10.012349862348",     OrderType.BUY},
                {"S 10 10.0",                       OrderType.SELL},
        };
    }

    @DataProvider(name = "invalid")
    public static Object[][] invalid() {
        return new Object[][] {
                {"B A 4",               null},
                {"B 5 A",               null},
                {"B -10 10.0",          null},
                {"B 10 10.0 AAAAAA",    null},
                {"B 10 -10.0",          null},
                {"8 10 10.0",           null},
                {"a 10 10.0",           null},
                {"A 10 10.0 0",         null},
                {"s 10 10.0",           null}
        };
    }

}

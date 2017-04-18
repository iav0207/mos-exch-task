package order;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.fail;

public class OrderStringParserTest {

    @InjectMocks
    private OrderStringParser parser;

    @Mock
    private OrderFactory factory;

    @BeforeClass
    public void init() {
        initMocks(this);
    }

    @BeforeMethod
    public void reset() {
        Mockito.reset(factory);

        doReturn(null)
                .when(factory)
                .buy(anyInteger(), anyDecimal());
        doReturn(null)
                .when(factory)
                .sell(anyInteger(), anyDecimal());
    }

    @Test(dataProvider = "valid", dataProviderClass = OrderStringParserTestData.class)
    public void shouldCallOrderFactoryToCreateOrderOfProperType(String input, OrderType expected) {
        parser.parse(input);
        switch (expected) {
            case BUY:
                verify(factory, only()).buy(anyInteger(), anyDecimal());
                break;
            case SELL:
                verify(factory, only()).sell(anyInteger(), anyDecimal());
                break;
            default: fail();
        }
    }

    @Test(dataProvider = "invalid", dataProviderClass = OrderStringParserTestData.class,
            expectedExceptions = OrderFormatException.class)
    public void shouldThrowExceptionIfStringIsOfIllegalFormat(String input, Object ignore) {
        parser.parse(input);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void shouldThrowNpeIfInputIsNull() {
        parser.parse(null);
    }

    private static Integer anyInteger() {
        return any(Integer.class);
    }

    private static BigDecimal anyDecimal() {
        return any(BigDecimal.class);
    }

}
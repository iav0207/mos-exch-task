package order;

/**
 * Created by takoe on 16.04.17.
 */
public class OrderFormatException extends IllegalArgumentException {

    public OrderFormatException(String s) {
        super(String.format("Illegal order format: %s\nShould match pattern %s", s, OrderStringParser.ORDER_REGEX));
    }

}

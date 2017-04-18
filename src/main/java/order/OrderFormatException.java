package order;

public class OrderFormatException extends IllegalArgumentException {

    OrderFormatException(String s) {
        super(String.format("Illegal order format: %s\nShould match pattern %s", s, OrderStringParser.ORDER_REGEX));
    }

}

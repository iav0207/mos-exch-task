package order;

import java.math.BigDecimal;

import static order.OrderFactory.MAX_ORDER_AMOUNT;
import static order.OrderFactory.MAX_PRICE;
import static order.OrderFactory.MIN_ORDER_AMOUNT;
import static order.OrderFactory.MIN_PRICE;

public class OrderValueException extends IllegalArgumentException {

    OrderValueException(Integer amount) {
        super(String.format("Illegal order amount: %d. Must be in range [%d; %d]",
                amount, MIN_ORDER_AMOUNT, MAX_ORDER_AMOUNT));
    }

    OrderValueException(BigDecimal price) {
        super(String.format("Illegal order item price: %f. Must be in range [%f; %f]",
                price, MIN_PRICE, MAX_PRICE));
    }

}

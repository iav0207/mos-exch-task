package order;

import java.math.BigDecimal;

import static order.OrderType.BUY;

public class BuyOrder extends Order {

    BuyOrder(Integer amount, BigDecimal price) {
        super(amount, price);
    }

    @Override
    public OrderType getType() {
        return BUY;
    }

}

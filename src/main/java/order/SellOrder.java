package order;

import java.math.BigDecimal;

import static order.OrderType.SELL;

/**
 * Created by takoe on 13.04.17.
 */
public class SellOrder extends Order {

    SellOrder(Integer amount, BigDecimal price) {
        super(amount, price);
    }

    @Override
    public OrderType getType() {
        return SELL;
    }

}

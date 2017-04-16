package order;

import java.math.BigDecimal;

/**
 * Created by takoe on 13.04.17.
 */
public abstract class Order {

    private final Integer amount;

    private final BigDecimal price;

    Order(Integer amount, BigDecimal price) {
        this.amount = amount;
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public abstract OrderType getType();

}

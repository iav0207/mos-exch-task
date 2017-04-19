package order;

import java.math.BigDecimal;

/**
 * Заявка на покупку или продажу бумаг в количестве {@code amount} по цене {@code price}.
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

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

    /**
     * Подсчитать сумму сделки по заявке в случае исполнения в полном объёме.
     * Рассчитывается как ({@code price x amount}).
     * @return объём сделки в случае исполнения заявки.
     */
    public BigDecimal getVolume() {
        return price.multiply(new BigDecimal(amount));
    }

    public Integer getAmount() {
        return amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public abstract OrderType getType();

}

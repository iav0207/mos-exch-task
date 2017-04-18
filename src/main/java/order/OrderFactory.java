package order;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

/**
 * Фабрика заявок с валидацией значений.
 */
class OrderFactory {

    /**
     * Минимальное количество бумаг в заявке.
     */
    public static final int MIN_ORDER_AMOUNT = 1;

    /**
     * Максимальное количество бумаг в заявке.
     */
    public static final int MAX_ORDER_AMOUNT = 1_000;

    /**
     * Минимальная цена за одну бумагу.
     */
    public static final BigDecimal MIN_PRICE = BigDecimal.ONE;

    /**
     * Максимальная цена за одну бумагу.
     */
    public static final BigDecimal MAX_PRICE = new BigDecimal(100);

    /**
     * Создать заявку на покупку.
     * При создании заявки значение цены округляется до копеек,
     * затем проводится валидация переданных значений.
     * @param amount    количество бумаг в заявке.
     * @param price     цена за бумагу.
     * @return          {@link BuyOrder}, новая заявка на покупку.
     * @throws OrderValueException в случае, если значения не прошли валидацию.
     */
    public BuyOrder buy(Integer amount, BigDecimal price) throws OrderValueException {
        validate(amount, price);
        return new BuyOrder(amount, round(price));
    }

    /**
     * Создать заявку на продажу.
     * При создании заявки значение цены округляется до копеек,
     * затем проводится валидация переданных значений.
     * @param amount    количество бумаг в заявке.
     * @param price     цена за бумагу.
     * @return          {@link BuyOrder}, новая заявка на продажу.
     * @throws OrderValueException в случае, если значения не прошли валидацию.
     */
    public SellOrder sell(Integer amount, BigDecimal price) throws OrderValueException {
        validate(amount, price);
        return new SellOrder(amount, round(price));
    }

    private static BigDecimal round(BigDecimal price) {
        return price.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private static void validate(Integer amount, BigDecimal price) {
        validate(amount);
        validate(price);
    }

    private static void validate(Integer amount) {
        requireNonNull(amount, "Order amount should not be null.");
        if (amount < MIN_ORDER_AMOUNT || MAX_ORDER_AMOUNT < amount)
            throw new OrderValueException(amount);
    }

    private static void validate(BigDecimal price) {
        requireNonNull(price, "Order price should not be null.");
        if (less(price, MIN_PRICE) || less(MAX_PRICE, price))
            throw new OrderValueException(price);
    }

    private static boolean less(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) < 0;
    }

}

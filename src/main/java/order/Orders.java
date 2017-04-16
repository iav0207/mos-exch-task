package order;

import java.math.BigDecimal;

/**
 * Фасад для создания заявок.
 */
public class Orders {

    public static final String ORDER_REGEX = OrderStringParser.ORDER_REGEX;

    private static OrderFactory factory = new OrderFactory();

    private static OrderStringParser parser = new OrderStringParser();

    /**
     * Создать заявку на основе строки вида
     * B 100 10.00
     *
     * Строка должна соответствовать формату {@value ORDER_REGEX}.
     *
     * @param s    строка, представляющая данные создаваемой заявки: тип, объём, цену.
     * @return     новая заявка, {@link Order}.
     * @throws OrderFormatException если формат строки не соответствует ожидаемому.
     * @throws OrderValueException  если значения объёма и цены не соответствуют ограничениям.
     */
    public static Order parse(String s) throws OrderFormatException, OrderValueException {
        return parser.parse(s);
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
    public static Order sell(Integer amount, BigDecimal price) throws OrderValueException {
        return factory.sell(amount, price);
    }

    /**
     * Создать заявку на покупку.
     * При создании заявки значение цены округляется до копеек,
     * затем проводится валидация переданных значений.
     * @param amount    количество бумаг в заявке.
     * @param price     цена за бумагу.
     * @return          {@link BuyOrder}, новая заявка на покупку.
     * @throws OrderValueException в случае, если значения не прошли валидацию.
     */
    public static Order buy(Integer amount, BigDecimal price) {
        return factory.buy(amount, price);
    }

}

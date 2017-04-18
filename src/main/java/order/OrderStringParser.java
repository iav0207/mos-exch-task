package order;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;
import static order.OrderType.isBuyOrderCode;
import static order.OrderType.isSellOrderCode;

/**
 * Created by takoe on 16.04.17.
 */
class OrderStringParser {

    public static final String ORDER_REGEX = "([B,S])\\s+([0-9])+\\s+(?:\\d*\\.)?\\d+";

    private static final Pattern orderPattern = Pattern.compile(ORDER_REGEX);

    private OrderFactory factory = new OrderFactory();

    /**
     * Создать заявку на основе строки вида
     * B 100 10.00
     *
     * Строка должна соответствовать формату {@value ORDER_REGEX}.
     *
     * @param s    строка, представляющая данные создаваемой заявки: тип, объём, цену.
     * @return     новая заявка, {@link Order}.
     * @throws OrderFormatException если формат строки не соответствует ожидаемому.
     */
    public Order parse(String s) throws OrderFormatException {

        validate(s);
        String[] subs = s.split("\\s+", 3);

        Integer amount = Integer.parseInt(subs[1]);
        BigDecimal price = new BigDecimal(subs[2]);
        Character type = subs[0].charAt(0);

        assert (isBuyOrderCode(type) ^ isSellOrderCode(type));

        return isBuyOrderCode(type) ? factory.buy(amount, price) : factory.sell(amount, price);
    }

    private void validate(String s) {
        requireNonNull(s, "Order string should not be null.");
        if (!orderPattern.matcher(s).matches())
            throw new OrderFormatException(s);
    }

}

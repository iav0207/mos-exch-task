package order;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by takoe on 16.04.17.
 */
public enum OrderType {

    BUY('B'),
    SELL('S');

    private Character code;

    OrderType(Character code) {
        this.code = code;
    }

    public Character getCode() {
        return code;
    }

    public static boolean isBuy(@Nonnull Order order) {
        return BUY.equals(order.getType());
    }

    public static boolean isSell(@Nonnull Order order) {
        return SELL.equals(order.getType());
    }
    public static boolean isBuyOrderCode(@Nullable Character c) {
        return BUY.code.equals(c);
    }

    public static boolean isSellOrderCode(@Nullable Character c) {
        return SELL.code.equals(c);
    }

}

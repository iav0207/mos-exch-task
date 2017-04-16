package order;

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

    public static boolean isBuyOrderCode(Character c) {
        return BUY.code.equals(c);
    }

    public static boolean isSellOrderCode(Character c) {
        return SELL.code.equals(c);
    }

}

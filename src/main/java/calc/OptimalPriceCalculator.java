package calc;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import order.Order;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Objects.requireNonNull;
import static order.OrderType.isBuy;

/**
 * Класс для рассчёта оптимальной цены дискретного аукциона.
 */
public class OptimalPriceCalculator {

    private Set<BigDecimal> prices = new TreeSet<>();

    private Multimap<BigDecimal, Order> buyMap = Multimaps.newSetMultimap(new HashMap<>(), HashSet::new);

    private Multimap<BigDecimal, Order> sellMap = Multimaps.newSetMultimap(new HashMap<>(), HashSet::new);

    private Integer buyOrdersTotalAmount = 0;

    /**
     * Разместить ордер на покупку или продажу бумаг.
     *
     * <p>При добавлении инкрементально подсчитывается суммарный объём размещённых ордеров на покупку.</p>
     *
     * <p>Сложность добавления одного элемента: O(log(n)), n - число размещённых ордеров</p>
     *
     * @param order заявка на покупку или продажу бумаг, {@link Order}.
     */
    public void place(@Nonnull Order order) {
        requireNonNull(order, "Order should not be null.");
        BigDecimal price = order.getPrice();

        prices.add(price);
        getCorrespondingMap(order).put(price, order);

        if (isBuy(order))
            buyOrdersTotalAmount += order.getAmount();
    }

    /**
     * Рассчитать оптимальную цену аукциона для размещённых ордеров на данный момент.
     * <p>
     * Для каждого значения цены инкрементально определяется суммарный объём возможных сделок.<br/>
     * Возвращаются значения цены и объёма торгуемых бумаг (одна пара значений), для которых объём сделок максимальный.
     * В случае, когда несколько корректных цен удовлетворяют этому критерию,
     * возвращается среднее арифметическое всех таких цен с округлением вверх до целых копеек.
     * </p>
     * <p>Сложность: O(n).</p>
     *
     * @return Объект {@link Result}, содержащий оптимальную цену аукциона,
     * а также количество торгуемых бумаг для этой цены.
     */
    public Result calculate() {
        Integer curBuyAmount = buyOrdersTotalAmount;
        Integer curSellAmount = 0;
        Integer maxTradeVolume = 0;

        final Set<BigDecimal> potentialOptimalPrices = new HashSet<>();

        for (BigDecimal price : prices) {

            Integer buyDecrement = getTotalAmount(buyMap.get(price));
            Integer sellIncrement = getTotalAmount(sellMap.get(price));

            curSellAmount += sellIncrement;
            Integer curTradeVolume = Math.min(curBuyAmount, curSellAmount); // возможный объём сделок для текущей цены
            curBuyAmount -= buyDecrement;

            if (maxTradeVolume < curTradeVolume) {
                maxTradeVolume = curTradeVolume;
                potentialOptimalPrices.clear();
                potentialOptimalPrices.add(price);

            } else if (curTradeVolume.equals(maxTradeVolume) && curTradeVolume > 0) {
                potentialOptimalPrices.add(price);
            }
        }

        BigDecimal optimalPrice = getSingleOptimalPrice(potentialOptimalPrices);
        return result(maxTradeVolume, optimalPrice);
    }

    @Nonnull
    private Multimap<BigDecimal, Order> getCorrespondingMap(Order order) {
        return isBuy(order) ? buyMap : sellMap;
    }

    private static Integer getTotalAmount(@Nonnull Collection<Order> orders) {
        return orders.stream().mapToInt(Order::getAmount).sum();
    }

    @Nullable
    private static BigDecimal getSingleOptimalPrice(@Nonnull Collection<BigDecimal> prices) {
        if (prices.isEmpty()) {
            return null;
        }
        return prices.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(new BigDecimal(prices.size()), 2, BigDecimal.ROUND_UP);
    }

    @Nonnull
    private static Result result(@Nonnull Integer maxTradeVolume, @Nullable BigDecimal optimalPrice) {
        if (maxTradeVolume == 0 || optimalPrice == null) {
            return Result.auctionFailed();
        }
        return new Result(maxTradeVolume, optimalPrice);
    }

    static class Result {

        private Integer amount;
        private BigDecimal price;

        Result(Integer amount, BigDecimal price) {
            this.amount = amount;
            this.price = price;
        }

        static Result auctionFailed() {
            return new Result(0, null);
        }

        @Override
        public String toString() {
            return price == null ? "0 n/a" : String.format("%d %.2f", amount, price);
        }
    }

}

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
import java.util.Iterator;
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

    private BigDecimal buyOrdersTotalVolume = BigDecimal.ZERO;

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
            buyOrdersTotalVolume = buyOrdersTotalVolume.add(order.getVolume());
    }

    /**
     * Рассчитать оптимальную цену аукциона для размещённых ордеров на данный момент.
     * <p>
     * Итерирование по ценам ордеров, а также по ценам, лежащим посередине между ними.<br/>
     * Для каждого значения цены инкрементально определяется суммарный объём возможных сделок.<br/>
     * Возвращаются значения цены и объёма торгуемых бумаг (одна пара значений), для которых объём сделок максимальный.
     * </p>
     * <p>Сложность: O(n).</p>
     *
     * @return Объект {@link Result}, содержащий оптимальную цену аукциона,
     * а также количество торгуемых бумаг для этой цены.
     */
    public Result calculate() {
        BigDecimal curBuyVolume = buyOrdersTotalVolume;
        BigDecimal curSellVolume = BigDecimal.ZERO;
        BigDecimal maxTradeVolume = BigDecimal.ZERO;
        BigDecimal optimalPrice = null;

        for (BigDecimal price : buildPricesSetToIterateThrough()) {

            BigDecimal buyIncrement = countVolumesTotal(buyMap.get(price));
            BigDecimal sellIncrement = countVolumesTotal(sellMap.get(price));

            curSellVolume = curSellVolume.add(sellIncrement);
            BigDecimal curTradeVolume = curBuyVolume.min(curSellVolume);    // возможный объём сделок для текущей цены
            curBuyVolume = curBuyVolume.subtract(buyIncrement);

            if (less(maxTradeVolume, curTradeVolume)) {
                maxTradeVolume = curTradeVolume;
                optimalPrice = price;
            }
        }

        return result(maxTradeVolume, optimalPrice);
    }

    @Nonnull
    private Multimap<BigDecimal, Order> getCorrespondingMap(Order order) {
        return isBuy(order) ? buyMap : sellMap;
    }

    /**
     * Оптимальными могут быть цены, по которым размещены заявки, а также средние значения между ними.
     *
     * @return Набор цен, по которым следует итерироваться в поисках оптимальной цены.
     */
    private Set<BigDecimal> buildPricesSetToIterateThrough() {
        Set<BigDecimal> pricesToIterate = new TreeSet<>(prices);
        Iterator<BigDecimal> iterator = prices.iterator();
        if (!iterator.hasNext())
            return pricesToIterate;
        BigDecimal prev = iterator.next();
        while (iterator.hasNext()) {
            BigDecimal cur = iterator.next();
            BigDecimal avg = prev.add(cur).divide(new BigDecimal(2), 2, BigDecimal.ROUND_UP);
            pricesToIterate.add(avg);
            prev = cur;
        }
        return pricesToIterate;
    }

    private static BigDecimal countVolumesTotal(@Nonnull Collection<Order> orders) {
        return orders.stream()
                .map(Order::getVolume)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static boolean less(@Nonnull BigDecimal first, BigDecimal second) {
        return first.compareTo(second) < 0;
    }

    @Nonnull
    private static Result result(@Nonnull BigDecimal maxTradeVolume, @Nullable BigDecimal optimalPrice) {
        if (optimalPrice == null) {
            return Result.auctionFailed();
        }
        Integer amount = maxTradeVolume.divide(optimalPrice, 0, BigDecimal.ROUND_DOWN).intValue();
        return new Result(amount, optimalPrice);
    }

    static class Result {

        private Integer amount;
        private BigDecimal price;

        Result() {
            this(0, null);
        }

        Result(Integer amount, BigDecimal price) {
            this.amount = amount;
            this.price = price;
        }

        static Result auctionFailed() {
            return new Result();
        }

        @Override
        public String toString() {
            return price == null ? "0 n/a" : String.format("%d %.2f", amount, price);
        }
    }

}

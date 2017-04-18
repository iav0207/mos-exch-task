import calc.OptimalPriceCalculator;
import io.StandardInputReader;
import order.OrderFormatException;
import order.OrderValueException;
import order.Orders;

/**
 * Created by takoe on 16.04.17.
 */
public class DiscreteAuction {

    public static void main(String... args) {

        OptimalPriceCalculator calculator = new OptimalPriceCalculator();

        try (StandardInputReader reader = new StandardInputReader()) {

            reader.readAllLines()
                    .map(Orders::parse)
                    .forEach(calculator::place);

            System.out.println(calculator.calculate());

        } catch (OrderFormatException | OrderValueException ex) {
            System.err.println(ex.getMessage());
        }

    }

}

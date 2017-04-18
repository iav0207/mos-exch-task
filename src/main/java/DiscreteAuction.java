import calc.OptimalPriceCalculator;
import io.StandardInputReader;
import order.OrderFormatException;
import order.OrderValueException;
import order.Orders;

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

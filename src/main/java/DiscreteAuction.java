import order.OrderFormatException;
import order.OrderValueException;
import order.Orders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by takoe on 16.04.17.
 */
public class DiscreteAuction {

    public static void main(String... args) {

        OptimalPriceCalculator calculator = new OptimalPriceCalculator();

        try (InputStreamReader isr = new InputStreamReader(System.in);
                BufferedReader in = new BufferedReader(isr)) {

            in.lines().map(Orders::parse).forEach(calculator::place);

            System.out.println(calculator.calculate());

        } catch (IOException ex) {
            ex.printStackTrace();

        } catch (OrderFormatException | OrderValueException ex) {
            System.err.println(ex.getMessage());
        }

    }

}

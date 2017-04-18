package io;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Created by takoe on 18.04.17.
 */
public class StandardInputReader implements Closeable {

    private Scanner scanner = new Scanner(System.in);

    public Stream<String> readAllLines() {
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty())
                break;
            lines.add(line);
        }
        return lines.stream();
    }

    public void close() {
        scanner.close();
    }

}

package org.comparison.validators;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Writer {
    public static void writeToFile(String outputPath, List<Long> durations) throws IOException {
        for (Long duration : durations)
            Files.write(Paths.get(outputPath), (duration.toString() + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
    }
}

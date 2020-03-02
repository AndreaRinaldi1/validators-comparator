package org.comparison.validators;

import com.google.common.io.CharStreams;
import org.api.executors.JSoundExecutor;
import org.api.executors.JSoundSchema;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JSound extends Writer {

    static void run(int reps, String filePath, String schemaPath, String outputPath) {
        try {
            List<Long> durations = new ArrayList<>();
            String schemaString = CharStreams.toString(new InputStreamReader(JSound.class.getResourceAsStream(schemaPath)));

            for (int i = 0; i < reps; ++i) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(JSound.class.getResourceAsStream(filePath)));

                long begin = System.currentTimeMillis();
                JSoundSchema schema = JSoundExecutor.loadSchema(schemaString, "targetType", false);
                bufferedReader.lines().forEach(schema::validateInstance);
                long duration = System.currentTimeMillis() - begin;

                if (outputPath != null) {
                    durations.add(duration);
                } else {
                    System.out.println(duration);
                }
            }
            if (outputPath != null) {
                writeToFile(outputPath, durations);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void annotate(int reps, String filePath, String schemaPath, String outputPath) {
        try {
            List<Long> durations = new ArrayList<>();
            String schemaString = CharStreams.toString(new InputStreamReader(JSound.class.getResourceAsStream(schemaPath)));

            for (int i = 0; i < reps; ++i) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(JSound.class.getResourceAsStream(filePath)));

                long begin = System.currentTimeMillis();
                JSoundSchema schema = JSoundExecutor.loadSchema(schemaString, "targetType", false);
                bufferedReader.lines().forEach(schema::annotateInstance);
                long duration = System.currentTimeMillis() - begin;

                if (outputPath != null) {
                    durations.add(duration);
                } else {
                    System.out.println(duration);
                }
            }
            if (outputPath != null) {
                writeToFile(outputPath, durations);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

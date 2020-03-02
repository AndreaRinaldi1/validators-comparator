package org.comparison.validators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.google.common.io.CharStreams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Fge extends Writer {

    static void run(int reps, String filePath, String schemaPath, String outputPath) throws IOException, ProcessingException {
        List<Long> durations = new ArrayList<>();
        JsonNode schemaNode = JsonLoader.fromResource(schemaPath);

        for (int i = 0; i < reps; ++i) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Fge.class.getResourceAsStream(filePath)));

            long begin = System.currentTimeMillis();
            JsonSchema schema = JsonSchemaFactory.byDefault().getJsonSchema(schemaNode);
            bufferedReader.lines().forEach(instance -> {
                try {
                    schema.validate(JsonLoader.fromString(instance));
                } catch (ProcessingException | IOException e) {
                    e.printStackTrace();
                }
            });
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
    }
}

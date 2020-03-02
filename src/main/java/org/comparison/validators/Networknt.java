package org.comparison.validators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.ValidationMessage;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Networknt extends Writer {

    static void run(int reps, String filePath, String schemaPath, String outputPath) throws IOException {
        List<Long> durations = new ArrayList<>();

        for (int i = 0; i < reps; ++i) {
            InputStream inputStream = Networknt.class.getResourceAsStream(schemaPath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Networknt.class.getResourceAsStream(filePath)));

            long begin = System.currentTimeMillis();
            JsonSchema schema = JsonSchemaFactory.getInstance().getSchema(inputStream);
            bufferedReader.lines().forEach(instance -> {
                try {
                    schema.validate(new ObjectMapper().readTree(instance));
                } catch (JsonProcessingException e) {
                    System.err.println(e.getMessage());
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

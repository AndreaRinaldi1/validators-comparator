package org.comparison.validators;

import com.google.common.io.CharStreams;
import com.jsoniter.JsonIterator;
import jsound.json.InstanceFileJsonParser;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Everit extends Writer {

  static void run(int reps, String filePath, String schemaPath, String outputPath) throws IOException {
    List<Long> durations = new ArrayList<>();

    for (int i = 0; i < reps; ++i) {
      InputStream schemaObject = Everit.class.getResourceAsStream(schemaPath);
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Everit.class.getResourceAsStream(filePath)));

      long begin = System.currentTimeMillis();
      Schema schema = SchemaLoader.load(new JSONObject(new JSONTokener(schemaObject)));
      bufferedReader.lines().forEach(instance-> schema.validate(new JSONObject(new JSONTokener(instance))));
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

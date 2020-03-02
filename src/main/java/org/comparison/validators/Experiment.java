package org.comparison.validators;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import jsound.exceptions.CliException;

import java.io.IOException;
import java.util.HashMap;


public class Experiment {
    private static final String ARGUMENT_PREFIX = "--";
    private static final String ARGUMENT_FORMAT_ERROR_MESSAGE =
            "Invalid argument format. Required format: --property value";
    private static HashMap<String, String> _arguments = new HashMap<>();

    public static void main(final String[] args) throws IOException, ProcessingException {

        for (int index = 0; index < args.length; index += 2) {
            if (args[index].startsWith(ARGUMENT_PREFIX))
                _arguments.put(args[index].trim().replace(ARGUMENT_PREFIX, ""), args[index + 1]);
            else
                throw new CliException(ARGUMENT_FORMAT_ERROR_MESSAGE);
        }
        if ("annotate".equals(getMode())) {
            JSound.annotate(getReps(), getFilePath(), getSchemaPath(), getOutputPath());
            return;
        }
        switch (getEngine()) {
            case "Everit":
                Everit.run(getReps(), getFilePath(), getSchemaPath(), getOutputPath());
                break;
            case "Fge":
                Fge.run(getReps(), getFilePath(), getSchemaPath(), getOutputPath());
                break;
            case "JSound":
                JSound.run(getReps(), getFilePath(), getSchemaPath(), getOutputPath());
                break;
            case "Networknt":
                Networknt.run(getReps(), getFilePath(), getSchemaPath(), getOutputPath());
                break;
        }

    }
    public static String getMode() {
        return _arguments.get("mode");
    }

    public static String getEngine() {
        return _arguments.get("engine");
    }

    public static int getReps() {
        return Integer.parseInt(_arguments.get("reps"));
    }

    public static String getFilePath() {
        return _arguments.get("file");
    }

    public static String getSchemaPath() {
        return _arguments.get("schema");
    }

    public static String getOutputPath() {
        return _arguments.get("output");
    }
}

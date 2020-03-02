# Schema Validators Comparison Tool

A performance test tool that compares the following Schema validation engines (all implemented in Java):
 - Networknt: https://github.com/networknt/json-schema-validator
 - Everit: https://github.com/everit-org/json-schema
 - Fge: https://github.com/java-json-tools/json-schema-validator
 - JSound 2.0 validation engine personally developed. 
 
The first three validators require a [JSON Schema](https://json-schema.org/) defined schema, while the last one needs a [JSound 2.0](http://www.jsound-spec.org/publish/en-US/JSound/2.0/html-single/JSound/index.html) schema in its extended syntax.

### How to build

`mvn clean compile assembly:single`

### How to run

There are a few arguments that need to be provided to the CLI command when running the test suite:

- engine: one of {Networknt, Everit, Fge, JSound}
- file: path to the file you want to validate
- schema: path the the schema file you want to validate the candidate instance against
- reps: number of repetitions of the validation process

Example:

`java -jar target/validatorsComparator-0.1.0-jar-with-dependencies.jar --engine JSound --file /game/file.json --schema /game/schemaJS.json --reps 2`

_Note: when using the JSound engine, please use `targetType` as the name for the target type you want to validate the candidate instance against._

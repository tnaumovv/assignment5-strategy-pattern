# Assignment 5 Strategy Pattern

Option B: a Java calculator with interchangeable addition, subtraction and multiplication algorithms.

Student: Timur Naumov, SE-2537. Course: Software Design Patterns, 2026-2027.

## Requirements

JDK 17 or newer. No Maven, Gradle, external Java libraries or IDE plugins are required.

## Build and run

Run these commands from the project directory:

```sh
mkdir -p out
javac --release 17 -encoding UTF-8 -Xlint:all -d out src/*.java
java -cp out Main
```

Expected output:

```text
One Calculator object, different strategies at runtime
10 + 5 = 15.0
10 - 5 = 5.0
10 * 5 = 50.0
Back to addition: 10 + 5 = 15.0
```

The client creates one Calculator and calls setStrategy on that same object. The context delegates calculate to OperationStrategy.execute and never checks a concrete strategy type.

## Verify all examples

```sh
javac --release 17 -encoding UTF-8 -Xlint:all -d out src/*.java examples/*.java tests/*.java
java -cp out AcceptanceChecks
java -cp out ExtensionChecks
java -cp out ExtensionDemo
```

Expected summaries: `PASS: 12/12 acceptance checks` and `PASS: 5/5 extension checks`. The extension demo prints a division result of `2.0`.

Checks cover runtime replacement and switching back, argument order, negative and fractional operands, zero multiplication, a previously unknown algorithm, null rejection and preservation of the current strategy after an invalid replacement. The extension also checks division by positive and negative zero.

Validation environment: compiled with Temurin JDK 17.0.21 using `--release 17` and executed on the same Java 17 runtime. All 17 checks passed without compiler warnings. The code was also checked with OpenJDK 27. Arithmetic uses ordinary Java `double` values, including their usual floating-point limits.

## Structure

```text
src/          Strategy interface, three algorithms, Calculator and Main
examples/     DivideStrategy and a separate client ExtensionDemo
tests/        Runnable acceptance checks without a test framework
report.md     Required Markdown reflection with six numbered sections
report/       UML PNG, SVG, PDF and editable PlantUML source; report PDF preview
DEFENSE.md    Russian explanation and short English defense answers
```

## Add a new strategy

1. Add a class that implements OperationStrategy and its execute method.
2. Instantiate it in client code and pass it to the constructor or setStrategy.
3. Compile the new file along with the original sources.

`examples/DivideStrategy.java` and `examples/ExtensionDemo.java` are a complete example. They add division without editing any file in `src/`. Calculator, OperationStrategy, all three original algorithms and Main stay unchanged when adding this extension. The `if` in DivideStrategy rejects a zero divisor; it does not select a strategy.


# Assignment 5 Strategy Pattern

Option B: Calculator with addition, subtraction and multiplication strategies. Requires JDK 17 or newer.

## Build and run

```sh
mkdir -p out
javac --release 17 -d out src/*.java
java -cp out Main
```

## Add a new strategy

Implement `OperationStrategy.execute(double a, double b)`, then pass the new strategy to the Calculator constructor or `setStrategy`. Calculator does not need modification.

Run the division example:

```sh
javac --release 17 -d out src/*.java examples/*.java
java -cp out ExtensionDemo
```


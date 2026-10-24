# Assignment 5 Strategy Pattern Report

**Student:** Timur Naumov  
**Group:** SE-2537  
**Course:** Software Design Patterns  
**Academic year:** 2026-2027  
**Chosen option:** B - Calculator

## 1. Introduction

This project is a calculator with three operations: addition, subtraction and multiplication. Each operation takes two `double` values and returns one result.

The operations use the same interface, `OperationStrategy`. `Calculator` stores the chosen strategy and calls its `execute` method. `Main` chooses the operation. It can change the strategy while the program is running. [1, 2]

## 2. UML class diagram

![UML class diagram of the calculator](https://raw.githubusercontent.com/tnaumovv/assignment5-strategy-pattern/main/report/Strategy_Class_Diagram.png)

*Figure 1. The six types in `src/`. The separate division example is outside this diagram. The editable [PlantUML file](https://github.com/tnaumovv/assignment5-strategy-pattern/blob/main/report/Strategy_Class_Diagram.puml) is also available.*

### Classes and relationships

`Calculator` is the Context, `OperationStrategy` is the Strategy interface, and `Main` is the Client. `AddStrategy`, `SubtractStrategy` and `MultiplyStrategy` are the concrete strategies.

- **Three interface realizations:** each concrete strategy has a dashed line with a hollow triangle pointing to `OperationStrategy`. In Java, these are the three `implements OperationStrategy` declarations.
- **One association:** the solid line with an open arrow points from `Calculator` to `OperationStrategy`. It shows the reference stored in the private `strategy` field. The field and the line show the same reference. The small `x` at the Calculator end means there is no navigation back: the strategies have no Calculator field.
- **Multiplicities:** `1` beside the interface means a fully constructed Calculator has one selected strategy. Null is rejected by the constructor and setter. `0..*` beside Calculator means one strategy object can be used by zero or more calculators. A single calculator still stores only one strategy at a time.
- **Four creation dependencies:** dashed lines with open arrowheads go from `Main` to Calculator and the three concrete strategies. `«Instantiate»` means the client creates objects of those classes. Main keeps its Calculator in a local variable, so this is a dependency rather than a stored field association.
- **Class details:** `+` means public, and `-` means private. The constructor has the `«Create»` label. The underlined `main` method is static. `«interface»` identifies the interface. Lines inside the boxes separate names, fields and methods.

The lines have the same width. Their meaning comes from the line style and arrowhead. A lollipop circle is another way to draw a provided interface. Here, a rectangle shows the interface and its method. The Calculator relationship is a normal reference without a whole-part meaning, so no aggregation diamond is needed. A filled composition diamond would add exclusive ownership, while this code allows strategies to be shared. [3, sections 7.7.4-7.7.5, 9.4.4, 10.4.4, 11.4.4, 11.5.4-11.5.5]

### How the program runs

Main creates an AddStrategy and passes it to a new Calculator. Calling `calculate(10, 5)` makes Calculator call `strategy.execute(10, 5)`. AddStrategy returns `15.0`, and Main prints it.

Main then calls `setStrategy` on the same Calculator. SubtractStrategy gives `5.0`, MultiplyStrategy gives `50.0`, and switching back to AddStrategy gives `15.0`. There is only one Calculator object during this demo.

## 3. Clean Code principles

### 3.1 Use polymorphism instead of operation checks

A calculator could choose its operation with conditions like these:

```java
// Illustration of a conditional approach, not submitted source code.
if (operation.equals("+")) return a + b;
if (operation.equals("-")) return a - b;
if (operation.equals("*")) return a * b;
```

In this project, `Calculator.calculate` contains one call:

```java
public double calculate(double a, double b) {
    return strategy.execute(a, b);
}
```

Calculator does not check the operation name or strategy type. Java calls the method of the object currently stored in `strategy`. A new operation therefore does not need another branch in Calculator. [2]

### 3.2 Give classes and methods clear names

```java
calculator.setStrategy(new SubtractStrategy());
System.out.println("10 - 5 = " + calculator.calculate(a, b));
```

`SubtractStrategy` tells the reader which operation is used. `setStrategy` replaces it, and `calculate` runs it. The reader does not need to remember that a number such as mode 2 means subtraction.

### 3.3 Give each strategy one job

```java
public class MultiplyStrategy implements OperationStrategy {
    @Override
    public double execute(double a, double b) {
        return a * b;
    }
}
```

MultiplyStrategy only multiplies the two numbers. Main prints the results, and Calculator stores the selected strategy. This keeps the calculation separate from printing and object setup. [4]

### 3.4 Let Calculator use the interface

```java
private OperationStrategy strategy;

public void setStrategy(OperationStrategy strategy) {
    this.strategy = Objects.requireNonNull(strategy, "Strategy must not be null");
}
```

Both the field and the setter use `OperationStrategy`. Calculator does not need to know the concrete class behind that reference. The null check makes sure there is a strategy to call. The constructor has the same check.

### 3.5 Add new operations without changing Calculator

The division example adds this class in `examples/DivideStrategy.java`:

```java
public class DivideStrategy implements OperationStrategy {
    @Override
    public double execute(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }
}
```

A separate client in `examples/ExtensionDemo.java` uses it:

```java
calculator.setStrategy(new DivideStrategy());
System.out.println("10 / 5 = " + calculator.calculate(10, 5));
```

These two new files add division without changing anything in `src/`. Calculator and the first three strategies keep working as before. This is an example of the Open/Closed Principle. The check inside DivideStrategy prevents division by zero. [2, 4]

## 4. Strategy vs. State

In this program, Main chooses how to calculate a result. Addition and subtraction are alternative operations. The Calculator does not move through stages of a lifecycle.

State is useful when behavior depends on the current stage of an object, such as a document being Draft or Published. State objects may also handle transitions between stages. The calculator strategies do not change each other or control transitions. Their job is to perform the operation selected by the client, so Strategy fits this problem. [2, 5]

## 5. Conclusion

The main benefit is that Calculator stays the same when another operation is added. Each operation is in a short class, and Main can replace it at runtime.

The downside is the number of files: the basic version has six types for only three simple operations. That is more code structure than a small fixed calculator needs. It becomes more useful when operations are added or changed separately. [2]

## 6. Link to the GitHub repository

[https://github.com/tnaumovv/assignment5-strategy-pattern](https://github.com/tnaumovv/assignment5-strategy-pattern)

### References

[1] Astana IT University. *Assignment 5 Strategy Pattern*, Software Design Patterns, 2026-2027. Supplied assignment PDF.

[2] Refactoring.Guru. *Strategy*. https://refactoring.guru/design-patterns/strategy

[3] Object Management Group. *Unified Modeling Language 2.5.1*, formal/2017-12-05. https://www.omg.org/spec/UML/2.5.1/PDF

[4] Martin, R. C. *Clean Code*, Chapter 10. Prentice Hall, 2008. Course reading reference.

[5] Refactoring.Guru. *State*. https://refactoring.guru/design-patterns/state

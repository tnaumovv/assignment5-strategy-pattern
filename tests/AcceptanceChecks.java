public class AcceptanceChecks {
    private static int passed;

    public static void main(String[] args) {
        OperationStrategy addition = new OperationStrategy() {
            @Override
            public double execute(double a, double b) {
                return a + b;
            }
        };
        Calculator calculator = new Calculator(addition);
        check("constructor strategy is executed", 15, calculator.calculate(10, 5));

        calculator.setStrategy(new AddStrategy());
        check("addition", 15, calculator.calculate(10, 5));
        calculator.setStrategy(new SubtractStrategy());
        check("same calculator switches to subtraction", 5, calculator.calculate(10, 5));
        calculator.setStrategy(new MultiplyStrategy());
        check("same calculator switches to multiplication", 50, calculator.calculate(10, 5));
        calculator.setStrategy(new AddStrategy());
        check("same calculator switches back", 15, calculator.calculate(10, 5));
        check("negative and fractional operands", -2.25, calculator.calculate(-3.5, 1.25));
        calculator.setStrategy(new SubtractStrategy());
        check("operand order is preserved", -5, calculator.calculate(5, 10));
        calculator.setStrategy(new MultiplyStrategy());
        check("zero multiplication", 0, calculator.calculate(0, 7));

        // This new algorithm is unknown to Calculator: no production class is edited.
        calculator.setStrategy(new OperationStrategy() {
            @Override
            public double execute(double a, double b) {
                return Math.max(a, b);
            }
        });
        check("unknown strategy receives both arguments", 9, calculator.calculate(4, 9));

        expectNullRejected("null constructor strategy", () -> new Calculator(null));
        expectNullRejected("null replacement strategy", () -> calculator.setStrategy(null));
        check("failed replacement keeps the previous strategy", 9, calculator.calculate(4, 9));
        System.out.println("PASS: " + passed + "/" + passed + " acceptance checks");
    }

    private static void check(String name, double expected, double actual) {
        if (Math.abs(expected - actual) > 0.000001 || Double.isNaN(actual)) {
            throw new AssertionError(name + ": expected " + expected + ", got " + actual);
        }
        passed++;
        System.out.println("PASS: " + name);
    }

    private static void expectNullRejected(String name, Runnable action) {
        try {
            action.run();
        } catch (NullPointerException expected) {
            passed++;
            System.out.println("PASS: " + name);
            return;
        }
        throw new AssertionError(name + ": null was accepted");
    }
}

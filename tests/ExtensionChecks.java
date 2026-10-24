public class ExtensionChecks {
    public static void main(String[] args) {
        Calculator calculator = new Calculator(new DivideStrategy());
        check(2, calculator.calculate(10, 5));
        check(2.5, calculator.calculate(5, 2));
        check(-2, calculator.calculate(-10, 5));
        expectZeroRejected(calculator, 0.0);
        expectZeroRejected(calculator, -0.0);
        System.out.println("PASS: 5/5 extension checks");
    }

    private static void check(double expected, double actual) {
        if (Math.abs(expected - actual) > 0.000001 || Double.isNaN(actual)) {
            throw new AssertionError("Division: expected " + expected + ", got " + actual);
        }
    }

    private static void expectZeroRejected(Calculator calculator, double divisor) {
        try {
            calculator.calculate(10, divisor);
        } catch (ArithmeticException expected) {
            return;
        }
        throw new AssertionError("Zero divisor was accepted: " + divisor);
    }
}

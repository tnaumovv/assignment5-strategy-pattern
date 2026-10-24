public class Main {
    public static void main(String[] args) {
        double a = 10;
        double b = 5;
        Calculator calculator = new Calculator(new AddStrategy());

        System.out.println("One Calculator object, different strategies at runtime");
        System.out.println("10 + 5 = " + calculator.calculate(a, b));

        calculator.setStrategy(new SubtractStrategy());
        System.out.println("10 - 5 = " + calculator.calculate(a, b));

        calculator.setStrategy(new MultiplyStrategy());
        System.out.println("10 * 5 = " + calculator.calculate(a, b));

        calculator.setStrategy(new AddStrategy());
        System.out.println("Back to addition: 10 + 5 = " + calculator.calculate(a, b));
    }
}

public class ExtensionDemo {
    public static void main(String[] args) {
        Calculator calculator = new Calculator(new AddStrategy());
        System.out.println("10 + 5 = " + calculator.calculate(10, 5));
        calculator.setStrategy(new DivideStrategy());
        System.out.println("10 / 5 = " + calculator.calculate(10, 5));
        System.out.println("DivideStrategy was added without editing any file in src/");
    }
}

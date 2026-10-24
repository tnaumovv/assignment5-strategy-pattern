# Подготовка к защите Assignment 5

## Запуск

Открыть терминал в папке проекта:

```sh
mkdir -p out
javac --release 17 -encoding UTF-8 -Xlint:all -d out src/*.java examples/*.java tests/*.java
java -cp out Main
java -cp out ExtensionDemo
java -cp out AcceptanceChecks
java -cp out ExtensionChecks
```

## Объяснение за одну минуту

Я выбрал Option B — Calculator. Strategy — поведенческий паттерн: разные алгоритмы имеют общий интерфейс, а нужный алгоритм можно заменить во время работы программы.

`OperationStrategy` задаёт метод `execute`. `AddStrategy`, `SubtractStrategy` и `MultiplyStrategy` реализуют этот метод по-разному. `Calculator` хранит ссылку на интерфейс, а его `calculate` вызывает `strategy.execute(a, b)`. Контекст не проверяет тип стратегии и не знает формулу.

`Main` выбирает стратегию. Сначала создаётся один Calculator со сложением, потом на том же объекте вызывается `setStrategy` для вычитания и умножения, затем снова для сложения. На числах 10 и 5 получаем 15, 5, 50, 15.

Чтобы добавить деление, создаю новый класс `DivideStrategy` и передаю его из отдельного `ExtensionDemo`. Исходные классы в `src/` менять не нужно.

## Как читать UML

- Пунктир + пустой треугольник: реализация интерфейса. Идёт от конкретной стратегии к OperationStrategy; это `implements` в Java.
- Сплошная линия + открытый наконечник: направленная ассоциация от Calculator к OperationStrategy, потому что поле `strategy` хранит ссылку.
- `1` у интерфейса: у одного Calculator всегда одна выбранная стратегия. `0..*` у Calculator: один объект стратегии можно передать нескольким калькуляторам.
- Пунктир + открытый наконечник: зависимость Main от классов, объекты которых он создаёт. Подпись `«Instantiate»` означает создание.
- `+` — public, `-` — private. `main` подчёркнут, потому что static.
- Кружок — компактная альтернативная запись предоставляемого интерфейса. Здесь интерфейс изображён прямоугольником, чтобы показать метод.
- Ромб не нужен: стратегии создаёт клиент, их можно заменить и использовать совместно; Calculator не управляет их временем жизни.
- Толщина линий одинаковая: смысл задают тип линии и наконечник.

## Вопросы преподавателя

**What is the Strategy pattern?**  
It defines a family of interchangeable algorithms behind one interface. A context delegates work to the selected algorithm.

**What is your context?**  
Calculator. It stores an OperationStrategy reference and delegates calculate to execute.

**Who chooses the strategy?**  
The client, Main. The context does not inspect strategy types.

**Does switching create another Calculator?**  
No. Main creates one Calculator, then replaces its strategy using setStrategy.

**How is this different from State?**  
Strategy selects an algorithm for a task. State changes behavior according to internal state and transition rules. My operations do not manage transitions.

**Where is the Open/Closed Principle?**  
The separate division example adds a new strategy and client without editing any original source class.

**Why do you have an if in DivideStrategy?**  
It rejects a zero divisor. It validates one algorithm's input and does not select a strategy. Calculator has no type-based branches.

**Why not put a switch in Calculator?**  
That would couple the context to concrete operations. Delegation lets new implementations work through the same interface.

**Why is there no composition diamond?**  
The client creates and supplies strategies. They can be replaced and shared; Calculator does not exclusively own their lifetime.

**Why is the realization arrow different from a dependency?**  
A realization has a dashed line with a hollow triangle toward the interface. A dependency has a dashed line with an open arrowhead toward the used class.

**What is the disadvantage?**  
More classes and client configuration. For three operations that never change, a simple implementation may be shorter.

**How did you verify it?**  
Twelve core checks and five extension checks pass. They cover runtime switching, delegation, input order, null handling and division by zero. Compilation and execution both use Temurin JDK 17.0.21. There are no compiler warnings.

## Что показать по файлам

1. `src/OperationStrategy.java`: общий контракт.
2. `src/AddStrategy.java`: один короткий алгоритм.
3. `src/Calculator.java`: поле интерфейса, setter и делегирование.
4. `src/Main.java`: один Calculator и замена стратегий.
5. `examples/DivideStrategy.java` и `examples/ExtensionDemo.java`: расширение без изменения исходного контекста.
6. `report/Strategy_Class_Diagram.svg`: связи UML соответствуют этим классам.

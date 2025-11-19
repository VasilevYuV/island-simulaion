<h1 style="font-family: 'Manrope_Cut_008 ExtraBold', sans-serif"> Тестирование Island Ecosystem Simulation</h1>

---
<h2 style="font-family: 'Manrope_Cut_008 ExtraBold', sans-serif">Содержание</h2>
<ul style="font-family: 'Manrope_Cut_008 Medium', sans-serif">
    <li><a href="#обзор-тестов">Обзор тестов</a></li>
    <li><a href="#типы-тестов">Типы тестов</a></li>
    <li><a href="#запуск-тестов">Запуск тестов</a></li>
    <li><a href="#отдельные-тесты">Запуск отдельных тестов</a></li>
    <li><a href="#структура-тестов">Структура тестов</a></li>
</ul>

---

## <span style="font-family: 'Manrope_Cut_008 ExtraBold', sans-serif">Обзор тестов</span>

<p id="обзор-тестов" style="font-family: 'Manrope_Cut_008 Medium', sans-serif">
Комплексная тестовая система для симуляции экосистемы острова. 
Тесты охватывают все ключевые компоненты: создание животных, инициализацию острова, 
поведение хищников и травоядных, а также интеграционное тестирование движка симуляции.
</p>

---

## <span style="font-family: 'Manrope_Cut_008 ExtraBold', sans-serif">Типы тестов</span>

<div id="типы-тестов" style="font-family: 'Manrope_Cut_008 Medium', sans-serif">

### Модульные тесты
- **AnimalTest** - тестирование базового класса животных
- **ConcreteAnimalsTest** - создание конкретных видов животных
- **LocationTest** - функциональность локаций острова
- **IslandTest** - инициализация и работа острова
- **AnimalConfigTest** - конфигурация параметров животных

### Интеграционные тесты
- **SimulationEngineTest** - полный цикл симуляции

### Тестовое покрытие
- Создание 15+ видов животных
- Иерархия наследования (Animal → Predator/Herbivore)
- Многопоточная обработка
- Пищевые цепочки и поведение
- Статистика и мониторинг

</div>

---

## <span style="font-family: 'Manrope_Cut_008 ExtraBold', sans-serif">Запуск тестов</span>
<a id="запуск-тестов"> </a>

```
## Все тесты одновременно

mvn test
mvn test surefire-report:report

## Быстрая проверка сборки

mvn clean package -DskipTests
mvn compile test-compile
```
---

## <span style="font-family: 'Manrope_Cut_008 ExtraBold', sans-serif">Запуск отдельных тестов</span>
<a id="отдельные-тесты"> </a>

```
## Тесты животных

mvn test -Dtest=AnimalTest
mvn test -Dtest=ConcreteAnimalsTest
mvn test -Dtest=AnimalConfigTest

## Тесты острова и локаций

mvn test -Dtest=LocationTest
mvn test -Dtest=IslandTest

## Интеграционные тесты

mvn test -Dtest=SimulationEngineTest
```

---

## <span style="font-family: 'Manrope_Cut_008 ExtraBold', sans-serif">Структура тестов</span>
<a id="структура-тестов"> </a>


```
src/test/java/com/VasilevYuV/island/
├── animals/
│   ├── AnimalTest.java
│   └── ConcreteAnimalsTest.java
├── island/
│   ├── IslandTest.java
│   └── LocationTest.java
├── config/
│   └── AnimalConfigTest.java
├── service/
│   └── SimulationEngineTest.java
└── controller/
└── IslandApplicationTests.java
```
<h1 style="font-family: 'Manrope_Cut_008 ExtraBold', sans-serif">🏝️ Island Ecosystem Simulation</h1>

---
<h2 style="font-family: 'Manrope_Cut_008 ExtraBold', sans-serif">Содержание</h2>
<ul style="font-family: 'Manrope_Cut_008 Medium', sans-serif">
    <li><a href="#обзор">Обзор</a></li>
    <li><a href="#функциональность">Функциональность</a></li>
    <li><a href="#технологии">Технологии</a></li>
    <li><a href="#установка">Установка</a></li>
    <li><a href="#использование">Использование</a></li>
    <li><a href="#структура-проекта">Структура проекта</a></li>
    <li><a href="#животные">Животные</a></li>
    <li><a href="#лицензия">Лицензия</a></li>
</ul>

---

## <span style="font-family: 'Manrope_Cut_008 ExtraBold', sans-serif">Обзор</span>

<p id="обзор" style="font-family: 'Manrope_Cut_008 Medium', sans-serif">
Многопоточная Java-симуляция экосистемы острова с реалистичным моделированием поведения животных. 
Проект демонстрирует принципы ООП, многопоточность и сложные взаимодействия в пищевой цепи.
</p>

---

## <span style="font-family: 'Manrope_Cut_008 ExtraBold', sans-serif">Функциональность</span>

<div id="функциональность" style="font-family: 'Manrope_Cut_008 Medium', sans-serif">

- 🐺 **15+ видов животных** с иерархией наследования
- 🌿 **Динамическая пищевая цепь** с вероятностным поеданием
- 🔄 **Жизненный цикл**: питание, размножение, движение, смерть
- ⚡ **Многопоточная архитектура** с ScheduledExecutorService
- 📊 **Real-time статистика** и визуализация
- 🎮 **Веб-интерфейс** для управления симуляцией
- ⚙️ **Конфигурируемые параметры** через properties
- 🖥️ **Псевдографика** с юникод-символами животных
- 📈 **Аналитика популяций** по тактам

</div>

---

## <span style="font-family: 'Manrope_Cut_008 ExtraBold', sans-serif">Технологии</span>

<div id="технологии" style="font-family: 'Manrope_Cut_008 Medium', sans-serif">

- **Backend**: Java 21, Spring Boot 3.2+
- **Multithreading**: ExecutorService, Virtual Threads
- **Frontend**: Thymeleaf, JavaScript
- **Build Tool**: Maven
- **Logging**: SLF4J + Logback
- **JSON**: Jackson для конфигурации
- **Container**: Docker

</div>

---

## <span style="font-family: 'Manrope_Cut_008 ExtraBold', sans-serif">Установка</span>
<a id="установка"> </a>

### Предварительные требования

- Java 21 или выше
- Maven 3.8+
- Spring Boot 3.2+
### <span style="font-family: 'Manrope_Cut_008 Medium', sans-serif">Локальная установка</span>
```
# Склонировать репозиторий
git clone https://github.com/VasilevYuV/island-simulation

# Запуск приложения
mvn spring-boot:run
```

### <span style="font-family: 'Manrope_Cut_008 Medium', sans-serif">Docker установка</span>

```
# Склонировать репозиторий
git clone https://github.com/VasilevYuV/island-simulation

# Сборка проекта (создание JAR файла)
mvn clean package -DskipTests

# Собрать Docker образ
docker build -t island-simulation -f docker/Dockerfile .

# Запустить контейнер
docker-compose -f docker/compose.yaml up

# Остановить
docker-compose -f docker/compose.yaml down
```
---

## <span style="font-family: 'Manrope_Cut_008 ExtraBold', sans-serif">Использование</span>

<div id="использование" style="font-family: 'Manrope_Cut_008 Medium', sans-serif">

1. **Запустите приложение** - сервер стартует на localhost:8080
2. **Настройте остров** в веб-интерфейсе:
    - Размер сетки (по умолчанию 100x20)
    - Начальная популяция животных
    - Количество растений
3. **Запустите симуляцию** - животные начнут свой жизненный цикл
4. **Наблюдайте статистику** в реальном времени
5. **Управляйте скоростью** симуляции
6. **Анализируйте динамику** пищевой цепи

</div>

---

## <span style="font-family: 'Manrope_Cut_008 ExtraBold', sans-serif">Структура проекта</span>
<a id="структура-проекта"> </a>

```
island-simulation/
├── src/main/java/com/VasilevYuV/island/
│   ├── animals/                # Иерархия животных
│   │   ├── Animal.java       
│   │   ├── Predator.java      
│   │   ├── Herbivore.java    
│   │   ├── predators/        
│   │   └── herbivores/       
│   ├── island/                 # Модель острова
│   │   ├── Island.java       
│   │   └── Location.java     
│   ├── config/                 # Конфигурация
│   │   ├── AnimalConfig.java 
│   │   ├── ThreadConfig.java
│   │   ├── WebSocketConfig.java
│   │   └── SimulationProperties.java
│   ├── service/                # Бизнес-логика
│   │   ├── SimulationEngine.java 
│   │   ├── StatisticsService.java 
│   │   └── IslandService.java    
│   ├── controller/             # Веб-контроллеры
│   ├── location/               # Модель локаций
│   └── event/                  # Spring события
├── src/main/resources/
│   ├── templates/              # Thymeleaf шаблоны
│   ├── static/               
│   └── application.properties
└── pom.xml
```
---

## <span style="font-family: 'Manrope_Cut_008 ExtraBold', sans-serif">Животные</span>
<a id="животные"> </a>

### Хищники (5 видов)

- **Волк** (Wolf) 🐺 - вес: 50кг, скорость: 3, охота на травоядных
- **Медведь** (Bear) 🐻 - вес: 500кг, всеядный, ест растения и животных
- **Лиса** (Fox) 🦊 - вес: 8кг, скорость: 2, охотится на мелких животных
- **Орел** (Eagle) 🦅 - вес: 6кг, скорость: 3, летающий хищник
- **Удав** (Boa) 🐍 - вес: 15кг, скорость: 1, атакует из засады

### Травоядные (10 видов)

- **Лошадь** (Horse) 🐎 - вес: 400кг, скорость: 4
- **Олень** (Deer) 🦌 - вес: 300кг, скорость: 4
- **Кролик** (Rabbit) 🐇 - вес: 2кг, скорость: 2, быстро размножается
- **Мышь** (Mouse) 🐁 - вес: 0.05кг, скорость: 1
- **Коза** (Goat) 🐐 - вес: 60кг, скорость: 3
- **Овца** (Sheep) 🐑 - вес: 70кг, скорость: 3
- **Кабан** (Boar) 🐗 - вес: 400кг, скорость: 2
- **Буйвол** (Buffalo) 🐃 - вес: 700кг, скорость: 3
- **Утка** (Duck) 🦆 - вес: 1кг, скорость: 4, ест гусениц
- **Гусеница** (Caterpillar) 🐛 - вес: 0.01кг, основа пищевой цепи

### Поведение животных

- **Питание**: Вероятностное поедание based on food matrix
- **Движение**: Интеллектуальное движение хищников к добыче
- **Размножение**: При наличии партнера и достаточной сытости
- **Смерть**: От голода или быть съеденным

---

## <span style="font-family: 'Manrope_Cut_008 ExtraBold', sans-serif">Лицензия</span>
<a id="лицензия"> </a>
<p style="font-family: 'Manrope_Cut_008 Medium', sans-serif">
MIT License. Полный текст лицензии доступен в файле 
<a href="https://github.com/VasilevYuV/island-simulaion/edit/develop/LICENSE">LICENSE</a>.

</p>

---

<div align="center" style="font-family: 'Manrope_Cut_008 Medium', sans-serif; margin-top: 2rem;">

**🏝️ Откройте для себя удивительный мир дикой природы! 🐾**

_Многопоточная симуляция экосистемы на Java_

</div>
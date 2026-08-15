# CLAUDE.md — стандарти коду mobile-automation-framework

> Читати перед кожним код-рев'ю. Джерело істини — цей файл + реальний код у репо, а не Notion-нотатки (ті можуть відставати).

## 1. Стек і версії

- Java, Maven, TestNG, Allure
- Appium 3.6.0 · java-client 10.1.0 · XCUITest driver 12.1.0 · uiautomator2 driver (Android)
- selenide-appium 7.9.4 (виключення `selenium-java` — версію Selenium приносить `java-client`)
- Тестова апка: Sauce Labs My Demo App (iOS — нативна `.app`, Android — нативний apk `saucelabs/my-demo-app-android`)

Перед додаванням/оновленням будь-якої залежності — перевірити актуальну версію пошуком, не покладатись на пам'ять (стосується і Claude, і людини).

## 2. Архітектура

```
Driver (інтерфейс)
  → IOSDriver / AndroidDriver (реалізації під платформу)
  → DriverFactory (Factory — вибір реалізації за Platform enum)
  → DriverManager (ThreadLocal — ізоляція для паралельних тестів)
  → BaseMobileTest (lifecycle: @BeforeMethod/@AfterMethod)
  → Page Objects через Selenide-appium (ScreenObject.screen())
```

Пакети: `com.mobileframework.driver`, `.config`, `.mobile.pages`, `.models`, `.data`, `.api`, `.utils`, `.exceptions`.

Патерни й де вони живуть: Factory — `DriverFactory`; Singleton — `ConfigLoader`; ThreadLocal — `DriverManager`; Builder/POJO — `models/`; Page Object + Fluent — `mobile/pages/` (методи повертають наступну сторінку або `this`).

**Спільна логіка драйверів живе в самому інтерфейсі `Driver`** (Java 8+ дозволяє це): `static serverUrl()` — парсинг адреси Appium-сервера, `default stop()` — завершення сесії через `getAppiumDriver()`. Конкретні `IOSDriver`/`AndroidDriver` містять тільки платформо-специфічне: `start()` з відповідними Options і `getPlatformName()`.

Свідоме рішення: **абстрактного базового класу драйверів немає**. За Rule of Three абстракцію виносимо на третьому однаковому випадку, для двох платформ це передчасне ускладнення — інтерфейс зі static/default методами покриває все, крім методів зі станом. Якщо з'явиться третя платформа і дублю стане більше — тоді й з'явиться базовий клас, не раніше.

## 3. Крос-платформена стратегія

Дефолт: **один Page-клас на екран**, різні локатори через `@iOSXCUITFindBy` / `@AndroidFindBy` на тих самих полях. Окрема ієрархія сторінок під платформу — запасний варіант, застосовується тільки якщо флоу справді розходяться, не за замовчуванням.
Розділення LoginPage на IOSLoginPage/AndroidLoginPage — свідомий виняток, а не нова норма. Причина: на iOS флоу логіну принципово інший (обхід клавіатури через список демо-акаунтів). Для решти екранів дефолт незмінний — один клас, дві анотації на полі, як у ProductsPage і MoreMenuPage.

Пріоритет локаторів:

| Платформа | Порядок |
|---|---|
| iOS | accessibility id → name/label → NSPredicate → class chain → xpath |
| Android | resource-id → content-desc → UiAutomator → xpath |

xpath — завжди останній варіант, не перший вибір.

## 4. Конфігурація

`ConfigLoader` — Singleton, читає `config.properties` із classpath. Пріоритет джерел: system property → env variable (`KEY.WITH.DOTS` → `KEY_WITH_DOTS`) → файл. Нові ключі під платформу — за аналогією з наявними (`ios.bundleId` ↔ `android.appPackage`/`android.appActivity`).

Мертві/невикористані ключі в конфіг не залишати "про всяк випадок" — якщо ключ ніде не читається кодом, це технічний борг, виносити в беклог і чистити.

## 5. Формат тестів — AAA

Кожен тест: Arrange → Act → Assert, візуально розділені (порожній рядок або коментар). Один Act на тест, крім journey-тестів (наскрізні сценарії, де кілька дій — це і є сенс тесту).

## 6. Waits

Ніколи не змішувати implicit і explicit waits в одному драйвері/тесті. Зараз у проєкті тимчасово стоїть `implicitlyWait` в `start()` з позначкою TODO — прибрати, коли explicit waits будуть у `BasePage`. Нову implicit-логіку поверх цього не додавати.

## 7. Дебаг — не гадати

При будь-якій незрозумілій поведінці: спочатку логи Appium-сервера, page source / Inspector, `mvn dependency:tree` (конфлікти версій), самі конфіги. Гіпотези без перевірки — не приймаються як відповідь.

## 8. Git workflow

## 8. Git workflow

Кожна задача — окрема гілка. Префікс за характером змін:

| Префікс | Коли | Приклад |
|---|---|---|
| `feature/` | новий функціонал, компонент, тест | `feature/android-driver` |
| `fix/` | виправлення багу в наявному коді | `fix/login-keyboard-overlap` |
| `docs/` | тільки документація, без змін коду | `docs/claude-md-and-readme` |
| `chore/` | інфраструктура, конфіги, залежності, CI | `chore/editorconfig` |

Назва гілки завжди фігурує в задачі до код-рев'ю. Цикл: гілка → коміт(и) → push → PR → рев'ю → виправлення → merge. Без прямих комітів у `main`.

**Коміт-меседжі** — короткий описовий рядок англійською в імперативі, без conventional-commits префіксів (`feat:`, `fix:` тощо): в історії репо прийнято саме так, напр. `Add ConfigLoader singleton and ThreadLocal DriverManager with lifecycle test`. Меседж описує результат, а не процес.

Мерж — **squash**, щоб проміжні й merge-коміти не засмічували `main`.
## 9. CI/CD

GitHub Actions, джоб `Unit & API tests` на `ubuntu-latest`: `mvn -B test` — тобто **тільки юніт- і API-тести**. Мобільні тести (`-Pmobile`) у CI не запускаються: потрібен Appium-сервер, емулятор/симулятор і апка, якої немає в репо. Allure-звіт публікується на GitHub Pages у підтеку `pr-<номер>` при кожному прогоні (`if: always()`, тобто і на червоних тестах). Мобільний смок наразі — ручний, локально.
**SonarCloud Quality Gate** висить на кожному PR. Найчутливіший поріг — **дублювання на новому коді ≤ 3%**: копіпаст між платформними класами валить гейт (реальний випадок — PR #12 дав 44%, поки спільну логіку не винесли в `Driver`). Sonar також підсвічує нові `TODO`-коментарі — не блокує, але краще заводити задачу, а не лишати коментар назавжди.

## 10. Naming

Класи — `PascalCase` (`LoginPage`, `DriverFactory`), методи — `camelCase` дієслівні (`loginAsListedUser`, `shouldBeOpened`). Page Object методи повертають тип наступного екрана або `this` для чейнінгу — не `void`.

## 11. Логування

`println` — заборонено (є у беклозі на видалення з існуючого коду, новий код узагалі без нього). Використовувати `slf4j` (задача підключити `slf4j-simple` як байндинг ще у беклозі — доки не підключено, новий клас теж не повинен додавати println).

## 12. Тестові дані

Публічні demo-креди виносити в `TestCredentials` / `config.properties`, не хардкодити в тілі тесту. Секрети (реальні API-ключі тощо) — тільки через env variable, ніколи в git.

## 13. Відомий технічний борг (не чіпати мимохідь, але і не плодити новий поруч)

`loginAs(Credentials)` зламаний через клавіатуру · немає негативного тесту з `wrongPassword` · частина тестів ще не під AAA · мертві ключі конфіга (`login.locked.username`, `ios.udid`) · slf4j-байндинг не підключено (`No SLF4J providers were found` у кожному прогоні) · `println` замість slf4j · Selenide reports у `target/` не прибираються.

Android: драйвер робочий, смок зелений. **Не зроблено:** Page Objects під Android (`@AndroidFindBy`), cross-platform `LoginTest`, встановлення апки через капабіліті `app` замість ручного `adb install` (без нього Android у CI не запуститься — тека `apps/` у `.gitignore`), explicit waits замість `implicitlyWait`. Останні дві заведені задачами в Pet Project Tasks.

Якщо рев'ю зачіпає файл із боргом поруч зі своєю задачею — не виправляти мовчки в тому ж PR, виносити окремим пунктом або окремою задачею.

## 14. Чек-лист перед merge

- Гілка названа за конвенцією, PR посилається на задачу
- Нема нових `println`, implicit+explicit waits разом, xpath як перший локатор
- Нові конфіг-ключі не дублюють і не лишають мертвих
- Тест — AAA, один Act (крім journey)
- Версії залежностей у PR-описі перевірені пошуком, якщо змінювались
- CI зелений (smoke на `-Pmobile`), Allure-звіт додався

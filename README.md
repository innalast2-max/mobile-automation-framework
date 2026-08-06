# mobile-automation-framework

Мобільний automation-фреймворк на Java + Appium (iOS/Android) з API-шаром (RestAssured). Стандарти коду й правила рев'ю — у [`CLAUDE.md`](CLAUDE.md), читати перед PR.

## Стек

Java · Maven · TestNG 7.10.2 · Appium java-client 10.1.0 · selenide-appium 7.9.4 (виключення `selenium-java` — версію Selenium приносить `java-client`) · RestAssured 5.5.0 · Allure 2.15.2 · GitHub Actions.

Тестова апка: Sauce Labs My Demo App — iOS нативна `.app`, Android нативний apk (`saucelabs/my-demo-app-android`), обидві лежать у `src/test/resources/apps/` (не в git, див. `.gitignore`).

## Запуск тестів

Мобільні тести (`**/tests/mobile/**`) за замовчуванням **виключені** зі звичайного `mvn test` — вони потребують запущеного Appium-сервера й підключеного симулятора/емулятора. Вмикаються через профіль:

```bash
mvn test -Pmobile
```

Appium-сервер піднімається окремо в терміналі (`appium`), симулятор/емулятор має бути запущений і доступний.

## Перемикання платформи (iOS / Android)

`platform` у `config.properties` — дефолт для запуску без параметрів (наприклад, коли тест запускається кнопкою ▶ в IDE, без жодних `-D`). Не тримаємо два рядки `platform=` одночасно — `Properties` мовчки бере останній, це заплутано при рев'ю. Один рядок, одне значення.

Щоб прогнати іншу платформу разово, не редагуючи файл — `ConfigLoader` читає system property/env **раніше** за файл, тому досить override у команді:

```bash
mvn test -Pmobile -Dplatform=ANDROID -Dtest=AppiumConnectionSmokeTest
```

Так само працює через env-змінну (зручно для CI): `PLATFORM=ANDROID mvn test -Pmobile`.

Файловий дефолт навмисно лишаємо заповненим (не порожнім) — якщо ключа немає ніде, `Platform.valueOf(null)` впаде голим `NullPointerException` замість зрозумілої помилки.

Пріоритет джерел конфігурації (`ConfigLoader.getProperty`): **system property → env variable → `config.properties`**.

## Архітектура коротко

```
Driver (інтерфейс) → IOSDriver / AndroidDriver → DriverFactory → DriverManager (ThreadLocal) → BaseMobileTest → Page Objects (Selenide-appium)
```

Спільна логіка драйверів — у самому інтерфейсі `Driver`: `static serverUrl()` і `default stop()`. Платформні класи містять тільки `start()` з відповідними Options (`XCUITestOptions` / `UiAutomator2Options`) і `getPlatformName()`.

Детальніше — патерни, крос-платформена стратегія, локатори, naming — у [`CLAUDE.md`](CLAUDE.md).

## Конфігурація застосунку під платформу

| | iOS | Android |
|---|---|---|
| Ідентифікація вже встановленого застосунку | `ios.bundleId` | `android.appPackage` + `android.appActivity` |
| Формат `appActivity` | — | без префіксу пакета, тільки хвіст після `/` (напр. `.view.activities.SplashActivity`) — значення з `adb shell cmd package resolve-activity --brief <package>` містить `package/activity`, префікс треба відкинути |

Наразі застосунок має бути **встановлений на пристрої заздалегідь** — драйвер його лише запускає за цими ідентифікаторами, не інсталює:

```bash
adb install src/test/resources/apps/mda-2.2.0-25.apk        # Android
xcrun simctl install booted "src/test/resources/apps/My Demo App.app"   # iOS
```

Тека `apps/` у `.gitignore`, тож на CI-раннері її немає — для Android у CI потрібне встановлення через капабіліті `app` (окрема задача в беклозі).

## CI

GitHub Actions (`Unit & API tests`): `mvn -B test` — юніт і API. Мобільні тести в CI не бігають (див. «Запуск тестів»), це ручний крок локально. Allure-звіт публікується на GitHub Pages у підтеку `pr-<номер>`, зокрема й для впалих прогонів.


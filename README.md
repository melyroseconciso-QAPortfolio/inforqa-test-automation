# InforQA – Test Automation Framework (starter)

A beginner-friendly web test automation framework, built with **Java, Selenium, Cucumber (BDD) and Maven**.
It runs simple, read-only health checks against the public Infor website (https://www.infor.com).

## One-time setup (about 20 minutes)

1. **Install Java 17 (JDK)** – download "Temurin 17" from https://adoptium.net and install it.
2. **Install Maven** – https://maven.apache.org/download.cgi (or `brew install maven` on Mac, `choco install maven` on Windows).
3. **Install Google Chrome** (the framework downloads the matching driver automatically).
4. **Install a code editor** – IntelliJ IDEA Community or VS Code (with the "Extension Pack for Java").
5. Check it worked – open a terminal and run:
   ```
   java -version
   mvn -version
   ```
   Both should print a version number (Java 17 or higher).

## Run the tests

Open a terminal **inside this folder** and run:

```
mvn test
```

The first run downloads libraries, so it takes a few minutes. After that:

| What you want | Command |
|---|---|
| Run everything | `mvn test` |
| Only quick checks | `mvn test -Dcucumber.filter.tags="@smoke"` |
| Watch the browser (not hidden) | `mvn test -Dheadless=false` |
| Use Firefox | `mvn test -Dbrowser=firefox` |

## See the results

Open `target/cucumber-reports/report.html` in your browser. Failed scenarios include a screenshot.

## How the project is organised

```
src/test/resources/features/   <- Tests written in plain English (.feature files)
src/test/java/.../steps/       <- Java code behind each plain-English sentence
src/test/java/.../pages/       <- Page Objects: how to find things on each page
src/test/java/.../core/        <- Browser start-up and settings
src/test/java/.../runner/      <- The "start button" Maven runs
src/test/resources/config.properties <- Website address, browser, timeouts
```

Read the code in this order: `infor_site.feature` -> `InforSteps.java` -> `HomePage.java` -> `BasePage.java` -> `DriverFactory.java`.

## If a test fails on the first run

That is normal and part of learning. The website may have changed since this was written.
Look at the screenshot in the report, then adjust the page address in the `.feature` file
(the `/products`, `/industries` list) or the element locator in `HomePage.java`.
Be considerate: these tests only read pages. Do not add tests that submit forms or send heavy traffic.

## Roadmap

- [x] Phase 1-3: Framework core + BDD (this starter)
- [ ] Phase 4: Allure reporting and test-run video
- [ ] Phase 5: API tests with Rest Assured
- [ ] Phase 6: Mobile (Appium) and BrowserStack
- [ ] Phase 7: Dockerfile and GitLab CI/CD pipeline
- [ ] Phase 8: Kubernetes/Helm Selenium Grid, UiPath bot
- [ ] Phase 9: AI-assisted failure analysis

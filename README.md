# Karate Framework AGEST-VN

Reference: [https://github.com/karatelabs/karate/blob/develop/karate-core/README.md](https://github.com/karatelabs/karate/blob/develop/karate-core/README.md)

## Using the Framework

### 1. Driver Configuration

* **Multiple Drivers Setup**:
  This allowed user to freely switch to different driver/platform during a test.
  You can configure multiple drivers in the same test feature:
  ```
  * startConfig('chrome', 'chrome1')
  * startConfig('chrome', 'chrome2')
  ```
* **Switching Drivers**:
  Use `switchDriver` to toggle between drivers:
  ```
  * switchDriver('chrome1')
  ```
* **Customizing Driver Settings**:
  Modify driver configurations in `/resources/config/driverSettings.js`:
  ```json
    chrome: {
      type: 'chrome',
      platform: '',
      settings: {
          type: 'chrome',
          addOptions: ['--incognito', '--start-maximized']
      }
    }
  ```

### 2. Framework Path Configuration

* **Business Objects**:
  `classpath:/apps/<app_name>/business/<file_name>.feature`
* **Locators**:
  `classpath:/apps/<app_name>/locator/<page_name>/<file_name>.json`
* **Test Data**:
  `classpath:/resources/data/<app_name>/<data_name>.json`
* **Calling Files**
  `# Business features are automatically called from framework`
  `* def controls = locator('<app_name>', '<file_name>')`
  `* def appData = data('<app_name>', '<data_name>')`

### 3. High Level Action Example

```karate
@ignore
Feature: Select left menu
  Background:
    * def controls = locator('tademostoreweb', 'basepage')

  Scenario: Select left menu
    * print 'DEFAULT: select left menu ' + menu
    * mouse(format(controls.genericLabel, 'All departments')).go()
    * click(format(controls.genericLink, menu))
```

### 4. Test Feature Example

```karate
Feature: Sample Karate Test Script
For help, see: https://github.com/karatelabs/karate/wiki/IDE-Support

  Background:
    * def userData = data('railway', 'users')
    * def loginPage = locator('railway', 'loginpage')
    * startConfig('chrome', 'chrome1')
    * startConfig('chrome', 'chrome2')

  Scenario: Log in
    * switchDriver('chrome2')
    * driver "http://www.saferailway.somee.com/"
    * click("//a[@href='/Account/Login.cshtml']")
    * input(loginPage.userTextbox, data.username)
    * input(loginPage.passTextbox, data.password)
    * click(loginPage.okButton)
    * switchDriver('chrome1')
    * driver 'https://www.google.com.vn'
```


### 5. Auto Complete Plugin

----------------------------
* Karate Framework itself does not include auto-complete for javascript or Java class methods
* It also does not have auto-complete for its own feature files calling
* AGEST Karate Framework offer an IntelliJ Plugin that support auto-complete for Karate
* This plugin's purpose is to help tester to manage code libraries for easier test implementation
----------------------------

* **Plugin Setup**
1. Go to `IntelliJ Setting -> Plugins... -> Gear Icon -> Install Plugin from Disk...`
2. Select assets/karate-plugin-1.0.0.zip and install this plugin

* **Using Auto complete**
> Use Ctrl-Space to trigger auto-complete list

* **If auto complete not working: File -> Close Projects and restart IntelliJ**

### 6. Auto download web driver
----------------------------
* Agest Karate Framework allow auto downloading web driver that is matching with local machine browser version
* You just need to leave empty string at executable capability for corresponding web driver type at driverSettings to make it work
----------------------------

Auto download chromedriver:
```json
        chromedriver_multi: {
                settings: {
                    start: true,
                    platformName: 'chrome',
                    type: 'chromedriver',
                    showDriverLog: true,
                    executable: '',
                    webDriverSession: { capabilities:
                        { alwaysMatch:
                            {
                            browserName: 'chrome',
                            'goog:chromeOptions':
                                { args: ['--incognito', '--start-maximized'] }
                            }
                        }
                    }
                }
        }
```

### 7. Running Karate Tests

* **Create a Runner Class and Run this class file using JUnit**:

  ```java
    package testcases.railway.login;
    
    import com.intuit.karate.junit5.Karate;
    
    public class LoginTest {
        @Karate.Test
        Karate testLogin() {
        String testName = 'login.feature';
        return Karate.run(testName).relativeTo(getClass());
        }
    }
  ```
* **Custom Runtime Configuration in IntelliJ IDEA**:

1. Create testcases.KarateRunner class

   ```java
    import com.intuit.karate.junit5.Karate;

    public class testcases.KarateRunner {
        @Karate.Test
        Karate runTest() {
            String filePath = System.getProperty("karate.test");
            if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("Error");
            }
            String fileName = filePath.replace('\\', '/');
            return Karate.run(fileName).relativeTo(testcases.KarateRunner.class);
        }
    }
   ```
2. Open IntelliJ IDEA, go to `Run` -> `Edit Configurations`.
3. Create or modify a runtime configuration using JUnit as a template.
4. Name your configuration, e.g., "KarateRunnerExc".
5. Input Class as `testcases.KarateRunner`
6. Use Dynamic Macros for program arguments: `-ea -Dkarate.test=$FilePathRelativeToSourcepath$`.
7. Save and use this configuration to run your test files.

### 6. Running Karate Tests with Maven

- **Navigate to Your Project Directory**: Open a terminal and navigate to the root directory of your project.
- **Run the Tests**: Use the following Maven command to run your Karate tests:

  ```bash
  mvn test -Dkarate.options="--tags @yourTag" -Dtest=YourTestRunnerClass
  ```
  => `-Dkarate.options="--tags @yourTag"`: This option allows you to filter which tests to run by specifying a tag. You can remove this option if you want to run all tests.
  => `-Dtest=YourTestRunnerClass`: Replace `YourTestRunnerClass` with the name of your Karate test runner class. This class should be annotated with `@Test`.
- **Running All Tests**: If you want to run all the tests in the project, you can simply use:

  ```bash
  mvn test
  ```
- Running Karate Tests with Maven enable tests to run through Command Line and CICD tools.

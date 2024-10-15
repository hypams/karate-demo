# Karate Framework AGEST-VN

Reference: [https://github.com/karatelabs/karate/blob/develop/karate-core/README.md](https://github.com/karatelabs/karate/blob/develop/karate-core/README.md)

## Installation and Setup

### 1. Required Installations:

* Karate
* Appium
* Android SDK Platform Tools

**Note**: Add `appium` and `adb` to your system's `PATH` environment variable.

#### Optional (Skip if not required):

* Appium Windows Driver
* WinAppDriver

### 2. Mobile Test Setup (Appium Android)

***Setup Guideline***

Follow these steps to set up your environment:

1. **Install Node.js**

   - Download and install Node.js from [nodejs.org](https://nodejs.org/).
2. **Download SDK Platform Tools**

   - Download at: https://developer.android.com/tools/releases/platform-tools
   - Set up the system environment variable `ANDROID_HOME` to point to your Android SDK installation.
   - Add `%ANDROID_HOME%` to your system `Path` environment variable.
3. **Install Appium and the UIAutomator2 Driver**

   - Open your terminal or command prompt and run the following commands:
     ```bash
     npm install -g appium
     appium driver install uiautomator2
     ```
4. **Download Appium Inspector**

   - Use Appium Inspector to check app interfaces. Configure the app capabilities as follows:
     ```json
     {
       "capabilities": {
         "appium:automationName": "uiautomator2",
         "platformName": "Android",
         "deviceName": "emulator-5554",
         "appium:appPackage": "com.android.settings",
         "appium:appActivity": "com.android.settings.Settings",
         "appium:noReset": "true"
       }
     }
     ```
   - To find the `appPackage` and `appActivity`, open the app on your phone and run the following commands in your command prompt or terminal:
     ```bash
     adb shell
     dumpsys window | grep -E 'mCurrentFocus'
     ```
5. **Download Java JDK 22**

   - Install JDK 22 from [AdoptOpenJDK](https://adoptium.net/) or the [Oracle website](https://www.oracle.com/java/technologies/javase-downloads.html).
   - Set the `JAVA_HOME` environment variable to the path of the JDK installation.
   - Add the JDK bin directory to your system `Path`.
6. **Install Android Studio (optional)**

   - Skip this step if you are using real device, this step is to set up Android Emulator
   - Download and install Android Studio from [developer.android.com/studio](https://developer.android.com/studio).
   - Use Android Studio to set up and manage your Android Emulator.
7. **Enable USB Debugging on Real Device**
8. **Customize Driver Settings for Android**:
   Modify driver configurations in `/resources/config/driverSettings.js`:

   ```json
     android_local: {
         settings: { type: 'android',
             webDriverUrl:'http://localhost:4723',
             webDriverPath : '/',
             start: true,
             httpConfig : { readTimeout: 160000 },
             webDriverSession: { capabilities:
                 {
                 "alwaysMatch": {
                   "platformName": "Android",
                   "appium:platformVersion": "14.0",
                   "appium:deviceName": "R5CTA2TV7NE",
                   "appium:automationName": "UiAutomator2",
                   "appium:appPackage": "com.android.settings",
                   "appium:appActivity": "com.android.settings.Settings",
                   "appium:connectHardwareKeyboard": true,
                   "appium:newCommandTimeout": 300,
                   "appium:noReset": false
                   },
                 "firstMatch": [{}]
                 }
             }
         }
     }
   ```

### 3. Mobile Setup (Appium iOS)

Reference: [setup_ios_appium_on_mac_2024.docx](assets/setup_ios_appium_on_mac_2024.docx)

**Setup iOS Appium Environment on macOS**

1. **Install Necessary Tools**
   1.1. **Install Xcode**

   * Select the Xcode version to install based on your device’s iOS and macOS version. Refer to the [Xcode Support Page](https://developer.apple.com/support/xcode) for details on minimum requirements and supported SDKs.
   * Ensure that Xcode is located in the "Applications" folder. WebDriverAgent will not run if Xcode is located elsewhere.
   * If you installed Xcode using an offline file, drag and drop the Xcode app into the "Applications" folder.
   * Check Xcode's path using the command:
     ```bash
     xcode-select -p
     ```
   * If the result is not `/Applications/Xcode.app/Contents/Developer`, set the path again using:
     ```bash
     sudo xcode-select -switch /Applications/Xcode.app/Contents/Developer
     ```

   1.2. **Install Homebrew**

   * Run the command:
     ```bash
     /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
     ```
   * If you encounter a permission error, follow the steps in this [OSXDaily guide](https://osxdaily.com/2018/10/09/fix-operation-not-permitted-terminal-error-macos/) to add full permission for the command line tool, or use:
     ```bash
     sudo chown -R $(whoami) $(brew --prefix)/*
     ```

   1.3. **Install Node.js and npm**

   * Get npm from [npm's website](https://www.npmjs.com/get-npm).
   * Verify installation by running:
     ```bash
     npm -v
     ```

   1.4. **Install Maven**

   * Run the command:
     ```bash
     brew install maven
     ```
   * Verify installation by running:
     ```bash
     mvn -v
     ```

   1.5. **Install Appium (Latest Version)**

   * Run the command:
     ```bash
     npm install -g appium
     ```
   * Refer to [Appium's official site](http://appium.io/) for more information.

   1.6. **Install XCUITest**

   * Run the command:
     ```bash
     brew install libimobiledevice
     ```

   1.7. **Install Carthage**

   * Run the commands:
     ```bash
     brew install carthage
     brew install ios-deploy
     ```

   1.8. **Install ios-webkit-debug-proxy**

   * Run the command:
     ```bash
     brew install ios-webkit-debug-proxy
     ```

   1.9. **Install Appium Doctor**

   * Run the command:
     ```
     npm install -g appium-doctor
     ```
   * Check if the setup is correct. Ensure Xcode is installed at `/Applications/Xcode.app/Contents/Developer` and that Xcode Command Line Tools are correctly set up.
2. **Connect Device to Xcode**
   2.1. **Real Device**

   * Connect your device to your Mac using a cable.
   * Open Xcode and navigate to `Window > Devices and Simulators > Devices` tab.
   * Ensure your device information appears, indicating a successful connection.

   2.2. **Remote Testing Environment (RTE)**

   * Rent an iPhone if needed.
   * Click the Xcode icon and enter your Mac password. Wait for a success message.
   * Open Xcode (close and reopen if it’s running), and go to `Window > Devices and Simulators > Devices` tab.
   * Wait for your device information to display (about 5 minutes).
3. **Configure WebDriverAgent**

   * Find the location of `WebDriverAgent.xcodeproj` using:
     ```
     echo "$(dirname "$(find "$HOME/.appium" -name WebDriverAgent.xcodeproj)")"
     ```
   * Navigate to the Appium directory:
     * Right-click in Finder, select “Go to Folder,” and enter the location from the previous step.
   * Open `WebDriverAgent.xcodeproj` in Xcode.
   * Sign in with an Apple Developer account and select a team.
   * If the Bundle Identifier fails, change the bundle name to a unique one if needed.
   * Select `WebDriverAgentLib`, choose your connected device, check “Automatically Manage Signing,” select a team, and build.
   * Repeat for `IntegrationApp` and `WebDriverAgentRunner`, selecting the target device, checking “Automatically Manage Signing,” and building each one.
   * To install `WebDriverAgent` on the device:
     * Select `Product > Scheme > WebDriverAgentRunner`.
     * On the menu bar, select `Product > Test`.
   * After a successful install, an error message may appear; this is normal.
   * Verify `WebDriverAgent` is installed on the device.
   * Trust `WebDriverAgent` on the device: Navigate to `Settings > General > VPN & Device Management`, select `WebDriverAgent`, and tap the “Trust” button.
   * Enable the following settings on the device:
     * `Settings > Privacy & Security > Developer Mode`
     * `Settings > Developer > Enable UI Automation`
     * `Settings > Safari > Advanced > Web Inspector`
   * Get the device UDID using:
     ```
     xcrun xctrace list devices
     ```
   * Create an Appium session to verify the setup. Download Appium Inspector from [here](https://github.com/appium/appium-inspector/releases) and use the following capabilities:
     ```json
     {
       "platformName": "iOS",
       "deviceName": "iPhone",
       "platformVersion": "16.6",
       "udid": "<device_udid>",
       "automationName": "XCUITest",
       "browserName": "Safari"
     }
     ```
   * Start the Appium server using:
     ```bash
     appium
     ```
   * If the session is created successfully, your setup is complete.
4. **Run iOS Test Suite**

   * Update the UDID:
     * Open the file `[Project path]/src/test/resources/browsers.setting.properties`.
     * Change the UDID for `[iosdemo.native]` to the one collected from the previous step.
   * Ensure `WebDriverAgent` is installed. If not, refer to the "Configure WebDriverAgent" section for details. Note that `WebDriverAgent` will expire after 7 days with a free Apple account, so reinstall it as needed.
   * In Finder, navigate to the project folder, right-click, and select “New Terminal at Folder.”
   * Run the command:
     ```bash
     sh run.sh
     ```
   * Check the report in `[project path]/test-output`.
5. **Common Errors**
   5.1. **Sudo Command Not Found**

   * Run the command:
     ```bash
     export PATH=/usr/bin:/usr/sbin:/bin:/usr/local/bin:/sbin:/opt/x11/bin:$PATH
     ```

   5.2. **Cannot Open `webdriveragent.xcodeproj` Due to Xcode Compatibility Version**

   * Right-click on `webdriveragent.xcodeproj`, select “Show Package Contents,” and open `project.pbxproj` with TextEdit.
   * Edit `objectVersion` based on your Xcode version:
     * For Xcode 10.0: `objectVersion = 51`
     * For Xcode 9.3: `objectVersion = 50`
     * For Xcode 8.0-9.2: `objectVersion = 48`
     * For Xcode 6.3-7.3.1: `objectVersion = 47`

   5.3. **Could Not Locate Device Support Files**

   * Follow the instructions [here](https://github.com/filsv/iOSDeviceSupport) to resolve the issue.

   5.4. **Failed to Create Provisioning Profile**

   * Go to the “Build Settings” tab in Xcode.
   * Add random text to “Product Bundle Identifier.”
   * Return to the “General” tab and wait for Xcode to accept the changes. If it does not, try changing the “Product Bundle Identifier” again.
6. **Finding app bundleId**

>> https://pspdfkit.com/guides/ios/faq/finding-the-app-bundle-id/
>>

## Using the Framework

### 1. Driver Configuration

* **Multiple Drivers Setup**:
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

* **Page Objects**:
  `classpath:/apps/<app_name>/business/<page_name>/<file_name>.feature`
* **Locators**:
  `classpath:/apps/<app_name>/locator/<page_name>/<file_name>.json`
* **Test Data**:
  `classpath:/resources/data/<app_name>/<data_name>.json`

### 3. Page Object Example

```karate
@ignore
Feature: Login Page

  Background:
    * def controls = locator('railway','loginpage') //locator('<app_name>','<page_name>')
    * def data = data('railway','data') //data('<app_name>','<data_name>')

  @methods
  Scenario:
    * def featurePath = feature('railway', 'loginpage', 'loginpage') //feature('<app_name>', '<page_name>', '<feature_filename>') 
    * def login = method(featurePath, '@login') //method(featurePath, '<@method_name>')

  @login
  Scenario: login to app
    * input(controls.usernameTextbox, username)
    * input(controls.passwordTextbox, password)
    * click(controls.loginButton)
```

### 4. Test Feature Example

```karate
Feature: Sample Karate Test Script
For help, see: https://github.com/karatelabs/karate/wiki/IDE-Support

  Background:
    * def userData = data('railway', 'users') //data('<app_name>','<data_name>')
    * def homePage = business('railway', 'homepage', 'test') //business('<app_name>','<page_name>','<platform>')
    * def loginPage = business('railway', 'loginpage')
    * startConfig('chrome', 'chrome1')
    * startConfig('chrome', 'chrome2')

  Scenario: Log in
    * switchDriver('chrome2')
    * call homePage.navigateToApp
    * call homePage.goToLogin
    * switchDriver('chrome1')
    * driver 'https://www.google.com.vn'
```

### 5. Running Karate Tests

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

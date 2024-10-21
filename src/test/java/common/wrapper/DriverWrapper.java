package common.wrapper;

import com.intuit.karate.core.ScenarioEngine;
import com.intuit.karate.core.ScenarioRuntime;
import com.intuit.karate.driver.Driver;
import com.intuit.karate.driver.DriverRunner;
import com.intuit.karate.driver.Target;
import com.intuit.karate.driver.chrome.Chrome;
import com.intuit.karate.driver.chrome.ChromeWebDriver;
import com.intuit.karate.driver.firefox.GeckoWebDriver;
import com.intuit.karate.driver.microsoft.*;
import com.intuit.karate.driver.playwright.PlaywrightDriver;
import com.intuit.karate.driver.safari.SafariWebDriver;
import common.wrapper.driver.AndroidDriver;
import common.wrapper.driver.IosDriver;
import common.wrapper.driver.WindowsDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.HashMap;
import java.util.Map;

/**
 * A wrapper class for initializing and managing different types of web and mobile drivers
 * using the Karate Framework.
 */
public class DriverWrapper {
    /**
     * The driver instance used for interacting with the browser or mobile platform.
     */
    public Driver driver;

    /**
     * The platform name on which the driver is running (e.g., "chrome", "android").
     */
    public Map<String, Object> settings;

    /**
     * Constructor for DriverWrapper. Initializes the driver with the specified options.
     *
     * @param options A map containing the configuration options for the driver.
     *                The "type" key specifies the type of the driver (e.g., "chrome", "android").
     */
    public DriverWrapper(Map<String, Object> options) {
        this(options, ScenarioEngine.get().runtime);
    }

    /**
     * Constructor for DriverWrapper. Initializes the driver with the specified options and ScenarioRuntime.
     *
     * @param options A map containing the configuration options for the driver.
     *                The "type" key specifies the type of the driver (e.g., "chrome", "android").
     * @param sr      The ScenarioRuntime instance for managing the scenario's lifecycle.
     */
    public DriverWrapper(Map<String, Object> options, ScenarioRuntime sr) {
        String type = (String) options.getOrDefault("type", "chrome");
        String executable = (String) options.getOrDefault("executable", null);
        Target target = (Target) options.get("target");

        try {
            if (target != null) {
                sr.logger.debug("Custom target configured, calling start()");
                Map<String, Object> map = target.start(sr);
                sr.logger.trace("Custom target returned options: {}", map);
                options.putAll(map);
            }

            Map<String, DriverRunner> drivers = driverRunners();
            DriverRunner driverRunner = drivers.getOrDefault(type, drivers.get("chrome"));

            // Automatically download suitable webdriver
            if (executable != null && executable.isEmpty()) {
                executable = resolveWebDriverExecutable(type);
                options.put("executable", executable);
            }

            this.driver = driverRunner.start(options, sr);
            this.settings = options;

        } catch (Exception e) {
            String message = "Driver config / start failed: " + e.getMessage() + ", options: " + options;
            sr.logger.error(message, e);

            if (target != null) {
                target.stop(sr);
            }

            throw new RuntimeException(message, e);
        }
    }

    /**
     * Provides a map of DriverRunner instances for different browser and platform types.
     * The map contains entries for types like "chrome", "android", "ios", etc.
     *
     * @return A map of driver types to their corresponding DriverRunner implementations.
     */
    private static Map<String, DriverRunner> driverRunners() {
        Map<String, DriverRunner> driverRunners = new HashMap<>();
        driverRunners.put("chrome", Chrome::start);
        driverRunners.put("msedge", EdgeChromium::start);
        driverRunners.put("chromedriver", ChromeWebDriver::start);
        driverRunners.put("geckodriver", GeckoWebDriver::start);
        driverRunners.put("safaridriver", SafariWebDriver::start);
        driverRunners.put("msedgedriver", MsEdgeDriver::start);
        driverRunners.put("mswebdriver", MsWebDriver::start);
        driverRunners.put("iedriver", IeWebDriver::start);
        driverRunners.put("winappdriver", WinAppDriver::start);
        driverRunners.put("android", AndroidDriver::start);
        driverRunners.put("ios", IosDriver::start);
        driverRunners.put("playwright", PlaywrightDriver::start);
        driverRunners.put("windows", WindowsDriver::start);
        return driverRunners;
    }

    /**
     * Download webdriver for browser type.
     *
     * @return The downloaded driver path.
     */
    private static String resolveWebDriverExecutable(String type) {
        WebDriverManager wrm;
        switch (type) {
            case "chromedriver": {
                wrm = WebDriverManager.chromedriver();
                break;
            }
            case "geckodriver": {
                wrm = WebDriverManager.firefoxdriver();
                break;
            }
            case "msedgedriver": {
                wrm = WebDriverManager.edgedriver();
                break;
            }
            case "iedriver": {
                wrm = WebDriverManager.iedriver();
                break;
            }
            default: {
                return "";
            }
        }
        wrm.setup();
        return wrm.getDownloadedDriverPath();
    }
}

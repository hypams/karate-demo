package common.wrapper;

import com.intuit.karate.core.ScenarioEngine;
import com.intuit.karate.driver.Driver;
import common.helper.java.BusinessHelper;

import java.util.*;

/**
 * Utility class for managing driver instances across different threads in Karate tests.
 * It handles driver initialization, configuration, switching between drivers, and quitting all drivers.
 */
public class DriverUtils {

    // Thread-local storage for managing driver instances and current driver key.
    private static ThreadLocal<Map<String, DriverWrapper>> drivers = ThreadLocal.withInitial(HashMap::new);
    private static ThreadLocal<String> currentKey = new ThreadLocal<>();
    private static ThreadLocal<String[]> currentDriverConfigs = new ThreadLocal<>();

    // Static map to store global driver settings.
    private static Map<String, Object> driverSettings = new HashMap<>();

    /**
     * Sets the global driver settings that will be used during driver initialization.
     *
     * @param settings a map containing the driver settings.
     */
    public static void setDriverSettings(Map<String, Object> settings) {
        driverSettings.putAll(settings);
    }

    /**
     * Sets current driver configs
     * @param configs a string of target driver configs
     */
    public static void setCurrentDriverConfigs(String configs) {
        setCurrentDriverConfigs(configs.split(","));
    }

    /**
     * Sets current driver configs
     * @param configs a string array of target driver configs
     */
    public static void setCurrentDriverConfigs(String[] configs) {
        currentDriverConfigs.set(configs);
    }

    /**
     * Initializes the driver map for the current thread.
     */
    public static void init() {
        drivers.set(new HashMap<>());
    }

    /**
     * Starts a driver based on a given configuration and optionally a custom key.
     *
     * @param config the configuration name from the driver settings.
     * @param key    an optional key to identify the driver. If null, the driver type is used as the key.
     */
    public static void startConfig(String config, String key) {
        Map<String, Object> driverConfig = getDriverSetting(config);
        startDriver(driverConfig, key);
    }

    /**
     * Starts a driver based on a current driver config index and optionally a custom key.
     *
     * @param index the driver config index.
     * @param key    an optional key to identify the driver. If null, the driver type is used as the key.
     */
    public static void startConfig(int index, String key) {
        Map<String, Object> driverConfig = getCurrentDriverConfigSetting(index-1);
        startDriver(driverConfig, key);
    }

    /**
     * Starts a driver based on a given configuration using the driver type as the key.
     *
     * @param config the configuration name from the driver settings.
     */
    public static void startConfig(String config) {
        startConfig(config, null);
    }

    /**
     * Starts a driver based on a current driver config index using the driver type as the key.
     *
     * @param index the driver config index.
     */
    public static void startConfig(int index) {
        startConfig(index, null);
    }

    /**
     * Starts a driver based on the specified browser type and an optional custom key.
     *
     * @param browserType the type of the browser (e.g., "chrome", "firefox").
     * @param key         an optional key to identify the driver. If null, the browser type is used as the key.
     */
    public static void start(String browserType, String key) {
        startDriver(Collections.singletonMap("type", browserType), key);
    }

    /**
     * Starts a driver based on the specified browser type using the browser type as the key.
     *
     * @param browserType the type of the browser (e.g., "chrome", "firefox").
     */
    public static void start(String browserType) {
        start(browserType, null);
    }

    /**
     * Starts a driver based on the given options and an optional custom key.
     *
     * @param options a map containing the driver options.
     * @param key     an optional key to identify the driver. If null, the driver type is used as the key.
     */
    public static void start(Map<String, Object> options, String key) {
        startDriver(options, key);
    }

    /**
     * Starts a driver based on the given options using the driver type as the key.
     *
     * @param options a map containing the driver options.
     */
    public static void start(Map<String, Object> options) {
        start(options, null);
    }

    /**
     * Switches the current driver to the one identified by the specified key.
     *
     * @param key the key identifying the driver to switch to.
     */
    public static void switchDriver(String key) {
        currentKey.set(key);
        ScenarioEngine.get().setDriver(driver());
        BusinessHelper.switchBusiness(platform());
    }

    /**
     * Switches the current driver to the one identified by the specified index.
     *
     * @param index the index identifying the driver to switch to.
     */
    public static void switchDriver(int index) {
        Object[] keySet = drivers.get().keySet().toArray();
        currentKey.set(keySet[index-1].toString());
        ScenarioEngine.get().setDriver(driver());
        BusinessHelper.switchBusiness(platform());
    }

    /**
     * Quits all drivers and clears the driver map for the current thread.
     */
    public static void quitAll() {
        drivers.get().values().forEach(wrapper -> wrapper.driver.quit());
        drivers.remove();
        ScenarioEngine.get().setDriverToNull();
    }

    /**
     * Returns the current driver instance.
     *
     * @return the current driver or null if no driver is available.
     */
    public static Driver driver() {
        return drivers.get().get(currentKey.get()).driver;
    }

    /**
     * Returns the platform of the current driver, or the operating system name if not specified.
     *
     * @return the platform name or operating system name.
     */
    public static String platform() {
        String platform = "";
        if (!drivers.get().isEmpty()) {
            platform = (String) drivers.get().get(currentKey.get()).settings.get("platformName");
        }
        else if (currentDriverConfigs.get() != null && currentDriverConfigs.get().length > 0) {
            platform = (String) getDriverSetting(currentDriverConfigs.get()[0]).get("platformName");
        }

        if (platform.isBlank()) {
            platform = getCurrentOS();
        }

        return platform;
    }

    /**
     * Returns all platforms of the current driver configs, or the operating system name if not specified.
     *
     * @return the platform names or operating system name.
     */
    public static List<String> platforms() {
        List<String> platforms = new ArrayList<>();
        if (currentDriverConfigs.get() != null && currentDriverConfigs.get().length > 0) {
            for (var item : currentDriverConfigs.get()) {
                String platform = (String)getDriverSetting(item).get("platformName");
                if (platform.isBlank()) platform = getCurrentOS();

                platforms.add(platform);
            }
        }
        else if (!drivers.get().isEmpty()) {
            for (var item : drivers.get().values()) {
                String platform = (String)item.settings.get("platformName");
                if (platform.isBlank()) platform = getCurrentOS();

                platforms.add(platform);
            }
        }
        else {
            platforms.add(getCurrentOS());
        }

        return platforms;
    }

    /**
     * Returns the platform of the current driver config based on index.
     *
     * @return the platform name.
     */
    public static String getCurrentDriverConfigPlatform(int index) {
        return (String) getDriverSetting(currentDriverConfigs.get()[index-1]).get("platformName");
    }

    /**
     * Returns tag value based on tag name
     *
     * @return tag value.
     */
    public static String getTagValue(String tagName) {
        Map<String, List<String>> tags = ScenarioEngine.get().runtime.tags.getTagValues();
        if (tags.containsKey(tagName)) {
            return tags.get(tagName).get(0);
        }
        return "";
    }

    /**
     * Helper method to start a driver based on the provided options and key.
     *
     * @param options a map containing the driver options.
     * @param key     an optional key to identify the driver. If null, the driver type is used as the key.
     */
    private static void startDriver(Map<String, Object> options, String key) {
        DriverWrapper driver = new DriverWrapper(options);
        String driverKey = key != null ? key : options.get("type").toString();
        drivers.get().put(driverKey, driver);
        currentKey.set(driverKey);
        ScenarioEngine.get().setDriver(driver.driver);
        BusinessHelper.switchBusiness(platform());
    }

    /**
     * Helper method to retrieve the driver configuration from the settings.
     *
     * @param config the configuration name.
     * @return a map containing the driver configuration.
     */
    private static Map<String, Object> getDriverSetting(String config) {
        config = config.isEmpty() ? "chrome" : config;
        Map<String, Object> driverConfig = (Map) driverSettings.get(config);
        return (Map<String, Object>)driverConfig.get("settings");
    }

    /**
     * Helper method to retrieve the driver configuration from the driver config index.
     *
     * @param index the driver config index.
     * @return a map containing the driver configuration.
     */
    private static Map<String, Object> getCurrentDriverConfigSetting(int index) {
        Map<String, Object> driverConfig = (Map) driverSettings.get(currentDriverConfigs.get()[index]);
        return (Map<String, Object>)driverConfig.get("settings");
    }

    /**
     * Helper method to retrieve the current os name.
     *
     * @return the base os name
     */
    private static String getCurrentOS() {
        String os = System.getProperty("os.name");
        return os.split(" ")[0].toLowerCase();
    }
}

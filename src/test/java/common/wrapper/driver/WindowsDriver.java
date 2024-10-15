package common.wrapper.driver;

import com.intuit.karate.FileUtils;
import com.intuit.karate.core.ScenarioRuntime;
import com.intuit.karate.driver.appium.AppiumDriver;
import com.intuit.karate.driver.appium.MobileDriverOptions;
import java.util.Map;

public class WindowsDriver extends AppiumDriver {

    public static final String DRIVER_TYPE = "windows";

    protected WindowsDriver(MobileDriverOptions options) {
        super(options);
    }

    public static WindowsDriver start(Map<String, Object> map, ScenarioRuntime sr) {
        MobileDriverOptions options = new MobileDriverOptions(map, sr, 4723, FileUtils.isOsWindows() ? "cmd.exe" : "appium");
        // additional commands needed to start appium on windows
        if (FileUtils.isOsWindows()){
            options.arg("/C");
            options.arg("cmd.exe");
            options.arg("/K");
            options.arg("appium");
        }
        options.arg("--port=" + options.port);
        return new WindowsDriver(options);
    }

    @Override
    public void activate() {
        super.setContext("NATIVE_APP");
    }

}
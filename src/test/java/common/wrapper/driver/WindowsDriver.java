package common.wrapper.driver;

import com.intuit.karate.FileUtils;
import com.intuit.karate.core.Plugin;
import com.intuit.karate.core.ScenarioRuntime;
import common.wrapper.option.AppiumDriverOptions;

import java.util.List;
import java.util.Map;

public class WindowsDriver extends CustomAppiumDriver {

    public static final String DRIVER_TYPE = "windows";

    protected WindowsDriver(AppiumDriverOptions options) {
        super(options);
    }

    public static WindowsDriver start(Map<String, Object> map, ScenarioRuntime sr) {
        AppiumDriverOptions options = new AppiumDriverOptions(map, sr, 4723, FileUtils.isOsWindows() ? "cmd.exe" : "appium");
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

    // Add AutoDef methods from this class into hidden variable 'driver'
    public List<String> methodNames() {
        List<String> methods = super.methodNames();
        methods.addAll(Plugin.methodNames(WindowsDriver.class));
        return methods;
    }

    public void activate() {
        super.setContext("NATIVE_APP");
    }

}
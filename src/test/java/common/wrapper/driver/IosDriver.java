package common.wrapper.driver;

import com.intuit.karate.core.AutoDef;
import com.intuit.karate.core.Plugin;
import com.intuit.karate.core.ScenarioRuntime;
import com.intuit.karate.http.Response;
import common.wrapper.option.AppiumDriverOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IosDriver extends CustomAppiumDriver {
    public static final String DRIVER_TYPE = "ios";

    public IosDriver(AppiumDriverOptions options) {
        super(options);
    }

    public static IosDriver start(Map<String, Object> map, ScenarioRuntime sr) {
        AppiumDriverOptions options = new AppiumDriverOptions(map, sr, 4723, "appium");
        options.arg("--port=" + options.port);
        return new IosDriver(options);
    }

    // Add AutoDef methods from this class into hidden variable 'driver'
    public List<String> methodNames() {
        List<String> methods = super.methodNames();
        methods.addAll(Plugin.methodNames(IosDriver.class));
        return methods;
    }

    public void activate() {
        super.setContext("NATIVE_APP");
    }

    @AutoDef
    public void swipeScreen(String direction) {
        Map<String, Object> args = new HashMap<>();
        args.put("direction", direction);
        script("mobile: scroll", args);
    }

    @AutoDef
    public void swipe(String direction) {
        Map<String, Object> args = new HashMap<>();
        args.put("direction", direction);
        script("mobile: swipe", args);
    }

    @AutoDef
    public void tap(int x, int y) {
        Map<String, Object> args = new HashMap<>();
        args.put("x", x);
        args.put("y", y);
        script("mobile: tap", args);
    }

}

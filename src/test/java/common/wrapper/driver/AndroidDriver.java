package common.wrapper.driver;

import com.intuit.karate.FileUtils;
import com.intuit.karate.core.AutoDef;
import com.intuit.karate.core.Plugin;
import com.intuit.karate.core.ScenarioRuntime;
import com.intuit.karate.http.Response;
import common.wrapper.option.AppiumDriverOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AndroidDriver extends CustomAppiumDriver {
    public static final String DRIVER_TYPE = "android";

    public AndroidDriver(AppiumDriverOptions options) {
        super(options);
    }

    public static AndroidDriver start(Map<String, Object> map, ScenarioRuntime sr) {
        AppiumDriverOptions options = new AppiumDriverOptions(map, sr, 4723, FileUtils.isOsWindows() ? "cmd.exe" : "appium");
        if (FileUtils.isOsWindows()) {
            options.arg("/C");
            options.arg("cmd.exe");
            options.arg("/K");
            options.arg("appium");
        }

        options.arg("--port=" + options.port);
        return new AndroidDriver(options);
    }

    // Add AutoDef methods from this class into hidden variable 'driver'
    public List<String> methodNames() {
        List<String> methods = super.methodNames();
        methods.addAll(Plugin.methodNames(AndroidDriver.class));
        return methods;
    }

    public void activate() {
        super.setContext("NATIVE_APP");
    }

    @AutoDef
    public void swipeScreen(String direction) {
        String ac_direction = "up";
        if (direction.equals("down")) ac_direction = "up";
        if (direction.equals("up")) ac_direction = "down";
        if (direction.equals("left")) ac_direction = "right";
        if (direction.equals("right")) ac_direction = "left";

        Map<String, Object> args = new HashMap<>();
        args.put("left", 300);
        args.put("top", 300);
        args.put("width", 600);
        args.put("height", 1000);
        args.put("direction", ac_direction);
        args.put("percent", 0.75);
        script("mobile: swipeGesture", args);
    }

    @AutoDef
    public void swipe(String direction) {
        String ac_direction = "up";
        if (direction.equals("down")) ac_direction = "up";
        if (direction.equals("up")) ac_direction = "down";
        if (direction.equals("left")) ac_direction = "right";
        if (direction.equals("right")) ac_direction = "left";

        Map<String, Object> args = new HashMap<>();
        args.put("left", 300);
        args.put("top", 300);
        args.put("width", 600);
        args.put("height", 1000);
        args.put("direction", ac_direction);
        args.put("percent", 0.25);
        script("mobile: swipeGesture", args);
    }

    @AutoDef
    public void tap(int x, int y) {
        Map<String, Object> args = new HashMap<>();
        args.put("x", x);
        args.put("y", y);
        script("mobile: clickGesture", args);
    }
}

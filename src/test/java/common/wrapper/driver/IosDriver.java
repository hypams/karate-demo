package common.wrapper.driver;

import com.intuit.karate.core.AutoDef;
import com.intuit.karate.core.Plugin;
import com.intuit.karate.core.ScenarioRuntime;
import com.intuit.karate.driver.appium.AppiumDriver;
import com.intuit.karate.driver.appium.MobileDriverOptions;
import com.intuit.karate.http.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IosDriver extends AppiumDriver {
    public static final String DRIVER_TYPE = "ios";

    public IosDriver(MobileDriverOptions options) {
        super(options);
    }

    public static IosDriver start(Map<String, Object> map, ScenarioRuntime sr) {
        MobileDriverOptions options = new MobileDriverOptions(map, sr, 4723, "appium");
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

    public boolean exists(String locator) {
        if (!((MobileDriverOptions)getOptions()).isWebSession()) {
            String json = selectorPayload(locator);
            try {
                Response res = getHttp().path(new String[]{"element"}).postJson(json);
                return res.getStatus() == 200;
            } catch (RuntimeException ex) {
                return false;
            }
        }
        else {
            return super.exists(locator);
        }
    }

    @AutoDef
    public void swipeScreen(String direction) {
        Map<String, Object> args = new HashMap<>();
        args.put("direction", direction);
        script("mobile: scroll", args);
    }

//    @Override
//    public void quit() {
//        if (!((MobileDriverOptions)getOptions()).isWebSession()) {
//            String appId = "";
//            Map<String, Object> capabilities = (Map)getOptions().webDriverSession.get("capabilities");
//            Map<String, Object> alwaysMatch = (Map)capabilities.get("alwaysMatch");
//            if (alwaysMatch.containsKey("bundleId")) {
//                appId = (String)alwaysMatch.get("bundleId");
//            }
//            else if (alwaysMatch.containsKey("appium:bundleId")) {
//                appId = (String)alwaysMatch.get("appium:bundleId");
//            }
//
//            if (!appId.isEmpty()) {
//                Map<String, Object> args = new HashMap<>();
//                args.put("bundleId", appId);
//                script("mobile: terminateApp", args);
//                this.open = false;
//            }
//        }
//
//        super.quit();
//    }

}

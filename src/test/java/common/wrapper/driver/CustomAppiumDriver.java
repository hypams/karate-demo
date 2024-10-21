package common.wrapper.driver;

import com.intuit.karate.core.Plugin;
import com.intuit.karate.driver.appium.AppiumDriver;
import common.wrapper.option.AppiumDriverOptions;

import java.util.List;

public abstract class CustomAppiumDriver extends AppiumDriver {

    protected CustomAppiumDriver(AppiumDriverOptions options) {
        super(options);
    }

    // Add AutoDef methods from this class into hidden variable 'driver'
    public List<String> methodNames() {
        List<String> methods = super.methodNames();
        methods.addAll(Plugin.methodNames(CustomAppiumDriver.class));
        return methods;
    }

    @Override
    public String selectorPayload(String id) {
        return super.selectorPayload(id);
    }

    @Override
    public String elementId(String locator) {
        if (locator.startsWith("elementId-")) {
            return locator.substring(10);
        }
        else {
            return super.elementId(locator);
        }
    }
}

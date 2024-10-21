package common.wrapper.option;

import com.intuit.karate.core.ScenarioRuntime;
import com.intuit.karate.driver.Driver;
import com.intuit.karate.driver.DriverElement;
import com.intuit.karate.driver.Element;
import com.intuit.karate.driver.MissingElement;
import com.intuit.karate.driver.appium.MobileDriverOptions;
import com.intuit.karate.http.Response;
import common.wrapper.driver.CustomAppiumDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppiumDriverOptions extends MobileDriverOptions {

    public AppiumDriverOptions(Map<String, Object> options, ScenarioRuntime sr, int defaultPort, String defaultExecutable) {
        super(options, sr, defaultPort, defaultExecutable);
    }

    @Override
    public Element optional(Driver driver, String locator) {
        if (isWebSession()) {
            return super.optional(driver, locator);
        }
        try {
            String json = ((CustomAppiumDriver)driver).selectorPayload(locator);
            Response res = ((CustomAppiumDriver)driver).getHttp().path(new String[]{"element"}).postJson(json);
            if (res.getStatus() == 200) {
                return DriverElement.locatorExists(driver, locator);
            }
            else {
                return new MissingElement(driver, locator);
            }
        }
        catch (RuntimeException re) {
            return new MissingElement(driver, locator);
        }
    }

    @Override
    public List<Element> findAll(Driver driver, String locator) {
        if (isWebSession()) {
            return super.findAll(driver, locator);
        }
        List<Element> elements = new ArrayList<>();
        List<String> resultElementIds = ((CustomAppiumDriver)driver).elementIds(locator);

        if (resultElementIds != null && !resultElementIds.isEmpty()) {

            // Remove duplicated items
            resultElementIds = resultElementIds.stream().distinct().toList();

            if (resultElementIds.size() == 1) {
                elements.add(DriverElement.locatorExists(driver, locator));
            }
            else {
                // Support new locator for xpath only
                if (locator.startsWith("/")) {
                    for (int i = 1; i <= resultElementIds.size(); i++) {
                        String indexLocator = String.format("/nothing | (%s)[%s]", locator, i);
                        elements.add(DriverElement.locatorExists(driver, indexLocator));
                    }
                }
                // Other locator type will use the elementId instead of
                else {
                    resultElementIds.forEach(x -> elements.add(DriverElement.locatorExists(driver, "elementId-" + x)));
                }
            }
        }

        return elements;
    }
}

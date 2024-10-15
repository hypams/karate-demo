package common.helper.java;

import common.wrapper.DriverUtils;

public class WaitHelper {

    /**
     * Wait until element not exists
     * @param locator element locator
     */
    public static void waitForNotExists(String locator) {
        DriverUtils.driver().waitUntil(() -> {
            try {
                if (!DriverUtils.driver().exists(locator))
                    return true;
                return null;
            } catch (RuntimeException ex) {
                return true;
            }
        });
    }
}

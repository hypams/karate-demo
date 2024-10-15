package common.helper.java;

import com.intuit.karate.core.ScenarioEngine;
import common.wrapper.DriverUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusinessHelper {
    private static final String appFolderPath = System.getProperty("user.dir") + "/src/test/java/apps";
    private static final String businessFolderPath = System.getProperty("user.dir") + "/src/test/java/apps/%s/business";
    private static final String businessClassPath = "classpath:apps/%s/business";

    // Thread-local storage for managing businesses.
    private static ThreadLocal<Map<String, Object>> businesses = ThreadLocal.withInitial(HashMap::new);
    private static ThreadLocal<List<String>> apps = ThreadLocal.withInitial(ArrayList::new);

    public static void loadBusinesses() {
        Map<String, Object> appMap = new HashMap<String, Object>();
        List<String> appNames = FileHelper.getAllFolderNames(appFolderPath);

        for (String appName : appNames) {
            apps.get().add(appName);
            String appBusinessFolderPath = String.format(businessFolderPath, appName);
            String appBusinessClassPath = String.format(businessClassPath, appName);
            var businessList = getUniqueBusinessList(FileHelper.getAllFileNames(appBusinessFolderPath, "feature", true));
            var platformList = DriverUtils.platforms();

            for (var platform : platformList) {
                Map<String, Object> businessMap = new HashMap<String, Object>();
                for (var business : businessList) {
                    if (FileHelper.exists(appBusinessFolderPath + "/" + business + "_" + platform + ".feature")) {
                        businessMap.put(business, KarateHelper.read(appBusinessClassPath + "/" + business + "_" + platform + ".feature"));
                    }
                    else if (FileHelper.exists(appBusinessFolderPath + "/" + business + ".feature")) {
                        businessMap.put(business, KarateHelper.read(appBusinessClassPath + "/" + business + ".feature"));
                    }
                }
                appMap.put(appName + "_" + platform, businessMap);
            }
        }
        businesses.get().putAll(appMap);
        ScenarioEngine.get().setVariables(appMap);

        // activate current platform's businesses
        switchBusiness(DriverUtils.platform());
    }

    public static void switchBusiness(String platform) {
        Map<String, Object> appMap = new HashMap<String, Object>();

        for (var appName : apps.get()) {
            if (businesses.get().containsKey(appName + "_" + platform)) {
                appMap.put(appName, businesses.get().get(appName + "_" + platform));
            }
            else {
                String appBusinessFolderPath = String.format(businessFolderPath, appName);
                String appBusinessClassPath = String.format(businessClassPath, appName);
                var businessList = getUniqueBusinessList(FileHelper.getAllFileNames(appBusinessFolderPath, "feature", true));
                Map<String, Object> businessMap = new HashMap<String, Object>();
                for (var business : businessList) {
                    if (FileHelper.exists(appBusinessFolderPath + "/" + business + "_" + platform + ".feature")) {
                        businessMap.put(business, KarateHelper.read(appBusinessClassPath + "/" + business + "_" + platform + ".feature"));
                    }
                    else if (FileHelper.exists(appBusinessFolderPath + "/" + business + ".feature")) {
                        businessMap.put(business, KarateHelper.read(appBusinessClassPath + "/" + business + ".feature"));
                    }
                }
                appMap.put(appName + "_" + platform, businessMap);
                appMap.put(appName, businessMap);
            }
        }
        businesses.get().putAll(appMap);
        ScenarioEngine.get().setVariables(appMap);
    }

    private static List<String> getUniqueBusinessList(List<String> list) {
        List<String> newList = new ArrayList<>();
        for (var item : list) {
            newList.add(item.split("_")[0]);
        }
        return newList.stream().distinct().toList();
    }
}

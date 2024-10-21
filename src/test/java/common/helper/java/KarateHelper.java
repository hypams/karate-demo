package common.helper.java;

import com.intuit.karate.core.ScenarioEngine;
import com.intuit.karate.graal.JsValue;

public class KarateHelper {
    public static Object read(String path) {
        Object result = ScenarioEngine.get().fileReader.readFile(path);
        return JsValue.fromJava(result);
    }
}

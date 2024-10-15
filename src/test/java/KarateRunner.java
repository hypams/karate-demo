import com.intuit.karate.junit5.Karate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class KarateRunner {
    private static final Logger log = LoggerFactory.getLogger(KarateRunner.class);

    @Karate.Test
    Karate runTest() {
        String filePath = System.getProperty("karate.test");
        log.info(filePath);
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("Error");
        }
        String fileName = filePath.replace('\\', '/');
        return Karate.run(fileName).relativeTo(KarateRunner.class);
    }
}
function f() {
    var driverString = karate.properties['karate.driverConfigs'] || karate.properties['driverConfigs'] || '';

    var driverConfigs = driverString ? driverString.split(',') : ['android_tcb_local', 'ios_tcb_local', 'chrome_local'];
    print('driver configs: ' + driverConfigs);

    var env = karate.properties['karate.env'] || '';
    print('environment: ' + env);

    // Read the resources configuration from the specified path and invoke it.
    var resourceHelper = karate.read('classpath:/common/helper/javascript/resources-helper.js')()

    // Import the Java class 'DriverUtils' from the 'common' package for managing WebDriver settings and actions.
    var driverUtils = Java.type('common.wrapper.DriverUtils')

    // Import the Java class 'BusinessHelper' from the 'common' package for working with businesses.
    var businessHelper = Java.type('common.helper.java.BusinessHelper')

    // Set the WebDriver settings by reading the configuration from a specified path.
    driverUtils.setDriverSettings(karate.read(paths.driverSettingsPath)())
    driverUtils.setCurrentDriverConfigs(driverConfigs)

    // Load all application's businesses for available platforms
    businessHelper.loadBusinesses()

    // Store the 'resource', 'driverUtils' objects in the Karate context, making them accessible throughout the tests.
    karate.set('resource', resourceHelper)
    karate.set('driverUtils', driverUtils)

    // Read javascript methods
    var driverHelper = karate.read('classpath:/common/helper/javascript/driver-helper.js')()
    var waitHelper = karate.read('classpath:/common/helper/javascript/wait-helper.js')()
    var businessHelper = karate.read('classpath:/common/helper/javascript/business-helper.js')()
    var datetimeHelper = karate.read('classpath:/common/helper/javascript/datetime-helper.js')()
    var numberHelper = karate.read('classpath:/common/helper/javascript/number-helper.js')()
    var stringHelper = karate.read('classpath:/common/helper/javascript/string-helper.js')()
    var randomHelper = karate.read('classpath:/common/helper/javascript/random-helper.js')()
    var imageHelper = karate.read('classpath:/common/helper/javascript/image-helper.js')()

    // Return all javascript methods
    return karate.merge(
        driverHelper,
        waitHelper,
        businessHelper,
        datetimeHelper,
        numberHelper,
        stringHelper,
        randomHelper,
        imageHelper
    )
}
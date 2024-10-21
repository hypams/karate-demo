function f() {
    return {
        // Initialize the WebDriver with the current settings.
        init: function() {
            driverUtils.init()
        },

        // Start the WebDriver with a specific configuration.
        startConfig: function(config) {
            driverUtils.startConfig(config)
        },

        // Overloaded method to start the WebDriver with a specific configuration and key.
        startConfig: function(config, key) {
            driverUtils.startConfig(config, key)
        },

        // Start the WebDriver with a specific type (e.g., browser, mobile, etc.).
        start: function(type) {
            driverUtils.start(type)
        },

        // Overloaded method to start the WebDriver with a specific type and key.
        start: function(type, key) {
            driverUtils.start(type, key)
        },

        // Switch the WebDriver to another instance identified by a key.
        switchDriver: function(key) {
            driverUtils.switchDriver(key)
        },

        // Set the context for the WebDriver (e.g., switching between native and web contexts).
        setContext: function(context) {
            driverUtils.driver().setContext(context)
        },

        // Quit all WebDriver instances managed by 'DriverUtils'.
        quitAll: function() {
            driverUtils.quitAll()
        },

        // Get current driver config platform by index.
        driverConfigPlatform: function(index) {
            return driverUtils.getCurrentDriverConfigPlatform(index)
        },

        // Get current driver config platform .
        platform: function() {
            return driverUtils.platform()
        },

        // Get current url .
        getURL: function() {
            return driverUtils.driver().url
        }
    }
}
function f() {
    return {
        // Wait until element with provided locator not exists.
        waitForNotExists: function(locator) {
            Java.type('common.helper.java.WaitHelper').waitForNotExists(locator)
        }
    }
}
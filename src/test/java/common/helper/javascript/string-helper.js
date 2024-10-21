function f() {
    return {
        // Format a string by replacing placeholders %s, %i, etc., with the provided arguments.
        format: function(str, ...args) {
            var result = Java.type('java.lang.String').format(str, ...args);
            return result;
        }
    }
}
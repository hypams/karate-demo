function f() {
    return {
        isNumber: function(val) {
            var parsed = parseInt(val);
            return !isNaN(parsed) && parsed % 1 === 0;
        }
    }
}
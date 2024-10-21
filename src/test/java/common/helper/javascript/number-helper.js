function f() {
    return {
        isNumber: function(value) {
            return !isNaN(value) && (typeof value === 'number' || typeof value === 'string' && value.trim() !== '' && !isNaN(Number(value)));
        }
    };
}
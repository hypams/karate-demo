function f() {
    return {
        getRandomFromArray: function(items) {
            return items[Math.floor(Math.random() * items.length)];
        },

        getRandomNumber: function(min, max) {
            return Math.floor(Math.random() * (max - min) ) + min;
        },

        generateRandomString: function(length) {
            if (length < 1) {
                throw new Error("Length must be at least 1.");
            }

            // Helper function to get a random integer between min and max (inclusive)
            function getRandomInt(min, max) {
                return Math.floor(Math.random() * (max - min + 1)) + min;
            }

            // Generate the first character (uppercase letter)
            const firstChar = String.fromCharCode(getRandomInt(65, 90)); // ASCII codes for 'A' to 'Z'

            // Generate the remaining characters (lowercase letters)
            const remainingChars = Array.from({ length: length - 1 }, () => {
                return String.fromCharCode(getRandomInt(97, 122)); // ASCII codes for 'a' to 'z'
            }).join('');

            // Concatenate the first character with the remaining characters
            return firstChar + remainingChars;
        }
    }
}
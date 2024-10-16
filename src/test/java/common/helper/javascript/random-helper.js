function f() {
    return {
        getRandomFirstName: function() {
            const firstNames = [
                "Alice", "Bob", "Charlie", "David", "Eve",
                "Frank", "Grace", "Hannah", "Isaac", "Jack",
                "Kathy", "Liam", "Mia", "Noah", "Olivia",
                "Paul", "Quinn", "Rachel", "Steve", "Tina",
                "Zoe", "Ava", "James", "Lucas", "Sophia",
                "Ella", "Michael", "Chloe", "Henry", "Amelia", "Daniel"
            ];
            return firstNames[Math.floor(Math.random() * firstNames.length)];
        },

        getRandomLastName: function() {
            const lastNames = [
                "Doe", "Smith", "Johnson", "Williams", "Jones", "Brown",
                "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson"
            ];
            return lastNames[Math.floor(Math.random() * lastNames.length)];
        },

        getRandomStreet: function() {
            const streetNames = [
                "Main St", "Second St", "Third St", "Fourth St", "Fifth St",
                "Maple Ave", "Oak St", "Pine St", "Cedar Ave", "Elm St"
            ];
            return streetNames[Math.floor(Math.random() * streetNames.length)];
        },

        getRandomCity: function() {
            const cities = ["Ho Chi Minh", "Hue", "Da Nang"];
            return cities[Math.floor(Math.random() * cities.length)];
        },

        getRandomPhone: function() {
        // Generate a random 10-digit phone number
            var phoneNumber = '';
            for (var i = 0; i < 10; i++) {
                phoneNumber += Math.floor(Math.random() * 10); // Append a random digit (0-9)
            }
            return phoneNumber;
        },

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
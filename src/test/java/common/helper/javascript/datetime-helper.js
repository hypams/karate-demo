function f() {
    return {
        // Generate a random string with provided length with the first uppercase letter and the lowercase letters others
        getCurrentDateAndFormat: function() {
            var date = new Date();
            const months = ['January', 'February', 'March', 'April', 'May', 'June','July', 'August', 'September', 'October', 'November', 'December'];
            const day = date.getDate();
            const month = months[date.getMonth()];
            const year = date.getFullYear();
            return `${month} ${day}, ${year}`;
        },

        getCurrentDate: function() {
            var date = new Date();
            var day = date.getDate();
            return day < 10 ? '0' + day : day.toString();
        },

        getCurrentMonth: function() {
            var date = new Date();
            var month = date.getMonth() + 1; // getMonth() returns 0-11, so add 1
            return month < 10 ? '0' + month : month.toString();
        },

        getCurrentYear: function() {
            var date = new Date();
            return date.getFullYear();
        },

        getNextMonth: function() {
            var date = new Date();
            var month = date.getMonth() + 2; // getMonth() returns 0-11, so add 1
            month = (month % 12);
            return month < 10 ? '0' + month : month.toString();
        },

        getTodayWithFormat: function(formatDate) {
            var DateHelper = Java.type('common.helper.java.DateHelper');
            var dateHelper = new DateHelper();
            return dateHelper.getTodayWithFormat(formatDate)
        }
    }
}
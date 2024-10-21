function f() {
    return {
        // get image class path
        baselinePath: function() {
            var imagePath = paths.imageClassPath + driverUtils.platform() + "/"
            return imagePath
        }
    }
}
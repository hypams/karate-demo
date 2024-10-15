function f() {
     karate.set('imageClassPath', 'classpath:/resources/baseline-images/')

    return {
        // get image class path
        baselinePath: function() {
            var imagePath = imageClassPath + driverUtils.platform() + "/"
            return imagePath
        }
    }
}
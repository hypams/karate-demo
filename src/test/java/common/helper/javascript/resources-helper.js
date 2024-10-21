function f() {
    const paths = {
        businessFeatureClassPath: 'classpath:/apps/%s/business/%s.feature',
        locatorClassPath: 'classpath:/apps/%s/locator/%s/%s.json',
        dataClassPath: 'classpath:/resources/data/%s/%s.json',
        driverSettingsPath: 'classpath:/resources/config/driverSettings.js',
        imageClassPath: 'classpath:/resources/baseline-images/'
    };

    var resolvePath = function resolvePath(pathTemplate, app, name, platform = "") {
        var currentPlatform = platform == "" ? driverUtils.platform() : platform

        var filePath = Java.type('java.lang.String').format(pathTemplate, app, name, name + '_' + currentPlatform)
        if (!Java.type('common.helper.java.FileHelper').exists(filePath)) {
            filePath = Java.type('java.lang.String').format(pathTemplate, app, name, name)
        }

        return filePath;
    }

    var resolveDataPath = function resolvePath(pathTemplate, app, name, platform = "", env = "") {
        var currentPlatform = platform == "" ? driverUtils.platform() : platform
        var filePath = ""

        if (env) {
            filePath = Java.type('java.lang.String').format(pathTemplate, app, name + '_' + currentPlatform + '_' + env)
            if (!Java.type('common.helper.java.FileHelper').exists(filePath)) {
                filePath = Java.type('java.lang.String').format(pathTemplate, app, name + '_' + env)
                if (!Java.type('common.helper.java.FileHelper').exists(filePath)) {
                    filePath = Java.type('java.lang.String').format(pathTemplate, app, name + '_' + currentPlatform)
                }
            }
        }
        else {
            filePath = Java.type('java.lang.String').format(pathTemplate, app, name + '_' + currentPlatform)
        }

        if (!Java.type('common.helper.java.FileHelper').exists(filePath)) {
            filePath = Java.type('java.lang.String').format(pathTemplate, app, name)
        }

        return filePath;
    }

    karate.set('paths', paths)
    karate.set('resolvePath', resolvePath)
    karate.set('resolveDataPath', resolveDataPath)

    return {
        featurePath: function(app, name, feature) {
            return Java.type('java.lang.String').format(paths.businessFeatureClassPath, app, name, feature)
        },
        businessPath: function(app, name, platform = "") {
            return resolvePath(paths.businessFeatureClassPath, app, name, platform) + '@methods'
        },
        locatorPath: function(app, name, platform = "") {
            return resolvePath(paths.locatorClassPath, app, name, platform)
        },
        dataPath: function(app, name, platform = "", env = "") {
            return resolveDataPath(paths.dataClassPath, app, name, platform, env)
        },
        defaultBusinessPath: function(app, name, feature) {
            return Java.type('java.lang.String').format(paths.businessFeatureClassPath, app, name, feature) + '@methods'
        },
        defaultLocatorPath: function(app, name, locator) {
            return Java.type('java.lang.String').format(paths.locatorClassPath, app, name, locator)
        },
        defaultDataPath: function(app, name) {
            return Java.type('java.lang.String').format(paths.dataClassPath, app, name)
        }
    }
}
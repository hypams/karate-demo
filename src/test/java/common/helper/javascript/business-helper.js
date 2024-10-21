function f() {
    return {
        // Construct the path to a feature file using the application, name, and feature parameters.
        feature: function(app, name, feature = "") {
            if (feature) {
                return resourceUtils.featurePath(app, name, feature)
            }
            else {
                var featureName = name
                var businessName = name
                if (featureName.includes('_')) {
                    businessName = featureName.split('_')[0]
                }
                return resourceUtils.featurePath(app, businessName, featureName)
            }
        },

        // Read and return the contents of a feature file given its path and name.
        method: function(feature, name) {
            return karate.read(feature + name)
        },

        // Call a specific business logic feature file based on the application and name.
        business: function(app, name, platform = "") {
            var platformData = karate.call(resourceUtils.businessPath(app, name, platform))
            return getMergedBusiness(platformData)
        },

        // Read and return the locator file contents based on the application and name.
        locator: function(app, name, platform = "") {
            var commonName = "basepage";
            var defaultCommonPath = resourceUtils.defaultLocatorPath(app, commonName, commonName)
            var commonPath = resourceUtils.locatorPath(app, commonName, platform)
            var defaultTargetPath = resourceUtils.defaultLocatorPath(app, name, name)
            var targetPath = resourceUtils.locatorPath(app, name, platform)

            // Get locators from common
            if (defaultCommonPath != commonPath) {
                var commonData1 = karate.read(defaultCommonPath)
                var commonData2 = karate.read(commonPath)
                var mergedData1 = karate.merge(commonData1, commonData2)
            }
            else {
                var mergedData1 = karate.read(defaultCommonPath)
            }

            // Get locators from target screen
            if (defaultTargetPath != targetPath) {
                var targetData1 = karate.read(defaultTargetPath)
                var targetData2 = karate.read(targetPath)
                var mergedData2 = karate.merge(targetData1, targetData2)
            }
            else {
                var mergedData2 = karate.read(defaultTargetPath)
            }

            return karate.merge(mergedData1, mergedData2)
        },

        // Read and return the data file contents based on the application and name.
        data: function(app, name, platform = "", env = "") {
            var defaultPath = resourceUtils.defaultDataPath(app, name)
            var dataPath = resourceUtils.dataPath(app, name, platform, env)
            var nonMergedData;
            if(dataPath != defaultPath) {
                nonMergedData = karate.read(dataPath)
            }
            else {
                nonMergedData = karate.read(defaultPath)
            }
            return nonMergedData
        },

        parentBusiness: function() {
            return driverUtils.getTagValue('parent')
        },

        // Get merged business methods from inheritance tree
        getMergedBusiness: function(data) {
            if (data.parent) {
                var parentParts = data.parent.split('.')
                var parentApp = parentParts[0]
                var parentName = parentParts[1]
                var parentFeature = parentParts[1]
                if (parentName.includes('_')) {
                    parentName = parentName.split('_')[0]
                }
                var parentData = karate.call(resourceUtils.defaultBusinessPath(parentApp, parentName, parentFeature))
                var mergedBusiness = getMergedBusiness(parentData)
                return karate.merge(mergedBusiness, data)
            }
            return data
        },

        // New method to run terminal commands using Java
        runCommand: function(command) {
            var processBuilder = new Java.type('java.lang.ProcessBuilder')();
            processBuilder.command(command.split(' '));  // Split the command into arguments
            var process = processBuilder.start();
            process.waitFor();

            var inputStream = process.getInputStream();
            var reader = new Java.type('java.io.BufferedReader')(new Java.type('java.io.InputStreamReader')(inputStream));
            var line;
            while ((line = reader.readLine()) != null) {
                print(line);
            }
            var errorStream = process.getErrorStream();
            var errorReader = new Java.type('java.io.BufferedReader')(new Java.type('java.io.InputStreamReader')(errorStream));
            while ((line = errorReader.readLine()) != null) {
                print("ERROR: " + line);
            }
            return process.exitValue();
        }
    }
}
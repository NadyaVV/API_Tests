timeout(10) {
    node('maven') {
        stage('Checkout') {
            checkout scm
        }
        stage("Run tests") {
            def exitCode = sh(
                    returnStatus: true,
                    script: """
                    mvn test -Dbrowser=$BROWSER_NAME -Dbrowser.version=$BROWSER_VERSION -Dwebdriver.base.url=$BASE_URL -Dwebdriver.remote.url=$GRID_URL
                    """
            )
            if (exitCode != 0) {
                currentBuild.result = 'UNSTABLE'
            }
        }
        stage('Publish artifacts') {
            allure([results          : [[
                                                path: 'allure-results'
                                        ]],
                    disabled         : false,
                    reportBuildPolicy: 'ALWAYS'])
        }
    }
}
timeout(5) {
    node('maven') {
        stage('Checkout') {
            checkout scm
        }
        stage('Run tests') {
            def jobs = [:]

            jobs['ui-tests'] = {
                node('maven-slave') {
                    stage('Ui tests on chrome') {
                        build(job: 'ui-tests',
                                parameters: [
                                        string(name: 'BRANCH', value: BROWSER_NAME),
                                        string(name: 'BASE_URL', value: BASE_URL),
                                        string(name: 'BROWSER', value: BROWSER_NAME),
                                        string(name: 'BROWSER_VERSION', value: BROWSER_VERSION),
                                        string(name: 'GRID_URL', value: GRID_URL)
                                ])
                    }
                }
            }
        }

        jobs['api-tests'] = {
            node('maven') {
                stage('API tests on chrome') {
                    build(job: 'api-tests',
                            parameters: [
                                    string(name: 'BRANCH', value: BROWSER_NAME),
                                    string(name: 'BASE_URL', value: BASE_URL),
                                    string(name: 'BROWSER', value: BROWSER_NAME),
                                    string(name: 'BROWSER_VERSION', value: BROWSER_VERSION),
                                    string(name: 'GRID_URL', value: GRID_URL)
                            ])
                }
            }
        }
        parallel jobs

        stage('Publish API artifacts') {
            allure([results          : [[
                                                path: 'allure-results'
                                        ]],
                    disabled         : false,
                    reportBuildPolicy: 'ALWAYS'])
        }
    }
}
pipeline {
    agent any
    stages {
        stage('MultiJob like stage') {
            parallel {
                stage('Run UI tests') {
                    build(job: 'ui-tests',
                            parameters: [
                                    string(name: 'BRANCH', value: BRANCH),
                                    string(name: 'BASE_URL', value: BASE_URL),
                                    string(name: 'BROWSER', value: BROWSER),
                                    string(name: 'BROWSER_VERSION', value: BROWSER_VERSION),
                                    string(name: 'GRID_URL', value: GRID_URL)
                            ])
                }
                stage('Run API tests') {
                    build(job: 'api-tests',
                            parameters: [
                                    string(name: 'BRANCH', value: BRANCH),
                                    string(name: 'BASE_URL', value: BASE_URL),
                                    string(name: 'BROWSER', value: BROWSER),
                                    string(name: 'BROWSER_VERSION', value: BROWSER_VERSION),
                                    string(name: 'GRID_URL', value: GRID_URL)
                            ])
                }
            }
            stage('Publish API artifacts') {
                allure([results          : [[
                                                    path: 'allure-results'
                                            ]],
                        disabled         : false,
                        reportBuildPolicy: 'ALWAYS'])
            }
        }
    }
}
timeout(5) {
    node('maven') {
        stage('Checkout') {
            checkout scm
        }
        stage('Run tests') {
            def jobs = [:]

            def runnerJobs = "$TEST_TYPE".split(",")

            jobs['ui-tests'] = {
                node('maven-slave') {
                    stage('Ui tests on chrome') {
                        if('ui' in runnerJobs) {
                            catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                                build(job: 'ui-tests',
                                        parameters: [
                                                string(name: 'BRANCH', value: BROWSER_NAME),
                                                string(name: 'BASE_URL', value: BASE_URL),
                                                string(name: 'BROWSER', value: BROWSER_NAME),
                                                string(name: 'BROWSER_VERSION', value: BROWSER_VERSION),
                                                string(name: 'GRID_URL', value: GRID_URL)
                                        ])
                            }
                        } else {
                            echo 'Skipping stage...'
                            Utils.markStageSkippedForConditional('keystone ui tests')
                        }
                    }
                }
            }

            jobs['api-tests'] = {
                node('maven-slave') {
                    stage('API tests on chrome') {
                        if('api' in runnerJobs) {
                            catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                                build(job: 'api-tests',
                                        parameters: [
                                                string(name: 'BRANCH', value: BROWSER_NAME),
                                                string(name: 'BASE_URL', value: BASE_URL),
                                                string(name: 'BROWSER', value: BROWSER_NAME),
                                                string(name: 'BROWSER_VERSION', value: BROWSER_VERSION),
                                                string(name: 'GRID_URL', value: GRID_URL)
                                        ])
                            }
                        } else {
                            echo 'Skipping stage...'
                            Utils.markStageSkippedForConditional('keystone api tests')
                        }
                    }
                }
            }
            parallel jobs
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
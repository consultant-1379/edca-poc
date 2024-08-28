#!/usr/bin/env groovy
pipeline {
    agent {
        node {
            label NODE_LABEL
        }
    }

    environment {
        PATH = "$HOME/repos/bob:$PATH"
        TEAM_NAME = "Zuegma Team"
        RELEASE = "false"
        KUBECONFIG = "$HOME/.kube/config" // This is pre-configured in the slave.
        // See Microservice Chassis CI Pipeline Start Guide for more
        // information
    }

    stages {
        stage('Clean') {
            steps {
                // Inject settings.xml into home folder
                configFileProvider([configFile(fileId: "${env.SETTINGS_CONFIG_FILE_NAME}", targetLocation: "${env.HOME}/.m2/")]) {

                }
                sh "bob clean"
            }
        }

        stage('Init') {
            steps {
                sh "cat ruleset2.0.yaml"
                echo "*****************"
                sh "cat precodereview.Jenkinsfile"
                echo "*****************"
                echo "Inject settings.xml file can be inserted here"
                sh "bob init-drop"
                archiveArtifacts 'artifact.properties'
            }
        }

        stage('Lint') {
            steps {
                parallel(
                        "lint markdown": {
                            sh 'bob lint:markdownlint lint:vale'
                        },
                        "lint zally": {
                            sh 'bob lint:lint-api-schema'
                            archiveArtifacts artifacts: 'zally-api-lint-report.txt'
                        },
                        "lint helm": {
                            sh 'bob lint:helm lint:helm-chart-check'
                        },
                        "lint code": {
                            sh 'bob license:check'
                        },
                        /*
                        TODO: Uncomment this block of code when you have release configuration ready.
                        TODO: Refer https://confluence.lmera.ericsson.se/pages/viewpage.action?spaceKey=AGW&title=Release+Pipeline
                        "lint dpraf-configuration": {
                            withCredentials([string(credentialsId: 'dpraf-api-token-id',
                                    variable: 'DPRAF_API_TOKEN')])
                                    {
                                        sh 'bob -r ruleset2.0.pra.yaml check-dpraf-configuration'
                                    }
                        }*/
                )
            }
        }

        stage('Generate') {
            steps {
                parallel(
                        "Generate Docs": {
                            sh 'bob generate-docs'
                            archiveArtifacts 'build/doc/**/*.*'
                            publishHTML (target: [
                                    allowMissing: false,
                                    alwaysLinkToLastBuild: false,
                                    keepAll: true,
                                    reportDir: 'build/doc',
                                    reportFiles: 'CTA_api.html',
                                    reportName: "REST API Documentation"
                            ])
                        },
                        /*
                          TODO: Uncomment this block of code when you have release configuration ready.
                          TODO: Refer https://confluence.lmera.ericsson.se/pages/viewpage.action?spaceKey=AGW&title=Release+Pipeline
                         "Generate preliminary PRI": {
                             withCredentials([usernamePassword(credentialsId: 'jira-user', usernameVariable: 'JIRA_USERNAME', passwordVariable: 'JIRA_PASSWORD'),
                                              usernamePassword(credentialsId: 'gerrit-user', usernameVariable: 'GERRIT_USERNAME', passwordVariable: 'GERRIT_PASSWORD')
                             ])
                                     {
                                         sh 'bob generate-pri'
                                         publishHTML (target: [
                                                 allowMissing: false,
                                                 alwaysLinkToLastBuild: false,
                                                 keepAll: true,
                                                 reportDir: 'build/',
                                                 reportFiles: 'pri.html',
                                                 reportName: "PRI"
                                         ])
                                         archiveArtifacts 'build/pri.html'
                                         archiveArtifacts 'build/pri.json'
                                         archiveArtifacts 'build/pri.pdf'
                                     }
                         },
                         "EVMS CSV generation for preview": {
                             sh 'bob evms-csv-generation'
                             archiveArtifacts '3pps_for_evms.csv'
                         }
                         */
                )
            }
        }

        stage('Build Source Code') {
            steps {
                sh "bob build"
            }
        }

        stage('Early Functional Test') {
            parallel {
                stage('Parallel: Unit Test') {
                    steps {
                        sh "bob unit"
                    }
                }

                stage('Parallel: Contract Test') {
                    steps {
                        sh "bob contract-tests"
                    }
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    withMaven { // This body required for Quality Gate step to recognize the task ID for SQ Analysis
                        sh "bob sonar"
                    }
                }
            }
        }

        stage("Quality Gate") {
            steps {
                withSonarQubeEnv('sonarqube') {
                    script {
                        timeout(time: 5, unit: 'MINUTES') {
                            getQualityGate()
                        }
                    }
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "bob image"
            }
        }

        stage('Package Helm Chart') {
            steps {
                withCredentials([string(credentialsId: 'HELM_REPO_API_TOKEN', variable: 'HELM_REPO_API_TOKEN')]) {
                    sh "bob package"
                }
            }
        }

        stage('Functional Test') {
            steps {
                echo "No current recommendation on how to do this - please follow https://jira-oss.seli.wh.rnd.internal.ericsson.com/browse/PDUOSSCNG-43 for updates"
            }
        }

        stage('Non-Functional Test') {
            steps {
                echo "No current recommendation on how to do this - please follow https://jira-oss.seli.wh.rnd.internal.ericsson.com/browse/PDUOSSCNG-43 for updates"
            }
        }
     /*
      TODO: Uncomment this block of code when you have release configuration ready.
      TODO: Refer https://confluence.lmera.ericsson.se/pages/viewpage.action?spaceKey=AGW&title=Release+Pipeline

        stage('Upload documents to Eridoc'){
            steps {
                withCredentials([usernamePassword(credentialsId: 'eridoc-user', usernameVariable: 'ERIDOC_USERNAME', passwordVariable: 'ERIDOC_PASSWORD')])
                        {
                            sh '.bob eridoc-upload'
                        }

            }
        }
      */
        stage('Publish') {
            steps {
                withCredentials([string(credentialsId: 'HELM_REPO_API_TOKEN', variable: 'HELM_REPO_API_TOKEN')]) {
                    sh "bob publish"
                }
            }
        }

    }
    post {
        always {
            echo "I am post"
        }
    }
}

def getQualityGate() {
    // Wait for SonarQube Analysis is done and Quality Gate is pushed back
    qualityGate = waitForQualityGate()

    // If Analysis file exists, parse the Dashboard URL
    if (fileExists(file: 'target/sonar/report-task.txt')) {
        sh 'cat target/sonar/report-task.txt'
        def props = readProperties file: 'target/sonar/report-task.txt'
        env.DASHBOARD_URL = props['dashboardUrl']
    }

    if (qualityGate.status != 'OK') { // If Quality Gate Failed
        if (env.GERRIT_CHANGE_NUMBER) {
            env.SQ_MESSAGE = "'" + "SonarQube Quality Gate Failed: ${DASHBOARD_URL}" + "'"
            sh '''
               ssh -p 29418 lciadm100@gerrit.ericsson.se gerrit review --label 'SQ-Quality-Gate=-1'  \
                 --message ${SQ_MESSAGE} --project $GERRIT_PROJECT $GERRIT_PATCHSET_REVISION
            '''
            error "Pipeline aborted due to quality gate failure!\n Report: ${env.DASHBOARD_URL}"
        }
    } else if (env.GERRIT_CHANGE_NUMBER) { // If Quality Gate Passed
        env.SQ_MESSAGE = "'" + "SonarQube Quality Gate Passed: ${DASHBOARD_URL}" + "'"
        sh '''
            ssh -p 29418 lciadm100@gerrit.ericsson.se gerrit review --label 'SQ-Quality-Gate=+1'  \
                --message ${SQ_MESSAGE} --project $GERRIT_PROJECT $GERRIT_PATCHSET_REVISION
         '''
    }
}

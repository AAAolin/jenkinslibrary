#!groovy
@Library('shareLibrary') _

def tools = new org.devops.printMes()

pipeline{

    agent any

    environment{
        cnpmPath = '/usr/local/node/bin'
    }

    stages{

        stage("getCode"){
            steps{
                script{
                    //println("获取代码")
                    tools.printMes("获取代码","green")
                    try{
                        def GetCode = new org.devops.getCode()
                        GetCode.getCode("$branch")
                    }catch(e){
                        currentBuild.description="拉取代码失败"
                        error tools.printMes("拉取代码失败","red")
                    }
                }
            }
        }

        stage("setEnvironment"){
            steps{
                script{
                    try{
                        def unzip = new org.devops.getCode()
                        unzip.setEnvironment("${WORKSPACE}","${cnpmPath}","${packageName}")
                    }catch(e){
                        currentBuild.description="环境配置失败"
                        error tools.printMes("环境配置失败","red")
                    }
                }
            }
        }

        stage("build"){
            steps{
                script{
                    try{
                        def webB = new org.devops.webBuild()
                        packageNameList = packageName.split(" ")
                        for (i=0;i<packageNameList.size();i++) {
                            tools.printMes("当前编译${packageNameList[i]}","green")
                            webB.webBuild("${WORKSPACE}","${packageNameList[i]}","${cnpmPath}")
                            webB.packageZip("${WORKSPACE}","${packageNameList[i]}")
                            webB.deploy("${WORKSPACE}","${JOB_NAME}","${BUILD_NUMBER}","${branch}","${packageNameList[i]}")
                        }

                    }catch(e){
                        currentBuild.description="编译打包失败"
                        error tools.printMes("编译打包失败","red")
                    }
                }
            }
        }
    }
}
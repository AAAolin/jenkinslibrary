package org.devops

def getCode(branch){
  checkout([$class: 'GitSCM', branches: [[name: '${branch}']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [],
              userRemoteConfigs: [[credentialsId: '57665be5-0c89-42e9-b403-def61bd75e6c', url: 'http://scm.hxmis.com/osc__00004/hxbankh5.git']]])
}

def setEnvironment(workspace,cnpmPath,packageName){
    try{
        sh """
        cp /root/kylin_modules.zip ${workspace}
        unzip -o kylin_modules.zip
        """
        ansiColor('xterm') {
            	echo "\033[32m kylin_modules.zip解压成功 \033[0m"
        }
    } catch (e) {
        currentBuild.description="kylin_modules.zip解压失败"
        ansiColor('xterm') {
            error "\033[31m kylin_modules.zip解压失败 \033[0m"
        }
    }

    try{
        sh """
        cd ${workspace}/package-jsons-online.config
        find . -type f -exec sed -i \'s/\\xEF\\xBB\\xBF//\' {} \\;
        """
        ansiColor('xterm') {
            echo "\033[32m 修改json格式成功 \033[0m"
        }
    } catch (e) {
        currentBuild.description="修改json格式失败"
        ansiColor('xterm') {
            error "\033[31m 修改json格式失败 \033[0m"
        }
    }

    try{
        sh """
        ${cnpmPath}/cnpm install
        ${cnpmPath}/cnpm install echarts
        ${cnpmPath}/cnpm install better-scroll
        """
        ansiColor('xterm') {
            echo "\033[32m 下载依赖成功 \033[0m"
        }
    } catch (e) {
        currentBuild.description="下载依赖失败"
        ansiColor('xterm') {
            error "\033[31m 下载依赖失败 \033[0m"
        }
    }

    try{
        sh """
        cp ${workspace}/node_modules/_acorn@5.7.3@acorn/dist/walk.js ${workspace}/node_modules/_acorn@6.1.1@acorn/dist/
        ls -l ${workspace}/node_modules/_acorn@6.1.1@acorn/dist/ | grep walk.js
        """
        ansiColor('xterm') {
            echo "\033[32m 拷贝walk.js成功 \033[0m"
        }
    } catch (e) {
        currentBuild.description="拷贝walk.js失败"
        ansiColor('xterm') {
            error "\033[31m 拷贝walk.js失败 \033[0m"
        }
    }
}

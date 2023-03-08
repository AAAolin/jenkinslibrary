package org.devops

def h5Name
def h5Id

def webBuild(workspace,packageName,cnpmPath){

    try{
        sh """
        cp ${workspace}/package-jsons-online.config/${packageName}.json ${workspace}
        cd ${workspace}
        rm -rf  package.json
        mv ${packageName}.json package.json
        """
        ansiColor('xterm') {
            echo "\033[32m ${packageName}.json修改成功 \033[0m"
        }
        //println("${packageName}.json修改成功")
    } catch (e) {
        currentBuild.description="${packageName}.json修改失败"
        ansiColor('xterm') {
            error "\033[31m ${packageName}.json修改失败 \033[0m"
        }
    }

    if (packageName.contains("_")) {
        h5Name = packageName.split("_")[0]
        h5Id = packageName.split("_")[1]
    } else {
        h5Name = packageName
        h5Id = packageName
    }

    println(h5Name)
    println(h5Id)

    try{
        sh """
            ${cnpmPath}/cnpm run build
        """
        ansiColor('xterm') {
            echo "\033[32m ${packageName}编译成功 \033[0m"
        }
    } catch (e) {
        currentBuild.description="${packageName}编译失败"
        ansiColor('xterm') {
            error "\033[31m ${packageName}编译失败 \033[0m"
        }
    }

}

def packageZip(workspace,packageName){

    try{
        sh """
	    cd ${workspace}
		mkdir -p ${h5Id}
		cp -rf ./www ./${h5Id}
		zip -q -r ${h5Id}.zip ./${h5Id}
	    """
        ansiColor('xterm') {
            echo "\033[32m ${packageName}打包成功 \033[0m"
        }
    } catch (e) {
        currentBuild.description="${packageName}打包失败"
        ansiColor('xterm') {
            error "\033[31m ${packageName}打包失败 \033[0m"
        }
    }

}

def deploy(workspace,JOB_NAME,BUILD_NUMBER,branch,packageName){

    try{
        sh """
	    cd ${workspace}
        mkdir -p /home/H5_pag/packages/${JOB_NAME}/Build_${BUILD_NUMBER}_${branch}/${h5Name}
        cp ${h5Id}.zip /home/H5_pag/packages/${JOB_NAME}/Build_${BUILD_NUMBER}_${branch}/${h5Name}
	    """
        ansiColor('xterm') {
            echo "\033[32m ${packageName}发布成功 \033[0m"
        }
    } catch (e) {
        currentBuild.description="${packageName}发布失败"
        ansiColor('xterm') {
            error "\033[31m ${packageName}发布失败 \033[0m"
        }
    }
}
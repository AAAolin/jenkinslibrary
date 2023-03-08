package org.devops

def printMes(message,color){
    colors = [ "green"  : "\033[32m ${message} \033[0m",
              "red"    : "\033[31m ${message} \033[0m",
              "yellow" : "\033[33m ${message} \033[0m"]
    ansiColor("xterm"){
        println(colors[color])
    }
}
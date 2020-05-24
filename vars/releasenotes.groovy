//import hudson.scm.ChangeLogSet.Entry
//import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper;
import groovy.io.*


import java.text.SimpleDateFormat;

@NonCPS
def call(Map config=[:]) {
    def workspace = new File(pwd() + "@script");
    new File(workspace.path + '/manifest.txt').withWriter('utf-8')
            {
                writer ->
                    workspace.eachFileRecurse(FileType.ANY) {
                        file ->
                            if (file.isDirectory()) {
                                writer.writeLine(file.name);
                            } else {
                                writer.writeLine(file.name + '\t' + file.length());
                            }
                    }
            }
    def date =  new Date();
    def sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:sss");
    echo "Date and Time IS: " + sdf.format(date);
//    currentBuild =  (RunWrapper)currentBuild
    echo "Last build number is " + currentBuild.getNumber()
    echo "Current build number ${BUILD_NUMBER}"

    showChangeLogs(currentBuild)

    if(config.changes != "false") {
        echo "changes";
    }
}

@NonCPS
private void showChangeLogs(currentBuild) {
    def changeLogSets = currentBuild.changeSets;

    changeLogSets.each { change ->
        entries = change.items;
        entries.each { entry ->
            echo "${entry.commitId} by ${entry.author} on ${new Date(entry.timestamp)} ${entry.msg})"
            entry.affectedFiles.each { file ->
                echo "${file.editType.name} ${file.path}"
            }
        }


    }
}


import hudson.model.*;
import hudson.scm.*;
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
    currentBuild =  (Build)currentBuild
    echo "Last build number is " + currentBuild.getNumber()
    echo "Current build number ${BUILD_NUMBER}"

    ChangeLogSet changeLogSets = currentBuild.changeSet;

    for(change in changeLogSets) {
         entries = change.items;
        for (Entry entry in entries) {
            echo "${entry.commitId} by ${entry.author} on ${new Date(entry.timestamp)} ${entry.msg})"
            for(file in entry.affectedFiles){
                echo "${file.editType.name} ${file.path}"
            }
        }
    }

    if(config.changes != "false") {
        echo "changes";
    }
}


import java.io.*
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

    echo "Last build number is " + currentBuild.getNumber()
    if(config.changes != "false") {
        echo "changes";
    }
}


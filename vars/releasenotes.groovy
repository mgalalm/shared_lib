import java.io.*
import groovy.io.*;

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
    if(config.changes != "false") {
        echo "changes";
    }
}


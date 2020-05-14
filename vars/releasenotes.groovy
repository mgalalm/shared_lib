import java.io.*
import groovy.io.*;


def call(Map config=[:]) {
    def workspace = new File(pwd());
    new File(workspace.path + '/manifest.txt').withWriter('utf-8')
            {
                writer ->
                    dir.eachFileRecurse(FileType.ANY) {
                        file ->
                            if (file.isDirectory()) {
                                writer.writeLine(file.name);
                            } else {
                                writer.writeLine(file.name + '\t' + file.length());
                            }

                    }
            }
}


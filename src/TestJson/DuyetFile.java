/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestJson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 *
 * @author quang
 */
public class DuyetFile {
    public static void main(String[] args) throws IOException {
//                try (Stream<Path> paths = Files.walk(Paths.get("D:\\Hoc_Ky_1_Nam4\\LTM\\RequestSoftware\\src\\FileJSON\\"))) {
//            paths.filter(Files::isRegularFile).forEach(System.out::println);
//                
//                } 
        final File folder = new File("D:\\Hoc_Ky_1_Nam4\\LTM\\RequestSoftware\\src\\FileJSON\\");
        listFilesForFolder(folder);
    }
    
    public static void listFilesForFolder(final File folder) {
    for (final File fileEntry : folder.listFiles()) {
        if (fileEntry.isDirectory()) {
            listFilesForFolder(fileEntry);
        } else {
            System.out.println(fileEntry.getName());
        }
    }
}
}

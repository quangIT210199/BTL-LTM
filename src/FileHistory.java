/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author quang
 */
public class FileHistory {
    private String nameFile;

    public FileHistory(String nameFile) {
        this.nameFile = nameFile;
    }
    
    public Object[] toObjects(){
        return new Object[]{
            nameFile
        };
    }
}

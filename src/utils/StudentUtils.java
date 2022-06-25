/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import model.Student;

/**
 *
 * @author duc
 */
public class StudentUtils {
    public static final String DEFAULT_DIR = "student_list";
    public static ArrayList<Student> getAllStudents() {
        ArrayList<Student> studentList = new ArrayList<>();
        File studentListDir = new File(DEFAULT_DIR);
        if(studentListDir.exists()){
            for(File studentFile : studentListDir.listFiles()){
                if(studentFile.isFile() && !"last_id.dat".equals(studentFile.getName())){
                    try (var ois = new ObjectInputStream(
                        new BufferedInputStream(new FileInputStream(studentFile)))){
                        Student s = (Student)ois.readObject();
                        studentList.add(s);
                    } catch (IOException | ClassNotFoundException ex){
                        ex.printStackTrace();
                    }
                }
            }
        }
        return studentList;
    }
    public static void writeToBinaryFile(Student student){
        try (var oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(DEFAULT_DIR + "/" + student.getId())))) {
            oos.writeObject(student);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        try(var fos = new FileOutputStream(DEFAULT_DIR + "/last_id.dat")){
            fos.write(student.getId());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static int getNextId(){
        int nextId = 0;
        try{
            File lastIdFile = new File(DEFAULT_DIR + "/last_id.dat");
            if(!lastIdFile.exists()){
                File dir = new File(DEFAULT_DIR);
                dir.mkdir();
                lastIdFile.createNewFile();
            }
            try (var fis = new FileInputStream(lastIdFile)){
                nextId = fis.read() + 1;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        } catch(IOException ex){
            ex.printStackTrace();
        }
        return nextId;
    }
    
    public static String getFileExtension(String fileName){
        String extension = "";
        int i = fileName.lastIndexOf('.');
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
        if (i > p) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }
    
    public static String saveImage(String sourceImageUrl, int studentId, String desImageUrl){
        final String IMAGES_DIR = DEFAULT_DIR + "/images";
        File sourceImage = new File(sourceImageUrl);
        File desImage;
        if(desImageUrl != null) {
            desImage = new File(desImageUrl);
        } else {
            desImage = new File(IMAGES_DIR + "/" + studentId + "." + getFileExtension(sourceImageUrl));
        }
        File dir = new File(IMAGES_DIR);
        if(!dir.exists()) {
            dir.mkdir();
        }
        try {
            Files.copy(sourceImage.toPath(), 
                    desImage.toPath(), 
                    StandardCopyOption.REPLACE_EXISTING);
        } catch(IOException ex){
            ex.printStackTrace();
        }
        return desImage.getPath();
    }
    
    public static Student findStudentById(int studentId){
        ArrayList<Student> studentList = getAllStudents();
        for(Student s : studentList){
            if(s.getId() == studentId){
                return s;
            }
        }
        return null;
    }
    
    public static boolean isFound(int studentId){
        ArrayList<Student> studentList = getAllStudents();
        for(Student s : studentList){
            if(s.getId() == studentId){
                return true;
            }
        }
        return false;
    }
    
    public static boolean removeStudent(int studentId){
        if(isFound(studentId)){
            File file = new File(DEFAULT_DIR + "/" + studentId);
            return file.delete();
        }
        return false;
    }
}

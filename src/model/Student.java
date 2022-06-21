/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author duc
 */
public class Student implements Serializable{
    public static final String DEFAULT_DIRECTION = "student_list/";
    private Integer id;
    private String fullName;
    private Float point;
    private String image;
    private String address;
    private String note;
    
    public Student(){
        this.id = getNextId();
    }
    
    public Student(String fullName, float point, String image, String address, String note){
        this();
        this.fullName = fullName;
        this.point = point;
        this.image = image;
        this.address = address;
        this.note = note;
    }
    
    public static void writeToBinaryFile(Student student){
        try (var oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(DEFAULT_DIRECTION + student.id)))) {
            oos.writeObject(student);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        try(var fos = new FileOutputStream("student_list/last_id.dat")){
            fos.write(student.id);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static int getNextId(){
        int nextId = 0;
        try{
            File yourFile = new File("student_list/last_id.dat");
            yourFile.createNewFile();
        } catch(IOException ex){
            ex.printStackTrace();
        }
        
        try (var fis = new FileInputStream("student_list/last_id.dat")){
            nextId = fis.read() + 1;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return nextId;
    }
    
    public String toString(){
        return "Student@[id=" + id + " , name=" + fullName + ", "
                + "address=" + address + ",note=" + note+ "]";
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the point
     */
    public Float getPoint() {
        return point;
    }

    /**
     * @param point the point to set
     */
    public void setPoint(Float point) {
        this.point = point;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }
}

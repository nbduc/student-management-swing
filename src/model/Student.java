/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author duc
 */
public class Student implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String fullName;
    private Float point;
    private byte [] image;
    private String address;
    private String note;
    
    public Student(){
        this.id = 0;
    }
    
    public String toString(){
        return id + "|" + fullName + "|" + address + "|" + note;
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
     * @return the image
     */
    public byte [] getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(byte [] image) {
        this.image = image;
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
}

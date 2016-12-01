package ies;

import java.util.ArrayList;

public class RFIDItem {

    int id;
    String expiryDate;
    String firstName;
    String surname;
    String grantedFloor;

    public RFIDItem(){

    }
    public RFIDItem(String id,String surname,String firstName, String grantedFloor, String expiryDate){
        this.id = Integer.parseInt(id);
        this.surname=surname;
        this.firstName=firstName;
        this.grantedFloor=grantedFloor;
        this.expiryDate=expiryDate;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getGrantedFloor(){
        return  grantedFloor;
    }

    public void setGrantedFloor(String grantedFloor) {
        this.grantedFloor = grantedFloor;
    }
}


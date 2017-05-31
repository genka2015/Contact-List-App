package com.example.android.contacts;



import java.io.Serializable;

/** A single contact db entry class. Has 3 fields: First name, Last name and Path to image
 * Created by Android on 5/30/2017.
 */

public class Contact implements Serializable {

    private String firstName;
    private String lastName;
    private String path;

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String s){
        this.firstName = s;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String s){
        this.lastName = s;
    }

    public String getPath(){
        return path;
    }

    public void setPath(String s){
        this.path = s;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return firstName + " " + lastName + " " + path;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalproject_nerosa_fernancarl;

/**
 *
 * @author FernanCarl
 */
public class Location {
    
    private int Location_id;
    private String Location_code;
    private String Description;

    public Location() {
    }

    public Location(int Location_id, String Location_code, String Description) {
        this.Location_id = Location_id;
        this.Location_code = Location_code;
        this.Description = Description;
    }

    public int getLocation_id() {
        return Location_id;
    }

    public void setLocation_id(int Location_id) {
        this.Location_id = Location_id;
    }

    public String getLocation_code() {
        return Location_code;
    }

    public void setLocation_code(String Location_code) {
        this.Location_code = Location_code;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    
    @Override
    public String toString() {
        return this.Location_code; // Displays room titles cleanly inside the ComboBox
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalproject_nerosa_fernancarl;

/**
 *
 * @author FernanCarl
 */
public class EquipmentItem {
    
    private int item_id;
    private int equipment_id;
    private int location_id;
    private String status;
    private String serial_number;

    public EquipmentItem() {
    }

    public EquipmentItem(int item_id, int equipment_id, int location_id, String status, String serial_number) {
        this.item_id = item_id;
        this.equipment_id = equipment_id;
        this.location_id = location_id;
        this.status = status;
        this.serial_number = serial_number;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getEquipment_id() {
        return equipment_id;
    }

    public void setEquipment_id(int equipment_id) {
        this.equipment_id = equipment_id;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }
    
    
    
}

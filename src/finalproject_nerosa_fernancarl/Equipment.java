/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalproject_nerosa_fernancarl;

/**
 *
 * @author FernanCarl
 */
public class Equipment {
    
    private int equipment_id;
    private String name;
    private String brand;
    private String model;
    private int category_id;
    private String categoryName;
    private String locationCode;

    public Equipment() {
    }

    public Equipment(int equipment_id, String name, String brand, String model, int category_id) {
        this.equipment_id = equipment_id;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.category_id = category_id;
    }
    
    public Equipment(int equipment_id, String name, String brand, String model, int category_id, String categoryName, String locationCode) {
        this.equipment_id = equipment_id;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.category_id = category_id;
        this.categoryName = categoryName;
        this.locationCode = locationCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public int getEquipment_id() {
        return equipment_id;
    }

    public void setEquipment_id(int equipment_id) {
        this.equipment_id = equipment_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
    
    
}

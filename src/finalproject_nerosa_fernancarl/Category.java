/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalproject_nerosa_fernancarl;

/**
 *
 * @author FernanCarl
 */
public class Category {
    
    private int Category_id;
    private String Category_name;

    public Category() {
    }

    public Category(int Category_id, String Category_name) {
        this.Category_id = Category_id;
        this.Category_name = Category_name;
    }

    public int getCategory_id() {
        return Category_id;
    }

    public void setCategory_id(int Category_id) {
        this.Category_id = Category_id;
    }

    public String getCategory_name() {
        return Category_name;
    }

    public void setCategory_name(String Category_name) {
        this.Category_name = Category_name;
    }

    
    @Override
    public String toString() {
        return this.Category_name; 
    }
    
    
}

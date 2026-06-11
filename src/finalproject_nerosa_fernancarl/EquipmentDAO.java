package finalproject_nerosa_fernancarl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author FernanCarl
 */
public class EquipmentDAO {
    
    public Equipment getEquipmentItemById(int itemId) {
    String sql = "SELECT e.equipment_id, e.name, e.brand, e.model, e.category_id, c.category_name, l.location_code " +
                 "FROM equipment_items ei " +
                 "INNER JOIN equipment e ON ei.equipment_id = e.equipment_id " +
                 "INNER JOIN categories c ON e.category_id = c.category_id " +
                 "INNER JOIN locations l ON ei.location_id = l.location_id " +
                 "WHERE ei.item_id = ?";
                 
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, itemId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                
                return new Equipment(
                    rs.getInt("equipment_id"),
                    rs.getString("name"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getInt("category_id"),
                    rs.getString("category_name"), 
                    rs.getString("location_code")  
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
    
    public boolean updateEquipmentSpecs(int itemId, String name, String brand, String model, int categoryId) {
    
    String sql = "UPDATE equipment e " +
                 "INNER JOIN equipment_items ei ON e.equipment_id = ei.equipment_id " +
                 "SET e.name = ?, e.brand = ?, e.model = ?, e.category_id = ? " +
                 "WHERE ei.item_id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, name);
        stmt.setString(2, brand);
        stmt.setString(3, model);
        stmt.setInt(4, categoryId);
        stmt.setInt(5, itemId);
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

public boolean updateEquipmentItemDetails(int itemId, int locationId, String status) {
    
    String sql = "UPDATE equipment_items SET location_id = ?, status = ? WHERE item_id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, locationId);
        stmt.setString(2, status); 
        stmt.setInt(3, itemId);
        
        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    public List<Object[]> getDashboardData() {
        List<Object[]> dataList = new ArrayList<>();
        
        
        String sql = "SELECT ei.item_id, e.name, c.category_name, ei.status, l.location_code " +
                     "FROM equipment_items ei " +
                     "INNER JOIN equipment e ON ei.equipment_id = e.equipment_id " +
                     "INNER JOIN categories c ON e.category_id = c.category_id " +
                     "INNER JOIN locations l ON ei.location_id = l.location_id " +
                     "ORDER BY ei.item_id ASC";

        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                
                Object[] row = new Object[5];
                row[0] = rs.getInt("item_id");            
                row[1] = rs.getString("name");            
                row[2] = rs.getString("category_name");   
                row[3] = rs.getString("status");          
                row[4] = rs.getString("location_code");   

                dataList.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Database execution error processing getDashboardData pipeline context:");
            e.printStackTrace();
        }
        
        return dataList;
    }

    /**
     * Handles creating the base equipment AND its initial operational tracking instance
     * inside a single, secure database transaction.
     */
    public boolean saveFullEquipmentWithItem(Equipment eq, String status, int locationId, String serialNumber) {
    String insertEquipmentSql = "INSERT INTO equipment (name, brand, model, category_id) VALUES (?, ?, ?, ?)";
    String insertItemSql = "INSERT INTO equipment_items (equipment_id, status, location_id, serial_number) VALUES (?, ?, ?, ?)";
    
    Connection conn = null;
    PreparedStatement stmtEq = null;
    PreparedStatement stmtItem = null;
    ResultSet generatedKeys = null;

    try {
        conn = DBConnection.getConnection();
        conn.setAutoCommit(false); 

        // [Step 1 & 2 remain identical to your current code for inserting base equipment]
        stmtEq = conn.prepareStatement(insertEquipmentSql, java.sql.Statement.RETURN_GENERATED_KEYS);
        stmtEq.setString(1, eq.getName());
        stmtEq.setString(2, eq.getBrand());
        stmtEq.setString(3, eq.getModel());
        stmtEq.setInt(4, eq.getCategory_id());
        stmtEq.executeUpdate();

        generatedKeys = stmtEq.getGeneratedKeys();
        int newEquipmentId = -1;
        if (generatedKeys.next()) {
            newEquipmentId = generatedKeys.getInt(1);
        } else {
            conn.rollback();
            return false;
        }

        // 3. Update the item insertion to use the real locationId variable!
        stmtItem = conn.prepareStatement(insertItemSql);
        stmtItem.setInt(1, newEquipmentId);
        stmtItem.setString(2, status);              
        
        // Change from setNull to setting the real selected integer ID
        stmtItem.setInt(3, locationId); 
        
        stmtItem.setString(4, serialNumber != null ? serialNumber : "SN-" + newEquipmentId);

        stmtItem.executeUpdate();
        conn.commit();
        return true;

    } catch (SQLException e) {
        e.printStackTrace();
        if (conn != null) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
        return false;
    } finally {
        try { if (generatedKeys != null) generatedKeys.close(); } catch (SQLException e) {}
        try { if (stmtEq != null) stmtEq.close(); } catch (SQLException e) {}
        try { if (stmtItem != null) stmtItem.close(); } catch (SQLException e) {}
        try { if (conn != null) conn.close(); } catch (SQLException e) {}
    }
}
    
    public boolean deleteEquipmentItem(int itemId) {
    String sql = "DELETE FROM equipment_items WHERE item_id = ?";
  
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, itemId);
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        System.err.println("Database execution error while attempting item deletion:");
        e.printStackTrace();
        return false;
    }
}
    
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalproject_nerosa_fernancarl;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author FernanCarl
 */
public class LocationsDAO {
    
    public List<Location> getAllLocations() {
        List<Location> list = new ArrayList<>();
        String sql = "SELECT location_id, location_code, description FROM locations ORDER BY location_code ASC";

        // Automated resource management using try-with-resources
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Instantiate a new Location entity per row returned
                Location loc = new Location();
                loc.setLocation_id(rs.getInt("location_id"));
                loc.setLocation_code(rs.getString("location_code"));
                loc.setDescription(rs.getString("description"));

                list.add(loc);
            }
        } catch (SQLException e) {
            System.err.println("Error pulling location definitions from database:");
            e.printStackTrace();
        }
        
        return list;
    }
    
}

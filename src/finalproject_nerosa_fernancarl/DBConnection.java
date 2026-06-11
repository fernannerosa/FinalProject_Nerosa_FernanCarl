/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalproject_nerosa_fernancarl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author FernanCarl
 */
public class DBConnection {

    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/equipmentnerosa",
                    "root",
                    "your_password_here"
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to database", e);
        }

        return connection;
    }
}

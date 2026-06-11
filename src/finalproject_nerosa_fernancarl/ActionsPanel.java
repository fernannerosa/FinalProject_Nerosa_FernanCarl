/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package finalproject_nerosa_fernancarl;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author FernanCarl
 */
public class ActionsPanel extends javax.swing.JPanel {
    
    private JTable table;
    private int row;
    private final EquipmentDAO equipmentDAO = new EquipmentDAO();
    
    /**
     * Creates new form ActionsPanel
     */
    public ActionsPanel() {
        initComponents();
    }
    
    public ActionsPanel(JTable table, int row) {
        initComponents();
        this.table = table;
        this.row = row;
        
        setupButtonListeners();
    }
    
    private void setupButtonListeners() {
        // --- THE EDIT BUTTON ---
        btnEdit.addActionListener(e -> {
            // Process: Forcefully terminate cell editing first to safely flush focus states
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }

            // Process: Push the Dialog display onto the end of the AWT Event queue
            javax.swing.SwingUtilities.invokeLater(() -> {
                int modelRow = table.convertRowIndexToModel(row);
                DefaultTableModel model = (DefaultTableModel) table.getModel();

                int itemId = getSafeIntegerId(model.getValueAt(modelRow, 0)); 
                
                String currentName = (String) model.getValueAt(modelRow, 1);
                String currentCategory = (String) model.getValueAt(modelRow, 2);
                String currentStatus = (String) model.getValueAt(modelRow, 3); // Extracted status
                String currentLocation = (String) model.getValueAt(modelRow, 4);
                
                Equipment completeEq = equipmentDAO.getEquipmentItemById(itemId); 
                String currentBrand = (completeEq != null && completeEq.getBrand() != null) ? completeEq.getBrand() : "";
                String currentModel = (completeEq != null && completeEq.getModel() != null) ? completeEq.getModel() : "";

                if (table.getTopLevelAncestor() instanceof EquipmentMainFrame) {
                    EquipmentMainFrame mainFrame = (EquipmentMainFrame) table.getTopLevelAncestor();
                    
                    // Process: Pass all 9 required arguments cleanly down into the constructor
                    showEditEquipmentDialog editDialog = new showEditEquipmentDialog(
                        mainFrame, true, itemId, currentName, currentBrand, currentModel, 
                        currentCategory, currentLocation, currentStatus
                    );
                    editDialog.setVisible(true);
                }
            });
        });

        // --- THE DELETE BUTTON ---
        btnDelete.addActionListener(e -> {
            // Process: Forcefully terminate cell editing first to safely flush focus states
            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }

            int modelRow = table.convertRowIndexToModel(row);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int itemId = getSafeIntegerId(model.getValueAt(modelRow, 0)); 
            String currentName = (String) model.getValueAt(modelRow, 1);

            // Process: Request confirmation from user before executing SQL mutations
            int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this, 
                "Are you sure you want to delete '" + currentName + "'?", 
                "Confirm Deletion", 
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE
            );

            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                boolean success = equipmentDAO.deleteEquipmentItem(itemId);
                if (success) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Equipment item removed successfully.");
                    if (table.getTopLevelAncestor() instanceof EquipmentMainFrame) {
                        EquipmentMainFrame mainFrame = (EquipmentMainFrame) table.getTopLevelAncestor();
                        mainFrame.refreshTableFromDatabase();
                        mainFrame.updateFooterCount();
                    }
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "Failed to delete equipment from database.", "Database Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

private int getSafeIntegerId(Object value) {
    if (value instanceof Number) {
        return ((Number) value).intValue();
    } else if (value != null) {
        String cleanText = value.toString().trim();
        try {
            return Integer.parseInt(cleanText);
        } catch (NumberFormatException e) {
            // Guard: Suppress the error logging trace if the value captured is a text title name descriptor
            if (!cleanText.isEmpty() && !Character.isDigit(cleanText.charAt(0))) {
                return -1; 
            }
            System.err.println("Failed to parse column ID string value: " + value);
        }
    }
    return -1;
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 6, 4));

        btnEdit.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(100, 100, 100));
        btnEdit.setText("\u270F");
        btnEdit.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 6, 2, 6));
        btnEdit.setContentAreaFilled(false);
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnEdit);

        btnDelete.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(220, 53, 69));
        btnDelete.setText("\uD83D\uDDD1");
        btnDelete.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 6, 2, 6));
        btnDelete.setContentAreaFilled(false);
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(btnDelete);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    // End of variables declaration//GEN-END:variables
}

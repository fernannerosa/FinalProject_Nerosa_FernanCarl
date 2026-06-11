/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package finalproject_nerosa_fernancarl;

import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author FernanCarl
 */
public class EquipmentMainFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(EquipmentMainFrame.class.getName());
    private final EquipmentDAO equipmentDAO = new EquipmentDAO();
    /**
     * Creates new form EquipmentMainFrame
     */
    public EquipmentMainFrame() {
        initComponents();
        setupTable();
        refreshTableFromDatabase();
        updateFooterCount();
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
    public void insertUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
    public void removeUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
    public void changedUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
});
  
    }
    
    private void filterTable() {
    String query = searchField.getText().trim().toLowerCase();
    if (query.startsWith("search equipment")) query = "";

    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    jTable1.setRowSorter(sorter);

    if (query.isEmpty()) {
        sorter.setRowFilter(null);
    } else {
        // Process: Safely query the exact indices for Equipment Name (1) and Location Code (4)
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query, 1, 4));
    }
    updateFooterCount();
}
    
    
    private void setupTable() {
    // 1. Process: Overwrite the NetBeans Designer template to explicitly hold 7 data tracks
    jTable1.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {},
        new String [] {
            "ID", "Equipment Name", "Category", "Status", "Location", "Last Maintenance", "Actions"
        }
    ) {
        Class[] types = new Class [] {
            java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
        };
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false, true
        };

        @Override public Class getColumnClass(int columnIndex) { return types [columnIndex]; }
        @Override public boolean isCellEditable(int rowIndex, int columnIndex) { return canEdit [columnIndex]; }
    });

    // 2. Process: Completely mask Column 0 (ID Column) from the visual layout display
    jTable1.getColumnModel().getColumn(0).setMinWidth(0);
    jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
    jTable1.getColumnModel().getColumn(0).setWidth(0);

    // 3. Process: Assign Column 6 (Actions Column) to utilize dynamic row evaluation
    jTable1.getColumnModel().getColumn(6).setCellRenderer((table, value, isSelected, hasFocus, row, col) -> {
        // Renderers draw statically, which is fine, but we track live row lookups in the Editor below
        ActionsPanel panel = new ActionsPanel(table, row);
        panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        return panel;
    });
    
    // FIX: Maintain a single-instance editor and calculate the row index dynamically upon click actions
    jTable1.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new javax.swing.JCheckBox()) {
        private ActionsPanel panel;

        @Override
        public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value,
                boolean isSelected, int row, int col) {
            
            // FIX: Get the true active visual row being edited right now rather than using the stale 'row' variable
            int activeEditingRow = table.getEditingRow();
            if (activeEditingRow == -1) {
                activeEditingRow = row; 
            }
            
            this.panel = new ActionsPanel(table, activeEditingRow);
            this.panel.setBackground(table.getSelectionBackground());
            return this.panel;
        }
        @Override public Object getCellEditorValue() { return ""; }
    });

    // 4. Set visual theme styling attributes
    jTable1.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 11));
    jTable1.getTableHeader().setForeground(new java.awt.Color(100, 100, 100));
    jTable1.getTableHeader().setBackground(new java.awt.Color(249, 250, 251));
    jTable1.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(220, 220, 220)));
    jTable1.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));
    
    // 5. Build row dynamic cell color highlighting engine
    jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int col) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

            label.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));
            label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 13));

            if (!isSelected) {
                label.setBackground(java.awt.Color.WHITE);
                label.setForeground(new java.awt.Color(30, 30, 30));
            }

            // Bold Equipment Name (Column Index 1)
            if (col == 1) {
                label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
            }
            
            // Status Badges (Column Index 3)
            if (col == 3) {
                label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
                String status = (value != null) ? value.toString() : "";
                if (!isSelected) {
                    switch (status) {
                        case "Operational":
                        case "Available":
                            label.setBackground(new java.awt.Color(220, 252, 231));
                            label.setForeground(new java.awt.Color(21, 128, 61));
                            break;
                        case "Under Maintenance":
                        case "Maintenance":
                            label.setBackground(new java.awt.Color(254, 243, 199));
                            label.setForeground(new java.awt.Color(180, 83, 9));
                            break;
                        case "Out of Service":
                        case "Borrowed":
                            label.setBackground(new java.awt.Color(254, 226, 226));
                            label.setForeground(new java.awt.Color(185, 28, 28));
                            break;
                        default:
                            label.setBackground(java.awt.Color.WHITE);
                            label.setForeground(new java.awt.Color(30, 30, 30));
                    }
                }
            } else {
                label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            }

            label.setOpaque(true);
            return label;
        }
    });
}
    
    public void updateFooterCount() {
    int count = jTable1.getRowCount();
    lblFooter.setText("Showing " + count + " of " + count + " equipment items");
}
    
    public void refreshTableFromDatabase() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0); 
    
    List<Object[]> rows = equipmentDAO.getDashboardData();
    
    for (Object[] rowData : rows) {
        Object[] sevenCol = new Object[]{
            rowData[0],         // Index 0: Hidden Primary Key (item_id Integer)
            rowData[1],         // Index 1: Equipment Name (String)
            rowData[2],         // Index 2: Category Name (String)
            rowData[3],         // Index 3: Status (String -> Available / Maintenance / Borrowed)
            rowData[4],         // Index 4: Location Code (String)
            "N/A",              // Index 5: Last Maintenance Placeholder
            ""                  // Index 6: ActionsPanel renderer target placeholder
        };
        model.addRow(sevenCol);
    }
}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        header = new javax.swing.JPanel();
        textPanel = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        searchField = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        footer = new javax.swing.JPanel();
        lblFooter = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Equipment Management System  - Main Dashboard");
        setBackground(new java.awt.Color(249, 250, 251));

        header.setBackground(new java.awt.Color(249, 250, 251));
        header.setBorder(javax.swing.BorderFactory.createEmptyBorder(18, 22, 18, 22));
        header.setLayout(new java.awt.BorderLayout());

        textPanel.setOpaque(false);
        textPanel.setLayout(new java.awt.BorderLayout());

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(0, 0, 0));
        lblTitle.setText("Equipment Management System");
        textPanel.add(lblTitle, java.awt.BorderLayout.NORTH);

        lblSubtitle.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSubtitle.setForeground(new java.awt.Color(102, 102, 102));
        lblSubtitle.setText("Sort, search, and manage your equipment inventory");
        textPanel.add(lblSubtitle, java.awt.BorderLayout.SOUTH);

        header.add(textPanel, java.awt.BorderLayout.WEST);

        getContentPane().add(header, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(249, 250, 251));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(18, 22, 18, 22));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        searchField.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        searchField.setForeground(new java.awt.Color(150, 150, 150));
        searchField.setText(" Search equipment by name or location...");
        searchField.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), javax.swing.BorderFactory.createEmptyBorder(7, 9, 7, 9)));
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchFieldFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 12);
        jPanel1.add(searchField, gridBagConstraints);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Operational", "Maintenance", "Out of Service" }));
        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 12);
        jPanel1.add(jComboBox1, gridBagConstraints);

        jButton1.setBackground(new java.awt.Color(13, 110, 253));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("+ Add Equipment");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(this::jButton1ActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel1.add(jButton1, gridBagConstraints);

        jPanel2.add(jPanel1, java.awt.BorderLayout.NORTH);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Equipment Name", "Category", "Status", "Location", "Last Maintenance", "Actions"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setColumnSelectionAllowed(true);
        jTable1.setGridColor(new java.awt.Color(240, 240, 240));
        jTable1.setRowHeight(44);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(160);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(140);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(130);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(140);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(130);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(90);
        }

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        footer.setLayout(new java.awt.BorderLayout());

        lblFooter.setForeground(new java.awt.Color(102, 102, 102));
        lblFooter.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 22, 10, 22));
        footer.add(lblFooter, java.awt.BorderLayout.WEST);

        getContentPane().add(footer, java.awt.BorderLayout.PAGE_END);

        setSize(new java.awt.Dimension(920, 650));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void searchFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFieldFocusGained
        if (searchField.getText().startsWith(" Search")) {
            searchField.setText("");
            searchField.setForeground(new java.awt.Color(30, 30, 30));
        }
    }//GEN-LAST:event_searchFieldFocusGained

    private void searchFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFieldFocusLost
        if (searchField.getText().isEmpty()) {
            searchField.setForeground(new java.awt.Color(150, 150, 150));
            searchField.setText(" Search equipment by name or location...");
        }
    }//GEN-LAST:event_searchFieldFocusLost

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        String selected = (String) jComboBox1.getSelectedItem();
    TableRowSorter<DefaultTableModel> sorter =
        new TableRowSorter<>((DefaultTableModel) jTable1.getModel());
    jTable1.setRowSorter(sorter);
    if ("All".equals(selected)) {
        sorter.setRowFilter(null);
    } else {
        sorter.setRowFilter(RowFilter.regexFilter("(?i)^" + selected + "$", 2));
    }
    updateFooterCount();    
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    new showAddEquipmentDialog(this, true).setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new EquipmentMainFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel footer;
    private javax.swing.JPanel header;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblFooter;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextField searchField;
    private javax.swing.JPanel textPanel;
    // End of variables declaration//GEN-END:variables
}

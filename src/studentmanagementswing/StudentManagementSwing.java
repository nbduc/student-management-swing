/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagementswing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author duc
 */
public class StudentManagementSwing extends JFrame implements ActionListener{
    private static JPanel titlePanel;
    private static JLabel titleLabel;
    
    private static JTable studentListTable;
    private static JScrollPane scrollPane;
    
    private static JPanel formPanel;
    private static JTextField idField;
    private static JTextField nameField;
    private static JTextField pointField;
    private static JTextField addressField;
    private static JTextArea noteField;
    private static JScrollPane noteFiledScrollPane;
    private static JLabel imageLabel;
    private static JButton imageButton;
    
    private static JPanel mainPanel;
    
    private static JPanel actionPanel;
    private static JButton addButton;
    private static JButton removeButton;
    private static JButton updateButton;
    private static JButton exportButton;
    
    static class StudentTableModel extends AbstractTableModel {
        private final String[] columnNames = {
            "Mã hs",
            "Tên học sinh",
            "Điểm",
            "Địa chỉ",
            "Ghi chú"
        };
        private final Object[][] data = {
            {1, "Smith", 8.5, "123 Lê Thánh Tôn", "Ngoan hiền, chăm học"},
            {2, "Doe", 8, "456 Nguyễn Thị Minh Khai", "Tiếp thu bài tốt, tuy nhiên còn hơi chậm"},
            {3, "Black", 7.6, "987 Hùng Vương", "Học khá, chăm chú nghe giảng"},
            {4, "White",  9, "112 Nguyễn Thái Học", "Chăm chỉ, hoàn thành xuất sắc bài tập"},
            {5, "Brown",  9.2, "89 Trần Hưng Đạo", "Gương sáng chói loà"}
        };
        
        public final Object[] longValues = 
            {10, "*".repeat(10), 9.25, "*".repeat(50), 
                "*".repeat(100)};

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
    }
    static class StudentListSelectionHandler implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
            System.out.println(lsm.getAnchorSelectionIndex());
        }
    }
    private static void initColumnSizes(JTable table) {
        StudentTableModel model = (StudentTableModel)table.getModel();
        TableColumn column;
        Component comp;
        int headerWidth;
        int cellWidth;
        Object[] longValues = model.longValues;
        TableCellRenderer headerRenderer =
            table.getTableHeader().getDefaultRenderer();
 
        for (int i = 0; i < model.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
 
            comp = headerRenderer.getTableCellRendererComponent(
                null, column.getHeaderValue(), false, false, 0, 0);
            headerWidth = comp.getPreferredSize().width;
 
            comp = table.getDefaultRenderer(model.getColumnClass(i))
                .getTableCellRendererComponent(table, longValues[i], false, false, 0, i);
            cellWidth = comp.getPreferredSize().width;
 
            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
        }
    }
    public static void addComponentsToPane(Container pane){
        pane.setLayout(new BorderLayout());
        
        titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titleLabel = new JLabel("QUẢN LÝ HỌC SINH");
        titleLabel.setFont(new Font("Calibri", Font.PLAIN, 30));
        titlePanel.add(titleLabel);
        
        studentListTable = new JTable(new StudentTableModel());
        studentListTable.setFillsViewportHeight(true);
        initColumnSizes(studentListTable);
        studentListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentListTable.getSelectionModel().addListSelectionListener(new StudentListSelectionHandler());
        
        scrollPane = new JScrollPane(studentListTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách học sinh"));
        
        formPanel = new JPanel();
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin học sinh"));
        formPanel.setLayout(new GridBagLayout());
        idField = new JTextField(25);
        idField.setEnabled(false);
        nameField = new JTextField(25);
        pointField = new JTextField(25);
        addressField = new JTextField(25);
        noteField = new JTextArea(3, 25);
        noteFiledScrollPane = new JScrollPane(noteField);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.EAST;
        c.insets = new Insets(3,5,3,5);
        formPanel.add(new JLabel("Mã học sinh:"), c);
        c.gridy += 1;
        formPanel.add(new JLabel("Tên học sinh:"), c);
        c.gridy += 1;
        formPanel.add(new JLabel("Điểm:"), c);
        c.gridy += 1;
        formPanel.add(new JLabel("Địa chỉ:"), c);
        c.gridx = 2;
        c.gridy = 0;
        formPanel.add(new JLabel("Ghi chú:"), c);
        
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(3,5,3,5);
        formPanel.add(idField, c);
        c.gridy += 1;
        formPanel.add(nameField, c);
        c.gridy += 1;
        formPanel.add(pointField, c);
        c.gridy += 1;
        formPanel.add(addressField, c);
        
        c = new GridBagConstraints();
        c.gridx = 3;
        c.gridy = 0;
        c.gridheight = 3;
        c.insets = new Insets(3,5,3,5);
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
        formPanel.add(noteFiledScrollPane, c);
        
        imageLabel = new JLabel(new ImageIcon("trend-avatar-1.jpg"));
        imageButton = new JButton("Thay đổi hình ảnh");
        
        c = new GridBagConstraints();
        c.gridx = 4;
        c.gridy = 0;
        c.insets = new Insets(0,10,0,0);
        formPanel.add(imageLabel, c);
        c.gridy = 3;
        formPanel.add(imageButton, c);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(formPanel, BorderLayout.SOUTH);
        
        actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());
        addButton = new JButton("Thêm");
        removeButton = new JButton("Xoá");
        updateButton = new JButton("Cập nhật");
        exportButton = new JButton("Xuất CSV");
        actionPanel.add(addButton);
        actionPanel.add(removeButton);
        actionPanel.add(updateButton);
        actionPanel.add(exportButton);
        
        pane.add(titlePanel, BorderLayout.NORTH);
        pane.add(actionPanel, BorderLayout.SOUTH);
        pane.add(mainPanel, BorderLayout.CENTER);
    }
    private static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame window = new JFrame();
        addComponentsToPane(window.getContentPane());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Quản lý học sinh");
        window.pack();
        window.setVisible(true);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                createAndShowGUI();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
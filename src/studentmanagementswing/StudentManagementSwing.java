/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagementswing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import model.Student;
import utils.StudentUtils;
import verifier.NotEmptyVerifier;
import verifier.PointVerifier;

/**
 *
 * @author duc
 */
public class StudentManagementSwing extends JFrame{
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
    private static JLabel imageNameLabel;
    private static JButton imageButton;
    
    private static JSplitPane mainPanel;
    
    private static JPanel actionPanel;
    private static JButton resetButton;
    private static JButton addButton;
    private static JButton removeButton;
    private static JButton updateButton;
    private static JButton exportButton;
    
    static class StudentTableModel extends AbstractTableModel {
        public int selectedIndex = 0;
        public final ArrayList<Student> studentList;
        public StudentTableModel(ArrayList<Student> studentList) {
            super();
            this.studentList = studentList;
        }
        private final String[] columnNames = {
            "Mã hs",
            "Tên học sinh",
            "Điểm",
            "Địa chỉ",
            "Ghi chú"
        };
        
        public final Object[] longValues = 
            {10, "*".repeat(10), 9.25, "*".repeat(50), 
                "*".repeat(100)};

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return studentList.size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            if(studentList.size() == 0){
                return null;
            }
            Student student = studentList.get(row);
            switch(col){
                case 0: return student.getId();
                case 1: return student.getFullName();
                case 2: return student.getPoint();
                case 3: return student.getAddress();
                case 4: return student.getNote();
                default: return null;
            }
        }

        public Class getColumnClass(int c) {
            if(studentList.size() == 0){
                return Object.class;
            }
            return getValueAt(0, c).getClass();
        }
        
        public void addStudent(Student student){
            studentList.add(student);
            fireTableDataChanged();
        }
        
        public void removeStudent(){
            studentList.remove(selectedIndex);
            fireTableDataChanged();
        }
    }
    static class StudentListSelectionHandler implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
            int index = lsm.getAnchorSelectionIndex();
            int length = ((StudentTableModel)studentListTable.getModel()).studentList.size();
            if(index >= length || index < 0){
                studentListTable.clearSelection();
                return;
            }
            
            addButton.setEnabled(lsm.isSelectionEmpty());
            removeButton.setEnabled(!lsm.isSelectionEmpty());
            updateButton.setEnabled(!lsm.isSelectionEmpty());
            
            StudentTableModel tableModel = (StudentTableModel)studentListTable.getModel();
            tableModel.selectedIndex = index;
            Student s = tableModel.studentList.get(index);
            idField.setText(String.valueOf(s.getId()));
            nameField.setText((String)s.getFullName());
            pointField.setText(String.valueOf(s.getPoint()));
            addressField.setText((String)s.getAddress());
            noteField.setText((String)s.getNote());
            setImage(imageLabel, imageNameLabel, s.getImage());
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
    public static void setImage(JLabel imageLabel, JLabel imageNameLabel, String imagePath){
        imageLabel.setIcon(new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT)));
        imageNameLabel.setText(imagePath);
    }
    public static void addComponentsToPane(Container pane){
        pane.setLayout(new BorderLayout());
        
        titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
        titleLabel = new JLabel("QUẢN LÝ HỌC SINH");
        titleLabel.setFont(new Font("Calibri", Font.PLAIN, 30));
        titlePanel.add(titleLabel);
        
        studentListTable = new JTable(new StudentTableModel(StudentUtils.getAllStudents()));
        studentListTable.setFillsViewportHeight(true);
        initColumnSizes(studentListTable);
        studentListTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentListTable.getSelectionModel().addListSelectionListener(new StudentListSelectionHandler());
        
        scrollPane = new JScrollPane(studentListTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách học sinh"));
        scrollPane.setPreferredSize(new Dimension(-1, 380));
        
        formPanel = new JPanel();
        formPanel.setLayout(new FlowLayout());
        JScrollPane formScrollPane = new JScrollPane(formPanel);
        formScrollPane.setBorder(BorderFactory.createTitledBorder("Thông tin học sinh"));
        
        idField = new JTextField(25);
        idField.setEnabled(false);
        nameField = new JTextField(25);
        nameField.setInputVerifier(new NotEmptyVerifier("Tên học sinh"));
        pointField = new JTextField(25);
        pointField.setInputVerifier(new NotEmptyVerifier("Điểm"));
        pointField.setInputVerifier(new PointVerifier());
        addressField = new JTextField(25);
        addressField.setInputVerifier(new NotEmptyVerifier("Địa chỉ"));
        noteField = new JTextArea(4, 25);
        noteField.setLineWrap(true);
        noteFiledScrollPane = new JScrollPane(noteField);
        JPanel formGBLPanel1 = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.EAST;
        c.insets = new Insets(3,5,3,5);
        formGBLPanel1.add(new JLabel("Mã học sinh:"), c);
        c.gridy += 1;
        formGBLPanel1.add(new JLabel("Tên học sinh:"), c);
        c.gridy += 1;
        formGBLPanel1.add(new JLabel("Điểm:"), c);
        c.gridy += 1;
        formGBLPanel1.add(new JLabel("Địa chỉ:"), c);
        
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        formGBLPanel1.add(idField, c);
        c.gridy += 1;
        formGBLPanel1.add(nameField, c);
        c.gridy += 1;
        formGBLPanel1.add(pointField, c);
        c.gridy += 1;
        formGBLPanel1.add(addressField, c);
        formPanel.add(formGBLPanel1);
        
        JPanel formGBLPanel2 = new JPanel(new GridBagLayout());
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(3,5,3,5);
        c.anchor = GridBagConstraints.WEST;
        formGBLPanel2.add(new JLabel("Ghi chú:"), c);
        c.gridy = 1;
        c.gridheight = 4;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.BOTH;
        formGBLPanel2.add(noteFiledScrollPane, c);
        formPanel.add(formGBLPanel2);
        
        JPanel formGBLPanel3 = new JPanel(new GridBagLayout());
        imageLabel = new JLabel();
        JFileChooser imageChooser = new JFileChooser();
        imageChooser.setDialogTitle("Chọn hình ảnh");
        imageChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        imageChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        imageChooser.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
        imageChooser.setAcceptAllFileFilterUsed(false);
        imageNameLabel = new JLabel();
        imageButton = new JButton("Chọn hình ảnh");
        imageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = imageChooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = imageChooser.getSelectedFile();
                    setImage(imageLabel, imageNameLabel, file.toPath().toString());
                }
            }
        });
        
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 3;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(0,10,0,0);
        formGBLPanel3.add(imageLabel, c);
        c.gridy = 3;
        formGBLPanel3.add(imageNameLabel, c);
        c.gridy = 8;
        formGBLPanel3.add(imageButton, c);
        formPanel.add(formGBLPanel3);
        
        mainPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scrollPane, formScrollPane);
        
        actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());
        resetButton = new JButton("Làm mới");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                studentListTable.clearSelection();
                idField.setText(null);
                nameField.setText(null);
                addressField.setText(null);
                pointField.setText(null);
                noteField.setText(null);
                imageLabel.setIcon(null);
                imageNameLabel.setText(null);
            }
        });
        addButton = new JButton("Thêm");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Student student = new Student();
                    
                    student.setFullName(nameField.getText());
                    student.setPoint(Float.parseFloat(pointField.getText()));
                    student.setAddress(addressField.getText());
                    student.setNote(noteField.getText());
                    student.setImage(StudentUtils.saveImage(imageNameLabel.getText(), student.getId(), null));
                    
                    StudentUtils.writeToBinaryFile(student);
                    
                    ((StudentTableModel)studentListTable.getModel()).addStudent(student);
                    
                    System.out.println(student.toString());
                    
                    JOptionPane.showMessageDialog(pane, "Thêm thành công", "About", 
                        JOptionPane.INFORMATION_MESSAGE);
                } catch(Exception ex) {
                    JOptionPane.showMessageDialog( pane, ex.getMessage(), "Lỗi", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        removeButton = new JButton("Xoá");
        removeButton.setEnabled(false);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    StudentUtils.removeStudent(id);
                    ((StudentTableModel)studentListTable.getModel()).removeStudent();
                    JOptionPane.showMessageDialog(pane, "Xoá thành công", "About", 
                        JOptionPane.INFORMATION_MESSAGE);
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        updateButton = new JButton("Cập nhật");
        updateButton.setEnabled(false);
        exportButton = new JButton("Xuất CSV");
        actionPanel.add(resetButton);
        actionPanel.add(addButton);
        actionPanel.add(removeButton);
        actionPanel.add(updateButton);
        actionPanel.add(exportButton);
        
        pane.add(titlePanel, BorderLayout.NORTH);
        pane.add(actionPanel, BorderLayout.SOUTH);
        pane.add(mainPanel, BorderLayout.CENTER);
    }
    private static void createAndShowGUI() {
//        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame window = new JFrame();
        addComponentsToPane(window.getContentPane());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Quản lý học sinh");
        window.setVisible(true);
        window.setSize(1150, 650);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
    
}
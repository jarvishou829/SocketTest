package test;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.*;
import java.util.Vector;

public class function {
    public static JFrame func;
    private static Vector<String> dataTitle = new Vector<String>();//表格列名
    private static Vector<Vector<String>> data = new Vector<Vector<String>>();//表格单元格内容
    private static int selectedRow;
    private static String uname;


    public static void main(String args) {
        uname=args;
        func =new JFrame("个人云空间功能页");
        func.setSize(760,480);
        func.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new FlowLayout());
        placeComponents(panel);
        func.setContentPane(panel);
        func.pack();
        func.setLocationRelativeTo(null);
        func.setVisible(true);

    }

    private static void placeComponents(JPanel panel){
        JLabel sourcelist = new JLabel("资源列表");
        sourcelist.setBounds(10,20,120,25);
        panel.add(sourcelist);
        dataTitle.add("资源名称");
        dataTitle.add("所在设备");
        dataTitle.add("是否可用");
        dataTitle.add("备注");
        // 创建一个表格，指定 表头 和 所有行数据
        DefaultTableModel newTableModel = new DefaultTableModel(data, dataTitle){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            };

        };
        JTable table = new JTable(newTableModel);
        try {
            Refresh();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.updateUI();

        // 设置表格内容颜色
        table.setForeground(Color.BLACK);                   // 字体颜色
        table.setFont(new Font(null, Font.PLAIN, 14));      // 字体样式
        table.setSelectionForeground(Color.DARK_GRAY);      // 选中后字体颜色
        table.setSelectionBackground(Color.LIGHT_GRAY);     // 选中后字体背景
        table.setGridColor(Color.GRAY);                     // 网格颜色
        // 设置表头
        table.getTableHeader().setFont(new Font(null, Font.BOLD, 14));  // 设置表头名称字体样式
        table.getTableHeader().setForeground(Color.RED);                // 设置表头名称字体颜色
        table.getTableHeader().setResizingAllowed(false);               // 设置不允许手动改变列宽
        table.getTableHeader().setReorderingAllowed(false);             // 设置不允许拖动重新排序各列
        // 设置行高
        table.setRowHeight(30);
        // 第一列列宽设置为40
        table.getColumnModel().getColumn(0).setPreferredWidth(40);
        // 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
        table.setPreferredScrollableViewportSize(new Dimension(600, 300));

        // 把 表格 放到 滚动面板 中（表头将自动添加到滚动面板顶部）
        JScrollPane scrollPane = new JScrollPane(table);

        // 添加 滚动面板 到 内容面板
        panel.add(scrollPane);

        JButton openBtn = new JButton("声明资源");
        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String addp = showFileOpenDialog(table, scrollPane);
                String[] array = addp.split("\\\\");
                System.out.println(array[array.length-1]);
                String pathway=array[array.length-1];
                try {
                    additem(pathway);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                try {
                    DefaultTableModel model =(DefaultTableModel) table.getModel();
                    while(model.getRowCount()>0){
                        model.removeRow(model.getRowCount()-1);
                    }
                    Refresh();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                table.updateUI();
            }
        });

        panel.add(openBtn);
        JButton loadBtn = new JButton("请求资源");

        ListSelectionModel selectionModel = table.getSelectionModel();
        int selectionMode=ListSelectionModel.SINGLE_SELECTION;
        selectionModel.setSelectionMode(selectionMode);
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent g) {
                // 获取选中的第一行

                selectedRow = table.getSelectedRow();
            }
        });
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(selectedRow);
                String getName = String.valueOf(table.getValueAt(selectedRow,0));
                System.out.println(getName);
            }
        });


//        loadBtn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ListSelectionModel selectionModel = table.getSelectionModel();
//                int selectionMode=ListSelectionModel.SINGLE_SELECTION;
//                selectionModel.setSelectionMode(selectionMode);
//                selectionModel.addListSelectionListener(new ListSelectionListener() {
//                    @Override
//                    public void valueChanged(ListSelectionEvent g) {
//                        // 获取选中的第一行
//                        selectedRow = table.getSelectedRow();
//                    }
//                });
//                System.out.println(selectedRow);
//                String getName = String.valueOf(table.getValueAt(selectedRow,0));
//                System.out.println(getName);
//            }
//        });
        panel.add(loadBtn);

    }
    private static String showFileOpenDialog(Component parent, JScrollPane msgTextArea) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置默认显示的文件夹为当前文件夹
        fileChooser.setCurrentDirectory(new File("."));

        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(true);

        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("zip(*.zip, *.rar)", "zip", "rar"));
        // 设置默认使用的文件过滤器
        fileChooser.setFileFilter(new FileNameExtensionFilter("image(*.jpg, *.png, *.gif)", "jpg", "png", "gif"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);
        String path="NULL";

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"确定", 则获取选择的文件路径
            File file = fileChooser.getSelectedFile();

            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            // File[] files = fileChooser.getSelectedFiles();

            path = file.getAbsolutePath();
            System.out.println(path);

            //msgTextArea.append("打开文件: " + file.getAbsolutePath() + "\n\n");
        }
        return path;
    }
    private static void Refresh() throws SQLException {
        Connection conn;
        conn = DriverManager.getConnection(Conn.url,Conn.user, Conn.password);
        if (!conn.isClosed()) {
            System.out.println("数据库连接成功");
        }
        Statement stmt = null;
        stmt = conn.createStatement();
        //PreparedStatement st=null;
        String sql;
        sql = "SELECT srcname, usrname, available, info FROM source";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()){
            //未完成
            Vector<String> Adi = new Vector<String>();
            Adi.add(rs.getString("srcname"));
            Adi.add(rs.getString("usrname"));
            Adi.add(String.valueOf(rs.getInt("available")));
            Adi.add(rs.getString("info"));
            data.add(Adi);
        }
        conn.close();

    }
    private static void additem(String args) throws SQLException {
        Connection conn;
        conn = DriverManager.getConnection(Conn.url,Conn.user, Conn.password);
        if (!conn.isClosed()) {
            System.out.println("数据库连接成功");
        }
        Statement stmt = null;
        stmt = conn.createStatement();
        //PreparedStatement st=null;
        String srcid=MD5.md5Encryption(args);
        String sql;
        sql = "INSERT into source (srcname, usrname, srcid) VALUES ('"+args+"','"+uname+"','"+srcid+"')";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.executeUpdate();
        conn.close();
    }
}
package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class function {
    public static JFrame func;

    public static void main(String args) {
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
        String[] columnNames = {"资源名称", "唯一编码", "是否可用", "备注"};

        // 表格所有行数据
        Object[][] rowData = {
                {"文档1","001","是","无"},
                {"文档1","001","是","无"},
                {"文档1","001","是","无"},
                {"文档1","001","是","无"},
                {"文档1","001","是","无"},
                {"音乐1","001","是","无"},
                {"音乐1","001","是","无"},
                {"音乐1","001","是","无"},
                {"音乐1","001","是","无"},
                {"视频1","001","是","无"},
                {"视频1","001","是","无"},
                {"视频1","001","是","无"},
                {"视频1","001","是","无"},
                {"视频1","001","是","无"},
                {"图片1","001","是","无"},
                {"图片1","001","是","无"}

        };

        // 创建一个表格，指定 表头 和 所有行数据
        JTable table = new JTable(rowData, columnNames);

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

    }
}
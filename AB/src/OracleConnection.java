import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class OracleConnection {

    //数据库连接对象
    private static Connection conn = null;

    private static String driver = "oracle.jdbc.driver.OracleDriver"; //驱动

    private static String url = "jdbc:oracle:thin:@localhost:1521:orcl"; //连接字符串

    private static String username = "system"; //用户名

    private static String password = "123456"; //密码

    // 获得连接对象
    private static synchronized Connection getConn(){
        if(conn == null){
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    //执行除查询外的sql语句
    public static void query(String sql){
        PreparedStatement pstmt = null;
        try {
            pstmt = getConn().prepareStatement(sql);
            pstmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        /*
            UI设计
         */

        //顶层容器JFrame
        JFrame jf = new JFrame("通讯录");
        jf.setLocation(400,150);
        jf.setSize(1200,800);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLayout(null);

        //中间容器JPanel
        JPanel jp = new JPanel();
        jp.setBounds(0,0,1200,800);
        jp.setBackground(Color.WHITE);
        jp.setLayout(null);
        jf.add(jp);

        //查询---下拉列表
        JComboBox<String> jcb_1 = new JComboBox<>();
        jcb_1.setBounds(50,20,300,30);
        jcb_1.addItem("----------请选择检索方式----------");
        jcb_1.addItem("学号");
        jcb_1.addItem("姓名");
        jp.add(jcb_1);

        //查询---滚动容器和文本域
        JScrollPane jsp_1 = new JScrollPane();
        jsp_1.setBounds(50,70,300,40);
        jp.add(jsp_1);

        JTextArea jta_1 = new JTextArea();
        jta_1.setLineWrap(true);
        jta_1.setForeground(Color.BLACK);
        jta_1.setFont(new Font("楷体",Font.BOLD,24));
        jsp_1.setViewportView(jta_1);

        //查询按钮
        JButton jb_1 = new JButton("查询");
        jb_1.setBounds(400,30,100,60);
        jp.add(jb_1);

        //查询结果---滚动容器和文本域
        JScrollPane jsp_2 = new JScrollPane();
        jsp_2.setBounds(50,130,450,200);
        jp.add(jsp_2);

        JTextArea jta_2 = new JTextArea();
        jta_2.setLineWrap(true);
        jta_2.setForeground(Color.BLACK);
        jta_2.setFont(new Font("楷体",Font.BOLD,24));
        jsp_2.setViewportView(jta_2);

        //为查询按钮添加事件处理
        jb_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String)jcb_1.getSelectedItem();
                if("学号".equals(selected)){
                    String sno = jta_1.getText();
                    String sql = "select * from scott.adressbook where sno='" + sno + "'";
                    PreparedStatement pstmt = null;
                    ResultSet rs = null;
                    try {
                        pstmt = getConn().prepareStatement(sql);
                        //建立一个结果集，用来保存查询出来的结果
                        rs = pstmt.executeQuery();
                        if(!rs.next()){
                            jta_2.setText("查询无结果！");
                        }else{
                            do{
                                String no = rs.getString("sno");
                                String name = rs.getString("sname");
                                String phone = rs.getString("stelphone");
                                jta_2.setText(no + "  " + name + "  " + phone);
                            }while (rs.next());
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }finally {
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        try {
                            if (pstmt != null) {
                                pstmt.close();
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        //close();
                    }
                }else if("姓名".equals(selected)){
                    String sname = jta_1.getText();
                    String sql = "select * from scott.adressbook where sname='" + sname + "'";
                    PreparedStatement pstmt = null;
                    ResultSet rs = null;
                    try {
                        pstmt = getConn().prepareStatement(sql);
                        //建立一个结果集，用来保存查询出来的结果
                        rs = pstmt.executeQuery();
                        if(!rs.next()){
                            jta_2.setText("查询无结果！");
                        }else{
                            do{
                                String no = rs.getString("sno");
                                String name = rs.getString("sname");
                                String phone = rs.getString("stelphone");
                                jta_2.setText(no + "  " + name + "  " + phone);
                            }while (rs.next());
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }finally {
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        try {
                            if (pstmt != null) {
                                pstmt.close();
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        //close();
                    }
                }
            }
        });

        //删除---下拉列表
        JComboBox<String> jcb_2 = new JComboBox<>();
        jcb_2.setBounds(600,90,300,30);
        jcb_2.addItem("----------请选择删除方式----------");
        jcb_2.addItem("学号");
        jcb_2.addItem("姓名");
        jp.add(jcb_2);

        //删除---滚动容器和文本域
        JScrollPane jsp_3 = new JScrollPane();
        jsp_3.setBounds(600,140,300,40);
        jp.add(jsp_3);

        JTextArea jta_3 = new JTextArea();
        jta_3.setLineWrap(true);
        jta_3.setForeground(Color.BLACK);
        jta_3.setFont(new Font("楷体",Font.BOLD,24));
        jsp_3.setViewportView(jta_3);

        //删除按钮
        JButton jb_2 = new JButton("删除");
        jb_2.setBounds(950,100,100,60);
        jp.add(jb_2);

        //删除结果---滚动容器和文本域
        JScrollPane jsp_4 = new JScrollPane();
        jsp_4.setBounds(680,200,200,60);
        jp.add(jsp_4);

        JTextArea jta_4 = new JTextArea();
        jta_4.setLineWrap(true);
        jta_4.setForeground(Color.BLACK);
        jta_4.setFont(new Font("楷体",Font.BOLD,24));
        jsp_4.setViewportView(jta_4);

        //为删除按钮添加事件处理
        jb_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String)jcb_2.getSelectedItem();
                if("学号".equals(selected)){
                    String sno = jta_3.getText();
                    String sql = "delete from scott.adressbook where sno='" + sno + "'";
                    query(sql);
                    jta_4.setText("删除成功！");
                }else if("姓名".equals(selected)){
                    String sname = jta_3.getText();
                    String sql = "delete from scott.adressbook where sname='" + sname + "'";
                    query(sql);
                    jta_4.setText("删除成功！");
                }
            }
        });

        //增加---标签JLabel_1
        JLabel jl_1 = new JLabel("学号");
        jl_1.setBounds(20,400,70,40);
        jl_1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        jp.add(jl_1);

        //增加---标签JLabel_2
        JLabel jl_2 = new JLabel("姓名");
        jl_2.setBounds(20,450,70,40);
        jl_2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        jp.add(jl_2);

        //增加---标签JLabel_3
        JLabel jl_3 = new JLabel("电话号码");
        jl_3.setBounds(20,500,70,40);
        jl_3.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        jp.add(jl_3);

        //增加学号---滚动容器和文本域
        JScrollPane jsp_5 = new JScrollPane();
        jsp_5.setBounds(100,400,300,40);
        jp.add(jsp_5);

        JTextArea jta_5 = new JTextArea();
        jta_5.setLineWrap(true);
        jta_5.setForeground(Color.BLACK);
        jta_5.setFont(new Font("楷体",Font.BOLD,24));
        jsp_5.setViewportView(jta_5);

        //增加姓名---滚动容器和文本域
        JScrollPane jsp_6 = new JScrollPane();
        jsp_6.setBounds(100,450,300,40);
        jp.add(jsp_6);

        JTextArea jta_6 = new JTextArea();
        jta_6.setLineWrap(true);
        jta_6.setForeground(Color.BLACK);
        jta_6.setFont(new Font("楷体",Font.BOLD,24));
        jsp_6.setViewportView(jta_6);

        //增加电话号码---滚动容器和文本域
        JScrollPane jsp_7 = new JScrollPane();
        jsp_7.setBounds(100,500,300,40);
        jp.add(jsp_7);

        JTextArea jta_7 = new JTextArea();
        jta_7.setLineWrap(true);
        jta_7.setForeground(Color.BLACK);
        jta_7.setFont(new Font("楷体",Font.BOLD,24));
        jsp_7.setViewportView(jta_7);

        //增加按钮
        JButton jb_3 = new JButton("添加");
        jb_3.setBounds(420,440,100,60);
        jp.add(jb_3);

        //增加结果---滚动容器和文本域
        JScrollPane jsp_8 = new JScrollPane();
        jsp_8.setBounds(140,560,200,60);
        jp.add(jsp_8);

        JTextArea jta_8 = new JTextArea();
        jta_8.setLineWrap(true);
        jta_8.setForeground(Color.BLACK);
        jta_8.setFont(new Font("楷体",Font.BOLD,24));
        jsp_8.setViewportView(jta_8);

        //为增加按钮添加事件处理
        jb_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sno = jta_5.getText();
                String sname = jta_6.getText();
                String stelphone = jta_7.getText();
                String sql = "insert into scott.adressbook(sno,sname,stelphone) values('" + sno + "','" + sname + "','" + stelphone + "')";
                query(sql);
                jta_8.setText("添加成功！");
            }
        });

        //修改---下拉列表
        JComboBox<String> jcb_3 = new JComboBox<>();
        jcb_3.setBounds(600,400,380,30);
        jcb_3.addItem("----------请选择修改方式----------");
        jcb_3.addItem("修改学号");
        jcb_3.addItem("修改姓名");
        jcb_3.addItem("修改电话号码");
        jp.add(jcb_3);

        //修改---标签JLabel_1
        JLabel jl_4 = new JLabel("学号");
        jl_4.setBounds(600,450,70,40);
        jl_4.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        jp.add(jl_4);

        //修改---标签JLabel_2
        JLabel jl_5 = new JLabel("姓名");
        jl_5.setBounds(600,500,70,40);
        jl_5.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        jp.add(jl_5);

        //修改---标签JLabel_3
        JLabel jl_6 = new JLabel("电话号码");
        jl_6.setBounds(600,550,70,40);
        jl_6.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        jp.add(jl_6);

        //修改学号---滚动容器和文本域
        JScrollPane jsp_9 = new JScrollPane();
        jsp_9.setBounds(680,450,300,40);
        jp.add(jsp_9);

        JTextArea jta_9 = new JTextArea();
        jta_9.setLineWrap(true);
        jta_9.setForeground(Color.BLACK);
        jta_9.setFont(new Font("楷体",Font.BOLD,24));
        jsp_9.setViewportView(jta_9);

        //修改姓名---滚动容器和文本域
        JScrollPane jsp_10 = new JScrollPane();
        jsp_10.setBounds(680,500,300,40);
        jp.add(jsp_10);

        JTextArea jta_10 = new JTextArea();
        jta_10.setLineWrap(true);
        jta_10.setForeground(Color.BLACK);
        jta_10.setFont(new Font("楷体",Font.BOLD,24));
        jsp_10.setViewportView(jta_10);

        //修改电话号码---滚动容器和文本域
        JScrollPane jsp_11 = new JScrollPane();
        jsp_11.setBounds(680,550,300,40);
        jp.add(jsp_11);

        JTextArea jta_11 = new JTextArea();
        jta_11.setLineWrap(true);
        jta_11.setForeground(Color.BLACK);
        jta_11.setFont(new Font("楷体",Font.BOLD,24));
        jsp_11.setViewportView(jta_11);

        //修改按钮
        JButton jb_4 = new JButton("修改");
        jb_4.setBounds(1000,490,100,60);
        jp.add(jb_4);

        //修改结果---滚动容器和文本域
        JScrollPane jsp_12 = new JScrollPane();
        jsp_12.setBounds(750,600,200,60);
        jp.add(jsp_12);

        JTextArea jta_12 = new JTextArea();
        jta_12.setLineWrap(true);
        jta_12.setForeground(Color.BLACK);
        jta_12.setFont(new Font("楷体",Font.BOLD,24));
        jsp_12.setViewportView(jta_12);

        //为修改按钮添加事件处理
        jb_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sno = jta_9.getText();
                String sname = jta_10.getText();
                String stelphone = jta_11.getText();
                String selected = (String)jcb_3.getSelectedItem();
                String sql = null;
                if("修改学号".equals(selected)){
                    sql ="update scott.adressbook set sno='" + sno + "' where sname='" + sname + "'";
                    query(sql);
                }else if("修改姓名".equals(selected)){
                    sql ="update scott.adressbook set sname='" + sname + "' where sno='" + sno + "'";
                    query(sql);
                } else if ("修改电话号码".equals(selected)) {
                    sql ="update scott.adressbook set stelphone='" + stelphone + "' where sno='" + sno + "'";
                    query(sql);
                }
                jta_12.setText("修改成功！");
            }
        });

        jf.setVisible(true);
    }
}

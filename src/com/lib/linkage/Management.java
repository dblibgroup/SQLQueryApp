package com.lib.linkage;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;


public class Management implements MouseListener, ActionListener{
	ImageLoader il = new ImageLoader();
	JPanel backui = new JPanel(new FlowLayout(FlowLayout.LEFT)) ;
	GridBagLayout gl = new GridBagLayout();
	JPanel inputpassword = new JPanel(gl);
	JLabel backlabel = new JLabel("返回主页");
	JPanel container = new JPanel(new BorderLayout());
	JFrame frame;
	JTextField username = new JTextField();
	JPasswordField password = new JPasswordField();
	StringBuffer hint = new StringBuffer("请输入管理员账号及密码");
	StringBuffer uhint = new StringBuffer("管理员：");
	StringBuffer phint = new StringBuffer("密码: ");
	JButton next = new JButton("登录");
	JLabel usr = new JLabel(new String(uhint));
	JLabel psw = new JLabel(new String(phint));
	JLabel admin, errormsg;
	
	//此对象负责加载图片
	ImageLoader helper = new ImageLoader();

	public void ShowPWUI(JFrame frame) {
		BufferedImage userimg = il.resizeImg(il.LoadImage("UnknownId.png"), 128,128);
		admin = new JLabel(new ImageIcon(userimg));
		errormsg = new JLabel();
		errormsg.setForeground(Color.RED);
		/* Here is the deal.
		 * You wanna input password OR
		 * ROLLING BACK.
		 */
		this.frame = frame;
		backlabel.setForeground(Color.WHITE);
		backlabel.setFont(new Font(backlabel.getFont().getFontName(),
				Font.BOLD, 18));
		
		//创建返回键的触发器
		backlabel.addMouseListener(this);
		backui.add(backlabel);
		backui.setBackground(Color.decode("#18304a"));
		container.add(backui, BorderLayout.NORTH);
		//创立 Constraints
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.HORIZONTAL;
		username.setColumns(20);
		gl.setConstraints(username, gc);
		password.setColumns(20);
		gl.setConstraints(password, gc);
		gl.setConstraints(admin, gc);
		inputpassword.add(admin);
		inputpassword.add(usr);
		inputpassword.add(username);
		inputpassword.add(psw);
		inputpassword.add(password);
		inputpassword.add(next);
		container.add(inputpassword, BorderLayout.CENTER);
		container.add(errormsg, BorderLayout.SOUTH);
		
		next.addActionListener(this);
		frame.add(container, BorderLayout.CENTER);
		
		frame.revalidate();
		frame.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		frame.remove(container);
		frame.revalidate();
		frame.repaint();
		//frame.remove(inputpassword);
		ShowMenu l = new ShowMenu();
		l.OperationTabInit(frame);
		frame.revalidate();
		frame.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String uname = username.getText();
		String pw = new String(password.getPassword());
		//Should be verified later. Raynold: hardcoded here
		System.out.println("username: " + uname + " password : " +pw);

		Object rs[][] = null;
		try{
			//check if the user exists
			Query usrQuery = new Query();
			Connection conn = DbConnection.DbConnect();
			String sql = "SELECT * FROM administrator WHERE adminID = ?";
		    PreparedStatement pstmt = null;
	 	    pstmt = (PreparedStatement) conn.prepareStatement(sql,
	 	    		  ResultSet.TYPE_SCROLL_INSENSITIVE, 
	 	    		  ResultSet.CONCUR_READ_ONLY);       //It is not suggested to do so in large Result sets.
		    pstmt.setString(1, uname);
			rs = usrQuery.PsExecQuery(pstmt);
			
			//The code below is to find out if there are any data retrieved from the database
			if(rs == null || rs.length == 0 || (rs.length == 1 && rs[0].length == 0)){
				errormsg.setText("管理员不存在，请先获取权限！");
			}else{
				for(int i = 0; i < rs.length; i++){
					String pwdInDb = String.valueOf(rs[i][1]);     //rs[i][2] relates to the password
					try{
						if(MD5.validPassword(pw, pwdInDb)){
							System.out.println("密码正确，转向ManagementStudio");
							frame.remove(container);
							ManagementStudio ms = new ManagementStudio();
							ms.showFrame(frame);
							
						}else{
							errormsg.setText("密码错误！");
							//Leave a error message on the screen and wait for another input.
							
						}
					} catch (Exception me){
						me.printStackTrace();
					}
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
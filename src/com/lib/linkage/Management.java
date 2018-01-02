package com.lib.linkage;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

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
	JLabel admin;
	
	//此对象负责加载图片
	ImageLoader helper = new ImageLoader();

	public void ShowPWUI(JFrame frame) {
		BufferedImage userimg = il.resizeImg(il.LoadImage("UnknownId.png"), 128,128);
		admin = new JLabel(new ImageIcon(userimg));
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
		System.out.println("password " +pw);
		if(uname.equals("root") && pw.equals("123456")) {
			System.out.println("The password is correct. Showing management studio below");
			frame.remove(container);
			ManagementStudio ms = new ManagementStudio();
			ms.showFrame(frame);
		}
		
	}
}
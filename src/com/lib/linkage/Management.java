package com.lib.linkage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Management implements MouseListener{
	JPanel backui = new JPanel(new FlowLayout(FlowLayout.LEFT)) ;
	GridBagLayout gl = new GridBagLayout();
	JPanel inputpassword = new JPanel(gl);
	JLabel backlabel = new JLabel("返回主页");
	JPanel operations = new JPanel();
	JFrame frame;
	
	JTextField username = new JTextField();
	JTextField password = new JTextField();
	StringBuffer hint = new StringBuffer("请输入管理员账号及密码");
	StringBuffer uhint = new StringBuffer("管理员：");
	StringBuffer phint = new StringBuffer("密码: ");
	
	JLabel usr = new JLabel(new String(uhint));
	JLabel psw = new JLabel(new String(phint));
	
	//此对象负责加载图片
	ImageLoader helper = new ImageLoader();

	public void ShowPWUI(JFrame frame) {
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
		//创立 Constraints
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gl.setConstraints(backui, gc);
		
		inputpassword.add(backui,gc);
		inputpassword.add(usr);
		inputpassword.add(username);
		inputpassword.add(psw);
		inputpassword.add(password);
		
		frame.add(inputpassword, BorderLayout.CENTER);
		
		frame.revalidate();
		frame.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		frame.remove(backui);
		//frame.remove(inputpassword);
		MainPage l = new MainPage();
		l.OperationTabInit(operations, frame);
		frame.add(operations, BorderLayout.CENTER);
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
}
package com.lib.linkage;

import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import java.awt.Graphics2D;


import javax.imageio.ImageIO;

public class ImageLoader{
	public BufferedImage LoadImage(String path) {
		BufferedImage icon = null;
		try {
			
			 icon = ImageIO.read(new File("res/"+ path));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		return icon;
	}
	
	public Icon resizeIcon(ImageIcon icon, JLabel label) {
		//Function to resize the button
		float framewidth = label.getWidth();
		float frameheight = label.getHeight();
		/*
		framewidth = (framewidth/4) * ratio;
		frameheight = (frameheight/4) * ratio;
		*/
		
	    Image img = icon.getImage();  
	    Image resizedImage = img.getScaledInstance((int)framewidth, (int)frameheight, java.awt.Image.SCALE_SMOOTH);  
	    return new ImageIcon(resizedImage);
	    
	}
	public BufferedImage resizeImg(BufferedImage image, int width, int height) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
		Graphics2D g = (Graphics2D) img.createGraphics();
		g.addRenderingHints(new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR));
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return img;
	}
}
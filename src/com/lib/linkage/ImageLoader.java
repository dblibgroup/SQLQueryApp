package com.lib.linkage;

import java.awt.Image;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;


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
	
	public Icon resizeIcon(ImageIcon icon, JFrame Frame, float ratio) {
		//Function to resize the button
		float framewidth = Frame.getWidth();
		float frameheight = icon.getIconHeight();
		framewidth = (framewidth/4) * ratio;
		frameheight = (frameheight/4) * ratio;
		
	    Image img = icon.getImage();  
	    Image resizedImage = img.getScaledInstance((int)framewidth, (int)frameheight,  java.awt.Image.SCALE_SMOOTH);  
	    return new ImageIcon(resizedImage);
	}
}
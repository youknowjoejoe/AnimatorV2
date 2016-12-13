package com.gmail.youknowjoejoe.animator; 

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ImageDrawable implements Drawable{

	private BufferedImage img;
	
	public ImageDrawable(BufferedImage img){
		this.img = img;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		AffineTransform save = g2d.getTransform();
		//g2d.translate(-img.getWidth()/2.0, -img.getHeight()/2.0);
		g2d.drawImage(img, 0, 0, null);
		g2d.setTransform(save);
	}

}

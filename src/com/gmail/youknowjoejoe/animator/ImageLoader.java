package com.gmail.youknowjoejoe.animator; 

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	private BufferedImage[] imgs;
	
	public ImageLoader(String folder, String[] paths, boolean internal){
		imgs = new BufferedImage[paths.length];
		
		try {
			for(int rep = 0; rep < paths.length; rep++){
				if(internal){
					imgs[rep] = ImageIO.read(getClass().getResourceAsStream(folder+paths[rep]));
				} else {
					imgs[rep] = ImageIO.read(new File(folder+paths[rep]));
				}
			}
		} catch (IOException e) {
			
		}
	}
	
	public BufferedImage getImage(int index){
		return imgs[index];
	}
}

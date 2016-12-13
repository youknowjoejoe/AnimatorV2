package com.gmail.youknowjoejoe.animator; 

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Animator {
    public static void main(String[] args) throws IOException{
        //setup image loader
        String[] paths = {"limb.png","body.png", "pelvis.png"};
        ImageLoader il = new ImageLoader("resources/",paths,false);
        BufferedImage empty = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
        
        //initialize variables
        Scene sc = new Scene();
        
        Node body = new Node(new ImageDrawable(il.getImage(1)));
        body.addTransformation(new Transformation(0,0,0,300,300,1.0,1.0),0.0);
        
        Node armSockets = new Node(new ImageDrawable(empty));
        armSockets.addTransformation(new Transformation(0,0,0,25,10,1.0,1.0),0.0);
        body.adoptChild(armSockets);
        
        Node arm1 = new Node(new ImageDrawable(il.getImage(0)));
        arm1.addTransformation(new Transformation(Math.PI/4.0,25.0,25.0,0,0,1.0,1.0,2),0.0);
        arm1.addTransformation(new Transformation(-Math.PI/2.0,25.0,25.0,0,0,1.0,1.0,2),2.0);
        arm1.addTransformation(new Transformation(Math.PI/2.0,25.0,25.0,0,0,1.0,1.0,2),4.0);
        
        Node foreArm1 = new Node(new ImageDrawable(il.getImage(0)));
        foreArm1.addTransformation(new Transformation(-Math.PI/2.0,25.0,25.0,0,75,1.0,1.0), 0.0);
        arm1.adoptChild(foreArm1);
        
        Node arm2 = new Node(new ImageDrawable(il.getImage(0)));
        arm2.addTransformation(new Transformation(-Math.PI/4.0,25.0,25.0,0,0,1.0,1.0,2),0.0);
        arm2.addTransformation(new Transformation(Math.PI/2.0,25.0,25.0,0,0,1.0,1.0,2),2.0);
        arm2.addTransformation(new Transformation(-Math.PI/2.0,25.0,25.0,0,0,1.0,1.0,2),4.0);
        
        Node foreArm2 = new Node(new ImageDrawable(il.getImage(0)));
        foreArm2.addTransformation(new Transformation(-Math.PI/2.0,25.0,25.0,0,75,1.0,1.0), 0.0);
        arm2.adoptChild(foreArm2);
        
        armSockets.adoptChild(arm1);
        armSockets.adoptChild(arm2);
        
        Node pelvis = new Node(new ImageDrawable(il.getImage(2)));
        pelvis.addTransformation(new Transformation(0,0,0,0,200,1.0,1.0),0.0);
        body.adoptChild(pelvis);
        
        Node legSockets = new Node(new ImageDrawable(empty));
        legSockets.addTransformation(new Transformation(0,0,0,15,0,1.0,1.0),0.0);
        pelvis.adoptChild(legSockets);
        
        Node leg1 = new Node(new ImageDrawable(il.getImage(0)));
        leg1.addTransformation(new Transformation(-Math.PI/4.0,25.0,25.0,0,0,1.0,1.0,2),0.0);
        leg1.addTransformation(new Transformation(Math.PI/2.0,25.0,25.0,0,0,1.0,1.0,2),2.0);
        leg1.addTransformation(new Transformation(-Math.PI/2.0,25.0,25.0,0,0,1.0,1.0,2),4.0);
        
        Node calf1 = new Node(new ImageDrawable(il.getImage(0)));
        calf1.addTransformation(new Transformation(Math.PI/4.0,25.0,25.0,0,75,1.0,1.0),0.0);
        leg1.adoptChild(calf1);
        
        Node leg2 = new Node(new ImageDrawable(il.getImage(0)));
        leg2.addTransformation(new Transformation(Math.PI/4.0,25.0,25.0,0,0,1.0,1.0,2),0.0);
        leg2.addTransformation(new Transformation(-Math.PI/2.0,25.0,25.0,0,0,1.0,1.0,2),2.0);
        leg2.addTransformation(new Transformation(Math.PI/2.0,25.0,25.0,0,0,1.0,1.0,2),4.0);
        
        Node calf2 = new Node(new ImageDrawable(il.getImage(0)));
        calf2.addTransformation(new Transformation(Math.PI/4.0,25.0,25.0,0,75,1.0,1.0),0.0);
        leg2.adoptChild(calf2);
        
        legSockets.adoptChild(leg1);
        legSockets.adoptChild(leg2);
        
        sc.addNode(leg1);
        sc.addNode(calf1);
        sc.addNode(arm1);
        sc.addNode(foreArm1);
        sc.addNode(pelvis);
        sc.addNode(legSockets);
        sc.addNode(body);
        sc.addNode(leg2);
        sc.addNode(calf2);
        sc.addNode(armSockets);
        sc.addNode(arm2);
        sc.addNode(foreArm2);
        
        int img = 0;
        
        for(int i = 0; i < 60; i++){
            //setup g2d
            BufferedImage buffer = new BufferedImage(800,800,BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = buffer.createGraphics();
            
            //draw all the things
            sc.update(i*4.0/60.0);
            sc.draw(g2d);
            
            //dispose and save image
            g2d.dispose();
            File f = new File("output/"+getNumber(i)+".png");
            ImageIO.write(buffer, "png", f);
        }
    }
    
    public static String getNumber(int i){
        String temp = String.valueOf(i);
        int digits = 5;
        int spacing = 5-temp.length();
        
        for(int rep = 0; rep < spacing; rep++){
            temp = "0" + temp;
        }
        return temp;
    }
}

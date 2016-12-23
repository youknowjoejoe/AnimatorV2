package com.gmail.youknowjoejoe.animator; 

import java.awt.geom.AffineTransform;

public class Transformation {
    private double theta;
    private double rx;
    private double ry;
    
    private double tx;
    private double ty;
    
    private double sx;
    private double sy;
    
    
    private AffineTransform at;
    private double previousFactor;
    
    //ADD LATER - controls how the transformation is applied... linear, quadratic, etc
    //for now, all transformations applied linear
    private int transitionType = 1; //1 = linear, 2 = quadratic, -2 = quadratic
    
    private void initialize(double theta,double rx,double ry,double tx,double ty,double sx,double sy){
        this.theta = theta;
        this.rx = rx;
        this.ry = ry;
        this.tx = tx;
        this.ty = ty;
        this.sx = sx;
        this.sy = sy;
    }
    
    private void updateTransform(double factor){
    	double tempFactor = factor;
    	if(transitionType > 1){
    		tempFactor = Math.pow(factor,transitionType);
    	} else if(transitionType < 0){
    		tempFactor = -Math.pow(Math.abs(factor-1), transitionType)+1;
    	}
    	
    	at = new AffineTransform();
    	at.scale(1+(sx-1)*tempFactor, 1+(sy-1)*tempFactor);
        at.translate(tx*tempFactor, ty*tempFactor);
        at.rotate(theta*tempFactor, rx, ry);
        this.previousFactor = tempFactor;
    }
    
    public Transformation(){
        initialize(0,0,0,0,0,1,1);
    }
    
    public Transformation(double theta,double rx,double ry,double tx,double ty,double sx,double sy, int transitionType){
        initialize(theta,rx,ry,tx,ty,sx,sy);
        this.transitionType = transitionType;
    }
    
    public Transformation(double theta,double rx,double ry,double tx,double ty,double sx,double sy){
        initialize(theta,rx,ry,tx,ty,sx,sy);
    }
    
    public Transformation(double theta, double rx, double ry){
        initialize(theta,rx,ry,0,0,1,1);
    }
    
    public Transformation(double tx, double ty){
        initialize(0,0,0,tx,ty,1,1);
    }
    
    public AffineTransform getTransform(double factor){
        if(previousFactor != factor){
            updateTransform(factor);
        }
        return at;
    }
    
    public AffineTransform getTransform(){
        if(previousFactor != 1){
            updateTransform(1);
        }
        return at;
    }
    
    public AffineTransform getTransform(double factor,int multiples){
        if(previousFactor != factor){
            updateTransform(factor);
        }
        AffineTransform temp = new AffineTransform(at);
        for(int rep = 0; rep < multiples; rep++){
            at = new AffineTransform(temp);
            temp.concatenate(at);
        }
        return at;
    }
}

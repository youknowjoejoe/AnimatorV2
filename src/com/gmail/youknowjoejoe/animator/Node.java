package com.gmail.youknowjoejoe.animator; 

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.LinkedList;

public class Node implements Drawable{
	private Node parent;
	private ArrayList<Node> children;
	private ArrayList<Pair<Double, Transformation>> transforms;
	private AffineTransform composition;
	private Drawable d;
	
	public Node(Drawable d){
		this.transforms = new ArrayList<Pair<Double, Transformation>>();
		this.transforms.add(new Pair<Double,Transformation>(0.0,new Transformation()));
		this.parent = null;
		this.children = new ArrayList<Node>();
		this.d = d;
	}
	
	public void addTransformation(Transformation t, double time){
		Pair<Double,Transformation> pair = new Pair<Double,Transformation>(time,t);
		for(int rep = 0; rep < transforms.size(); rep++){
			if(time < transforms.get(rep).key){
				transforms.add(rep, pair);
				return;
			}
			if(time == transforms.get(rep).key){
				transforms.set(rep, pair);
				return;
			}
		}
		
		transforms.add(pair);
	}
	
	public void removeTransformation(double time){
		for(int rep = 0; rep < transforms.size(); rep++){
			if(transforms.get(rep).key == time){
				transforms.remove(rep);
				return;
			}
		}
	}
	
	//tries to add Node to children
	//sets n's parent to "this"
	//returns true if successful
	public boolean adoptChild(Node n){
		if(n.isOrphan()){
			n.parent = this;
			children.add(n);
			return true;
		}
		return false;
	}
	
	//tries to remove Node n from children
	//sets parent to null;
	//returns true if successful
	public boolean removeChild(Node n){
		if(n.parent == this){
			n.parent = null;
			children.remove(n);
			return true;
		}
		return false;
	}
	
	public boolean isOrphan(){
		return parent==null;
	}
	
	private AffineTransform getAffineTransform(double time){
		if(time <= 0){
			return transforms.get(0).value.getTransform();
		}
		AffineTransform previous = new AffineTransform();
		
		int index = 0;
		for(int rep = 0; rep < transforms.size(); rep++){
			Pair<Double,Transformation> pair = transforms.get(rep);
			
			if(pair.key < time){
				index = rep;
				previous.concatenate(pair.value.getTransform());
			}
		}
		
		if(index >= transforms.size()-1){
		    return previous;
		} else {
		    Pair<Double,Transformation> pair0 = transforms.get(index);
		    Pair<Double,Transformation> pair1 = transforms.get(index+1);
		    previous.concatenate(pair1.value.getTransform((time-pair0.key)/(pair1.key-pair0.key)));
		    return previous;
		}
	}
	
	//composes transformations. sends current transformation to child nodes
	public void composeTransforms(double time){
	    AffineTransform t = this.getAffineTransform(time);
		for(Node n: children){
			n.composeTransforms(t, time);
		}
		this.composition = t;
	}
	
	//takes transformation sent from parent node
	//applies the transformation as a composition
	//sends composition to child nodes
	public void composeTransforms(AffineTransform at, double time){
	    AffineTransform t = this.getAffineTransform(time);
		this.composition = new AffineTransform(at);
		this.composition.concatenate(t);
		for(Node n: children){
			n.composeTransforms(this.composition, time);
		}
	}
	
	@Override
	public void draw(Graphics2D g2d){
		AffineTransform save = g2d.getTransform();
		g2d.transform(this.composition);
		d.draw(g2d);
		g2d.setTransform(save);
	}
}

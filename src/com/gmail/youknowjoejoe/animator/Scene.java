package com.gmail.youknowjoejoe.animator;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Scene implements Drawable {
	private ArrayList<Node> nodes;
	
	public Scene(){
		nodes = new ArrayList<Node>();
	}
	
	public void addNode(Node n){
		nodes.add(n);
	}
	
	public void update(double time){
		updateNodes(time);
	}
	
	public void updateNodes(double time){
		composeTransforms(time);
	}
	
	private void composeTransforms(double time){
		for(Node n: nodes){
			if(n.isOrphan()){
				n.composeTransforms(time);
			}
		}
	}
	
	@Override
	public void draw(Graphics2D g2d){
		for(Node node: nodes){
			node.draw(g2d);
		}
	}
}

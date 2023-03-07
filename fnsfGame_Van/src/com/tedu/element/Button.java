//package com.tedu.element;
//
//import java.awt.Graphics;
//
//import javax.swing.ImageIcon;
//
//import com.tedu.manager.GameLoad;
//
//
//public class Button extends ElementObj{
//	private Stirng upID;
//
//	@Override
//	public void showElement(Graphics g) {
//		g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
//	}
//	
//	@Override
//	public ElementObj createElement(String ...str) {
//		//System.out.println(str);
//		String[] split = str[0].split(",");
//
//		this.PlayerID = split[0];
//		
//		this.setX(560);
//		this.setY(560);
//		this.setIcon(new ImageIcon(GameLoad.animeImgMap.get(this.PlayerID).get(0)));
//		this.setH(this.getIcon().getIconHeight()+40);
//		this.setW(this.getIcon().getIconWidth()+40);
//		return this;
//	}
//}

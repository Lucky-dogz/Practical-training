package com.tedu.element;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class UIPanel extends ElementObj
{
	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
	}
	
	//生成UI图片 str为图片的相对位置
	public ElementObj createElement(int x, int y, String str) {
		this.setIcon(new ImageIcon(str));
		this.setX(x);
		this.setY(y);
		this.setH(this.getIcon().getIconHeight()+15);
		this.setW(this.getIcon().getIconWidth()+50);
		return this;
	}
	
}

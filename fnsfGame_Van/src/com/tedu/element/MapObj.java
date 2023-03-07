package com.tedu.element;

import java.awt.Graphics;

import javax.swing.ImageIcon;

/**
 * @说明 地图中的每一块小对象
 * @author Van
 *
 */
public class MapObj extends ElementObj {

	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), getX(), getY(), getW(), getH(), null);

	}

	//这里设置成背景图片
	@Override
	public ElementObj createElement(String ...str) {

		ImageIcon icon = null;
		icon = new ImageIcon("image/FishCatcher/background/fishlightbg_"+str[0]+".jpg");
		this.setX(0);
		this.setY(0);
		this.setW(1280);
		this.setH(720);
		this.setIcon(icon);
		return this;
	}
}

package com.tedu.element;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.tedu.controller.GameListener;

public class StartUI extends ElementObj
{
	public boolean isMove=false;
	public int progress = 0;    //加载进度
	@Override
	public void showElement(Graphics g) 
	{
		if(this.isMove)
		{
			if(this.progress < 270)
			{
				progress+=3;
			}
			//进度加载完毕 可以点击开始游戏
			if(this.progress >= 270)
			{
				GameListener.isFinish = true;
				g.setColor(Color.RED);
				g.setFont(new Font("微软雅黑",Font.BOLD,32));
				//时间
				g.drawString("任意点击进入游戏", 500, 460);
			}
			this.setH(this.getIcon().getIconHeight()+11);
			this.setW(this.getIcon().getIconWidth()-200+progress);
		}
		g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
	}
	
	//生成UI图片 str为图片的相对位置
	public ElementObj createElement(int x,int y, String str, boolean isMove)
	{
		this.setIcon(new ImageIcon(str));
		this.setX(x);
		this.setY(y);
		this.setH(this.getIcon().getIconHeight()+15);
		this.setW(this.getIcon().getIconWidth()+50);
		this.isMove = isMove;
		return this;
	}
}

package com.tedu.element;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.tedu.controller.GameListener;

public class StartUI extends ElementObj
{
	public boolean isMove=false;
	public int progress = 0;    //���ؽ���
	@Override
	public void showElement(Graphics g) 
	{
		if(this.isMove)
		{
			if(this.progress < 270)
			{
				progress+=3;
			}
			//���ȼ������ ���Ե����ʼ��Ϸ
			if(this.progress >= 270)
			{
				GameListener.isFinish = true;
				g.setColor(Color.RED);
				g.setFont(new Font("΢���ź�",Font.BOLD,32));
				//ʱ��
				g.drawString("������������Ϸ", 500, 460);
			}
			this.setH(this.getIcon().getIconHeight()+11);
			this.setW(this.getIcon().getIconWidth()-200+progress);
		}
		g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
	}
	
	//����UIͼƬ strΪͼƬ�����λ��
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

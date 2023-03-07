package com.tedu.element;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.tedu.manager.GameLoad;

public class Money extends ElementObj{
	private int money; //金钱
	private int movespeed=30;//金币移动速度
	private int imagenum=1;
	@Override
	public void move() {
		if(this.getX()<400)//这里应该放显示金币数的图标位置，现在用400代替
		{
			this.setX(this.getX()+movespeed);
		}
		else {
			this.setX(this.getX()-movespeed);
		}
		
		if(this.getY()<800) {
			this.setY(this.getY()+movespeed);
		}
		//死亡清除金币
		if(this.getY()>720) {
			this.setLive(false);
		}
	} //移动
	
	@Override
	public void showElement(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
	}
	@Override
	protected void updateImage() {
		// TODO Auto-generated method stub
		//super.updateImage(gameTime);
		//ImageIcon icon=GameLoad.imgMap.get("gold"+((gameTime%9)+1));
//		System.out.println(icon.getIconHeight());
//		System.out.println("update");
		//this.setIcon(new ImageIcon("image/FishCatcher/component/gold_coin/gold0"+((gameTime%9)+1)+".png"));
		//this.setIcon(new ImageIcon("image/FishCatcher/component/gold_coin/gold03.png"));
		this.setIcon(GameLoad.imgMap.get("gold"+((imagenum%9)+1)));//换图片
		switch ((int)((imagenum%9)+1)) {
		case 1:
			this.setW(38);
			break;
		case 2:
			this.setW(32);
			break;
		case 3:
			this.setW(13);
			break;
		case 4:
			this.setW(24);
			break;
		case 5:
			this.setW(38);
			break;
		case 6:
			this.setW(38);
			break;
		case 7:
			this.setW(24);
			break;
		case 8:
			this.setW(13);
			break;
		case 9:
			this.setW(33);
			break;
		}
		//this.setW(GameLoad.imgMap.get("gold"+((gameTime%9)+1)).getIconHeight());
		//this.setH(GameLoad.imgMap.get("gold"+((gameTime%9)+1)).getIconHeight());
		//System.out.println((gameTime%9)+1);
		
		new Thread() {
			public void run() {
				try {
					Thread.sleep(80);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
		
//		if(imagenum<9) {
//			System.out.println(imagenum);
//			imagenum++;
//		}
//		
//		else {
//			imagenum=1;
//		}
		imagenum++;
	}
	@Override
	public ElementObj createElement(String ...str) {
		//System.out.println("goldstart");
		String[] split = str[0].split(",");
		this.setX(Integer.valueOf(split[0]));
		this.setY(Integer.valueOf(split[1]));
//		this.setW(38);
		this.setH(38);
		BgmMusic.playMusic("coinanimate");
		this.setIcon(new ImageIcon("image/FishCatcher/component/gold_coin/gold01.png"));
		// TODO Auto-generated method stub
		return this;
	}
	
	@Override
	public void dead() {
		if(this.getY()>500) {
			this.setLive(false);
		}
	}
	
}

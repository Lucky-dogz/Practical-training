package com.tedu.element;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.tedu.manager.GameLoad;

public class HighPoint extends ElementObj{
	private int pointnum;//传入的分数值
	private int pointtime=1;//图片序号
	private boolean two=true;//让图片重复两次
	@Override
	public void showElement(Graphics g) {
		try {
//			System.out.println("X是"+this.getX());
//			System.out.println("Y是"+this.getY());
			g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
		} catch (Exception e) {
			// TODO: handle exception
//			System.out.println("出错的地方是"+this.pointnum);
		
		}
		
	}
	@Override
	public ElementObj createElement(String... str) {
		//System.out.println(str[0]);
		String[] split = str[0].split(",");
		this.setX(Integer.valueOf(split[0]));
		this.setY(Integer.valueOf(split[1]));
		this.setPointnum(Integer.valueOf(split[2]));//具体得分
		if(Integer.valueOf(split[0])>1000) {
			this.setX(1050);
		}
		if(pointnum<100) {
			this.setW(108);
			this.setH(108);
		}
		else {
			this.setW(200);
			this.setH(120);
		}
		//this.setIcon(new ImageIcon("image/FishCatcher/component/high_point/100/100_1.png"));
		this.setIcon(new ImageIcon("image/FishCatcher/component/high_point/"+this.pointnum+"/"+this.pointnum+"_1.png"));
		if(pointnum<100)
		{
			BgmMusic.playMusic("highpoints");
		}
		else {
			BgmMusic.playMusic("hundredpoints");
		}
		return this;
		
	}
	
	@Override
	protected void updateImage() {
		// TODO Auto-generated method stub
		if(pointnum<100) {
			
			this.setIcon(GameLoad.imgMap.get(pointnum+"_"+((pointtime%6)+1)));
		}
		else if (pointnum>=100) {
			
			this.setIcon(GameLoad.imgMap.get(pointnum+"_"+((pointtime%8)+1)));
		}
		pointtime++;
		new Thread() {
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		if(!two && pointnum<100) {
			this.setLive(false);
		}
		if(!two && pointnum>=100) {
			this.setLive(false);
		}
		if(pointtime>18 && pointnum>=8) {
			pointtime=1;
			two=false;
		}
		if(pointnum<100 && pointtime>6) {
			pointtime=1;
			two=false;
		}
	}
	
	public int getPointnum() {
		return pointnum;
	}
	public void setPointnum(int pointnum) {
		this.pointnum = pointnum;
	}
	
}

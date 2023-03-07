package com.tedu.element;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import com.tedu.game.GameInfo;
import com.tedu.manager.GameLoad;
import com.tedu.show.GameJFrame;

/**
 * 子弹
 * @author zouyujunlalala
 *
 */
public class Bullet extends ElementObj{
	private int speed=10; // 速度
	private int level; //子弹等级
	private String BulletID="bullet_01"; //当前炮台id


	private String faceDir="up"; // 方向值
	private boolean isHit = false;
	private double rotateAngel; // 旋转角度
	private double targetX; //子弹移动目标坐标
	private double targetY;
	private int centerX; //子弹旋转中心
	private int centerY;
	private double back=0;
	private  Graphics bulletG;

	public Bullet() {} // 一个空的构造方法
	

	@Override
	public void showElement(Graphics g) {
//		System.out.println(rotateAngel);
		bulletG = g.create();
		Graphics2D g2 = (Graphics2D)bulletG;
//		System.out.println(centerX);
//		if (i!=0) {
//			g2.rotate(Math.toRadians(back), centerX, centerY); //复原子弹
//			i++;
//		}
//		g2.rotate(Math.toRadians(rotateAngel), centerX, centerX); //旋转子弹
//		System.out.println(this.getX() + "    " + this.getY());
		g2.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
//		bulletG.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
//		back = -rotateAngel;
		
	}
	
	// 创建子弹
	@Override
	public ElementObj createElement(String ...str) {
		String[] split = str[0].split(",");
		for (String str1 : split) {
			String[] split2 = str1.split(":");
			switch (split2[0]) {
			case "ID": this.BulletID = split2[1]; break;
			case "degree": this.rotateAngel = Double.parseDouble(split2[1]); break;
			case "x":
				this.setX(Integer.parseInt(split2[1]));
				this.centerX = (Integer.parseInt(split2[1]));
				break;
			case "y":
				this.setY(Integer.parseInt(split2[1]));
				this.centerY = (Integer.parseInt(split2[1]));
				break;
			case "mouseX": this.targetX = (Double.parseDouble(split2[1])); break;
			case "mouseY": this.targetY = (Double.parseDouble(split2[1])); break;
			}
		}
		this.setIcon(GameLoad.imgMap.get(this.BulletID));
		
		this.setH(this.getIcon().getIconHeight()-37);
		this.setW(this.getIcon().getIconWidth()-37);
		this.speed = this.speed*GameInfo.level;
		if (GameInfo.level<5) {
			this.speed = this.speed+GameInfo.level*2;
		}
		else {
			this.speed = this.speed+10;
		}

		return this;
	}
	
	private int x = 0; //用于保存子弹变成网时的坐标，修改网的位置
	private int y = 0;
	//照片切换成网
	@Override
	public void shotOnTarget() {
		//这里暂时只是把图片替换成网
		if (!isHit) {
			x = this.getX()-90;
			y = this.getY()-100;
		}
		isHit = true;
		ImageIcon icon = new ImageIcon("image/FishCatcher/component/net/net11.png");
		setIcon(icon);
		this.setH(100+GameInfo.level*20);
		this.setW(100+GameInfo.level*20);
		this.setX(x);
		this.setY(y);
		new Thread() {
			public void run() {
				try {
					Thread.sleep(1000);
					setLive(false);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}

	@Override
	protected void move() {
		/*子弹存活判定*/
		// 边界判定
		if (isHit) {
			return ;//打中了就别动了
		}
		if (this.getX()<0 || this.getX()>GameJFrame.GameX ||
			this.getY()<0 || this.getY()>GameJFrame.GameY  ) {
			this.setLive(false);
			return;
		}
		double distance = Math.sqrt((targetX-centerX)*(targetX-centerX) + (targetY - centerY)*(targetY - centerY));
		int x = (int) (speed * (targetX-centerX) / distance);
		int y = (int) (speed * (targetY-centerY) / distance);
		this.setX(this.getX() + x);
		this.setY(this.getY() + y);
	}
}

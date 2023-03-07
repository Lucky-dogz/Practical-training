package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.zip.CheckedOutputStream;

import javax.swing.ImageIcon;

import com.tedu.game.GameInfo;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

/**
 * 炮台
 * @author zouyujunlalala
 *
 */

public class Player extends ElementObj {
	private String PlayerID="fire_01"; //当前炮台id
	private String BulletID; //当前炮台id
	private double rotateAngel=0; //当前角度
	public static double oldRotateAngel=0; //当前角度
	private double degree=0; //应旋转角度
	
//	private int level=1; //炮台等级,对应子弹等级
	private ImageIcon fort;
	
	private double clickX;
	private double clickY;
	
	private ImageIcon push; //吐
	private ImageIcon pull; //收
	// 子弹发射状态 true-shoot false-not_shoot
	private boolean isShoot = false;

	// 射击时间，上一次记录的游戏时间
	private long shootTime = 0;
	// 射击间隔
	private int shootCD =10;
	
	public Player() {}
	public Player(int x, int y, int w, int h, ImageIcon icon) {
		super(x, y, w, h, icon);
	}

	@Override
	public void showElement(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.rotate(Math.toRadians(rotateAngel), this.getX()+this.getW()/2, this.getY()+this.getH()/2);
		
//		g2.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
		g2.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
		
	}
	
	@Override
	public ElementObj createElement(String ...str) {

		//System.out.println(str);
		String[] split = str[0].split(",");

		this.PlayerID = split[0];
		
		this.setX(560);
		this.setY(560);
		push = new ImageIcon(GameLoad.animeImgMap.get(this.PlayerID).get(0));
		pull = new ImageIcon(GameLoad.animeImgMap.get(this.PlayerID).get(1));
		this.setIcon(push);
		this.setH(this.getIcon().getIconHeight()+47);
		this.setW(this.getIcon().getIconWidth()+47);
		return this;
	}
	
	@Override
	public void mouseClick(Boolean isClicked, double x, double y) {
//		System.out.println(isClicked);
		this.isShoot = isClicked;
		if (isShoot) {
			this.setIcon(push);
		}else {
			this.setIcon(pull);
		}
		this.clickX = x;
		this.clickY = y;
	}
	
	/**
	 * 移动方法
	 */
	@Override
	protected void move() {
//		System.out.println(rotateAngel);
		double m; //计算正切值
		m = Math.abs((this.clickX-this.getX()-this.getW()/2)) / Math.abs(this.clickY-this.getY()-this.getH()/2);
		degree = Math.toDegrees(Math.atan(m)); //计算应旋转的角度
		
//		System.out.println(rotateAngel);
		
		if (this.clickX<this.getX()+this.getW()/2) degree = -Math.abs(degree); //若点击在左边，则旋转角度为负
		else if (this.clickX>this.getX()+this.getW()/2) degree = Math.abs(degree);//若点击在右边，则旋转角度为正
//		System.out.println(degree);
		rotateAngel = degree;
	}

	/**
	 * 重写父类方法shoot
	 */
	@Override
	protected void shoot(long gameTime) {
		
		if (!isShoot||GameInfo.money<1) {
			return;
		}
		if (gameTime - this.shootTime < this.shootCD&&gameTime - this.shootTime>0) {
			
			return;
		}
		
		this.shootTime = gameTime;
		
		ElementObj element = new Bullet().createElement(this.toString());
		BgmMusic.playMusic("bgm_fire");
		if (GameInfo.canGenerate) {//正常情况
			StaticUI.minusMoney(GameInfo.level);
		}
		else {//关卡结束到关卡开始之间
			StaticUI.minusMoney(0);
		}
		
		// 装入到集合中
		ElementManager.getManager().addElement(GameElement.BULLET, element);

		// 控制子弹
	}
	
	//{x:3,y:5,f:up} 解析小工厂
	@Override
	public String toString() {
		String[] split = PlayerID.split("_");
		BulletID =  "bullet_" + split[1];
//		BulletID =  "bullet_27";
//		System.out.println(BulletID);

//		int x = this.getX()+this.getW()/2-7; //子弹产生的地方
//		int y = this.getY()+this.getH()/2+7;
		
		double x = this.getX()+this.getW()/2-18; //旋转点
		double y = this.getY()+35;
		
//		double x0 = this.getX()+this.getW()/2; //原点
//		double y0 = this.getY()+this.getH();

//		任意点P(x1,y1)，绕一个坐标点Q(x2,y2)旋转θ角度后,新的坐标设为(x, y)的计算公式：
//		x= (x1 - x2)*cos(θ) - (y1 - y2)*sin(θ) + x2 ;
//		y= (x1 - x2)*sin(θ) + (y1 - y2)*cos(θ) + y2 ;
//		double bulletX = (x-x0)*Math.cos(rotateAngel) - (y-y0)*Math.sin(rotateAngel) + x0;
//		double bulletY = (x-x0)*Math.sin(rotateAngel) + (y-y0)*Math.cos(rotateAngel) + y0;
		
//		System.out.println(x + " " + y);
//		System.out.println(degree);
		return "ID:" + BulletID + ",degree:" + degree + ",x:" + (int)x + ",y:" + (int)y 
				+ ",mouseX:" + this.clickX + ",mouseY:" + this.clickY;
	}

	//升级炮台
	public void playerUp() {
//		System.out.println("ppp");
		//最高升级到11级
		if(GameInfo.level >= 11) {
			return;
		}
		//升级时需要消耗根据等级递增的金币
		if (GameInfo.money - (GameInfo.level+1)*100 > 0) {
			GameInfo.money -= (GameInfo.level+1)*100;
			GameInfo.level++;
			if(GameInfo.level < 10) {
				this.PlayerID = "fire_0" + GameInfo.level; 
			} else {
				this.PlayerID = "fire_" + GameInfo.level; 
			}
			fort = new ImageIcon(GameLoad.animeImgMap.get(this.PlayerID).get(0));
			push = fort;
			pull = new ImageIcon(GameLoad.animeImgMap.get(this.PlayerID).get(1));
			this.setIcon(fort);
			BgmMusic.playMusic("firechange");
		}
		
	}
	
	/**
	 * 炮台降级
	 */
	public void playerDown() {
		if(GameInfo.level <= 1) {
			return;
		}
		GameInfo.level--;
		if(GameInfo.level < 10) {
			this.PlayerID = "fire_0" + GameInfo.level; 
		} else {
			this.PlayerID = "fire_" + GameInfo.level; 
		}
		//System.out.println("现在的等级是"+level);
		fort = new ImageIcon(GameLoad.animeImgMap.get(this.PlayerID).get(0));
		push = fort;
		pull = new ImageIcon(GameLoad.animeImgMap.get(this.PlayerID).get(1));
		this.setIcon(fort);
		BgmMusic.playMusic("firechange");
	}
	
//	@Override
//	protected void updateImage() {
//		this.setIcon(fort);
//	}
}

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
 * ��̨
 * @author zouyujunlalala
 *
 */

public class Player extends ElementObj {
	private String PlayerID="fire_01"; //��ǰ��̨id
	private String BulletID; //��ǰ��̨id
	private double rotateAngel=0; //��ǰ�Ƕ�
	public static double oldRotateAngel=0; //��ǰ�Ƕ�
	private double degree=0; //Ӧ��ת�Ƕ�
	
//	private int level=1; //��̨�ȼ�,��Ӧ�ӵ��ȼ�
	private ImageIcon fort;
	
	private double clickX;
	private double clickY;
	
	private ImageIcon push; //��
	private ImageIcon pull; //��
	// �ӵ�����״̬ true-shoot false-not_shoot
	private boolean isShoot = false;

	// ���ʱ�䣬��һ�μ�¼����Ϸʱ��
	private long shootTime = 0;
	// ������
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
	 * �ƶ�����
	 */
	@Override
	protected void move() {
//		System.out.println(rotateAngel);
		double m; //��������ֵ
		m = Math.abs((this.clickX-this.getX()-this.getW()/2)) / Math.abs(this.clickY-this.getY()-this.getH()/2);
		degree = Math.toDegrees(Math.atan(m)); //����Ӧ��ת�ĽǶ�
		
//		System.out.println(rotateAngel);
		
		if (this.clickX<this.getX()+this.getW()/2) degree = -Math.abs(degree); //���������ߣ�����ת�Ƕ�Ϊ��
		else if (this.clickX>this.getX()+this.getW()/2) degree = Math.abs(degree);//��������ұߣ�����ת�Ƕ�Ϊ��
//		System.out.println(degree);
		rotateAngel = degree;
	}

	/**
	 * ��д���෽��shoot
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
		if (GameInfo.canGenerate) {//�������
			StaticUI.minusMoney(GameInfo.level);
		}
		else {//�ؿ��������ؿ���ʼ֮��
			StaticUI.minusMoney(0);
		}
		
		// װ�뵽������
		ElementManager.getManager().addElement(GameElement.BULLET, element);

		// �����ӵ�
	}
	
	//{x:3,y:5,f:up} ����С����
	@Override
	public String toString() {
		String[] split = PlayerID.split("_");
		BulletID =  "bullet_" + split[1];
//		BulletID =  "bullet_27";
//		System.out.println(BulletID);

//		int x = this.getX()+this.getW()/2-7; //�ӵ������ĵط�
//		int y = this.getY()+this.getH()/2+7;
		
		double x = this.getX()+this.getW()/2-18; //��ת��
		double y = this.getY()+35;
		
//		double x0 = this.getX()+this.getW()/2; //ԭ��
//		double y0 = this.getY()+this.getH();

//		�����P(x1,y1)����һ�������Q(x2,y2)��ת�ȽǶȺ�,�µ�������Ϊ(x, y)�ļ��㹫ʽ��
//		x= (x1 - x2)*cos(��) - (y1 - y2)*sin(��) + x2 ;
//		y= (x1 - x2)*sin(��) + (y1 - y2)*cos(��) + y2 ;
//		double bulletX = (x-x0)*Math.cos(rotateAngel) - (y-y0)*Math.sin(rotateAngel) + x0;
//		double bulletY = (x-x0)*Math.sin(rotateAngel) + (y-y0)*Math.cos(rotateAngel) + y0;
		
//		System.out.println(x + " " + y);
//		System.out.println(degree);
		return "ID:" + BulletID + ",degree:" + degree + ",x:" + (int)x + ",y:" + (int)y 
				+ ",mouseX:" + this.clickX + ",mouseY:" + this.clickY;
	}

	//������̨
	public void playerUp() {
//		System.out.println("ppp");
		//���������11��
		if(GameInfo.level >= 11) {
			return;
		}
		//����ʱ��Ҫ���ĸ��ݵȼ������Ľ��
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
	 * ��̨����
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
		//System.out.println("���ڵĵȼ���"+level);
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

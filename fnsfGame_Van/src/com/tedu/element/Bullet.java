package com.tedu.element;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import com.tedu.game.GameInfo;
import com.tedu.manager.GameLoad;
import com.tedu.show.GameJFrame;

/**
 * �ӵ�
 * @author zouyujunlalala
 *
 */
public class Bullet extends ElementObj{
	private int speed=10; // �ٶ�
	private int level; //�ӵ��ȼ�
	private String BulletID="bullet_01"; //��ǰ��̨id


	private String faceDir="up"; // ����ֵ
	private boolean isHit = false;
	private double rotateAngel; // ��ת�Ƕ�
	private double targetX; //�ӵ��ƶ�Ŀ������
	private double targetY;
	private int centerX; //�ӵ���ת����
	private int centerY;
	private double back=0;
	private  Graphics bulletG;

	public Bullet() {} // һ���յĹ��췽��
	

	@Override
	public void showElement(Graphics g) {
//		System.out.println(rotateAngel);
		bulletG = g.create();
		Graphics2D g2 = (Graphics2D)bulletG;
//		System.out.println(centerX);
//		if (i!=0) {
//			g2.rotate(Math.toRadians(back), centerX, centerY); //��ԭ�ӵ�
//			i++;
//		}
//		g2.rotate(Math.toRadians(rotateAngel), centerX, centerX); //��ת�ӵ�
//		System.out.println(this.getX() + "    " + this.getY());
		g2.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
//		bulletG.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
//		back = -rotateAngel;
		
	}
	
	// �����ӵ�
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
	
	private int x = 0; //���ڱ����ӵ������ʱ�����꣬�޸�����λ��
	private int y = 0;
	//��Ƭ�л�����
	@Override
	public void shotOnTarget() {
		//������ʱֻ�ǰ�ͼƬ�滻����
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
		/*�ӵ�����ж�*/
		// �߽��ж�
		if (isHit) {
			return ;//�����˾ͱ���
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

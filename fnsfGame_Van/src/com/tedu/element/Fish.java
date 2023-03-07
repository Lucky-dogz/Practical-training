package com.tedu.element;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.tools.Tool;

public class Fish extends ElementObj{
	private String fishID;
	private int speed; // �ٶ�
	private int layer; // ͼ��㼶
	private int worth; // ��ֵ
	private int probability; // ������
	private int imgNum = 0;
	private String path ;//·��
	private List<Integer> nextPointX= new ArrayList<>();//�õ����������鱣��X����
	private List<Integer> nextPointY= new ArrayList<>();//�õ����������鱣��Y����
	private int currentX = 0;
	private int currentY = 0;
	//private float theta = 0;
	private boolean isCaught = false;
	@Override
	public void showElement(Graphics g) {

	g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
	
	}

	@Override
	 protected void updateImage() {
	  if(isCaught) {
	   if (imgNum == GameLoad.animeImgMap.get(this.fishID + "_caught").size()) {
	    imgNum = 0;
	   }
	   this.setIcon(new ImageIcon(GameLoad.animeImgMap.get(this.fishID + "_caught").get(imgNum++)));
	  } else {
	   if (imgNum == GameLoad.animeImgMap.get(this.fishID + "_swim").size()) {
	    imgNum = 0;
	   }
	   this.setIcon(new ImageIcon(GameLoad.animeImgMap.get(this.fishID + "_swim").get(imgNum++)));
	  }
	 }

	/**
	 * �������У�����������ֵӦ�����������ļ��еģ��������ļ���ȡ��
	 */
	@Override
	public Fish createElement(String ...str) {
		
		String[] split = str[0].split(",");
		String[] split2 = str[1].split(",");
		this.path=str[1];

		this.fishID = split[0];
		this.speed = Integer.parseInt(split[1])/20;
		this.layer = Integer.parseInt(split[2]);
		this.worth = Integer.parseInt(split[3]);
		this.probability = Integer.parseInt(split[4]);
		this.getPath();
		this.setX(nextPointX.get(currentX));
		this.setY(nextPointY.get(currentY));
		this.setIcon(new ImageIcon(GameLoad.animeImgMap.get(this.fishID + "_swim").get(0)));
		this.setH(this.getIcon().getIconHeight());
		this.setW(this.getIcon().getIconWidth());
		return this;
	}
	
	//��ȡ·��
	public void getPath() {
		String[] splitSteStrings = path.split(";");//�õ�������,�õ� "0,100"  "100,160 "
		for(int i=0;i<splitSteStrings.length;i++)
		{	
			String[] point = splitSteStrings[i].split(",");//�õ�"0" "100"
			nextPointX.add(Integer.parseInt(point[0]));
			nextPointY.add(Integer.parseInt(point[1]));
		}
	}
	@Override
	protected void move() {
		//��Ҫ����:��ǰxλ�� = ����x + speed
		//����ֻ���������㷽��,�����һ���� - ��ǰ�����������Ǿ�Ҫ��
		int length = nextPointX.size();
//		System.out.println(nextPointX.get(currentX));
//		System.out.println(nextPointY.get(currentY));
		if(isCaught||isStop())
		{
			return; //�����ץ�˾ͱ���
		}
		if(currentX+1<length)//�������һ����
		{
			if(nextPointX.get(currentX+1)-nextPointX.get(currentX)>0)//�����һ�������ұ�
			{	
				if(this.getX()>nextPointX.get(currentX+1))
				{
					currentX++;
				}
				
				this.setX(this.getX()+speed);
			}
			else {
				if(this.getX()<nextPointX.get(currentX+1))
				{
					currentX++;
				}
				this.setX(this.getX()-speed);
			}}
			
			if(currentY+1<length)//�������һ����
			{
			if(nextPointY.get(currentY+1)-nextPointY.get(currentY)>0)//�����һ�������±�
			{	
				if(this.getY()>nextPointY.get(currentY+1))
				{
//					theta = Tool.getAngle(nextPointX.get(currentX), nextPointY.get(currentY),this.getW()/2, this.getH()/2);
					currentY++;
				}
				this.setY(this.getY()+speed);
			}
			else {
				if(this.getY()<nextPointY.get(currentY+1))
				{
					currentY++;
				}
				this.setY(this.getY()-speed);
			}
			
		}
	}
	
	public int gotShot() {
		int fireLevel = 1;//�ӵ��ȼ�
		int caughtRandom = (int) (Math.random()*10000);//����0~10000������
		int caughtProbability = (int) (probability*fireLevel);//��������ʣ�Ϊ0~probability
		//���������Ϊ���������������0~caughtProbability�������ڣ��򱻲���
		
		if(caughtRandom<caughtProbability)
		{	
//			if(this.worth>40)
//			{
//				System.out.println("�ӵ�������� = = "+caughtRandom);
//				System.out.println("�㱻������� = = "+caughtProbability);
//				System.out.println("�������� = ="+this.fishID+"���ļ�ֵ��"+worth);
//				
//			}
			isCaught = true;
			imgNum = 0;
			new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(2000);//������������һ��
						//caughtPlay();//���ű�������
						moneyPlay();//���Ž�һ��߸߷ֶ�����ȡ����worth
						setLive(false);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}.start();
			
			return worth;
		}
		//���򷵻�0
		return 0;//���û�б����񣬷���0
	}
	
	//����֮���Ǯ����
	private void moneyPlay() {
		// TODO Auto-generated method stub
		if (this.worth<40) {//С��40��ֻ�Ǽ��ؽ��

			ElementObj coin = new Money().createElement(this.toString());
			ElementManager.getManager().addElement(GameElement.MONEY, coin);
		}
		else {//����40�����ظ߷ֶ���
			ElementObj highPoint = new HighPoint().createElement(this.toString());
			ElementManager.getManager().addElement(GameElement.HIGHPOINT, highPoint);
		}
	}

	/**
	 * 
	 * @����˵��:���ű�����������������ͣ������
	 */
	private void caughtPlay() {
		// TODO Auto-generated method stub
		//BgmMusic.playMusic("fishbg2");
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(500);
					setLive(false);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}

	// getter
	public String getFishID() {
		return fishID;
	}

	public int getSpeed() {
		return speed;
	}

	public int getLayer() {
		return layer;
	}

	public int getWorth() {
		return worth;
	}

	public int getProbability() {
		return probability;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
//		int x = this.getX()+this.getW()/2;
//		int y = this.getY()+this.getH()/2;
		int x = this.getX();
		int y = this.getY();
		return x+","+y+","+this.worth;
	}
}

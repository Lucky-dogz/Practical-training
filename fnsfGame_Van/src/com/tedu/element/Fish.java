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
	private int speed; // 速度
	private int layer; // 图层层级
	private int worth; // 价值
	private int probability; // 捕获率
	private int imgNum = 0;
	private String path ;//路径
	private List<Integer> nextPointX= new ArrayList<>();//得到不定长数组保存X坐标
	private List<Integer> nextPointY= new ArrayList<>();//得到不定长数组保存Y坐标
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
	 * 在鱼类中，传进来的数值应该是在配置文件中的，从配置文件中取得
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
	
	//获取路径
	public void getPath() {
		String[] splitSteStrings = path.split(";");//得到坐标组,得到 "0,100"  "100,160 "
		for(int i=0;i<splitSteStrings.length;i++)
		{	
			String[] point = splitSteStrings[i].split(",");//得到"0" "100"
			nextPointX.add(Integer.parseInt(point[0]));
			nextPointY.add(Integer.parseInt(point[1]));
		}
	}
	@Override
	protected void move() {
		//需要计算:当前x位置 = 现在x + speed
		//坐标只是用来计算方向,如果下一方向 - 当前方向是正数那就要加
		int length = nextPointX.size();
//		System.out.println(nextPointX.get(currentX));
//		System.out.println(nextPointY.get(currentY));
		if(isCaught||isStop())
		{
			return; //如果被抓了就别动了
		}
		if(currentX+1<length)//不是最后一个点
		{
			if(nextPointX.get(currentX+1)-nextPointX.get(currentX)>0)//如果下一个点在右边
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
			
			if(currentY+1<length)//不是最后一个点
			{
			if(nextPointY.get(currentY+1)-nextPointY.get(currentY)>0)//如果下一个点在下边
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
		int fireLevel = 1;//子弹等级
		int caughtRandom = (int) (Math.random()*10000);//产生0~10000的数字
		int caughtProbability = (int) (probability*fireLevel);//被捕获概率，为0~probability
		//被捕获规则为如果产生的数字在0~caughtProbability的区间内，则被捕获
		
		if(caughtRandom<caughtProbability)
		{	
//			if(this.worth>40)
//			{
//				System.out.println("子弹捕获概率 = = "+caughtRandom);
//				System.out.println("鱼被捕获概率 = = "+caughtProbability);
//				System.out.println("这条鱼是 = ="+this.fishID+"它的价值是"+worth);
//				
//			}
			isCaught = true;
			imgNum = 0;
			new Thread() {
				@Override
				public void run() {
					try {
						Thread.sleep(2000);//这里让鱼挣扎一下
						//caughtPlay();//播放被捕动画
						moneyPlay();//播放金币或者高分动画，取决于worth
						setLive(false);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}.start();
			
			return worth;
		}
		//否则返回0
		return 0;//如果没有被捕获，返回0
	}
	
	//捕获之后金钱动画
	private void moneyPlay() {
		// TODO Auto-generated method stub
		if (this.worth<40) {//小于40，只是加载金币

			ElementObj coin = new Money().createElement(this.toString());
			ElementManager.getManager().addElement(GameElement.MONEY, coin);
		}
		else {//大于40，加载高分动画
			ElementObj highPoint = new HighPoint().createElement(this.toString());
			ElementManager.getManager().addElement(GameElement.HIGHPOINT, highPoint);
		}
	}

	/**
	 * 
	 * @功能说明:播放被捕动画，这里用暂停来代替
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

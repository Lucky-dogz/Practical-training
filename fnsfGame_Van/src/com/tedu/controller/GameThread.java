package com.tedu.controller;

import java.util.List;
import java.util.Map;

import com.tedu.element.BgmMusic;
import com.tedu.element.ElementObj;
import com.tedu.element.StaticUI;
import com.tedu.game.GameInfo;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

/**
 * @说明 游戏的主线程，用于控制游戏加载、游戏关卡、游戏运行时自动化、游戏地图切换、资源释放、重新读取
 * @author Van
 * @说明 这里使用继承的方式实现多线程，一般使用接口的方式
 */
public class GameThread extends Thread {
	private ElementManager em;
	private boolean isFirstTime = true;//是否第一次加载
	public GameThread() {
		em = ElementManager.getManager();
	}
	
	@Override
	public void run() { // 游戏的run方法，主线程
		while (true) { // 可以将true修改为一个变量用于控制
			// 游戏开始前 进度条，加载场景资源
			gameLoad();
			// 游戏进行时 
			gameRun();
			// 游戏场景结束 回收场景资源
			gameOver();
			try {
				sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 游戏加载
	 */
	private void gameLoad() {
		
		if(isFirstTime)//第一次需要加载配置文件
		{
			//加载开始界面
			GameLoad.loadGameStartUI();
			GameLoad.loadGameInfo();
			GameLoad.loadBackgroud(GameInfo.gamePart%7+"");
			GameLoad.BgmLoad("bg");
			GameLoad.loadImg();
			GameLoad.loadAnimeImg();
			
			GameLoad.loadFishConfig();
			GameLoad.loadPathConfig();

			GameLoad.loadFish();
			GameLoad.loadPlayer();
			
			GameLoad.loadUIPanel();
			GameLoad.loadStaticUI();
			isFirstTime = false;
			
		}
		else{
			GameLoad.clear();
			GameLoad.loadBackgroud(GameInfo.gamePart%7+"");
			GameLoad.loadFish();
//			GameLoad.loadPlayer();
//			GameLoad.loadUIPanel();
			GameLoad.loadStaticUI();
		}
		GameInfo.canGenerate = true;//设置可以加载鱼
	}

	
	/**
	 * 游戏进行时
	 * @说明 玩家的移动、碰撞、死亡、新元素的增加、暂停
	 */
	private void gameRun() {
		long gameTime = 0L; // 主线程时间控制
		
		//游戏开始界面
		GameStart(gameTime);
		
		int limitTime = 60; //60秒的游玩时间
		long startTime = System.currentTimeMillis();   //获取开始游戏时的时间戳
		int money = GameInfo.money;
//		System.out.println(GameInfo.targetMoney);
//		System.out.println(((money+GameInfo.gamePart*200)/100));
		GameInfo.targetMoney =  ((money+GameInfo.gamePart*200)/100)*100; //赋值关卡目标金币
//		System.out.println(GameInfo.targetMoney);
		int time = 0;
		while(true) { // 可以扩展为变量
			Map<GameElement, List<ElementObj>> all = em.getGameElements();
			List<ElementObj> fishes= em.getElementByKey(GameElement.FISH);
			List<ElementObj> bullets = em.getElementByKey(GameElement.BULLET);
			timeCount(limitTime,startTime);    //时间倒计时器
			autoModel(all, gameTime);
			elementColliDetect(fishes, bullets);//fish为listA
			gameTime++;
			try {
				sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(GameInfo.money>=GameInfo.targetMoney ||!GameInfo.canGenerate)//金币增加关卡数*200就通关
			{
				//if(GameInfo.money>101)//测试
				
				if(time==0)
				{	
					//System.out.println("推出了");
					GameInfo.canGenerate = false;
					for(ElementObj fish:fishes) {
						fish.setStop(true);
					}
				}
				else if(time>=50)
				{
					
					break;
				}		
				//System.out.println(time);
				time++;
			}
			
		}
		}
		

	
	public void timeCount(int limitTime, long startTime)
	{
		
		if(StaticUI.time <= 0) {
			return;
		}
		long currentTime = System.currentTimeMillis();
		StaticUI.time = limitTime - (int)((currentTime - startTime)/1000);
	}
	
	
	/**
	 * 游戏元素自动处理
	 */
	private void autoModel(Map<GameElement, List<ElementObj>> all, long gameTime) {
		for(GameElement ge:GameElement.values()) { // 根据枚举定义时的顺序来拿不同类型的元素
			List<ElementObj> list = all.get(ge);
			for(int i = 0; i<list.size(); i++) {
				ElementObj obj = list.get(i);
				if(!obj.isLive()) { // 如果不是存活状态
					obj.dead(); // 启动一个死亡方法
					list.remove(i--);
					continue;
				}
				obj.model(gameTime); // 调用每个类自己的model方法完成执行逻辑
			}
		}
	}
	
	/**
	 * 元素种类的一对一碰撞检测方法
	 */
	private void elementColliDetect(List<ElementObj> listA, List<ElementObj> listB) {
		
		// 循环，做一对一判定，如果为真，就设置两个对象的死亡状态
		for(int i = 0; i<listA.size(); i++) {
			ElementObj objA = listA.get(i);
			for(int j = 0; j<listB.size(); j++) {
				ElementObj objB = listB.get(j);
				if(objA.collisionDetect(objB)) {
					//TODO 这里写受攻击逻辑
					objB.shotOnTarget();//击中目标
					int money = objA.gotShot();//被击中，应该把信息传递回给线程，这里简化，只返回它值多少钱
					if (money>0) {
						//播放金钱动作
						new Thread() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								GameLoad.loadCoin(objA.getX()+","+objA.getY());//这里会出错，是money类的问题
								GameInfo.money += money;
							}
						}.start();
					}
					break;
				}
			}
		}
	}
	
	
	/**
	 * 游戏切换关卡，换关卡的时候，播放波浪
	 */
	private void gameOver() {
		BgmMusic.playMusic("fishbg2");
		GameInfo.gamePart++;
//		GameLoad.saveGameInfo();
	}
	
	/**
	 * 游戏开始界面
	 */
	public void GameStart(long gameTime)
	{
		if(GameListener.isPressed)
		{
			return;
		}
		List<ElementObj> startUI = em.getElementByKey(GameElement.STARTUI);
		//显示ui
		for(ElementObj obj:startUI)
		{
			obj.model(gameTime);
		}
		
//		GameListener.isFinish = true;
		while(true)
		{
			System.out.println(GameListener.isPressed);
			//如果按下鼠标,就开始游戏
			if(GameListener.isPressed&&GameListener.isFinish)
			{
				System.out.println("rrr");
				for(ElementObj obj:startUI)
				{
					obj.setLive(false);
				}
				break;
			}
		}
	}
}

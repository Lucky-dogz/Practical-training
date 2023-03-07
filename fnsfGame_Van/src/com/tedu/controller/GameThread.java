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
 * @˵�� ��Ϸ�����̣߳����ڿ�����Ϸ���ء���Ϸ�ؿ�����Ϸ����ʱ�Զ�������Ϸ��ͼ�л�����Դ�ͷš����¶�ȡ
 * @author Van
 * @˵�� ����ʹ�ü̳еķ�ʽʵ�ֶ��̣߳�һ��ʹ�ýӿڵķ�ʽ
 */
public class GameThread extends Thread {
	private ElementManager em;
	private boolean isFirstTime = true;//�Ƿ��һ�μ���
	public GameThread() {
		em = ElementManager.getManager();
	}
	
	@Override
	public void run() { // ��Ϸ��run���������߳�
		while (true) { // ���Խ�true�޸�Ϊһ���������ڿ���
			// ��Ϸ��ʼǰ �����������س�����Դ
			gameLoad();
			// ��Ϸ����ʱ 
			gameRun();
			// ��Ϸ�������� ���ճ�����Դ
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
	 * ��Ϸ����
	 */
	private void gameLoad() {
		
		if(isFirstTime)//��һ����Ҫ���������ļ�
		{
			//���ؿ�ʼ����
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
		GameInfo.canGenerate = true;//���ÿ��Լ�����
	}

	
	/**
	 * ��Ϸ����ʱ
	 * @˵�� ��ҵ��ƶ�����ײ����������Ԫ�ص����ӡ���ͣ
	 */
	private void gameRun() {
		long gameTime = 0L; // ���߳�ʱ�����
		
		//��Ϸ��ʼ����
		GameStart(gameTime);
		
		int limitTime = 60; //60�������ʱ��
		long startTime = System.currentTimeMillis();   //��ȡ��ʼ��Ϸʱ��ʱ���
		int money = GameInfo.money;
//		System.out.println(GameInfo.targetMoney);
//		System.out.println(((money+GameInfo.gamePart*200)/100));
		GameInfo.targetMoney =  ((money+GameInfo.gamePart*200)/100)*100; //��ֵ�ؿ�Ŀ����
//		System.out.println(GameInfo.targetMoney);
		int time = 0;
		while(true) { // ������չΪ����
			Map<GameElement, List<ElementObj>> all = em.getGameElements();
			List<ElementObj> fishes= em.getElementByKey(GameElement.FISH);
			List<ElementObj> bullets = em.getElementByKey(GameElement.BULLET);
			timeCount(limitTime,startTime);    //ʱ�䵹��ʱ��
			autoModel(all, gameTime);
			elementColliDetect(fishes, bullets);//fishΪlistA
			gameTime++;
			try {
				sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(GameInfo.money>=GameInfo.targetMoney ||!GameInfo.canGenerate)//������ӹؿ���*200��ͨ��
			{
				//if(GameInfo.money>101)//����
				
				if(time==0)
				{	
					//System.out.println("�Ƴ���");
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
	 * ��ϷԪ���Զ�����
	 */
	private void autoModel(Map<GameElement, List<ElementObj>> all, long gameTime) {
		for(GameElement ge:GameElement.values()) { // ����ö�ٶ���ʱ��˳�����ò�ͬ���͵�Ԫ��
			List<ElementObj> list = all.get(ge);
			for(int i = 0; i<list.size(); i++) {
				ElementObj obj = list.get(i);
				if(!obj.isLive()) { // ������Ǵ��״̬
					obj.dead(); // ����һ����������
					list.remove(i--);
					continue;
				}
				obj.model(gameTime); // ����ÿ�����Լ���model�������ִ���߼�
			}
		}
	}
	
	/**
	 * Ԫ�������һ��һ��ײ��ⷽ��
	 */
	private void elementColliDetect(List<ElementObj> listA, List<ElementObj> listB) {
		
		// ѭ������һ��һ�ж������Ϊ�棬�������������������״̬
		for(int i = 0; i<listA.size(); i++) {
			ElementObj objA = listA.get(i);
			for(int j = 0; j<listB.size(); j++) {
				ElementObj objB = listB.get(j);
				if(objA.collisionDetect(objB)) {
					//TODO ����д�ܹ����߼�
					objB.shotOnTarget();//����Ŀ��
					int money = objA.gotShot();//�����У�Ӧ�ð���Ϣ���ݻظ��̣߳�����򻯣�ֻ������ֵ����Ǯ
					if (money>0) {
						//���Ž�Ǯ����
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
								GameLoad.loadCoin(objA.getX()+","+objA.getY());//����������money�������
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
	 * ��Ϸ�л��ؿ������ؿ���ʱ�򣬲��Ų���
	 */
	private void gameOver() {
		BgmMusic.playMusic("fishbg2");
		GameInfo.gamePart++;
//		GameLoad.saveGameInfo();
	}
	
	/**
	 * ��Ϸ��ʼ����
	 */
	public void GameStart(long gameTime)
	{
		if(GameListener.isPressed)
		{
			return;
		}
		List<ElementObj> startUI = em.getElementByKey(GameElement.STARTUI);
		//��ʾui
		for(ElementObj obj:startUI)
		{
			obj.model(gameTime);
		}
		
//		GameListener.isFinish = true;
		while(true)
		{
			System.out.println(GameListener.isPressed);
			//����������,�Ϳ�ʼ��Ϸ
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

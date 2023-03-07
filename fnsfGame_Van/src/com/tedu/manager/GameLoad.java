package com.tedu.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.swing.ImageIcon;

import com.tedu.element.BgmMusic;
import com.tedu.element.ElementObj;
import com.tedu.element.Fish;
import com.tedu.element.HighPoint;
import com.tedu.element.MapObj;
import com.tedu.element.Money;
import com.tedu.element.Player;
import com.tedu.element.StartUI;
import com.tedu.element.StaticUI;
import com.tedu.element.UIPanel;
import com.tedu.game.GameInfo;

/**
 * @˵�� �����������ڶ�ȡ�����ļ��Ĺ���
 * @˵�� �����࣬����ṩ����static����
 * @author Van
 *
 */
public class GameLoad {
	private static ElementManager em = ElementManager.getManager();

	// ͼƬ���� ʹ��map���洢 ö����������ƶ�����չ��
	public static Map<String, ImageIcon> imgMap = new HashMap<>();
	
	// ��̬ͼƬ���� 
	public static Map<String, List<String>> animeImgMap = new HashMap<>();
	
	// ����������Ϣ
	public static Map<String, String> fishConfigMap = new HashMap<>();
	
	// ·����Ϣ
	public static Map<String, String> fishPathMap =  new HashMap<>();

	// ��ȡ�ļ�����
	private static Properties pro = new Properties();
	
	// ��������������� ����loadFish
	private static int generateFishNum = 10;
	// �����������ʱ�䣨���룩 ����loadFish
	private static int generateFishTime = 10000;
	

	/**
	 * @˵�� �����ͼID�����ļ������Զ�������ͼ
	 * @param mapId �ļ����
	 */
	public static void BgmLoad(String musicName) {
		BgmMusic.playMusicCycle(musicName);
	}
	
	/**
	 * ���ر���ͼƬ
	 */
	public static void loadBackgroud(String gamePart) {
		ElementObj element = new MapObj().createElement(gamePart);
		em.addElement(GameElement.MAPS, element);
	}
	
	/**
	 * ���ؾ�̬ͼƬ
	 */
	public static void loadImg() { // ���Դ���������ͬ�Ĺ�Ҳ������Ҫ��һ����ͼƬ��Դ
		String textUrl = "com/tedu/text/staticPictureData.pro";
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream texts = classLoader.getResourceAsStream(textUrl);
		pro.clear();
		try {
			pro.load(texts);
			Set<Object> set = pro.keySet();
			for(Object o:set) {
				String url = pro.getProperty(o.toString());
//				System.out.println(o.toString());
//				System.out.println(url);
				imgMap.put(o.toString(), new ImageIcon(url));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ���ض�̬ͼƬ
	 */
	public static void loadAnimeImg() { // ���Դ���������ͬ�Ĺ�Ҳ������Ҫ��һ����ͼƬ��Դ
		String textUrl = "com/tedu/text/dynamicPictureData.pro";
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream texts = classLoader.getResourceAsStream(textUrl);
		pro.clear(); // ����ǰ�����һ��
		try {
			pro.load(texts); // ʹ��properties�������������
			Set<Object> set = pro.keySet(); // �õ�keyֵ����
			for(Object o:set) {
				String url = pro.getProperty(o.toString()); // ����keyֵ�õ�value
//				System.out.println(o.toString());
//				System.out.println(url);
				File[] fileList = new File(url).listFiles(); //������url�����ļ����µ�����ͼƬ
				List<String> imgNameList = new ArrayList<String>();
				for(int i=0; i<fileList.length; i++) {
//					System.out.println(o.toString()+"      " + fileList[i].toString());
					imgNameList.add(fileList[i].toString());
				}
				animeImgMap.put(o.toString(), imgNameList);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ������������
	 */
	public static void loadFishConfig() {
		String textUrl = "com/tedu/text/FishData.pro";
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream texts = classLoader.getResourceAsStream(textUrl);
		pro.clear();
		try {
			pro.load(texts);
			Set<Object> set = pro.keySet();
			for(Object o:set) {
				String config = pro.getProperty(o.toString());
//				System.out.println(o.toString());
//				System.out.println(config);
				fishConfigMap.put(o.toString(), config);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @����˵��:����·��
	 */
	public static void loadPathConfig() {
		String textUrl = "com/tedu/text/fishRoute.pro";
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream texts = classLoader.getResourceAsStream(textUrl);
		pro.clear();
		try {
			
			pro.load(texts);
			Set<Object> set = pro.keySet();
			for(Object o:set) {
				String config = pro.getProperty(o.toString());
//				System.out.println(o.toString());
//				System.out.println(config);
				fishPathMap .put(o.toString(), config);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ������Զ������㣬ÿ��һ��ʱ�����һ��
	 */
	public static void loadFish() {
		//������
		int oldGamePart = GameInfo.gamePart;
			new Thread() {
			@Override
			public void run() {
				// TODO Ϊʲô�ڶ��ο�ʼ������Ͳ��ܿ����ˣ���Ϊ�¾��̶߳��ڹ���
				while (oldGamePart==GameInfo.gamePart) {
					generateFish();
//					System.out.println("���ɵ�"+times+++"����");
					try {
						Random random = new Random();
						int waitSecond = random.nextInt(8)+5;//8-13������һ��
//						System.out.println(waitSecond+"��");
						Thread.sleep((long)(waitSecond*1000));
//						System.out.println("�����߳���"+this.currentThread());
					} catch (Exception e) {
						System.out.println(e);
					}
//					if(!GameInfo.canGenerate)
//					{	//��Ϊ������Sleep��ʱ��canGenerateΪfalse������Sleep���˾�Ϊtrue�ˣ������̲߳���break;
//						
//						break;
//					}
				}
				
			}
			
		}.start();
		
//		if(!GameInfo.canGenerate)
//			loadFishThread.interrupt();
	}

	/**
	 * 
	 * @����˵��:����С����
	 * ������ĸ���ȡ���ڹؿ�
	 */
	private static void generateFish() {
		List<Fish> fishList = new ArrayList<>();
		for(int i = 0; i<generateFishNum+GameInfo.gamePart; i++) {
			int rand = (int)(1 + Math.random() * 17);
			String fishID;
			if(rand < 10) {
				fishID = "fish0" + rand; 
			} else {
				fishID = "fish" + rand;
			}
			Random random = new Random();
			int route = random.nextInt(4)+4;//����4��·��,����ֻ���ұ߽���
			if(fishConfigMap.containsKey(fishID)) {

				Fish fish = new Fish().createElement(fishID + "," + fishConfigMap.get(fishID),fishPathMap.get("R"+route));
				fishList.add(fish);
				
			}
		}

		Collections.sort(fishList, new Comparator<Fish>() {
			@Override
			public int compare(Fish o1, Fish o2) {
				// TODO Auto-generated method stub
				return o1.getLayer() - o2.getLayer();
			}
		});
		for(int i = 0; i<fishList.size(); i++) {
			em.addElement(GameElement.FISH, fishList.get(i));
		}
//		List<ElementObj> fishss = em.getElementByKey(GameElement.FISH);
//		System.out.println(fishss.size());
	}
	

	/**
	 * ������̨
	 */
	public static void loadPlayer() {
		String PlayerID;
		PlayerID = "fire_01"; 
		if(animeImgMap.containsKey(PlayerID)) {
//			System.out.println(animeImgMap.get(PlayerID).get(0));
			ElementObj Player = new Player().createElement(PlayerID + "," + animeImgMap.get(PlayerID));
			em.addElement(GameElement.PLAYER, Player);
		}
	}


	/**
	 * ���ؽ��
	 */
	public static void loadCoin(String str) {
		ElementObj coin = new Money().createElement(str);
		em.addElement(GameElement.MONEY, coin);
	}

	/**
	 * ����UI����: ʱ�䡢��ҡ���̨��������
	 */
	public static void loadUIPanel()
	{
		//����ʱ��ui
		ElementObj time = new UIPanel().createElement(160,610,"image/FishCatcher/component/ui_panel/bottom_time.png");
		em.addElement(GameElement.UI, time);
		
		//�������ui
		ElementObj jinBi = new UIPanel().createElement(960,610,"image/FishCatcher/component/ui_panel/bottom_gold.png");
		em.addElement(GameElement.UI, jinBi);
		
		//����Ŀ����
		ElementObj targetMoney = new UIPanel().createElement(80,20,"image/FishCatcher/component/ui_panel/bottom_gold.png");
		em.addElement(GameElement.UI, targetMoney);
		
		//���� ��̨����ui
		ElementObj bottomPlayer = new UIPanel().createElement(560,610,"image/FishCatcher/component/ui_panel/bottom.png");
		em.addElement(GameElement.UI, bottomPlayer);
		
		//���� ����̨ui
		ElementObj subPlayer = new UIPanel().createElement(422,610,"image/FishCatcher/component/ui_panel/sub.png");
		em.addElement(GameElement.UI, subPlayer);
		
		//���� ����̨ui
		ElementObj addPlayer = new UIPanel().createElement(675,610,"image/FishCatcher/component/ui_panel/add.png");
		em.addElement(GameElement.UI, addPlayer);
	}
	
	/**
	 * ���ɶ�̬ui: ʱ�䡢���
	 */
	public static void loadStaticUI() {
		ElementObj staticUI = new StaticUI();
		em.addElement(GameElement.STATICUI, staticUI);
	}
	
	public static void clear() {
		
			Map<GameElement, List<ElementObj>> all = em.getGameElements();
			for (GameElement ge : GameElement.values()) {
				if(ge==GameElement.MAPS||ge==GameElement.FISH)//ֻ��fish��maps��Ҫ�����
				{
					List<ElementObj> list = all.get(ge);
					for (int i = list.size() - 1; i >= 0; i--) {
						list.remove(i);
				}
				}
			}
		}
	
	//���ؿ�ʼ����UI
	public static void loadGameStartUI()
	{
		//������ʼ����ı���ͼƬ
		ElementObj startBg = new StartUI().createElement(0,0,"image/FishCatcher/progress/progress_bg.jpg",false);
		em.addElement(GameElement.STARTUI, startBg);
		
		//��������������
		ElementObj sliderBg = new StartUI().createElement(564,315,"image/FishCatcher/progress/login_sliderBg.png",false);
		em.addElement(GameElement.STARTUI, sliderBg);
		
		//���������� �����ƶ�
		ElementObj silder = new StartUI().createElement(563,321,"image/FishCatcher/progress/login_jd.png",true);
		em.addElement(GameElement.STARTUI, silder);
		
		//����״̬��
		ElementObj ban = new StartUI().createElement(310,260,"image/FishCatcher/progress/login_bg1.png",false);
		em.addElement(GameElement.STARTUI, ban);
		
	}
	
	/**
	 * 
	 * @����˵��:������Ϸ��Ϣ����gameLoad����
	 */
	public static void loadGameInfo() {
		String textUrl = "com/tedu/text/GameInfo.pro";
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream texts = classLoader.getResourceAsStream(textUrl);
		pro.clear();
		try {
			pro.load(texts);
			GameInfo.level = Integer.parseInt(pro.getProperty("level"));
			GameInfo.gamePart = Integer.parseInt(pro.getProperty("gamePart"));
			GameInfo.money = Integer.parseInt(pro.getProperty("money"));
			GameInfo.targetMoney = Integer.parseInt(pro.getProperty("targetMoney"));
//			System.out.println(GameInfo.targetMoney);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @����˵��:������Ϸ��Ϣ����gameOver����
	 */
	public static void saveGameInfo() {
			pro.clear();
	        try {
	        	OutputStream  outputStream=new FileOutputStream("src/com/tedu/text/GameInfo.pro");
	            pro.setProperty("level", GameInfo.level+"");
	            pro.setProperty("gamePart", GameInfo.gamePart+"");
	            pro.setProperty("money", GameInfo.money+"");
	            pro.setProperty("targetMoney", GameInfo.targetMoney+"");
	            pro.store(outputStream, "Player01");
	            outputStream.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	}
		
	}

	

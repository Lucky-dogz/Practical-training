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
 * @说明 加载器，用于读取配置文件的工具
 * @说明 工具类，大多提供的是static方法
 * @author Van
 *
 */
public class GameLoad {
	private static ElementManager em = ElementManager.getManager();

	// 图片集合 使用map来存储 枚举类型配合移动（扩展）
	public static Map<String, ImageIcon> imgMap = new HashMap<>();
	
	// 动态图片集合 
	public static Map<String, List<String>> animeImgMap = new HashMap<>();
	
	// 鱼类配置信息
	public static Map<String, String> fishConfigMap = new HashMap<>();
	
	// 路径信息
	public static Map<String, String> fishPathMap =  new HashMap<>();

	// 读取文件的类
	private static Properties pro = new Properties();
	
	// 控制生成鱼的数量 用于loadFish
	private static int generateFishNum = 10;
	// 控制生成鱼的时间（毫秒） 用于loadFish
	private static int generateFishTime = 10000;
	

	/**
	 * @说明 传入地图ID依据文件规则自动产生地图
	 * @param mapId 文件编号
	 */
	public static void BgmLoad(String musicName) {
		BgmMusic.playMusicCycle(musicName);
	}
	
	/**
	 * 加载背景图片
	 */
	public static void loadBackgroud(String gamePart) {
		ElementObj element = new MapObj().createElement(gamePart);
		em.addElement(GameElement.MAPS, element);
	}
	
	/**
	 * 加载静态图片
	 */
	public static void loadImg() { // 可以带参数，不同的关也可能需要不一样的图片资源
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
	 * 加载动态图片
	 */
	public static void loadAnimeImg() { // 可以带参数，不同的关也可能需要不一样的图片资源
		String textUrl = "com/tedu/text/dynamicPictureData.pro";
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream texts = classLoader.getResourceAsStream(textUrl);
		pro.clear(); // 加载前先清除一遍
		try {
			pro.load(texts); // 使用properties对象加载输入流
			Set<Object> set = pro.keySet(); // 得到key值集合
			for(Object o:set) {
				String url = pro.getProperty(o.toString()); // 根据key值拿到value
//				System.out.println(o.toString());
//				System.out.println(url);
				File[] fileList = new File(url).listFiles(); //解析出url所在文件夹下的所有图片
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
	 * 加载鱼类配置
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
	 * @功能说明:加载路径
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
	 * 随机且自动加载鱼，每隔一段时间加载一组
	 */
	public static void loadFish() {
		//生成鱼
		int oldGamePart = GameInfo.gamePart;
			new Thread() {
			@Override
			public void run() {
				// TODO 为什么第二次开始生成鱼就不受控制了？因为新旧线程都在工作
				while (oldGamePart==GameInfo.gamePart) {
					generateFish();
//					System.out.println("生成第"+times+++"次鱼");
					try {
						Random random = new Random();
						int waitSecond = random.nextInt(8)+5;//8-13秒生成一波
//						System.out.println(waitSecond+"秒");
						Thread.sleep((long)(waitSecond*1000));
//						System.out.println("现在线程是"+this.currentThread());
					} catch (Exception e) {
						System.out.println(e);
					}
//					if(!GameInfo.canGenerate)
//					{	//因为可能在Sleep的时候canGenerate为false，但是Sleep完了就为true了，导致线程不能break;
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
	 * @功能说明:生成小鱼鱼
	 * 生成鱼的个数取决于关卡
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
			int route = random.nextInt(4)+4;//产生4条路线,测试只从右边进入
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
	 * 加载炮台
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
	 * 加载金币
	 */
	public static void loadCoin(String str) {
		ElementObj coin = new Money().createElement(str);
		em.addElement(GameElement.MONEY, coin);
	}

	/**
	 * 加载UI界面: 时间、金币、炮台左右升级
	 */
	public static void loadUIPanel()
	{
		//创建时间ui
		ElementObj time = new UIPanel().createElement(160,610,"image/FishCatcher/component/ui_panel/bottom_time.png");
		em.addElement(GameElement.UI, time);
		
		//创建金币ui
		ElementObj jinBi = new UIPanel().createElement(960,610,"image/FishCatcher/component/ui_panel/bottom_gold.png");
		em.addElement(GameElement.UI, jinBi);
		
		//创建目标金币
		ElementObj targetMoney = new UIPanel().createElement(80,20,"image/FishCatcher/component/ui_panel/bottom_gold.png");
		em.addElement(GameElement.UI, targetMoney);
		
		//创建 炮台底座ui
		ElementObj bottomPlayer = new UIPanel().createElement(560,610,"image/FishCatcher/component/ui_panel/bottom.png");
		em.addElement(GameElement.UI, bottomPlayer);
		
		//创建 降炮台ui
		ElementObj subPlayer = new UIPanel().createElement(422,610,"image/FishCatcher/component/ui_panel/sub.png");
		em.addElement(GameElement.UI, subPlayer);
		
		//创建 升炮台ui
		ElementObj addPlayer = new UIPanel().createElement(675,610,"image/FishCatcher/component/ui_panel/add.png");
		em.addElement(GameElement.UI, addPlayer);
	}
	
	/**
	 * 生成动态ui: 时间、金币
	 */
	public static void loadStaticUI() {
		ElementObj staticUI = new StaticUI();
		em.addElement(GameElement.STATICUI, staticUI);
	}
	
	public static void clear() {
		
			Map<GameElement, List<ElementObj>> all = em.getGameElements();
			for (GameElement ge : GameElement.values()) {
				if(ge==GameElement.MAPS||ge==GameElement.FISH)//只有fish和maps需要被清除
				{
					List<ElementObj> list = all.get(ge);
					for (int i = list.size() - 1; i >= 0; i--) {
						list.remove(i);
				}
				}
			}
		}
	
	//加载开始界面UI
	public static void loadGameStartUI()
	{
		//创建开始界面的背景图片
		ElementObj startBg = new StartUI().createElement(0,0,"image/FishCatcher/progress/progress_bg.jpg",false);
		em.addElement(GameElement.STARTUI, startBg);
		
		//创建进度条背景
		ElementObj sliderBg = new StartUI().createElement(564,315,"image/FishCatcher/progress/login_sliderBg.png",false);
		em.addElement(GameElement.STARTUI, sliderBg);
		
		//创建进度条 可以移动
		ElementObj silder = new StartUI().createElement(563,321,"image/FishCatcher/progress/login_jd.png",true);
		em.addElement(GameElement.STARTUI, silder);
		
		//创建状态栏
		ElementObj ban = new StartUI().createElement(310,260,"image/FishCatcher/progress/login_bg1.png",false);
		em.addElement(GameElement.STARTUI, ban);
		
	}
	
	/**
	 * 
	 * @功能说明:加载游戏信息，在gameLoad保存
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
	 * @功能说明:保存游戏信息，再gameOver保存
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

	

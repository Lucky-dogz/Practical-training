package com.tedu.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tedu.element.ElementObj;

/**
 * @说明 本类是元素管理器，专门存储所有的元素，同时提供方法给视图和控制获取数据
 * @author Van
 * 
 */
public class ElementManager {
	
	/**
	 * 枚举类型的GameElement，作为map的key用于区分不同的游戏资源
	 * List中的元素泛型应该是元素基类
	 * 
	 * 现实模块View只需要获取到这个map就可以显示界面需要的所有元素（调用元素基类的showElement）
	 */
	private Map<GameElement, List<ElementObj>> gameElements;

	public Map<GameElement, List<ElementObj>> getGameElements() {
		return gameElements;
	}
	
	// 添加元素
	public void addElement(GameElement ge, ElementObj obj) {
		gameElements.get(ge).add(obj); // 按key值将对象添加到集合中
	}
	
	// 取出某类元素
	public List<ElementObj> getElementByKey(GameElement ge) {
		return gameElements.get(ge);
	}
	
	/**
	 * 单例模式：一个类有且只有一个实例
	 * 	饿汉模式：启动就加载实例
	 * 	饱汉模式：需要的时候才加载实例
	 * 
	 * 编写方法：
	 * 1. 一个静态的单例引用
	 * 2. 一个静态方法获取单例
	 * 3. 私有化构造方法防止其他人使用，ElementManager em = new ElementManager()
	 */
	private static ElementManager Em = null;
	
	// 线程锁synchronized，保证该方法执行时只有一个线程，否则可能会创建多个实例
	public static synchronized ElementManager getManager() {
		if(Em == null) {
			Em = new ElementManager();
		}
		return Em;
	}
	
	private ElementManager() { // 私有化构造方法
		init();
	}
	
	//	static { // 饿汉模式实例化，static语句块在类被加载的时候才执行，只会执行一次
	//		Em = new ElementManager();
	//	}
	
	public void init() {
		gameElements = new HashMap<GameElement, List<ElementObj>>();
		
		// 根据枚举类型在gameElements中生成列表用于存放不同类型的元素
//		gameElements.put(GameElement.PLAYER, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.BULLET, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.FISH, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.MAPS, new ArrayList<ElementObj>());
		for(GameElement ge:GameElement.values()) {
			gameElements.put(ge, new ArrayList<ElementObj>());
		}
	}
}

package com.tedu.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tedu.element.ElementObj;

/**
 * @˵�� ������Ԫ�ع�������ר�Ŵ洢���е�Ԫ�أ�ͬʱ�ṩ��������ͼ�Ϳ��ƻ�ȡ����
 * @author Van
 * 
 */
public class ElementManager {
	
	/**
	 * ö�����͵�GameElement����Ϊmap��key�������ֲ�ͬ����Ϸ��Դ
	 * List�е�Ԫ�ط���Ӧ����Ԫ�ػ���
	 * 
	 * ��ʵģ��Viewֻ��Ҫ��ȡ�����map�Ϳ�����ʾ������Ҫ������Ԫ�أ�����Ԫ�ػ����showElement��
	 */
	private Map<GameElement, List<ElementObj>> gameElements;

	public Map<GameElement, List<ElementObj>> getGameElements() {
		return gameElements;
	}
	
	// ���Ԫ��
	public void addElement(GameElement ge, ElementObj obj) {
		gameElements.get(ge).add(obj); // ��keyֵ��������ӵ�������
	}
	
	// ȡ��ĳ��Ԫ��
	public List<ElementObj> getElementByKey(GameElement ge) {
		return gameElements.get(ge);
	}
	
	/**
	 * ����ģʽ��һ��������ֻ��һ��ʵ��
	 * 	����ģʽ�������ͼ���ʵ��
	 * 	����ģʽ����Ҫ��ʱ��ż���ʵ��
	 * 
	 * ��д������
	 * 1. һ����̬�ĵ�������
	 * 2. һ����̬������ȡ����
	 * 3. ˽�л����췽����ֹ������ʹ�ã�ElementManager em = new ElementManager()
	 */
	private static ElementManager Em = null;
	
	// �߳���synchronized����֤�÷���ִ��ʱֻ��һ���̣߳�������ܻᴴ�����ʵ��
	public static synchronized ElementManager getManager() {
		if(Em == null) {
			Em = new ElementManager();
		}
		return Em;
	}
	
	private ElementManager() { // ˽�л����췽��
		init();
	}
	
	//	static { // ����ģʽʵ������static�������౻���ص�ʱ���ִ�У�ֻ��ִ��һ��
	//		Em = new ElementManager();
	//	}
	
	public void init() {
		gameElements = new HashMap<GameElement, List<ElementObj>>();
		
		// ����ö��������gameElements�������б����ڴ�Ų�ͬ���͵�Ԫ��
//		gameElements.put(GameElement.PLAYER, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.BULLET, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.FISH, new ArrayList<ElementObj>());
//		gameElements.put(GameElement.MAPS, new ArrayList<ElementObj>());
		for(GameElement ge:GameElement.values()) {
			gameElements.put(ge, new ArrayList<ElementObj>());
		}
	}
}

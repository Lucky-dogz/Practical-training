package com.tedu.show;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

import com.tedu.element.Bullet;
import com.tedu.element.ElementObj;
import com.tedu.element.Player;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

/**
 * @˵�� ��Ϸ�������
 * @author Van
 * @���� ��Ҫ����Ԫ�ص���ʾ��ͬʱ���н����ˢ�£����̣߳�
 * @���߳�ˢ�� 1.����ʵ���߳̽ӿ�
 * 			 2.�����ж���һ���ڲ�����ʵ��
 */
public class GameMainJPanel extends JPanel implements Runnable{
	private  Graphics g2;
	private  Graphics g3;
	// ����������
	private ElementManager em;
	
	public GameMainJPanel() {
		init();
	}
	
	public void init() {
		em = ElementManager.getManager(); //�õ�Ԫ�ع�����ʵ��
	}
	
	/**
	 * paint�����ڻ滭��ʱ���й̶���˳���Ȼ滭��ͼƬ���ڵײ㣬��滭��ͼƬ�Ḳ���Ȼ滭��
	 * Լ����������ִֻ��һ�Σ���Ҫʵʱˢ��Ҫд���߳�
	 */
	@Override // ����������ϻ滭��
	public void paint(Graphics g) {
		super.paint(g);
		g2 = g.create();
		g3 = g.create();
		Map<GameElement, List<ElementObj>> all = em.getGameElements();
//		GameElement.values(); // ���ط��� ����ֵ��һ�����飬�����˳����Ƕ���ö�ٵ�˳��
		for(GameElement ge:GameElement.values()) { // ����ö�ٶ���ʱ��˳�����ò�ͬ���͵�Ԫ��
			List<ElementObj> list = all.get(ge);
			for(int i = 0; i<list.size(); i++) {
				ElementObj obj = list.get(i);
//				if(obj == null) {
//					System.out.println("?");
//					return;
				if (obj instanceof Bullet) {
//					System.out.println(obj);
					obj.showElement(g2);
				}
				else if (obj instanceof Player) {
					obj.showElement(g3);
				}
				else obj.showElement(g); // ����ÿ�����Լ���show��������Լ�����ʾ
			}
		}
		
//		Set<GameElement> set = all.keySet(); // �õ����е�key����
//		for(GameElement ge:set) {
//			List<ElementObj> list = all.get(ge);
//			for(int i = 0; i<list.size(); i++) {
//				ElementObj obj = list.get(i);
//				obj.showElement(g); // ����ÿ�����Լ���show��������Լ�����ʾ
//			}
//		}
	}

	@Override
	public void run() {
		while(true) {
			this.repaint();
			// һ������¶��̶߳���ʹ��һ�����ߣ������ٶ�
			try {
				Thread.sleep(50); // ����17����
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}

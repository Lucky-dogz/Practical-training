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
 * @说明 游戏的主面板
 * @author Van
 * @功能 主要进行元素的显示，同时进行界面的刷新（多线程）
 * @多线程刷新 1.本类实现线程接口
 * 			 2.本类中定义一个内部类来实现
 */
public class GameMainJPanel extends JPanel implements Runnable{
	private  Graphics g2;
	private  Graphics g3;
	// 联动管理器
	private ElementManager em;
	
	public GameMainJPanel() {
		init();
	}
	
	public void init() {
		em = ElementManager.getManager(); //得到元素管理器实例
	}
	
	/**
	 * paint方法在绘画的时候有固定的顺序，先绘画的图片会在底层，后绘画的图片会覆盖先绘画的
	 * 约定：本方法只执行一次，想要实时刷新要写多线程
	 */
	@Override // 用于在面板上绘画的
	public void paint(Graphics g) {
		super.paint(g);
		g2 = g.create();
		g3 = g.create();
		Map<GameElement, List<ElementObj>> all = em.getGameElements();
//		GameElement.values(); // 隐藏方法 返回值是一个数组，数组的顺序就是定义枚举的顺序。
		for(GameElement ge:GameElement.values()) { // 根据枚举定义时的顺序来拿不同类型的元素
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
				else obj.showElement(g); // 调用每个类自己的show方法完成自己的显示
			}
		}
		
//		Set<GameElement> set = all.keySet(); // 得到所有的key集合
//		for(GameElement ge:set) {
//			List<ElementObj> list = all.get(ge);
//			for(int i = 0; i<list.size(); i++) {
//				ElementObj obj = list.get(i);
//				obj.showElement(g); // 调用每个类自己的show方法完成自己的显示
//			}
//		}
	}

	@Override
	public void run() {
		while(true) {
			this.repaint();
			// 一般情况下多线程都会使用一个休眠，控制速度
			try {
				Thread.sleep(50); // 休眠17毫秒
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}

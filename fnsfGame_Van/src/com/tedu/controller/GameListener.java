package com.tedu.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

/**
 * @说明 监听类，用于监听用户的操作 KeyListener
 * @author Van
 *
 */
public class GameListener implements MouseListener,KeyListener {
	public static boolean isFinish;       //游戏是否正在进行
	public static boolean isPressed = false;      //游戏进度条加载完后是否按下鼠标了
	private ElementManager em = ElementManager.getManager();

	/*能否通过一个集合来记录所有按下的键，如果重复触发，就直接结束
	 * 同时第一次按下，记录到集合中，第二次判定集合中是否有
	 * 松开就直接删除集合中的记录
	 */
	private Set<Integer> set = new HashSet<Integer>();
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
//		System.out.println(key);
		if(set.contains(key)) { // 判定集合中是否已经存在包含这个对象
			return;
		}
		set.add(key);
		// 获取player集合
		List<ElementObj> player = em.getElementByKey(GameElement.PLAYER);
		for (ElementObj obj : player) {
			obj.keyClick(true, e.getKeyCode());
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(!set.contains(key)) { // 判定集合中是否已经存在包含这个对象
			return;
		}
		set.remove(key);
		// 获取player集合
		List<ElementObj> player = em.getElementByKey(GameElement.PLAYER);
		for (ElementObj obj : player) {
			obj.keyClick(false, e.getKeyCode());
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		double mouseX = e.getX();
		double mouseY = e.getY();
//		System.out.println(mouseX + " " + mouseY);
		
		//游戏开始界面正在加载: 进度条
		if(!isFinish)
		{
			return;
		}
				
		//进度条加载完毕 等待玩家点击
		if(isFinish)
		{
			//System.out.println("ppp");
			isPressed = true;
		}
		
		// 获取player集合
		List<ElementObj> player = em.getElementByKey(GameElement.PLAYER);
		
		//点击降级炮台
		if(mouseX>505 && mouseX<535 && mouseY>665 && mouseY<695) {
			for (ElementObj obj : player) {
				obj.playerDown();
			}
			return;
		}
		
		//点击升级炮台
		if(mouseX>720 && mouseX<745 && mouseY>665 && mouseY<690) {
			for (ElementObj obj : player) {
				obj.playerUp();
			}
			return;
		}
		
		for (ElementObj obj : player) {
			obj.mouseClick(true,mouseX,mouseY);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		double mouseX = e.getX();
		double mouseY = e.getY();
//		System.out.println(mouseX + " " + mouseY);
		// 获取player集合
		List<ElementObj> player = em.getElementByKey(GameElement.PLAYER);
		for (ElementObj obj : player) {
			obj.mouseClick(false,mouseX,mouseY);
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}

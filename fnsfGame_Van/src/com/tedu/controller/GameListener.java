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
 * @˵�� �����࣬���ڼ����û��Ĳ��� KeyListener
 * @author Van
 *
 */
public class GameListener implements MouseListener,KeyListener {
	public static boolean isFinish;       //��Ϸ�Ƿ����ڽ���
	public static boolean isPressed = false;      //��Ϸ��������������Ƿ��������
	private ElementManager em = ElementManager.getManager();

	/*�ܷ�ͨ��һ����������¼���а��µļ�������ظ���������ֱ�ӽ���
	 * ͬʱ��һ�ΰ��£���¼�������У��ڶ����ж��������Ƿ���
	 * �ɿ���ֱ��ɾ�������еļ�¼
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
		if(set.contains(key)) { // �ж��������Ƿ��Ѿ����ڰ����������
			return;
		}
		set.add(key);
		// ��ȡplayer����
		List<ElementObj> player = em.getElementByKey(GameElement.PLAYER);
		for (ElementObj obj : player) {
			obj.keyClick(true, e.getKeyCode());
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(!set.contains(key)) { // �ж��������Ƿ��Ѿ����ڰ����������
			return;
		}
		set.remove(key);
		// ��ȡplayer����
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
		
		//��Ϸ��ʼ�������ڼ���: ������
		if(!isFinish)
		{
			return;
		}
				
		//������������� �ȴ���ҵ��
		if(isFinish)
		{
			//System.out.println("ppp");
			isPressed = true;
		}
		
		// ��ȡplayer����
		List<ElementObj> player = em.getElementByKey(GameElement.PLAYER);
		
		//���������̨
		if(mouseX>505 && mouseX<535 && mouseY>665 && mouseY<695) {
			for (ElementObj obj : player) {
				obj.playerDown();
			}
			return;
		}
		
		//���������̨
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
		// ��ȡplayer����
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

package com.tedu.game;

import com.tedu.controller.GameListener;
import com.tedu.controller.GameThread;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;

public class GameStart {
	/**
	 * �����Ψһ���
	 */
	public static void main(String[] args) {
		GameJFrame gj = new GameJFrame();
		
		/*ʵ������壬ע�뵽jframe��*/
		GameMainJPanel jp = new GameMainJPanel();
		
		/*ʵ��������*/
		GameListener listener = new GameListener();
		/*ʵ�������߳�*/
		GameThread thread = new GameThread();
		
		// ע�� 
		gj.setjPanel(jp);
		gj.setMouseListener(listener);
		gj.addMouseListener(listener);;
		
		gj.setThread(thread);
		
		gj.start();
	}

}

package com.tedu.show;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.tedu.manager.GameLoad;

/**
 * @˵�� ��Ϸ����
 * @author Van
 * @���ܣ�Ƕ������塢�������߳�
 */
public class GameJFrame extends JFrame {
	public static int GameX = 1280;
	public static int GameY = 720;
	private JPanel jPanel = null; // ������ʾ�����
	private KeyListener keyListener = null; // ���̼���
	private MouseListener mouseListener = null; // ������ͷŽ����˳�����
	private MouseMotionListener mouseMotionListener = null; // ����ƶ�����ק����
	private Thread thread = null; // ��Ϸ���߳�
	private boolean isClosing = false;
	public GameJFrame() {
		init();
	}
	
	public void init() {
		this.setSize(GameX, GameY);
		this.setTitle("�������֮�����");
		this.setLocationRelativeTo(null); // ������
	
		this.enableEvents(AWTEvent.WINDOW_EVENT_MASK);//�趨��־����Frame�Լ����մ����¼�
		
	}
	
	/*���岼�֣���������button����*/
	public void addButton() {
		
	}
	
	/**
	 * ��������
	 */
	public void start() {
		if(jPanel != null) {
			this.add(jPanel);
		}
		if(keyListener != null) {
			this.addKeyListener(keyListener);
		}
		if(thread != null) {
			thread.start(); // �����߳�
		}
		this.setVisible(true);
		// �����ˢ��
		// ���jpanel��runnable ������ʵ�����
		if(this.jPanel instanceof Runnable) { // �����ж����������ǿ��ת���������
			new Thread((Runnable)this.jPanel).start();
		}
	}
	
	

	//Ȼ����ʵ������ĺ�����

	protected void processWindowEvent(final WindowEvent pEvent) {

	if (pEvent.getID() == WindowEvent.WINDOW_CLOSING) {

	/** ��ֹ�û���ε�����رա���ť����ظ����� **/

	if( !isClosing ) 
		isClosing = true;
	else return;
	
	//����JFrame�ر��¼�����
	GameLoad.saveGameInfo();
	//�Լ�����
	System.exit(0);
	}
	else{
		
	//���������¼�������JFrame����
		
	super.processWindowEvent(pEvent);

	}

	}


	// setע�룺ͨ��set����ע�������ļ��ж�ȡ�����ݣ����������ļ��е����ݸ�ֵΪ�������
	public void setjPanel(JPanel jPanel) {
		this.jPanel = jPanel;
	}

	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}

	public void setMouseListener(MouseListener mouseListener) {
		this.mouseListener = mouseListener;
	}

	public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
		this.mouseMotionListener = mouseMotionListener;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}
	
	
}

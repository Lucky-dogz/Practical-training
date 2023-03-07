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
 * @说明 游戏窗体
 * @author Van
 * @功能：嵌入主面板、启动主线程
 */
public class GameJFrame extends JFrame {
	public static int GameX = 1280;
	public static int GameY = 720;
	private JPanel jPanel = null; // 正在显示的面板
	private KeyListener keyListener = null; // 键盘监听
	private MouseListener mouseListener = null; // 鼠标点击释放进入退出监听
	private MouseMotionListener mouseMotionListener = null; // 鼠标移动和拖拽监听
	private Thread thread = null; // 游戏主线程
	private boolean isClosing = false;
	public GameJFrame() {
		init();
	}
	
	public void init() {
		this.setSize(GameX, GameY);
		this.setTitle("捕鱼达人之大闹深海");
		this.setLocationRelativeTo(null); // 面板居中
	
		this.enableEvents(AWTEvent.WINDOW_EVENT_MASK);//设定标志，让Frame自己接收窗口事件
		
	}
	
	/*窗体布局：可以设置button布局*/
	public void addButton() {
		
	}
	
	/**
	 * 启动方法
	 */
	public void start() {
		if(jPanel != null) {
			this.add(jPanel);
		}
		if(keyListener != null) {
			this.addKeyListener(keyListener);
		}
		if(thread != null) {
			thread.start(); // 启动线程
		}
		this.setVisible(true);
		// 界面的刷新
		// 如果jpanel是runnable 的子类实体对象
		if(this.jPanel instanceof Runnable) { // 类型判定，让下面的强制转换不会出错
			new Thread((Runnable)this.jPanel).start();
		}
	}
	
	

	//然后再实现下面的函数：

	protected void processWindowEvent(final WindowEvent pEvent) {

	if (pEvent.getID() == WindowEvent.WINDOW_CLOSING) {

	/** 防止用户多次点击“关闭”按钮造成重复保存 **/

	if( !isClosing ) 
		isClosing = true;
	else return;
	
	//处理JFrame关闭事件……
	GameLoad.saveGameInfo();
	//自己关了
	System.exit(0);
	}
	else{
		
	//忽略其他事件，交给JFrame处理
		
	super.processWindowEvent(pEvent);

	}

	}


	// set注入：通过set方法注入配置文件中读取的数据，即将配置文件中的数据赋值为类的属性
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

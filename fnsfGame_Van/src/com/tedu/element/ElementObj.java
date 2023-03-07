package com.tedu.element;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * @说明 所有元素的基类，抽象类
 * @author Van
 *
 */
public abstract class ElementObj {
	private Graphics g;
	private int x;
	private int y;
	private int w;
	private int h;
	private ImageIcon icon;
	private boolean stop;

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	// 状态值
	private boolean isLive = true; // 对象是否存活

	public ElementObj() { // 这个无参构造只是为了继承的时候不报错而写的，没有实际作用

	}

	/**
	 * @说明 带参数的构造方法
	 * @param x    以左上角为准的x坐标
	 * @param y    以左上角为准的y坐标
	 * @param w    以左上角为准的width
	 * @param h    以左上角为准的height
	 * @param icon 图片
	 */
	public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.icon = icon;
	}

	/**
	 * @说明 抽象方法，用于显示元素
	 * @param g 画笔 用于绘画元素
	 */
	public abstract void showElement(Graphics g);

	/**
	 * 键盘点击事件
	 * 
	 * @说明 使用父类定义接收键盘事件的方法
	 * @约定 只有需要实现键盘监听的子类，才需要重写这个方法
	 * @说明 使用接口的方式，需要在监听类进行类型转换
	 * @param iskeyPressed 点击的类型 true为按下 false为松开
	 * @param keyCode      代表触发的键盘的code值
	 */
	public void keyClick(boolean iskeyPressed, int keyCode) { // 不是强制必须实现，所以会有括号

	}

	public void mouseClick(Boolean isClicked, double x,double y) {
		
	}
	public void mousePressed(Boolean isClicked) {
		
	}
	/**
	 * 移动方法
	 * 
	 * @说明：需要移动的子类请实现这个方法
	 */
	protected void move() {

	}

	/**
	 * @说明 元素图片的更新
	 */
	protected void updateImage() {

	}

	/**
	 * @说明 发射子弹
	 * @param gameTime 游戏时间
	 * @注意 protect只允许本类和子类调用
	 */
	protected void shoot(long gameTime) {

	}

	/**
	 * @设计模式 模板模式：在模板模式中定义对象执行方法的先后顺序
	 * @说明 子类选择性重写该方法
	 * @说明 final 不允许重写
	 */
	public final void model(long gameTime) {
		// 更新图片
		updateImage();
		// 移动
		move();
		// 发射子弹
		shoot(gameTime);
	}

	/**
	 * @说明 根据json字符串创建一个元素
	 * @param str 一串用于解析的json字符串
	 * @return 一个元素对象
	 */
	public ElementObj createElement(String ...str) {
		return null;
	}

	/**
	 * @说明 元素的死亡方法，用于处理一些死亡事务（死亡动画，音效，加积分）
	 */
	public void dead() {

	}

	/** 
	 * @说明 实时返回元素的矩形对象（相当于碰撞体）
	 * @return 一个矩形对象
	 */
	public Rectangle getRectangle() {
		return new Rectangle(x, y, w, h);
	}
	
	/**
	 * @说明 碰撞检测
	 * @param obj 需要和本对象进行碰撞检测的另一个对象
	 * @return true为碰撞 false为没有碰撞
	 */
	public boolean collisionDetect(ElementObj obj) {
		// 两个对象的碰撞矩形对象做矩形相交判断
		return this.getRectangle().intersects(obj.getRectangle());
	}
	
	//被击中
	public int gotShot() {
		
		return 0;//如果没有被捕获，返回0
	}
	
	//击中目标动画
	public void shotOnTarget() {
		
	}
	
	//升级炮台
	public void playerUp() {
		
	}
	
	//降级炮台
	public void playerDown() {
		
	}

	/**
	 * POJO中的VO，阿里规约 只要是VO类 View Object 用于显示层使用的数据，就要为其生成getter和setter
	 */
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}
}

package com.tedu.element;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * @˵�� ����Ԫ�صĻ��࣬������
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

	// ״ֵ̬
	private boolean isLive = true; // �����Ƿ���

	public ElementObj() { // ����޲ι���ֻ��Ϊ�˼̳е�ʱ�򲻱����д�ģ�û��ʵ������

	}

	/**
	 * @˵�� �������Ĺ��췽��
	 * @param x    �����Ͻ�Ϊ׼��x����
	 * @param y    �����Ͻ�Ϊ׼��y����
	 * @param w    �����Ͻ�Ϊ׼��width
	 * @param h    �����Ͻ�Ϊ׼��height
	 * @param icon ͼƬ
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
	 * @˵�� ���󷽷���������ʾԪ��
	 * @param g ���� ���ڻ滭Ԫ��
	 */
	public abstract void showElement(Graphics g);

	/**
	 * ���̵���¼�
	 * 
	 * @˵�� ʹ�ø��ඨ����ռ����¼��ķ���
	 * @Լ�� ֻ����Ҫʵ�ּ��̼��������࣬����Ҫ��д�������
	 * @˵�� ʹ�ýӿڵķ�ʽ����Ҫ�ڼ������������ת��
	 * @param iskeyPressed ��������� trueΪ���� falseΪ�ɿ�
	 * @param keyCode      �������ļ��̵�codeֵ
	 */
	public void keyClick(boolean iskeyPressed, int keyCode) { // ����ǿ�Ʊ���ʵ�֣����Ի�������

	}

	public void mouseClick(Boolean isClicked, double x,double y) {
		
	}
	public void mousePressed(Boolean isClicked) {
		
	}
	/**
	 * �ƶ�����
	 * 
	 * @˵������Ҫ�ƶ���������ʵ���������
	 */
	protected void move() {

	}

	/**
	 * @˵�� Ԫ��ͼƬ�ĸ���
	 */
	protected void updateImage() {

	}

	/**
	 * @˵�� �����ӵ�
	 * @param gameTime ��Ϸʱ��
	 * @ע�� protectֻ��������������
	 */
	protected void shoot(long gameTime) {

	}

	/**
	 * @���ģʽ ģ��ģʽ����ģ��ģʽ�ж������ִ�з������Ⱥ�˳��
	 * @˵�� ����ѡ������д�÷���
	 * @˵�� final ��������д
	 */
	public final void model(long gameTime) {
		// ����ͼƬ
		updateImage();
		// �ƶ�
		move();
		// �����ӵ�
		shoot(gameTime);
	}

	/**
	 * @˵�� ����json�ַ�������һ��Ԫ��
	 * @param str һ�����ڽ�����json�ַ���
	 * @return һ��Ԫ�ض���
	 */
	public ElementObj createElement(String ...str) {
		return null;
	}

	/**
	 * @˵�� Ԫ�ص��������������ڴ���һЩ��������������������Ч���ӻ��֣�
	 */
	public void dead() {

	}

	/** 
	 * @˵�� ʵʱ����Ԫ�صľ��ζ����൱����ײ�壩
	 * @return һ�����ζ���
	 */
	public Rectangle getRectangle() {
		return new Rectangle(x, y, w, h);
	}
	
	/**
	 * @˵�� ��ײ���
	 * @param obj ��Ҫ�ͱ����������ײ������һ������
	 * @return trueΪ��ײ falseΪû����ײ
	 */
	public boolean collisionDetect(ElementObj obj) {
		// �����������ײ���ζ����������ཻ�ж�
		return this.getRectangle().intersects(obj.getRectangle());
	}
	
	//������
	public int gotShot() {
		
		return 0;//���û�б����񣬷���0
	}
	
	//����Ŀ�궯��
	public void shotOnTarget() {
		
	}
	
	//������̨
	public void playerUp() {
		
	}
	
	//������̨
	public void playerDown() {
		
	}

	/**
	 * POJO�е�VO�������Լ ֻҪ��VO�� View Object ������ʾ��ʹ�õ����ݣ���ҪΪ������getter��setter
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

package com.tedu.element;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.tedu.game.GameInfo;

public class StaticUI extends ElementObj
{
	public static int time = 1;  //��Ϸʱ��

	private boolean once = true;
	@Override
	public void showElement(Graphics g) {
		//������int��ΪString
		String timeStr = time+ "";
		String moneyStr = GameInfo.money+ "";
		String targetMoney = "Ŀ���ң�" + GameInfo.targetMoney + "";
		g.setColor(Color.white);
		g.setFont(new Font("����",Font.BOLD,20));
		//ʱ��
		g.drawString(timeStr, 250, 641);
		//���
		g.drawString(moneyStr, 1050, 641);
		//�ؿ�Ŀ����
		g.setFont(new Font("����",Font.BOLD,16));
		g.drawString(targetMoney, 125, 50);
		if(GameInfo.money>0) {
			once = true;
		}
		if(GameInfo.money==0&&once) {	
			BgmMusic.playMusic("coinsnone");
			once = false;
		}
	}
	
	public static void minusMoney(int level) {
		GameInfo.money -=  level*level;
	}
	
	

}

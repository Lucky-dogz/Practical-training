package com.tedu.element;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.tedu.game.GameInfo;

public class StaticUI extends ElementObj
{
	public static int time = 1;  //游戏时间

	private boolean once = true;
	@Override
	public void showElement(Graphics g) {
		//将两个int变为String
		String timeStr = time+ "";
		String moneyStr = GameInfo.money+ "";
		String targetMoney = "目标金币：" + GameInfo.targetMoney + "";
		g.setColor(Color.white);
		g.setFont(new Font("仿宋",Font.BOLD,20));
		//时间
		g.drawString(timeStr, 250, 641);
		//金币
		g.drawString(moneyStr, 1050, 641);
		//关卡目标金币
		g.setFont(new Font("仿宋",Font.BOLD,16));
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

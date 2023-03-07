package com.tedu.game;
/**
 * 功能说明：这个类用来控制整个游戏的进程，包括当前炮弹的等级，所以是一个单例
 *	需要写进文件的数据：
 *	Level、gamePart、money、targetMoney
 * @author Ethan
 *
 */

public class GameInfo {
	//炮弹等级
	public static int level = 1;
	//关卡
	public static int gamePart = 1;
	//开始金币
	public static int money = 100;  //游戏所获得的金币
	//每关目标金币
	public static int targetMoney=300;
	//需要一个变量用来控制暂时不再生成鱼
	public static boolean canGenerate = true;
	
}

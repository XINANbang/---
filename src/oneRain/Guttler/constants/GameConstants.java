package oneRain.Guttler.constants;

import android.app.Activity;

import android.content.SharedPreferences;
//保存数据
import oneRain.Guttler.logic.Operator;

public class GameConstants 
{
	//设置移动方向
	public static final int UP = 1;
	public static final int LEFT = 2;
	public static final int DOWN = 3;
	public static final int RIGHT = 4;
	
	//设置常量
	public static final int GREEN_START = 1;
	public static final int RED_START = 2;
	public static final int YELLOW_START = 3;
	
	//消息类型
	public static final int MSG_UPDATE = 1;
	public static final int MSG_GAMEOVER = 2;
	public static final int MSG_PAUSE = 3;
	
	//方块的大小
	public static int SIZE = 20;
	
	//更新频率
	public static int UPDATE_RATE = 200;
	
	//控制类
	public static Operator operator = null;
	//地图二维数组
	public static int[][] map;
	//设置当前方向
	public static int curOrientation = 3;
	//设置当前分数
	public static int score = 0;
	//设置第一次吃得到的分数
	public static int eveScore = 1;
	
	//SharedPreferences
	public static SharedPreferences sp;
	
	//地图选择列表
	public static final String[] maps = {"\"2\"型的", "\"I\"型的", "\"U\"型的"};
	
	//地图选择
	public static int mapChoice = 4;
}

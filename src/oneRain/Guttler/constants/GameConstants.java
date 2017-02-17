package oneRain.Guttler.constants;

import android.app.Activity;

import android.content.SharedPreferences;
//��������
import oneRain.Guttler.logic.Operator;

public class GameConstants 
{
	//�����ƶ�����
	public static final int UP = 1;
	public static final int LEFT = 2;
	public static final int DOWN = 3;
	public static final int RIGHT = 4;
	
	//���ó���
	public static final int GREEN_START = 1;
	public static final int RED_START = 2;
	public static final int YELLOW_START = 3;
	
	//��Ϣ����
	public static final int MSG_UPDATE = 1;
	public static final int MSG_GAMEOVER = 2;
	public static final int MSG_PAUSE = 3;
	
	//����Ĵ�С
	public static int SIZE = 20;
	
	//����Ƶ��
	public static int UPDATE_RATE = 200;
	
	//������
	public static Operator operator = null;
	//��ͼ��ά����
	public static int[][] map;
	//���õ�ǰ����
	public static int curOrientation = 3;
	//���õ�ǰ����
	public static int score = 0;
	//���õ�һ�γԵõ��ķ���
	public static int eveScore = 1;
	
	//SharedPreferences
	public static SharedPreferences sp;
	
	//��ͼѡ���б�
	public static final String[] maps = {"\"2\"�͵�", "\"I\"�͵�", "\"U\"�͵�"};
	
	//��ͼѡ��
	public static int mapChoice = 4;
}

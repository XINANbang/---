/**
 * ��Ҫ���߼������࣬�ж��ƶ�����ʳ�����
 */
package oneRain.Guttler.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import oneRain.Guttler.constants.GameConstants;
import oneRain.Guttler.model.Block;
import oneRain.Guttler.model.Food;
import oneRain.Guttler.model.Guttler;


public class Operator 
{
	//̰���߶���
	private Guttler guttler = null;
	
	//ʳ�����
	private Food food = null;
	
	//��ͼ��ά����
	private int[][] map = null;
	
	private int xCount, yCount;
	
	private boolean foodFlag = false;

	//���췽�����������ֿ��Զ��ߵĴ�С��λ�����޸�
	public Operator(int snakeLength, int xCount, int yCount) 
	{
		this.xCount = xCount;
		this.yCount = yCount;
		
		//��ʼ����ͼ
		initMap(xCount, yCount);
		
		//��ʼ��̰����
		initSnake(snakeLength);
	}
	
	//��ʼ����ͼ
	public void initMap(int xCount, int yCount)
	{
		map = new int[yCount][xCount];
		food = new Food();
	}
	
	//��ʼ����
	public void initSnake(int snakeLength)
	{
		Block head = new Block(10, 1);
		
		List<Block> body = new ArrayList<Block>();
		for(int i=0; i<snakeLength; i++)
		{
			body.add(new Block(9-i, 1));
		}
		

		guttler = new Guttler(head, body, snakeLength);
	}
	
	//��ʱ��Ϊ�仯���µ�ͼ
	public int[][] update(int orientation)
	{	
		//ÿһ����¶���һ���ػ�
		clearTile();
		buildWall();
		
		if(!foodFlag)
		{
			updateFood();
			foodFlag = true;
		}
		
		buildFood();
		
		Block b = new Block();
		
		int x = 0;
		int y = 0;
		//��Ҫ��Ϊ�ƶ��ͳ�ƻ���������
		switch(orientation)
		{	
		case GameConstants.UP:
			x = guttler.getHead().getX()-1;
			y = guttler.getHead().getY();
			break;
		case GameConstants.LEFT:
			x = guttler.getHead().getX();
			y = guttler.getHead().getY()-1;
			break;
		case GameConstants.DOWN:
			x = guttler.getHead().getX()+1;
			y = guttler.getHead().getY();
			break;
		case GameConstants.RIGHT:
			x = guttler.getHead().getX();
			y = guttler.getHead().getY()+1;
			break;
		}
		
		b.setX(x);
		b.setY(y);
	
		//�ж����ƶ����ǳ�ʳ�ﻹ������
		if((map[x][y] == GameConstants.YELLOW_START) ||
				(map[x][y] == GameConstants.GREEN_START))
		{
			//����
			return null;
		}
		else if(map[x][y] == 0)
		{
			//�ƶ�
			return move(b);
		}
		else //��ʳ��
		{
			return eat(b);
		}
	}
	
	//����ʳ��
	private void buildFood() 
	{
		if(foodFlag)
		{
			setTile(GameConstants.RED_START, 
					food.getFood().getX(), food.getFood().getY());
		}
	}

	//��ʼ��ʳ��
	public void updateFood()
	{
		int x, y;
		
		//��ȡ�����
		do
		{
			Random random = new Random();
			
			x = Math.abs(random.nextInt()) % (yCount-1);
			y = Math.abs(random.nextInt()) % (xCount-1);
			
			//��ֹ��һ�л��ߵ�һ�У���ߺ��ϱߵ�ǽ��
			if(x == 0)
			{
				x = 1;
			}
			if(y == 0)
			{
				y = 1;
			}
			
		}while(map[x][y] != 0);
		
		setTile(GameConstants.RED_START, x, y);
		food.setFood(new Block(x, y));
	}
	
	//��ʳ������
	public int[][] eat(Block block)
	{
		foodFlag = false;
		
		map = move(block);
		
		Block tail = guttler.getBody().get(guttler.getBody().size()-1);
		guttler.getBody().add(tail);
		
		setTile(GameConstants.YELLOW_START, tail.getX(), tail.getY());
		
		//���ݳ�ʳ��Ķ������޸�ˢ�µ�Ƶ�ʣ��ͻ���
		//�ӳٱ�� 
		GameConstants.UPDATE_RATE = (int) (GameConstants.UPDATE_RATE * 0.98);
		//�÷�����
		GameConstants.score += GameConstants.eveScore;
		//GameConstants.eveScore = (int) (GameConstants.eveScore * 1.1);
		
		return map;
	}
	
	//�ƶ������
	public int[][] move(Block block)
	{
		//Ϊ���帳ֵ
		for(int i=guttler.getBody().size()-1; i>0; i--)
		{
			//���θ�ֵ
			guttler.getBody().set(i, guttler.getBody().get(i-1));
		}
		//Ϊ�����һ���ڵ㸳ֵ��ֵ
		guttler.getBody().set(0, guttler.getHead());
		//Ϊͷ��ֵ
		guttler.setHead(block);
		
		//Ϊ��ͷ��ֵ
		setTile(GameConstants.RED_START, 
				guttler.getHead().getX(), guttler.getHead().getY());
		
		//Ϊ�ߵ����帳ֵ
		for(int i=0; i<guttler.getBody().size(); i++)
		{
			Block b = guttler.getBody().get(i);
			
			setTile(GameConstants.YELLOW_START, b.getX(), b.getY());
		}
		
		return map;
	}
	
	//��ղ���
	public void clearTile()
	{
		for(int x=0; x<yCount; x++)
			for(int y=0; y<xCount; y++)
			{
				setTile(0, x, y);
			}
	}
	
	//���õ�ͼ��ʾ����
	public void setTile(int picIndex, int x, int y)
	{
		map[x][y] = picIndex;
	}
	
	//���ݲ�ͬ��ѡ����Ʋ�ͬ��ǽ
	public void buildWall()
	{
		switch(GameConstants.mapChoice)
		{
		case 0:
			build2Wall(); //����"2"��ǽ
			break;
		case 1:
			buildIWall(); //����"I"��ǽ
			break;
		case 2:
			buildUWall(); //����"U"��ǽ
			break;
		default:
			buildNormalWall(); //������ͨǽ
			break;
		}
		
	}
	
	//����2�͵�ǽ
	public void build2Wall()
	{
		buildNormalWall();
		
		int i, j;
		
		for(j=xCount/4; j<xCount*3/4; j++)
			setTile(GameConstants.GREEN_START, yCount/6, j);
		for(i=yCount/6; i<yCount/2; i++)
			setTile(GameConstants.GREEN_START, i, xCount*3/4);
		for(j=xCount/4; j<xCount*3/4+1; j++)
			setTile(GameConstants.GREEN_START, yCount/2, j);
		for(i=yCount/2; i<yCount*5/6; i++)
			setTile(GameConstants.GREEN_START, i, xCount/4);
		for(j=xCount/4; j<xCount*3/4; j++)
			setTile(GameConstants.GREEN_START, yCount*5/6, j);
	}
	
	//����I�͵�ǽ
	public void buildIWall()
	{
		buildNormalWall();
		
		int i, j;
		
		for(j=xCount*2/5; j<xCount*3/5+1; j++)
			setTile(GameConstants.GREEN_START, yCount/4, j);
		for(i=yCount/4; i<yCount*3/4; i++)
			setTile(GameConstants.GREEN_START, i, xCount/2);
		for(j=xCount*2/5; j<xCount*3/5+1; j++)
			setTile(GameConstants.GREEN_START, yCount*3/4, j);
	}
	
	//����U��ǽ
	public void buildUWall()
	{
		buildNormalWall();
		
		int i, j;
		
		for(i=yCount/4; i<yCount*3/4; i++)
			setTile(GameConstants.GREEN_START, i, xCount/4);
		for(j=xCount/4; j<xCount*3/4+1; j++)
			setTile(GameConstants.GREEN_START, yCount*3/4, j);
		for(i=yCount/4; i<yCount*3/4; i++)
			setTile(GameConstants.GREEN_START, i, xCount*3/4);
	}
	
	//������ͨǽ
	public void buildNormalWall()
	{
		//��������
		for(int x=0; x<yCount; x++)
		{
			setTile(GameConstants.GREEN_START, x, 0);
			setTile(GameConstants.GREEN_START, x, xCount-1);
		}
		//��������
		for(int y=0; y<xCount; y++)
		{
			setTile(GameConstants.GREEN_START, 0, y);
			setTile(GameConstants.GREEN_START, yCount-1, y);
		}
	}
}

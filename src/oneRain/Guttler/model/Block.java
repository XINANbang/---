/**
 * �������ÿһ��������ϵ�С���飬��Ҫ��¼�����ڶ�ά�����е�����
 */
package oneRain.Guttler.model;

public class Block 
{
	//����ͼƬ���x,y������
	private int x;
	private int y;
	
	
	public Block() 
	{
		super();
	}

	//���췽��
	public Block(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}

	//Setter,Getter����
	public int getX()
	{
		return x;
	}


	public void setX(int x) 
	{
		this.x = x;
	}


	public int getY()
	{
		return y;
	}


	public void setY(int y) 
	{
		this.y = y;
	}
	
	
}

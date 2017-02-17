/**
 * ���࣬û����������������ԭ������Ժ���ܻ����������ȷ����ƣ������۾��ߣ������͡�������
 */
package oneRain.Guttler.model;

import java.util.List;

public class Guttler 
{
	
	//̰���ߵ���Ϣ���ɱ䳤�ȵ����壬��ͷ������
	private List<Block> body;
	private Block head;
	private int length;
	
	
	//���췽��
	public Guttler(Block head, List<Block> body, int length) 
	{
		this.head = head;
		this.body = body;
		this.length = length;
	}
	
	//Set,Get����ĳһ��
	public Block getBlock(int i)
	{
		return this.getBody().get(i);
	}
	
	public void setBlock(int i, Block block)
	{
		this.getBody().get(i).setX(block.getX());
		this.getBody().get(i).setY(block.getY());
	}
	
	//Setter,Getter����
	public List<Block> getBody() 
	{
		return this.body;
	}

	public void setBody(List<Block> body)
	{
		this.body = body;
	}

	public Block getHead() 
	{
		return head;
	}

	public void setHead(Block head) 
	{
		this.head = head;
	}

	public int getLength() 
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}
	
}

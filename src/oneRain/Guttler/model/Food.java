/**
 * ֮������ʳ���������Ժ���ܻ�����ͬ��ʳ���ͬ���Ͳ�ͬ״̬��ʳ��뷽������ͬ
 */
package oneRain.Guttler.model;

//��ʳ��ר�Ŷ���һ�����ԭ���Ƿ����Ժ��ʳ������չ�����粻ͬ��ʳ��õ���ͬ�ķ���
public class Food 
{
	private Block food;

	public Food() 
	{
		super();
	}

	public Food(Block food) 
	{
		this.food = food;
	}
	
	//Setter, Getter����
	public Block getFood() 
	{
		return food;
	}

	public void setFood(Block food) 
	{
		this.food = food;
	}
}

package oneRain.Guttler.view;

import oneRain.Guttler.R;
import oneRain.Guttler.constants.GameConstants;
import oneRain.Guttler.logic.Operator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class GameView extends View 
{	
	//��������Ϣ
	private Context c;
	
	//��Ļ��Ϣ
	private int width, height;
	
	//������Ļx,y��ķ�������
	private int xCount;
	private int yCount;
	
	//������Ļx,y��ƫ����
	private int xOffset;
	private int yOffset;
	
	//����Դ�ļ���ȡBitmap
	private Bitmap[] pics;
	
	//���췽��
	public GameView(Context context) 
	{
		super(context);
		this.c = context;
	}
	
	public GameView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);	
		this.c = context;
	}
	
	//����Ϸ��Ļ�ߴ�仯������µ��ã���������ʼ��
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{	
		super.onSizeChanged(w, h, oldw, oldh);
		
		initData(w, h);
	}
	
	/**
	 * ��ʼ����ͼ��ά���飬���������
	 * @param w
	 * @param h
	 */
	public void initData(int w, int h)
	{
		width = w;
		height = h;
		
		
		xCount = (int)Math.floor(w / GameConstants.SIZE);
		yCount = (int)Math.floor(h / GameConstants.SIZE);
		
		xOffset = (w - GameConstants.SIZE * xCount) / 2;
		yOffset = (h - GameConstants.SIZE * yCount) / 2;
		
		//��ʼ����ͼ��С
		GameConstants.map = new int[yCount][xCount];
		
		GameConstants.operator = new Operator(4, xCount, yCount);
		GameConstants.map = GameConstants.operator.update(GameConstants.curOrientation);
		
		initGame();
	}
	
	/**
	 * ��ʼ����Ϸ����ʾͼƬ��Դ
	 */
	public void initGame()
	{
		Resources r = getContext().getResources();
		
		pics = new Bitmap[4];
		
		loadPic(GameConstants.GREEN_START, r.getDrawable(R.drawable.greenstar));
		loadPic(GameConstants.RED_START, r.getDrawable(R.drawable.redstar));
		loadPic(GameConstants.YELLOW_START, r.getDrawable(R.drawable.yellowstar));
	}
	
	/**
	 * ��ʼ��ͼƬ��Դ����
	 * @param key
	 * @param drawable
	 */
	public void loadPic(int key, Drawable drawable)
	{
		Bitmap bitmap = Bitmap.createBitmap(
				GameConstants.SIZE,
				GameConstants.SIZE, 
				Bitmap.Config.ARGB_8888    );
		
		Canvas canvas = new Canvas(bitmap);
		
		drawable.setBounds(0, 0, GameConstants.SIZE, GameConstants.SIZE);
		drawable.draw(canvas);
		
		pics[key] = bitmap;
	}
	
	
	/**
	 * ��Ӧ�����¼�
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int x = (int)event.getX();
		int y = (int)event.getY();
		
		//�жϣ������ǰ�������ƶ�������Ӧ���ң������ǰ�������ƶ�������Ӧ����
		if((GameConstants.curOrientation%2) == 1) //�����ƶ�
		{
			//�ж���������
			if(x < width/2)
			{
				//��������
				GameConstants.curOrientation = GameConstants.LEFT;
			}
			else
			{
				//��������
				GameConstants.curOrientation = GameConstants.RIGHT;
			}
		}
		else //�����ƶ�
		{
			//�ж����ϻ�����
			if(y < height/2)
			{
				//��������
				GameConstants.curOrientation = GameConstants.UP;
			}
			else
			{
				GameConstants.curOrientation = GameConstants.DOWN;
			}
		}
		
		return super.onTouchEvent(event);
	}

	/**
	 * ���Ʒ���
	 */
	@Override
	protected void onDraw(Canvas canvas) 
	{
		super.onDraw(canvas);
		
		Paint paint = new Paint();

		//����View����������������ֵ����֮��ͬ��ͼƬ
		if(GameConstants.map == null) //�ж�������״̬�¡�����
		{
			
		}
		else //����
		{
			for(int x=0; x<yCount; x++)
				for(int y=0; y<xCount; y++)
				{
					//������
					if(GameConstants.map[x][y] > 0)
					{
						canvas.drawBitmap(pics[GameConstants.map[x][y]], 
								xOffset + y * GameConstants.SIZE,
								yOffset + x * GameConstants.SIZE, 
								paint);
					}
				}
		}
	}
}

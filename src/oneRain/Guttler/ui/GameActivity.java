/**
 * 显示游戏主Activity，没找到更好的音效，就没用增加音效
 */
package oneRain.Guttler.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
//import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import oneRain.Guttler.R;
import oneRain.Guttler.constants.GameConstants;
import oneRain.Guttler.view.GameView;

public class GameActivity extends Activity 
{
	private LinearLayout layout = null;
	private GameView gameView = null;
	
	MyHandler handler = new MyHandler();
	
	//音乐播放
	private MediaPlayer mp = null;
	
	//是否暂停
	private boolean isPaused = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        initMusic();
        initData();
        initView();
        ut.start();
    }
    
    public void initMusic()
    {
    	mp = MediaPlayer.create(this, R.raw.becauselove);
    	
    	mp.start();
    }
    
    public void initData()
    {
    	GameConstants.curOrientation = GameConstants.DOWN;
    	GameConstants.map = null;
    	GameConstants.operator = null;
    	GameConstants.UPDATE_RATE = 200;
    	GameConstants.score = 0;
    	GameConstants.eveScore = 1;
    	
    	GameConstants.mapChoice = getIntent().getIntExtra("map", 4);
    }
    
    @Override
	protected void onResume() 
    {
		super.onResume();
	}

	public void initView()
    {
    	layout = (LinearLayout)findViewById(R.id.layout);
    	gameView = new GameView(this);
    	gameView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,
    			LayoutParams.FILL_PARENT));
    	layout.addView(gameView);
    }
    
    @Override
	protected void onPause()
    {
		super.onPause();
	}

	@Override
	protected void onStop() 
	{
//		GameConstants.mapChoice = 4;
		
		super.onStop();
	}

	@Override
	protected void onDestroy() 
    {
		super.onDestroy();
		
		ut.setFlag(false);
		
		mp.stop();
	}
	
	private UpThread ut = new UpThread();
	
	//更新数据线程
	public class UpThread extends Thread
	{
		private boolean flag = true;
		
		public void setFlag(boolean flag)
		{
			this.flag = flag;
		}
		
		@Override
		public void run() 
		{	
			while(flag)
			{				
				try 
				{
					Thread.sleep(GameConstants.UPDATE_RATE);
				}
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				
				//判断是否暂停
				if(!isPaused) //没暂停的时候
				{
					update();
				}
				else //暂停的时候
				{
					Message msg = new Message();
					msg.what = GameConstants.MSG_PAUSE;
					handler.sendMessage(msg);
				}
			}
		}
	}
	
	
	//更新数据
	public void update()
	{
		GameConstants.map = GameConstants.operator.update(GameConstants.curOrientation);
		
		if(GameConstants.map == null) //死了
		{
			ut.setFlag(false);
			
			Message msg = new Message();
			msg.what = GameConstants.MSG_GAMEOVER;
			handler.sendMessage(msg);
		}
		else //没死
		{
			Message msg = new Message();
			msg.what = GameConstants.MSG_UPDATE;
			handler.sendMessage(msg);
		}		
	}
	
	//响应更新信息，更新UI
	public class MyHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg)
		{			
			switch(msg.what)
			{
			case GameConstants.MSG_UPDATE:
				gameView.invalidate();
				break;
			case GameConstants.MSG_GAMEOVER:
				//游戏死亡
				//看看有没有更新排行榜
				int first = GameConstants.sp.getInt("first", 0);
				int second = GameConstants.sp.getInt("second", 0);
				int third = GameConstants.sp.getInt("third", 0);
				
				String congratulation = "";
				if(GameConstants.score < third) //未上榜
				{
					
				}
				else if(GameConstants.score > first) //比第一还牛x
				{
					SharedPreferences.Editor editor = GameConstants.sp.edit();
					int t = GameConstants.sp.getInt("second", 0);
					editor.putInt("third", t);
					int s = GameConstants.sp.getInt("first", 0);
					editor.putInt("second", s);
					editor.putInt("first", GameConstants.score);
					editor.commit();
					congratulation = "打破第一名的成绩。";
				}
				else if(GameConstants.score > second) //上了榜，但是没第一多，比第二多
				{
					SharedPreferences.Editor editor = GameConstants.sp.edit();
					int t = GameConstants.sp.getInt("second", 0);
					editor.putInt("third", t);
					editor.putInt("second", GameConstants.score);
					editor.commit();
					congratulation = "打破第二名成绩。";
				}
				else if(GameConstants.score > third) //上了榜，但是没第一多，没第二多，比第三多
				{
					SharedPreferences.Editor editor = GameConstants.sp.edit();
					editor.putInt("third", GameConstants.score);
					editor.commit();
					congratulation = "打破第三名成绩。";
				}
				
				//弹出对话框
				AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
				builder.setTitle("游戏结束");
				builder.setMessage("本次游戏分数是: " + GameConstants.score + "\n"
						+ congratulation);
				builder.setPositiveButton("返回", new DialogInterface.OnClickListener()
				{	
					public void onClick(DialogInterface dialog, int which) 
					{
//						//退出程序
						Intent i = getIntent();
						i.putExtra("result", 1);
						GameActivity.this.setResult(0, i);
						finish();
					}
				});
				builder.setNegativeButton("再来一次", new DialogInterface.OnClickListener()
				{	
					public void onClick(DialogInterface dialog, int which) 
					{				
						Intent i = getIntent();
						i.putExtra("result", 0);
						GameActivity.this.setResult(0, i);
						finish();
					}
				});
				builder.create().show();
				
//				gameView.invalidate();
				break;
			case GameConstants.MSG_PAUSE:
				//调用GameView重绘
				gameView.invalidate();
				break;
			}
		}
	}
	
	/**
	 * 按键监听
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
    {
		switch(keyCode)
		{
		case KeyEvent.KEYCODE_BACK: //返回按钮下
			isPaused = true;
			
			//弹出对话框
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage("暂停中...\n");
			builder.setPositiveButton("返回上一页", new DialogInterface.OnClickListener()
			{	
				public void onClick(DialogInterface dialog, int which) 
				{					
					Intent i = getIntent();
					i.putExtra("result", 1);
					GameActivity.this.setResult(0, i);
					finish();
				}
			});
			builder.setNegativeButton("取消暂停", new DialogInterface.OnClickListener()
			{	
				public void onClick(DialogInterface dialog, int which) 
				{
					isPaused = false;
				}
			});
			builder.create().show();
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
}
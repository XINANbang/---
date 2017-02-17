/**
 * 菜单Activity
 */
package oneRain.Guttler.ui;

import oneRain.Guttler.R;
import oneRain.Guttler.constants.GameConstants;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
//import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
//import android.widget.Toast;

public class MenuActivity extends Activity
{
	private Button start_btn = null;
	private Button choice_btn = null;
	private Button arrange_btn = null;
	private Button help_btn = null;
	private Button about_btn = null;
	private Button exit_btn = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		
		initView();
		

        //初始化sharedpreferance
		GameConstants.sp = getPreferences(Activity.MODE_PRIVATE);
	}
	
	public void initView()
	{
		start_btn = (Button)findViewById(R.id.start_btn);
		choice_btn = (Button)findViewById(R.id.choice_btn);
		arrange_btn = (Button)findViewById(R.id.arrange_btn);
		help_btn = (Button)findViewById(R.id.help_btn);
		about_btn = (Button)findViewById(R.id.about_btn);
		exit_btn = (Button)findViewById(R.id.exit_btn);
		


		start_btn.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(MenuActivity.this, GameActivity.class);
				MenuActivity.this.startActivityForResult(intent, 0);
			}
		});
		

		choice_btn.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View v)
			{

				showMapChoiceDialog();
			}
		});
		

		arrange_btn.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View v) 
			{

				int first = GameConstants.sp.getInt("first", 0);
				int second = GameConstants.sp.getInt("second", 0);
				int third = GameConstants.sp.getInt("third", 0);
				
                String content ="第一名："+first+"\n"+"第二名："+second+"\n"+"第三名："+third;
				showMessageDialog("排名榜",content,"OK");
			}
		});
		

		help_btn.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View v) 
			{

				String help_content = "		                   帮助 \n" +
						"		\n" +
						"		\n" +
						"		\n" +
						"		 \n" +
						"		两个手指放在屏幕的左上角和右下角，当希望蛇向左和向上走的时候，按下左上角的手指。当想要蛇向右或者向下走的时候，按下右下角的手指。\n" +
						"		\n" +
						"       \n" +
						"       \n";
				
				showMessageDialog("玩法介绍", help_content, "OK！");
			}
		});
		

		about_btn.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View v) 
			{

				String about_content = "		陈汉滨\n" +
						"		2016年1月26日。\n" +
						"		";
		
				showMessageDialog("关于", about_content, "OK");
			}
		});
		

		exit_btn.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View v) 
			{
				
				showExitDialog();
			}
		});
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
    {
		switch(keyCode)
		{
		case KeyEvent.KEYCODE_BACK:
			showExitDialog();
			return true;
		}
		
		return super.onKeyDown(keyCode, event);
	}
	

	private void showMapChoiceDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
		builder.setTitle("开始");
		builder.setItems(GameConstants.maps, 
				new DialogInterface.OnClickListener()
				{			
					@Override
					public void onClick(DialogInterface dialog, int which) 
					{
						int map = 4;
						
						switch(which)
						{
						case 0:
							map = 0;
							break;
						case 1:
							map = 1;
							break;
						case 2:
							map = 2;
							break;
						}
						
						Intent intent = new Intent(MenuActivity.this, GameActivity.class);
						intent.putExtra("map", map);
						MenuActivity.this.startActivityForResult(intent, 0);
					}
				});
		builder.create().show();
	}
	

	private void showMessageDialog(String title, String content, String conContent)
	{

		AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
		builder.setTitle(title);
		builder.setMessage(content);
		builder.setPositiveButton(conContent, null);

		builder.create().show();
	}
	

	private void showExitDialog()
	{

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("提示");
		builder.setMessage("确定离开？");
		builder.setPositiveButton("离开", new DialogInterface.OnClickListener()
		{	
			public void onClick(DialogInterface dialog, int which) 
			{

				finish();
			}
		});
		builder.setNegativeButton("不离开", null);
		builder.create().show();
	}
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		int result = data.getIntExtra("result", 4);
		System.out.println(resultCode + result);
		switch(result)
		{
		case 0:
			System.out.println("onActivityResul");
			Intent intent = new Intent(MenuActivity.this, GameActivity.class);
			intent.putExtra("map", GameConstants.mapChoice);
			MenuActivity.this.startActivityForResult(intent, 0);
			break;
		case 1:

			break;
		default:
			break;
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
}

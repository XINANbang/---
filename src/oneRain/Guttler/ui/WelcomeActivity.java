/**
 * 欢迎界面，自己画的小画！哈哈
 */
package oneRain.Guttler.ui;

import oneRain.Guttler.R;
//import oneRain.Guttler.constants.GameConstants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class WelcomeActivity extends Activity 
{
	private ImageView welcomeImageView = null;
	
	public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        
        initView();
    }
	
	//初始化界面
	private void initView()
    {
    	welcomeImageView = (ImageView)findViewById(R.id.welcome);
    	
    	//三秒钟的渐变动画，动画完成后启动菜单选项
    	AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
    	animation.setDuration(3000);
    	welcomeImageView.startAnimation(animation);
    	
    	
    	
    	
    	animation.setAnimationListener(new AnimationListener()
    	{
			public void onAnimationEnd(Animation animation) 
			{				
				Intent intent = new Intent(WelcomeActivity.this, MenuActivity.class);
				WelcomeActivity.this.startActivity(intent);
				WelcomeActivity.this.finish();
			}

			public void onAnimationRepeat(Animation animation) 
			{
				
			}

			public void onAnimationStart(Animation animation) 
			{
				
			}
    	});
    }
}

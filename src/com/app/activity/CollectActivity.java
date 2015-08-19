package com.app.activity;

import edu.nju.shalbum.R;
import edu.nju.shalbum.base.BaseUi;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;


public class CollectActivity extends BaseUi{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collect);

	}
	/*
	 * 重写手机返回键
	 */
	@Override  
    public void onBackPressed() {  
        super.onBackPressed();  
        finish();   
        //返回动画
		overridePendingTransition(R.anim.back_slide_in_left,  
                R.anim.back_slide_in_right); 
    }  
	/*
	 * 重写左上角返回键
	 */
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            //返回动画
    		overridePendingTransition(R.anim.back_slide_in_left,  
                    R.anim.back_slide_in_right); 
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

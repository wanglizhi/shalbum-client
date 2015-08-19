package com.app.activity;

import com.app.activity.AttentionActivity.AppSectionsPagerAdapter;
import com.app.fragment.HaveAttentionFragment;
import com.app.fragment.MessageOfFansFragment;
import com.app.fragment.MessageOfPraiseFragment;
import com.app.fragment.RecommendAttention;

import edu.nju.shalbum.R;
import edu.nju.shalbum.base.BaseUi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;


public class MessageActivity extends BaseUi implements ActionBar.TabListener {

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention);
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        final ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }
    /*
	 * 重写手机返回键
	 */
	@Override  
    public void onBackPressed() { 
		//清空动画
		HaveAttentionFragment.AnimateFirstDisplayListener.displayedImages.clear();
		RecommendAttention.AnimateFirstDisplayListener.displayedImages.clear();
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
        
            finish();
            //返回动画
    		overridePendingTransition(R.anim.back_slide_in_left,  
                    R.anim.back_slide_in_right);
            return true;
        //return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

    	Fragment messageoffans = new MessageOfFansFragment();
    	Fragment messageofpraise = new MessageOfPraiseFragment();
        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return messageoffans;

                default:
                    return messageofpraise;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
        	switch(position){
        		case 0:
        			return "粉丝";
        		case 1:
        			return "赞";
        		default:
        			return "";
        	}
           
        }
    }

}

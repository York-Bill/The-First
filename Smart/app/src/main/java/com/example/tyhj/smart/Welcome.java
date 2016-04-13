package com.example.tyhj.smart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Donghua.DepthPageTransformer;
import Donghua.ZoomOutPageTransformer;
import savephoto.GetModelHeadImage;

public class Welcome extends Activity {
    ViewPager viewpager;
    private int[] mImags = new int[] { R.drawable.wel1,
            R.drawable.wel3, R.drawable.wel4};
    private List<ImageView> mview = new ArrayList<ImageView>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AVOSCloud.initialize(this, "fsbc53ABzyGIW7BMeCIVhlk4-gzGzoHsz", "i0tswVfwEsaEBR06nKmBRxxV");
        AVUser user=AVUser.getCurrentUser();
        if(user!=null){
            Intent in=new Intent(Welcome.this,MainActivity.class);
            GetModelHeadImage.setIfFirst(true);
            startActivity(in);
        }else {
            GetModelHeadImage.setIfFirst(false);
        }
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        init();
    }

    private void init() {
        WindowManager wm = (WindowManager) Welcome.this
                .getSystemService(Context.WINDOW_SERVICE);
        final int width = wm.getDefaultDisplay().getWidth();
        final int height = wm.getDefaultDisplay().getHeight();
        viewpager= (ViewPager) findViewById(R.id.vp_welcome);
        //设置切换动画
        viewpager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewpager.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(Welcome.this);
                // 设置图片
                Picasso.with(Welcome.this)
                        .load(mImags[position])
                        .resize(width, height)
                        .centerCrop()
                        .into(imageView);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                container.addView(imageView);
                mview.add(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(mview.get(position));
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                // TODO Auto-generated method stub
                return view == object;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return mImags.length;
            }
        });
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == mImags.length - 1) {
                    Intent intent = new Intent(Welcome.this,
                            MyLogin.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}

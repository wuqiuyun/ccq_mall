/**
 * @date 2015年7月21日 下午3:13:16
 * @Class GalleryActivity
 */
package clerk.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TextView;


import clerk.sz28yun.com.R;
import clerk.sz28yun.com.base.CCJActivity;
import clerk.sz28yun.com.fragment.DemoImagePreviewFragment;

import java.util.ArrayList;

import butterknife.Bind;

import uk.co.senab.photoview.HackyViewPager;


public class DemoGalleryActivity extends CCJActivity {

    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";

    @Bind(R.id.pager)
    HackyViewPager pager;
    @Bind(R.id.indicator)
    TextView indicator;

    private int pagerPosition;

    public static void launch(Context context) {
        Intent intent = new Intent(context, DemoGalleryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }


    @Override
    public int getLayoutResId() {
        return R.layout.activity_gallery_layout;
    }

    @Override
    public void onInitialize(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        initView(getdata(), savedInstanceState);
    }

    private void initView(ArrayList<String> list, Bundle savedInstanceState) {
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), getdata());
        pager.setAdapter(mAdapter);
        CharSequence text = getString(R.string.viewpager_indicator, 1, pager.getAdapter().getCount());
        indicator.setText(text);
        // 更新下标
        pager.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                CharSequence text = getString(R.string.viewpager_indicator, arg0 + 1, pager.getAdapter().getCount());
                indicator.setText(text);
            }

        });
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        pager.setCurrentItem(pagerPosition);
    }

    private ArrayList<String> getdata() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("http://b.hiphotos.baidu.com/image/h%3D360/sign=0cf205f979899e51678e3c1272a7d990/e824b899a9014c08570efea1087b02087bf4f4f9.jpg");
        list.add("http://a.hiphotos.baidu.com/image/h%3D360/sign=2ed33dc13f6d55fbdac670205d224f40/96dda144ad345982baf3b48d0ef431adcbef84b7.jpg");
        list.add("http://h.hiphotos.baidu.com/image/h%3D360/sign=a740b777f9f2b211fb2e8348fa816511/bd315c6034a85edf3ac92f274b540923dd547569.jpg");
        return list;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, pager.getCurrentItem());
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public ArrayList<String> fileList;

        public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList.get(position);
            return DemoImagePreviewFragment.newInstance(url);
        }

    }

}

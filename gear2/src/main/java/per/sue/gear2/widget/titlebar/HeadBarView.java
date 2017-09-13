/**
 * @date 2015年7月21日 下午2:09:0
 * 6
 * @Class MainTitleBar
 */
package per.sue.gear2.widget.titlebar;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import per.sue.gear2.R;
import per.sue.gear2.utils.UnitUtils;
import per.sue.gear2.widget.CleanableEditText;


@SuppressLint("InflateParams")
public class HeadBarView extends LinearLayout {

    private LayoutInflater inflater;

    private LinearLayout barLeftLayout;
    private LinearLayout barRightLayout;

    private FrameLayout middleLayout;
    private FrameLayout middleToLayout;


    private PopupWindow popupWindow;// 菜单弹窗
    private ListView menuListView;// 菜单列表
    private TitleMenuAdapter titleMenuAdapter;

    private OnItemClickListener menuItemClickListener;

    private final int LENGTH = 96;

    private TextView mTitleView;

    public HeadBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.gear_head_layout_view, this);
        // 初始化控件
        initView();
    }

    public void initView() {
        barLeftLayout = (LinearLayout) findViewById(R.id.leftlayout);
        barRightLayout = (LinearLayout) findViewById(R.id.rightlayout);
        middleLayout = (FrameLayout) findViewById(R.id.middlelayout);
        middleToLayout = (FrameLayout) findViewById(R.id.middleTolayout);
    }


    public ImageView addRightItem(int resId, OnClickListener l) {
        LayoutParams params = new LayoutParams(LENGTH, LENGTH);
        params.gravity = Gravity.CENTER;
        //LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        ImageView view = generateImageView(resId, l);
        barRightLayout.addView(view, params);
        computeTitleLength();
        setMenuItemParams(view);
        return view;
    }

    public View addRightItem(View v) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        barRightLayout.addView(v, params);
        computeTitleLength();
        return v;
    }

    public View addRightItem(View v, OnClickListener l) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        v.setOnClickListener(l);
        barRightLayout.addView(v, params);
        setMenuItemParams(v);
        computeTitleLength();
        return v;
    }

    public View addLeftItem(View v) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        barLeftLayout.addView(v, params);
        computeTitleLength();
        return v;
    }

    public View addRightTextItem(int resId, OnClickListener l) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        TextView view = generateTextView(getResources().getString(resId), l);
        barRightLayout.addView(view, params);
        computeTitleLength();
        setMenuItemParams(view);
        return view;
    }

    public TextView addRightTextItem(String  str, OnClickListener l) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        TextView view = generateTextView(str, l);
        view.setGravity( Gravity.CENTER);
        view.setTextColor(getResources().getColor(R.color.gear_white));
        barRightLayout.addView(view, params);
        computeTitleLength();
        setMenuItemParams(view);
        return view;
    }

    public View addLeftItem(int resId, OnClickListener l) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        View view = generateImageView(resId, l);
        barLeftLayout.addView(view, params);
        computeTitleLength();
        setMenuItemParams(view);
        return view;
    }

    public CleanableEditText addMiddlerEditText(String hint) {
        CleanableEditText view = generateEditTextView(hint);
        middleToLayout.addView(view, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        computeTitleLength();
        return view;
    }


    public TextView addMiddlerTitle(String title) {
        TextView view = generateTextView(title, null);
        view.setSingleLine(true);
        view.setMaxEms(20);
        view.setEllipsize(TextUtils.TruncateAt.END);
        view.setGravity(Gravity.CENTER);
        mTitleView = view;
        if(null != middleLayout){
            middleLayout.addView(view, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        }

        return view;
    }

    public void setMenuItemParams(View view) {
        view.setBackgroundResource(R.drawable.gear_tab_bg);
        view.getLayoutParams().width = UnitUtils.px2dip(getContext(), 48);
        view.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) view.getLayoutParams()).gravity = Gravity.CENTER;
        }
    }

    public void computeTitleLength() {


        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int mariginWidth = 0;
                int leftWidth = barRightLayout.getWidth();
                int rightWidth = barLeftLayout.getWidth();
                if (leftWidth > rightWidth) {
                    mariginWidth = leftWidth;
                } else {
                    mariginWidth = rightWidth;
                }
                ((RelativeLayout.LayoutParams) middleLayout.getLayoutParams()).leftMargin = mariginWidth;
                ((RelativeLayout.LayoutParams) middleLayout.getLayoutParams()).rightMargin = mariginWidth;
            }
        });
    }

    /**
     * 生成ImageView
     * @param resId
     * @param l
     * @return
     */
    private ImageView generateImageView(int resId, OnClickListener l) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(resId);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setOnClickListener(l);
        return imageView;
    }

    /**
     * 生成TextView
     * @param str
     * @param l
     * @return
     */
    private TextView generateTextView(String str, OnClickListener l) {
        TextView textView = new TextView(getContext());
        textView.setText(str);
        textView.setOnClickListener(l);
        return textView;
    }

    /**
     * 生成EditView
     * @param hint
     * @return
     */
    public CleanableEditText generateEditTextView(String hint) {
        CleanableEditText editText = new CleanableEditText(getContext());
        editText.setHint(hint);
        return editText;
    }

    /**
     * 获取PopupWindow实例
     */
    private void showMenuPopupWindow(View v, MenuItem menuItem) {
        if (null == popupWindow) {
            initPopuptWindow();
        }
        if (!popupWindow.isShowing()) {
            if (null != menuItem) {
                titleMenuAdapter.setData(menuItem.getMenuImgs(), menuItem.getMenuTexts());
                menuListView.setOnItemClickListener(menuItem.getItemClickListener());
            }
            popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
            popupWindow.setWidth(LayoutParams.WRAP_CONTENT);

            popupWindow.showAsDropDown(v);
        } else {
            popupWindow.dismiss();
        }

    }

    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindow() {
        // 获取自定义布局文件title_menu_popupwindow.xml的视图
        menuListView = new ListView(getContext());
        // 创建PopupWindow实例
        popupWindow = new PopupWindow(menuListView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        titleMenuAdapter = new TitleMenuAdapter(getContext());
        menuListView.setAdapter(titleMenuAdapter);
        menuListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 菜单子列表的点击事件
                popupWindow.dismiss();
                if (menuItemClickListener != null) {
                    menuItemClickListener.onItemClick(parent, view, position, id);
                }
            }
        });
    }

    public MenuItemView addMenuView(int resId, int[] menuImgs, String[] menuTexts, OnItemClickListener itemClickListener) {
        MenuItem menuItem = new MenuItem(menuImgs, menuTexts, itemClickListener);
        MenuItemView menuItemView = new MenuItemView(getContext());
        menuItemView.setMenuItem(menuItem);
        menuItemView.setImageResource(resId);
        menuItemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuItemView menuItemView = (MenuItemView) v;
                showMenuPopupWindow(v, menuItemView.getMenuItem());

            }
        });
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        barRightLayout.addView(menuItemView, params);
        return menuItemView;

    }

    public View addMenuView(View view, int[] menuImgs, String[] menuTexts, OnItemClickListener itemClickListener) {
        MenuItem menuItem = new MenuItem(menuImgs, menuTexts, itemClickListener);

        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuItemView menuItemView = (MenuItemView) v;
                showMenuPopupWindow(v, menuItemView.getMenuItem());

            }
        });
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        barRightLayout.addView(view, params);
        return view;

    }

    public void setMenuItemClick(OnItemClickListener l) {
        menuItemClickListener = l;
    }

    private int px2dip(int valuePx) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valuePx, getContext().getResources().getDisplayMetrics());
    }

    public TextView getTitleView() {
        return mTitleView;
    }
}

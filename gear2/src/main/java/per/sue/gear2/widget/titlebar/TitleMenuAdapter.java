package per.sue.gear2.widget.titlebar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import per.sue.gear2.R;


public class TitleMenuAdapter extends BaseAdapter{

	private int[] menuImgs;
	private String[] menuTexts;
	private LayoutInflater mInflater;

	public TitleMenuAdapter(Context context){
		this.mInflater = LayoutInflater.from(context);
	};
	
	public void setData (int[] menuImgs, String[] menuTexts){
		this.menuImgs = menuImgs;
		this.menuTexts = menuTexts;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return menuTexts.length;
	}

	@Override
	public Object getItem(int position) {
		return menuTexts[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		// 如果缓存convertView为空，则需要创建View
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.gear_head_menu_item, null);
			holder.img = (ImageView) convertView.findViewById(R.id.menu_img);
			holder.text = (TextView) convertView.findViewById(R.id.menu_text);
			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (null != menuImgs) {
			
		}
		holder.img.setBackgroundResource(menuImgs[position]);
		holder.text.setText(menuTexts[position]);
		return convertView;
	}

	public class ViewHolder {
		ImageView img;
		TextView text;
	}
}

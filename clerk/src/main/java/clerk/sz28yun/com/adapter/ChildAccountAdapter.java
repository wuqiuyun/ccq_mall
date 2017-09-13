package clerk.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import clerk.sz28yun.com.R;
import clerk.sz28yun.com.bean.ChildAccountBean;
import butterknife.Bind;
import butterknife.ButterKnife;
import per.sue.gear2.adapter.ArrayListAdapter;

/**
 * Created by sue on 2016/11/17.
 */
public class ChildAccountAdapter extends ArrayListAdapter<ChildAccountBean> {


    private OnChildAccountAdapterListener onChildAccountAdapterListener;

    public ChildAccountAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        ChildAccountBean bean = getItem(position);
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_child_account, null);
           viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.nameTv.setText(bean.getMemberTruename());
        viewHolder.editTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onChildAccountAdapterListener){
                    onChildAccountAdapterListener.onClickEdit(bean);
                }
            }
        });
        viewHolder.searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != onChildAccountAdapterListener){
                    onChildAccountAdapterListener.onClickSearch(bean);
                }
            }
        });
        return convertView;
    }

    public void setOnChildAccountAdapterListener(OnChildAccountAdapterListener onChildAccountAdapterListener) {
        this.onChildAccountAdapterListener = onChildAccountAdapterListener;
    }

    static class ViewHolder {
        @Bind(R.id.name_tv)
        TextView nameTv;
        @Bind(R.id.edit_tv)
        TextView editTv;
        @Bind(R.id.search_iv)
        ImageView searchIV;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface  OnChildAccountAdapterListener{

        void onClickEdit(ChildAccountBean bean);
        void onClickSearch(ChildAccountBean bean);
    }
}

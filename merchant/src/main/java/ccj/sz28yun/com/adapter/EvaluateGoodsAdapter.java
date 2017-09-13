package ccj.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.bean.EvaluateGoodsBean;
import ccj.sz28yun.com.bean.EvaluateMerchantBean;
import per.sue.gear2.adapter.ArrayListAdapter;
import per.sue.gear2.controll.GearImageLoad;

/**
 * Created by sue on 2016/12/6.
 */
public class EvaluateGoodsAdapter extends ArrayListAdapter<EvaluateGoodsBean> {

    private EvaluateGoodsAdapterListener evaluateGoodsAdapterListener;

    public EvaluateGoodsAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_evaluate, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        EvaluateGoodsBean bean = getItem(position);
        viewHolder.orderNumTv.setText(String.format("订单号:  %s",  bean.getGevalOrderno()) );
        viewHolder.userNameTv.setText(new StringBuilder("用户: ").append(bean.getGevalFrommembername()));
        viewHolder.evaluateContentTv.setText(new StringBuilder(bean.getIsPosi()));
        viewHolder.evaluateDateTv.setText(bean.getGevalAddtime());
        GearImageLoad.getSingleton(getContext()).load(bean.getImage(), viewHolder.userAvatarIv);

        if(bean.getIsPosi() == 1){
            viewHolder.replyContentTv.setVisibility(View.VISIBLE);
            viewHolder.replyDateTv.setVisibility(View.VISIBLE);
            viewHolder.replyBtn.setVisibility(View.GONE);
            viewHolder.replyContentTv.setText(bean.getPosi());
            viewHolder.replyDateTv.setText("");
        }else{
            viewHolder.replyBtn.setVisibility(View.VISIBLE);
            viewHolder.replyContentTv.setVisibility(View.GONE);
            viewHolder.replyDateTv.setVisibility(View.GONE);
        }
        viewHolder.replyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != evaluateGoodsAdapterListener){
                    evaluateGoodsAdapterListener.onReply(bean, position);
                }

            }
        });

        return convertView;
    }

    public void setEvaluateGoodsAdapterListener(EvaluateGoodsAdapterListener evaluateGoodsAdapterListener) {
        this.evaluateGoodsAdapterListener = evaluateGoodsAdapterListener;
    }

    public interface EvaluateGoodsAdapterListener{

        void  onReply(EvaluateGoodsBean bean, int position);
    }

    static class ViewHolder {
        @Bind(R.id.user_name_tv)
        TextView userNameTv;
        @Bind(R.id.order_num_tv)
        TextView orderNumTv;
        @Bind(R.id.user_avatar_iv)
        ImageView userAvatarIv;
        @Bind(R.id.evaluate_content_tv)
        TextView evaluateContentTv;
        @Bind(R.id.evaluate_date_tv)
        TextView evaluateDateTv;
        @Bind(R.id.reply_content_tv)
        TextView replyContentTv;
        @Bind(R.id.reply_date_tv)
        TextView replyDateTv;
        @Bind(R.id.reply_btn)
        Button replyBtn;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

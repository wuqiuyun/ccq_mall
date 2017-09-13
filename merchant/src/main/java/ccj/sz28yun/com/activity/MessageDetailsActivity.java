package ccj.sz28yun.com.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.CCJActivity;
import ccj.sz28yun.com.bean.MessageBean;
import ccj.sz28yun.com.presenter.MessagePresenter;

/**
 * Created by sue on 2017/1/3.
 */
public class MessageDetailsActivity extends CCJActivity implements MessagePresenter.MessageView {


    @Bind(R.id.type_name_tv)
    TextView typeNameTv;
    @Bind(R.id.content_tv)
    TextView contentTv;
    @Bind(R.id.date_tv)
    TextView dateTv;

    MessageBean bean;

    MessagePresenter messagePresenter;

    public static Intent startIntent(Context context, MessageBean bean) {
        Intent intent;
        intent = new Intent(context, MessageDetailsActivity.class);
        intent.putExtra("MessageBean", bean);
        return intent;
    }

    @Override
    public boolean showBackView() {
        return true;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_message_details;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        bean = (MessageBean)getIntent().getSerializableExtra("MessageBean");

        typeNameTv.setText(bean.getSmtCode());
        contentTv.setText(bean.getSmContent());
        dateTv.setText(bean.getSmAddtime());

        messagePresenter = new MessagePresenter(getActivity(), this);
        if(bean.getRead() == 0){
            messagePresenter.setRead(bean.getSmId());//在详情里面设置已读
        }

    }


    @Override
    public void onSetReaded(String message) {

    }

    @Override
    public void onSuccessRefresh(ArrayList<MessageBean> result) {

    }

    @Override
    public void onSuccessLoadModre(ArrayList<MessageBean> result) {

    }
}

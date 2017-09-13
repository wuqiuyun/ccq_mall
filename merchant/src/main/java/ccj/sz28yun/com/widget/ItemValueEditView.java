package ccj.sz28yun.com.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ccj.sz28yun.com.R;

/**
 * Created by sue on 2017/1/3.
 */
public class ItemValueEditView extends RelativeLayout {



    TextView valueLabelTv;
    TextView valueNameTv;
    EditText valueNameEt;
    TextView valueOptTv;

    private boolean isEditType;
    private boolean canEdit = true;

    private OnValueEditListener onValueEditListener;

    public ItemValueEditView(Context context) {
        super(context);
        loadView(context);
    }

    public ItemValueEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadView(context);
        String label = "label";
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.ItemValueEditView);
        label = a.getString(R.styleable.ItemValueEditView_value_label);
        if(!TextUtils.isEmpty(label)){
            valueLabelTv.setText(label);
        }
        a.recycle();
    }

    public ItemValueEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadView(context);
        String label = "label";
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.ItemValueEditView);
        label = a.getString(R.styleable.ItemValueEditView_value_label);
        if(!TextUtils.isEmpty(label)){
            valueLabelTv.setText(label);
        }
        a.recycle();
    }


    private void loadView(Context context) {
        View.inflate(context, R.layout.view_item_text_edit, this);
        valueLabelTv = (TextView)findViewById(R.id.value_label_tv);
        valueNameTv = (TextView)findViewById(R.id.value_name_tv);
        valueNameEt = (EditText)findViewById(R.id.value_name_et);
        valueOptTv = (TextView)findViewById(R.id.value_opt_tv);

        valueOptTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isEditType){
                    String value = valueNameEt.getText().toString();
                    if(!TextUtils.isEmpty(value)){
                        if(onValueEditListener != null){
                            onValueEditListener.onEditValue(ItemValueEditView.this , value);
                        }
                        setValue(value);
                        isEditType = !isEditType;
                        refreshView();
                    }else{
                        valueNameEt.requestFocus();
                        valueNameEt.setError("请输入内容");
                    }

                }else{
                    isEditType = !isEditType;
                    refreshView();
                }
            }
        });
        refreshView();
    }

    public void setValueLabel(String lable){
        valueLabelTv.setText(lable);
    }

    public void setValue(String value){
        valueNameTv.setText(value);
        valueNameEt.setText(value);
    }

    public void setEditType(boolean editType) {
        isEditType = editType;
    }

    private void refreshView(){
        if(!isEditType){
            valueNameTv.setVisibility(View.VISIBLE);
            valueNameEt.setVisibility(View.GONE);
            valueOptTv.setText("修改");
            valueOptTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        }else{
            valueNameTv.setVisibility(View.GONE);
            valueNameEt.setVisibility(View.VISIBLE);
            valueOptTv.setText("确定");
            valueOptTv.setTextColor(getResources().getColor(R.color.app_blue));
        }

        if(canEdit){
            valueOptTv.setVisibility(View.VISIBLE);
        }else{
            valueOptTv.setVisibility(View.INVISIBLE);
        }

    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
        refreshView();
    }

    public void setOnValueEditListener(OnValueEditListener onValueEditListener) {
        this.onValueEditListener = onValueEditListener;
    }

    public interface OnValueEditListener{

        void onEditValue(ItemValueEditView  view, String content);

    }
}

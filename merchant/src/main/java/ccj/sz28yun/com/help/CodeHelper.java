package ccj.sz28yun.com.help;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import ccj.sz28yun.com.R;


/**
 * Created by SUE on 2016/7/9 0009.
 */
public class CodeHelper {

    private Context context;
    private TextView codeTV;

    public CodeHelper(Context context, TextView codeTV) {
        this.context = context;
        this.codeTV = codeTV;
        enableCodeView();
    }

    private int mCodeTime = 60;
    private boolean canGetCode = true;
    private Handler mHandler = new Handler();
    private Runnable mTimeRun = new Runnable() {
        @Override
        public void run() {
            if (mCodeTime != 0) {
                mCodeTime--;
                String timeFormat = context.getResources().getString(R.string.account_label_time_code);
                codeTV.setText(String.format(timeFormat, mCodeTime));
                mHandler.postDelayed(mTimeRun, 1000);
            } else {
                enableCodeView();
            }
        }
    };


    public void enableCodeView() {
        mHandler.removeCallbacks(mTimeRun);
        codeTV.setText(context.getResources().getText(R.string.account_label_code_get));
        codeTV.setEnabled(true);
        canGetCode = true;
    }

    public void notEnableCodeView(){
        mHandler.postDelayed(mTimeRun, 1000);
        canGetCode = false;
        mCodeTime = 60;
        codeTV.setEnabled(false);
    }

    public boolean canGetCode(){
        return canGetCode;
    }


}

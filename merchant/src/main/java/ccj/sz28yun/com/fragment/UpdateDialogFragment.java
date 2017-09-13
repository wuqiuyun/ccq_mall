package ccj.sz28yun.com.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.base.UpdateManager;
import ccj.sz28yun.com.bean.ApkInfoBean;
import ccj.sz28yun.com.service.DownloadService;
import per.sue.gear2.dialog.GearCustomDialog;


/**
 * Created by langgu on 16/5/20.
 */
public class UpdateDialogFragment extends DialogFragment {


    private ApkInfoBean apkInfoBean;


    public static UpdateDialogFragment newInstance(ApkInfoBean bean) {
        UpdateDialogFragment fragment = new UpdateDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("ApkInfoBean", bean);
        fragment.setArguments(bundle);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        apkInfoBean = (ApkInfoBean) getArguments().getSerializable("ApkInfoBean");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_update_version, null);
        GearCustomDialog dialog = new GearCustomDialog.Builder(getActivity())
                .setContentView(view)
                .setUseDefult(false)
                .create();


        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotification(apkInfoBean.versions, apkInfoBean.url);
                dismiss();

            }
        });
        viewHolder.closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

            }
        });

        StringBuilder desStr = new StringBuilder();
        ArrayList<String> infos = apkInfoBean.versionsInfo;
        if(null != infos){
            for(String str :infos ){
                desStr.append(str).append("\n");
            }
        }
        viewHolder.describeTv.setText(desStr.toString());

//        if(apkInfoBean.isMust()){
//            viewHolder.sureBtn.setVisibility(View.GONE);
//            viewHolder.cancelBtn.setVisibility(View.GONE);
//            viewHolder.numberProgressBar.setVisibility(View.VISIBLE);
//            viewHolder.numberProgressBar.setMax(100);
//            dialog.setCanceledOnTouchOutside(false);
//
//            DataApiFactory.getInstance().createICommonAPI(getActivity()).loadFile(apkInfoBean.getDownloadUrl(), new UIProgressResponseListener() {
//                @Override
//                public void onUIResponseProgress(long bytesRead, long contentLength, boolean done) {
//                    int progress = (int) ((bytesRead * 100) / contentLength);
//                    viewHolder.numberProgressBar.setProgress(progress);
//                }
//            }).subscribe(new Action1<File>() {
//                @Override
//                public void call(File apkFile) {
//                    String[] command = {"chmod","777",apkFile.getAbsolutePath()};
//                    ProcessBuilder builder = new ProcessBuilder(command);
//                    try {
//                        builder.start();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.setDataAndType(Uri.fromFile(apkFile),"application/vnd.android.package-archive");
//                    startActivity(intent);
//                }
//            });
//
//        }
        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }


    /**
     * Show Notification
     */
    public void showNotification(String content, String apkUrl) {
        Notification noti;
        Intent myIntent = new Intent(getActivity(), DownloadService.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.putExtra(UpdateManager.APK_DOWNLOAD_URL, apkUrl);
        PendingIntent pendingIntent = PendingIntent.getService(getActivity(), 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int smallIcon = getActivity().getApplicationInfo().icon;
        noti = new NotificationCompat.Builder(getActivity()).setTicker(getString(R.string.newUpdateAvailable))
                .setContentTitle(getString(R.string.newUpdateAvailable)).setContentText(content).setSmallIcon(smallIcon)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .build();

        noti.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);

        //Intent intent=new Intent(getActivity().getApplicationContext(),DownloadService.class);
        //intent.putExtra(DownloadManager.APK_DOWNLOAD_URL,  apkUrl);
        getActivity().startService(myIntent);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

    }

    static class ViewHolder {
        @Bind(R.id.close_iv)
        ImageView closeIv;
        @Bind(R.id.describe_tv)
        TextView describeTv;
        @Bind(R.id.submit_btn)
        TextView submitBtn;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

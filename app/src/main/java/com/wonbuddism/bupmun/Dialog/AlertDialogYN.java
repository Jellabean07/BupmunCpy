package com.wonbuddism.bupmun.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.wonbuddism.bupmun.Login.LoginMainActivity;

/**
 * Created by user on 2016-02-04.
 */
public class AlertDialogYN {

    private Activity activity;

    public AlertDialogYN(Activity activity) {
        this.activity = activity;
    }

    public void showLoginDialog(){
        AlertDialog.Builder d = new AlertDialog.Builder(activity);
        d.setTitle("로그인");
        d.setMessage("로그인이 필요한 서비스입니다\n로그인 하시겠습니까?");
        // d.setIcon(R.drawable.warnning_off);

        d.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                activity.finish();
                activity.startActivity(new Intent(activity, LoginMainActivity.class));
            }
        });

        d.setNegativeButton("아니요",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        d.show();
    }

    public boolean showExportDialog() {
        final boolean[] answer = {false};
        AlertDialog.Builder d = new AlertDialog.Builder(activity);
        d.setTitle("임시저장");
        d.setMessage("정말 저장 하시겠습니꺄?");

        d.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TypingTempExport();

                Toast.makeText(activity, "저장 되었습니다", Toast.LENGTH_SHORT).show();
                answer[0] = true;
            }
        });

        d.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                answer[0] = false;
            }
        });
        d.show();
        return answer[0];
    }

    public void showExitDialog(){
        AlertDialog.Builder d = new AlertDialog.Builder(activity);
        d.setTitle("법문쓰기 종료");
        d.setMessage("법문쓰기를 끝내시겠습니까?");
        // d.setIcon(R.drawable.warnning_off);

        d.setPositiveButton("예",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                activity.finish();
            }
        });

        d.setNegativeButton("아니요",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        d.show();
    }

    public void deleteDialog(){
        AlertDialog.Builder d = new AlertDialog.Builder(activity);
        d.setTitle("삭제");
        d.setMessage("정말 삭제 하시겠습니꺄?");
        // d.setIcon(R.drawable.warnning_off);

        d.setPositiveButton("예",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                activity.finish();
            }
        });

        d.setNegativeButton("아니요",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        d.show();
    }
}

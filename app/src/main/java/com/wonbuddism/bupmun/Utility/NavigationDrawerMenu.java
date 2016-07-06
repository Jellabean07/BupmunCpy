package com.wonbuddism.bupmun.Utility;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wonbuddism.bupmun.Board.BoardMainActivity;
import com.wonbuddism.bupmun.Login.LoginMainActivity;
import com.wonbuddism.bupmun.Main.MainActivity;
import com.wonbuddism.bupmun.MyFeeling.FeelingMainActivity;
import com.wonbuddism.bupmun.Progress.ProgressMainActivity;
import com.wonbuddism.bupmun.R;
import com.wonbuddism.bupmun.Rank.RankMainActivity;
import com.wonbuddism.bupmun.HttpConnection.HTTPconnVillMainInfo;
import com.wonbuddism.bupmun.Writing.WritingMainActivity;

import java.util.List;

public class NavigationDrawerMenu implements View.OnClickListener{

    private Activity activity;
    private DrawerLayout mDrawerLayout;
    private PrefUserInfoManager manager;
    private SharedPreferences pref;
    private TextView log;

    public NavigationDrawerMenu(Activity activity, DrawerLayout mDrawerLayout) {
        this.activity = activity;
        this.mDrawerLayout = mDrawerLayout;
        manager = new PrefUserInfoManager(activity);
        pref=activity.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
    }

    private void setDrawerHeader(NavigationView navigationView){
        View headerView = navigationView.inflateHeaderView(R.layout.navi_header);
/*        if(manager.getLoginState()){
            headerView = navigationView.inflateHeaderView(R.layout.nav_header);
            log = (TextView)headerView.findViewById(R.id.nav_header_login_out_textview);
        }else{
            headerView = navigationView.inflateHeaderView(R.layout.nav_header_no_login);
            log = (TextView)headerView.findViewById(R.id.nav_header_login_in_textview);
        }

        log.setOnClickListener(this);*/
    }

    public void setupDrawerContent(NavigationView navigationView) {
        setDrawerHeader(navigationView);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //menuItem.setChecked(false);
                        menuItem.setCheckable(false);
                        int pos = menuItem.getItemId();

                        /*현재 액티비티를 구함 복잡복잡*/
                        ActivityManager am = (ActivityManager)activity.getSystemService(Context.ACTIVITY_SERVICE);
                        List<ActivityManager.RunningTaskInfo> Info = am.getRunningTasks(1);
                        ComponentName topActivity = Info.get(0).topActivity;
                        String topactivityname = topActivity.getClassName();

                        Log.e("현재액티비티", topactivityname);
                        if(!topactivityname.equals("com.wonbuddism.bupmun.Main.MainActivity")){
                            if(pos!=R.id.nav_club)
                            activity.finish();
                        }


                        switch (pos) {
                            case R.id.nav_home:
                                activity.finish();
                                if(topactivityname.equals("com.wonbuddism.bupmun.Main.MainActivity")){
                                    activity.startActivity(new Intent(activity, MainActivity.class));
                                }

                                break;
                            case R.id.nav_writing:
                              //  activity.finish();
                                activity.startActivity(new Intent(activity, WritingMainActivity.class));
                                break;
                            case R.id.nav_progress:
                              //  activity.finish();
                                activity.startActivity(new Intent(activity, ProgressMainActivity.class));
                                break;
                            case R.id.nav_setting:
                                Toast.makeText(activity, "서비스 준비중", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                if(manager.getLoginState()){
                                    //activity.finish();
                                    switch (pos){
                                        case R.id.nav_board:
                                            activity.startActivity(new Intent(activity, BoardMainActivity.class));
                                            break;
                                        case R.id.nav_feeling:
                                            activity.startActivity(new Intent(activity, FeelingMainActivity.class));
                                            break;
                                        case R.id.nav_club:
                                            Dialog dialog = new ProgressWaitDaialog(activity);
                                            dialog.show();
                                            new HTTPconnVillMainInfo(activity,dialog).execute();
                                           // activity.startActivity(new Intent(activity, VillageMainActivity.class));
                                            break;
                                        case R.id.nav_ranking:

                                            activity.startActivity(new Intent(activity, RankMainActivity.class));
                                            break;
                                        /*case R.id.nav_logout:
                                            //new HTTPconnectionLogout(activity).execute();
                                            break;*/

                                    }
                                }else{
                                    getDialog();
                                }
                                break;
                        }

                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    public void getDialog(){
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

        d.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        d.show();
    }

    @Override
    public void onClick(View v) {
        int pos = v.getId();
/*        switch (pos){
            case R.id.nav_header_login_in_textview:
                activity.finish();
                activity.startActivity(new Intent(activity, LoginMainActivity.class));
                break;
            case R.id.nav_header_login_out_textview:
                Toast.makeText(activity, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                PrefUserInfo userInfo = new PrefUserInfo();
                new PrefUserInfoManager(activity).setUserInfo(userInfo);
                new PrefUserInfoManager(activity).setModeManager(false);
                new PrefUserInfoManager(activity).setLoginState(false);

                activity.finish();
                activity.startActivity(new Intent(activity, BeginningActivity.class));
                break;
        }*/
    }
}

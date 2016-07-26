package com.wonbuddism.bupmun.Listener;

import com.wonbuddism.bupmun.DataVo.HttpResultProgress;

import java.util.ArrayList;

/**
 * Created by csc-pc on 2016. 7. 25..
 */
public interface ProgressReponseListener {
    void HttpResponse(ArrayList<HttpResultProgress> httpResults);
}

package com.wonbuddism.bupmun.Database;

import android.content.Context;


import com.wonbuddism.bupmun.Database.DTO.Board.BoardDTO;

import java.util.concurrent.ExecutionException;

/**
 * Created by user on 2016-01-09.
 */
public class BupmunDAO {
    private DBConnection connection;

    public BupmunDAO(Context context) {
        connection = new DBConnection(context);
    }

    public Object boardList(BoardDTO dto) throws InterruptedException, ExecutionException { // success 1  , fail -1 , default 0
        connection.execute(dto);
        return connection.get();
    }

}

package com.wonbuddism.bupmun.Database.Function;

import android.content.Context;

import com.wonbuddism.bupmun.Database.DTO.Board.BoardDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by user on 2016-01-09.
 */
public class BoardProc {
    private Connection connection = null;
    private Statement st;
    private ResultSet rs;
    private Context context;

    public BoardProc(Connection connection, Context context) {
        this.connection = connection;
        this.context = context;
    }


    public BoardDTO selectList(String query,BoardDTO dto){ // 로그인 메소드
        try {
            Statement st = connection.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()){
                dto.setIndex( rs.getInt("WRITENO"));
                dto.setTitle(rs.getString("TITLE"));
                dto.setId(rs.getString("USERID"));
                dto.setDate(rs.getTimestamp("WRITETIME"));
                dto.setHit(rs.getInt("READCNT"));
                dto.setCategory(rs.getInt("BOARDNO"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dto;
    }

}

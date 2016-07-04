package com.wonbuddism.bupmun.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.wonbuddism.bupmun.Database.DTO.Board.BoardDTO;
import com.wonbuddism.bupmun.Database.DTO.SuperDTO;
import com.wonbuddism.bupmun.Database.Function.BoardProc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by user on 2016-01-09.
 */
public class DBConnection extends AsyncTask<SuperDTO,Void,Object> {

    private String serverIP = "115.91.196.143";
    private final String DB_USERID = "ta_type";
    private final String DB_PASSWORD = "ta_type";
    private String DATABASENAME = "BUPMUN";
    private String DBPORT = "60002";
    private String connectionURL = null;
    private Connection connection = null;
    private SuperDTO dto;

    private Statement st;
    private ResultSet rs;
    private Context context;

    public DBConnection(Context context){
        this.context = context;
        /*SharedPreferences pref = context.getSharedPreferences("ServerSetting", context.MODE_PRIVATE);
        serverIP = pref.getString("serverip","115.91.196.143"); // 서버IP를 입력을 하지않았을때 디폴트로 메일로받은 서버IP를 명시해두었습니다.*/
    }

    @Override
    protected Object doInBackground(SuperDTO... params) { // 백그라운드로 init 메소드를 호출하여 DB접속을 하는 메소드
        dto = params[0];
        init();
        Object result = identifyDTO(dto);
        return result;
    }

    private Object identifyDTO(SuperDTO dto) { // 매개변수로 넘긴 Object(DTO)를 구별하는 메소드
        Object result = 0;
        if(dto instanceof BoardDTO){ // 게시판
            BoardDTO boardDTO = (BoardDTO)dto;
            BoardProc boardProc = new BoardProc(connection,context);
            boardDTO = boardProc.selectList(dto.getQuery()[0],boardDTO);
            result=boardDTO;

        }

        return result;
    }

    public int selectLogin(String query){ // 로그인 메소드
        int result = -1;
        try {
            Statement st = connection.createStatement();
            rs = st.executeQuery(query);
            if(rs.next()){
                result = rs.getInt("ID_USED");
                String name = rs.getString("WORKER_NAME");
                String id = rs.getString("WORKER_ID");
                String working_region = rs.getString("WORKING_REGION"); // 1111 , 1101 , 1100, 1000
                Log.d("asdasd",working_region);
                if(result == 0) {

                    SharedPreferences pref = context.getSharedPreferences("userdata", context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("working_region", working_region);
                    editor.putString("username", name);
                    editor.putString("userid",id);
                    editor.commit();
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public int select_Function(String query){ // 함수를 호출하는 메소드
        int result = 0;
        try{
            Statement st = connection.createStatement();
            rs = st.executeQuery(query);
            if(rs.next()) {
                result = rs.getInt("result");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }


    public int insert(String query){  // DB에 insert 하는 메소드
        int result = 0;
        Log.i("asdasd","query="+query);
        try {
            Statement st = connection.createStatement();
            result = st.executeUpdate(query);
            Log.d("asdasd","insert="+result);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public int update(String query){  // DB에 update 하는 메소드
        int result = 0;
        Log.i("asdasd","query="+query);
        try {
            Statement st = connection.createStatement();
            result = st.executeUpdate(query);
            Log.d("asdasd","update="+result);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public void init(){ // DB접속 메소드
        try {
            //Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            //connection = DriverManager.getConnection("jdbc:jtds:sqlserver://" + serverIP + "/" + DATABASENAME, DB_USERID, DB_PASSWORD);
            //com.ibm.db2.jcc.DB2Driver
            //COM.ibm.db2.jdbc.net.DB2Driver
            //conn = DriverManager.getConnection("jdbc:db2://서버주소:포트번호/디비이름", "계정아이디", "비밀번호");
            Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
            String url = "jdbc:db2://115.91.196.143:60002/BUPMUN";
            connectionURL =  "jdbc:db2://"+serverIP+":"+DBPORT+"/"+DATABASENAME;
            connection = DriverManager.getConnection(connectionURL, DB_USERID, DB_PASSWORD);

            if (connection != null) {
                Log.i("connection", "connection is not null");
            }
            st = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

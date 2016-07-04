package com.wonbuddism.bupmun.Progress;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wonbuddism.bupmun.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2015-12-28.
 */
public class ProgressTitleTopRankAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<HashMap<String,String>> mList;
    private ArrayList<String> content;
    private HashMap<String,ArrayList<Pair<String,String>>> contentData;
    private ArrayList<Pair<String,String>> contents;
    private String pKey;

    public ProgressTitleTopRankAdapter(Context context, String pKey) {
        this.context = context;
        this.contents = getContentData(pKey);
        this.pKey = pKey;
    }

    @Override
    public int getCount() {
        return contents.size();
    }

    @Override
    public Object getItem(int position) {
        return contents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView title    = null;
        TextView content    = null;
        CustomHolder    holder  = null;

        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_progress_title_toprank, parent, false);
            title = (TextView) convertView.findViewById(R.id.item_title_top_title);
            content = (TextView) convertView.findViewById(R.id.item_title_top_content);

            // 홀더 생성 및 Tag로 등록
            holder = new CustomHolder();
            holder.title_TextView = title;
            holder.content_TextView = content;
            convertView.setTag(holder);
        }else{
            holder  = (CustomHolder) convertView.getTag();
            title   = holder.title_TextView;
            content = holder.content_TextView;

        }
//        String key = contentData.get(pKey).get(position).first;
        String key = contents.get(position).first;
        /*String value =  contentData.get(pKey).get(position).second;*/
        String value = contents.get(position).second;
        title.setText(key);
        content.setText(value);


        return convertView;
    }

    // 외부에서 아이템 추가 요청 시 사용
/*    public void add(String _msg) {
        mList.add(_msg);
    }*/

    // 외부에서 아이템 삭제 요청 시 사용
    public void remove(int _position) {
        mList.remove(_position);
    }



    //외부에서 아이템 초기화 요청
    public void clear(){
        mList.clear();
    }


  /*  public HashMap<String,ArrayList<Pair<String,String>>> getContentData(){
        HashMap<String,ArrayList<Pair<String,String>>> data=new HashMap<>();
        ArrayList<Pair<String,String>> contentList = new ArrayList<>();
        contentList.add(Pair.create("제목1", "안녕하세요1"));
        contentList.add(Pair.create("내용1", "반가워요1"));
        contentList.add(Pair.create("더할말1", "업성용1"));
        data.put("정전", contentList);
        contentList.clear();
        contentList.add(Pair.create("제목2", "안녕하세요2"));
        contentList.add(Pair.create("내용2", "반가워요2"));
        contentList.add(Pair.create("더할말2", "업성용2"));
        data.put("대종경",contentList);
        contentList.clear();
        contentList.add(Pair.create("제목3", "안녕하세요3"));
        contentList.add(Pair.create("내용3", "반가워요3"));
        contentList.add(Pair.create("더할말3", "업성용3"));
        data.put("불조요경",contentList);
        contentList.clear();
        contentList.add(Pair.create("제목4", "안녕하세요3"));
        contentList.add(Pair.create("내용4", "반가워요3"));
        contentList.add(Pair.create("더할말4", "업성용3"));
        data.put("예전",contentList);
        contentList.clear();
        contentList.add(Pair.create("제목5", "안녕하세요4"));
        contentList.add(Pair.create("내용5", "반가워요4"));
        contentList.add(Pair.create("더할말5", "업성용4"));
        data.put("정산종사법어",contentList);
        contentList.clear();
        contentList.add(Pair.create("제목6", "안녕하세요5"));
        contentList.add(Pair.create("내용6", "반가워요5"));
        contentList.add(Pair.create("더할말6", "업성용5"));
        data.put("대산종사법어",contentList);
        contentList.clear();
        contentList.add(Pair.create("제목7", "안녕하세요7"));
        contentList.add(Pair.create("내용7", "반가워요7"));
        contentList.add(Pair.create("더할말7", "업성용7"));
        data.put("원불교교사",contentList);
        return data;
    }*/

    public void contentChange(String title){
        this.pKey = title;
        this.contents = getContentData(pKey);
    }

    private ArrayList<Pair<String,String>> getContentData(String pKey){
        ArrayList<Pair<String,String>> list = new ArrayList<>();
        if(pKey.equals("정전")){

            list.add( Pair.create("개요",context.getResources().getString(R.string.info_content1_title1)));
            list.add( Pair.create("결집과정",context.getResources().getString(R.string.info_content2_title1)));
            list.add( Pair.create("구성내용",context.getResources().getString(R.string.info_content3_title1)));
            list.add( Pair.create("의의",context.getResources().getString(R.string.info_content4_title1)));
        }else if(pKey.equals("대종경")){
            list.add( Pair.create("개요",context.getResources().getString(R.string.info_content1_title2)));
            list.add( Pair.create("내용과 특징",context.getResources().getString(R.string.info_content2_title2)));
            list.add( Pair.create("대종경의 형성과정",context.getResources().getString(R.string.info_content3_title2)));
            list.add( Pair.create("대종경 자료의 출처",context.getResources().getString(R.string.info_content4_title2)));
            list.add( Pair.create("대종경의 의의",context.getResources().getString(R.string.info_content5_title2)));
        }else if(pKey.equals("불조요경")){
            list.add( Pair.create("개요",context.getResources().getString(R.string.info_content1_title3)));
            list.add( Pair.create("불조요경 결집과정",context.getResources().getString(R.string.info_content2_title3)));
            list.add( Pair.create("원불교 교리와의 상관성",context.getResources().getString(R.string.info_content3_title3)));
            list.add( Pair.create("볼조요경의 의의",context.getResources().getString(R.string.info_content4_title3)));
        }else if(pKey.equals("예전")){
            list.add( Pair.create("개요",context.getResources().getString(R.string.info_content1_title4)));
            list.add( Pair.create("편찬의 사상적 배경",context.getResources().getString(R.string.info_content2_title4)));
            list.add( Pair.create("예전의 편수 과정",context.getResources().getString(R.string.info_content3_title4)));
            list.add( Pair.create("예전의 사회적 성격",context.getResources().getString(R.string.info_content4_title4)));


        }else if(pKey.equals("정산종사법어")){
            list.add( Pair.create("개요",context.getResources().getString(R.string.info_content1_title5)));
            list.add( Pair.create("편찬과정",context.getResources().getString(R.string.info_content2_title5)));
            list.add( Pair.create("세전의 구성",context.getResources().getString(R.string.info_content3_title5)));
            list.add( Pair.create("법어의 구성",context.getResources().getString(R.string.info_content4_title5)));

        }else if(pKey.equals("대산종사법어")){
            list.add( Pair.create("제목",context.getResources().getString(R.string.info_content1_title6)));
            list.add( Pair.create("내용",context.getResources().getString(R.string.info_content2_title6)));
            list.add( Pair.create("추가내용",context.getResources().getString(R.string.info_content3_title6)));
        }else if(pKey.equals("원불교교사")){
            list.add( Pair.create("개요",context.getResources().getString(R.string.info_content1_title7)));
            list.add( Pair.create("편찬과정",context.getResources().getString(R.string.info_content2_title7)));
            list.add( Pair.create("내용구성",context.getResources().getString(R.string.info_content3_title7)));
            list.add( Pair.create("편찬의의",context.getResources().getString(R.string.info_content4_title7)));
        }

        return list;
    }
    private class CustomHolder {
        TextView    title_TextView;
        TextView    content_TextView;
    }
}

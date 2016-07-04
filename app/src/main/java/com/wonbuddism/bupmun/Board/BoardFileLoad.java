package com.wonbuddism.bupmun.Board;


import java.io.File;
import java.io.FilenameFilter;

public class BoardFileLoad {


    private String[] getTitleList() //알아 보기 쉽게 메소드 부터 시작합니다.
    {
        try
        {
            FilenameFilter fileFilter = new FilenameFilter()  //이부분은 특정 확장자만 가지고 오고 싶을 경우 사용하시면 됩니다.
            {
                public boolean accept(File dir, String name)
                {
                    return name.endsWith("txt"); //이 부분에 사용하고 싶은 확장자를 넣으시면 됩니다.
                } //end accept
            };
            File file = new File("/sdcard/"); //경로를 SD카드로 잡은거고 그 안에 있는 A폴더 입니다. 입맛에 따라 바꾸세요.
            File[] files = file.listFiles(fileFilter); //위에 만들어 두신 필터를 넣으세요. 만약 필요치 않으시면 fileFilter를 지우세요.
            String [] titleList = new String [files.length]; //파일이 있는 만큼 어레이 생성했구요
            for(int i = 0;i < files.length;i++)
            {
                titleList[i] = files[i].getName();	//루프로 돌면서 어레이에 하나씩 집어 넣습니다.
            }//end for
            return titleList;
        } catch( Exception e )
        {
            return null;
        }//end catch()
    }

}

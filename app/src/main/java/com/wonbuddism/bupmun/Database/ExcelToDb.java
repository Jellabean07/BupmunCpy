package com.wonbuddism.bupmun.Database;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;

public class ExcelToDb {
    private Context context;
    private Activity activity;
    private DbAdapter dbAdapter;
    private static final String TAG = "DbAdapter2";

    public ExcelToDb() {
    }

    public ExcelToDb(Activity activity) {
        this.activity = activity;
        this.dbAdapter = new DbAdapter(activity);
    }

    public String replaceChinese(String words){
        String regex = "\\((.*?)\\)";
        return words.replaceAll(regex, "");
    }

    public void copyExcelDataToDatabase() {
        Log.w("ExcelToDatabase", "copyExcelDataToDatabase()");

        Workbook workbook = null;
        Sheet sheet = null;

        try {
            InputStream is = activity.getAssets().open("bupmun.xls");
            workbook = Workbook.getWorkbook(is);

            if (workbook != null) {
                sheet = workbook.getSheet(0);

                if (sheet != null) {

                    int nMaxColumn = 2;
                    int nRowStartIndex = 0;
                    int nRowEndIndex = sheet.getColumn(nMaxColumn - 1).length - 1;
                    int nColumnStartIndex = 0;
                    int nColumnEndIndex = sheet.getRow(2).length - 1;

                    dbAdapter.open();
                    dbAdapter.beginTransaction();
                    try {
                        for (int nRow = nRowStartIndex + 1; nRow <= nRowEndIndex; nRow++) {
                            //  Log.e("IDX",nRow+"");
                            String BUPMUNINDEX = sheet.getCell(nColumnStartIndex, nRow).getContents();
                            String TITLE1 = sheet.getCell(nColumnStartIndex + 1, nRow).getContents();
                            String TITLE2 = sheet.getCell(nColumnStartIndex + 2, nRow).getContents();
                            String TITLE3 = sheet.getCell(nColumnStartIndex + 3, nRow).getContents();
                            String TITLE4 = sheet.getCell(nColumnStartIndex + 4, nRow).getContents();
                            String BUPMUNSORT = sheet.getCell(nColumnStartIndex + 5, nRow).getContents();
                            String CONTENTS = sheet.getCell(nColumnStartIndex + 6, nRow).getContents();
                            String SHORTTITLE = sheet.getCell(nColumnStartIndex + 7, nRow).getContents();
                            String PARAGRAPH_CNT = sheet.getCell(nColumnStartIndex + 8, nRow).getContents();
                            String CHINESE_HELP = sheet.getCell(nColumnStartIndex + 9, nRow).getContents();
                            String CONTENTS_KOR = sheet.getCell(nColumnStartIndex + 6, nRow).getContents();

                            BUPMUN_TYPING_INDEX dto = new BUPMUN_TYPING_INDEX(BUPMUNINDEX, replaceChinese(TITLE1), replaceChinese(TITLE2), replaceChinese(TITLE3), replaceChinese(TITLE4), BUPMUNSORT,
                                    CONTENTS.replace("·",".").replace("①", "1.").replace("②","2.").
                                            replace("③","3.").replace("④","4.").replace("⑤","5.").replace("⑥","6.").
                                            replace("⑦","7.").replace("⑧","8.").replace("⑨","9.").replace("⑩","10.").
                                            replace("⑪","11.").replace("⑫","12."), SHORTTITLE, Integer.parseInt(PARAGRAPH_CNT), CHINESE_HELP, replaceChinese(CONTENTS_KOR));

                            dbAdapter.createNote_bupmun_typing_index(dto);

                        }
                        dbAdapter.setTransaction();
                    } finally {
                        dbAdapter.endTransaction();
                    }
                    dbAdapter.close();
                } else {
                    System.out.println("Sheet is null!!");
                }
            } else {
                System.out.println("WorkBook is null!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }
}

package com.warcraft_game.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.warcraft_game.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryRecordActitvty extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_record);
        ListView lv = (ListView)findViewById(R.id.history_record_lv);
        List list = new ArrayList<Map<String, String>>();

        try {
            Map<String, String> map = null;
            File file = new File(getCacheDir(), "record");
            if(file.exists()) {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

                String line;
                while (null != (line = br.readLine())) {
                    map = new HashMap<String, String>();
                    String[] param = line.split("&");

                    String score = "", useTime = "", date = "";

                    for (int i = 0; i < param.length; i++) {
                        String[] arr = param[i].split("=");
                        if ("score".equals(arr[0])) {
                            score = arr[1];
                        } else if ("useTime".equals(arr[0])) {
                            useTime = arr[1];
                        } else if ("date".equals(arr[0])) {
                            date = arr[1];
                        }
                    }
                    map.put("score", "得分：    " + score + "\n用时：    " + useTime);
                    map.put("date", date);
                    list.add(map);
                }
                br.close();
            }else{
                map = new HashMap<String, String>();
                map.put("score", "暂无记录");
                map.put("date", "");
                list.add(map);
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        SimpleAdapter adapter = new SimpleAdapter(this,
                list,
                R.layout.row_layout,
                new String[]{"score", "date"},
                new int[]{R.id.score, R.id.date}
        );

        lv.setAdapter(adapter);
    }
}

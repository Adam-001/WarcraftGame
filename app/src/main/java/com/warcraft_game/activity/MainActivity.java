package com.warcraft_game.activity;
/////////////////////////////////////////////////
//                   _ooOoo_                   //
//                  o8888888o                  //
//                  88" . "88                  //
//                  (| -_- |)                  //
//                  O\  =  /O                  //
//               ____/`---'\____               //
//             .'  \\|     |//  `.             //
//            /  \\|||  :  |||//  \            //
//           /  _||||| -:- |||||-  \           //
//           |   | \\\  -  /// |   |           //
//           | \_|  ''\---/''  |   |           //
//           \  .-\__  `-`  ___/-. /           //
//         ___`. .'  /--.--\  `. . __          //
//      ."" '<  `.___\_<|>_/___.'  >'"".       //
//     | | :  `- \`.;`\ _ /`;.`/ - ` : | |     //
//     \  \ `-.   \_ __\ /__ _/   .-` /  /     //
//======`-.____`-.___\_____/___.-`____.-'======//
//                   `=---='                   //
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^//
//         佛祖保佑       永无BUG               //
/////////////////////////////////////////////////



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.warcraft_game.R;

/**
 * 主Activity
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_layout);
        findViewById(R.id.begin_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });
        findViewById(R.id.history_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHistoryRecord();
            }
        });

        Log.d("create\n","\n"+
                "/////////////////////////////////////////////////\n" +
                "//                   _ooOoo_                   //\n" +
                "//                  o8888888o                  //\n" +
                "//                  88\" . \"88                  //\n" +
                "//                  (| -_- |)                  //\n" +
                "//                  O\\  =  /O                  //\n" +
                "//               ____/`---'\\____               //\n" +
                "//             .'  \\\\|     |//  `.             //\n" +
                "//            /  \\\\|||  :  |||//  \\            //\n" +
                "//           /  _||||| -:- |||||-  \\           //\n" +
                "//           |   | \\\\\\  -  /// |   |           //\n" +
                "//           | \\_|  ''\\---/''  |   |           //\n" +
                "//           \\  .-\\__  `-`  ___/-. /           //\n" +
                "//         ___`. .'  /--.--\\  `. . __          //\n" +
                "//      .\"\" '<  `.___\\_<|>_/___.'  >'\"\".       //\n" +
                "//     | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |     //\n" +
                "//     \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /     //\n" +
                "//======`-.____`-.___\\_____/___.-`____.-'======//\n" +
                "//                   `=---='                   //\n" +
                "//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^//\n" +
                "//         佛祖保佑       永无BUG               //\n" +
                "/////////////////////////////////////////////////");
    }

    /**
     * 启动游戏
     */
    private void startGame() {
        Intent intent = new Intent();
        intent.setClass(this, Game.class);
        startActivity(intent);
    }

    /**
     * 显示历史战绩
     */
    private void showHistoryRecord() {

        Intent intent = new Intent();
        intent.setClass(this, HistoryRecordActitvty.class);
        startActivity(intent);
    }
}

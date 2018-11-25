package com.example.lovejackknife.dbsumple;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    Cursor c;
    MySqlite helper;

    EditText editText1;
    EditText editText2;
    EditText editText3;
    Button button;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //下記2行はデータベースを作る用の文章
        helper = new MySqlite(this);
        db = helper.getReadableDatabase();

        accDB();
        editText1 = findViewById(R.id.edit_text1);
        editText2 = findViewById(R.id.edit_text2);
        editText3 = findViewById(R.id.edit_text3);
        button = findViewById(R.id.button1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //edittextの読み込みと変換
                String text1 = editText1.getText().toString();
                String text2 = editText2.getText().toString();
                double text2_1 = Double.valueOf(text2);
                String text3 = editText3.getText().toString();
                double text3_1 = Double.valueOf(text3);

                //DBをMySqliteにsetその後insertDate実行後表示用メゾット実行
                helper.setDB(db);
                helper.insertData(text1,text2_1,text3_1);
                accDB();

                //androidのインターフェースの変更
                //上からソフトキーボードの削除・フォーカスをボタンにチェンジ・editTextの中身削除
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                button.setFocusable(true);
                button.setFocusableInTouchMode(true);
                button.requestFocus();
                editText1.getEditableText().clear();
                editText2.getEditableText().clear();
                editText3.getEditableText().clear();
            }
        });
    }
    private void accDB() {
        // 処理
        c = db.query("locations",
                new String[] { "_id", "number", "latitude", "longitude" },
                null, null, null, null, null);

        startManagingCursor(c); //リソースの扱いをActivityに委ねます
        c.moveToFirst();
        ListAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.list_item, c,
                new String[] {"number", "latitude", "longitude"},
                new int[] {R.id.number, R.id.latitude, R.id.longitude });
        ListView lv = (ListView)findViewById(R.id.list);
        lv.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
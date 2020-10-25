package com.example.a201019;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private  MyDB db = null;

    private final static String _ID   = "_id";
    private final static String NAME  = "name";
    private final static String PRICE = "price";

    Button badd , bedit, bdel , bclear;
    EditText edit_name , edit_price;
    ListView LV;
    Cursor cursor;
    long myid; //儲存_id值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_name = findViewById(R.id.edit_name);
        edit_price= findViewById(R.id.edit_price);
        LV =  findViewById(R.id.LV);
        badd = findViewById(R.id.add);
        bedit= findViewById(R.id.edit);
        bdel = findViewById(R.id.delete);
        bclear= findViewById(R.id.clear);

        badd.setOnClickListener(listener);
        bedit.setOnClickListener(listener);
        bdel.setOnClickListener(listener);
        bclear.setOnClickListener(listener);
        LV.setOnClickListener(listener);

        db = new MyDB(this);
        db.open();
        cursor = db.getAll();
        UpdateAdapter(cursor);
    }

    private LV.OnItemClickListener LVlistener = new LV.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> parent, View v,int position,long ID)
        {
            ShowData(ID);
            cursor.moveToFirst(position);
        }
    };

    private void ShowData(long id)
    {
        Cursor c = db.get(id);
        myid = id;
        edit_name.setText(c.getString(1)); //name
        edit_price.setText(""+c.getInt(2)); //price
    }

    protected void onDestory(){
        super.onDestroy();
        db.close();
    }

    private Button.OnClickListener listener = new Button.OnClickListener(){
        public void onClick(View v){
            try {
                switch (v.getId()){
                    case R.id.add: {    //新增
                        int price = Integer.parseInt(edit_price.getText().toString());
                        String name = edit_name.getText().toString();
                        if (db.append(name, price) > 0) {
                            cursor = db.getAll();
                            UpdateAdapter(cursor);
                            ClearEdit();
                        }
                        break; }
                    case R.id.edit:{    //修改
                        int price = Integer.parseInt(edit_price.getText().toString());
                        String name = edit_name.getText().toString();
                        if(db.update(myid,name,price))
                        {
                            cursor = db.getAll();
                            UpdateAdapter(cursor);
                        }
                        break; }
                    case R.id.delete:{
                        if(cursor != null && cursor.getCount() >= 0){
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("確定刪除");
                            builder.setMessage("確定要刪除" + edit_name.getText() + "這筆資料?");
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (db.delete(myid)){
                                        cursor = db.getAll();
                                        UpdateAdapter(cursor);
                                        ClearEdit();
                                    }
                                }
                            });
                            builder.show();
                        }
                        break;}
                    case R.id.clear:{

                    }

                }

            }
        }
    }
}
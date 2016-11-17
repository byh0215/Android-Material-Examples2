package com.saulmm.material.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.Toast;

import com.saulmm.material.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.AdapterView.OnItemClickListener;
import static android.widget.AdapterView.OnItemSelectedListener;


public class InformationActivity extends Activity
{
    private String[] names;
    private String[] descs;
    private int[] imageIds;
    private String uac;
    SQLiteDatabase db=null;
    int flag;
    public void setValues(String na,String pw,String po){
        names = new String[]
                { "昵称", "密码", "地址"};
        descs = new String[]
                { na, pw,po};
        imageIds = new int[]
                {R.drawable.ic_launcher , R.drawable.ic_launcher,R.drawable.ic_launcher};
    }

    public void customView()
    {
        // 装载xml界面布局文件
        TableLayout nachangeForm = (TableLayout)getLayoutInflater()
                .inflate( R.layout.changena, null);
        TableLayout pwchangeForm = (TableLayout)getLayoutInflater()
                .inflate( R.layout.changepw, null);
        TableLayout pochangeForm = (TableLayout)getLayoutInflater()
                .inflate( R.layout.changepo, null);
        final EditText Ena=(EditText)nachangeForm.findViewById(R.id.new_name);
        final EditText Epw=(EditText)pwchangeForm.findViewById(R.id.new_pw);
        final EditText Epo=(EditText)pochangeForm.findViewById(R.id.new_po);

        switch(flag){
            case 0:
                new AlertDialog.Builder(this)
                        // 设置对话框的图标
                        .setIcon(R.drawable.ic_launcher)
                        // 设置对话框的标题
                        .setTitle("请输入新的昵称")
                        // 设置对话框显示的View对象
                        .setView(nachangeForm)
                        // 为对话框设置一个“确定”按钮
                        .setPositiveButton("确认修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                String name=Ena.getText().toString().trim();
                                alterString(flag,name);
                                Intent i = new Intent( );
                                i.setClass(getBaseContext(),InformationActivity.class);
                                i.putExtra("account",uac);
                                startActivity(i);
                                InformationActivity.this.finish();
                            }})
                        // 为对话框设置一个“取消”按钮
                        .setNegativeButton("取消", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {
                                // 取消登录，不做任何事情
                            }
                        })
                        // 创建并显示对话框
                        .create()
                        .show();
                break;
            case 1:
                new AlertDialog.Builder(this)
                        // 设置对话框的图标
                        .setIcon(R.drawable.ic_launcher)
                        // 设置对话框的标题
                        .setTitle("请输入新的密码")
                        // 设置对话框显示的View对象
                        .setView(pwchangeForm)
                        // 为对话框设置一个“确定”按钮
                        .setPositiveButton("确认修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                String pw=Epw.getText().toString().trim();
                                alterString(flag,pw);
                                Intent i = new Intent( );
                                i.setClass(getBaseContext(),InformationActivity.class);
                                i.putExtra("account",uac);
                                startActivity(i);
                                InformationActivity.this.finish();
                            }})
                        // 为对话框设置一个“取消”按钮
                        .setNegativeButton("取消", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {
                                // 取消登录，不做任何事情
                            }
                        })
                        // 创建并显示对话框
                        .create()
                        .show();
                break;
            case 2:
                new AlertDialog.Builder(this)
                        // 设置对话框的图标
                        .setIcon(R.drawable.ic_launcher)
                        // 设置对话框的标题
                        .setTitle("请输入新的地址")
                        // 设置对话框显示的View对象
                        .setView(pochangeForm)
                        // 为对话框设置一个“确定”按钮
                        .setPositiveButton("确认修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                String po=Epo.getText().toString().trim();
                                alterString(flag,po);
                                Intent i = new Intent( );
                                i.setClass(getBaseContext(),InformationActivity.class);
                                i.putExtra("account",uac);
                                startActivity(i);
                                InformationActivity.this.finish();
                            }})
                        // 为对话框设置一个“取消”按钮
                        .setNegativeButton("取消", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {
                                // 取消登录，不做任何事情
                            }
                        })
                        // 创建并显示对话框
                        .create()
                        .show();
                break;
        }

    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        String Strna="";
        String Strpw="";
        String Strpo="";
        db = openOrCreateDatabase("test3.db", Context.MODE_PRIVATE, null);
        Intent intent=getIntent();
        Bundle bundle =intent.getExtras();
        uac=bundle.getString("account");
        Cursor cna = db.rawQuery("select name from user where account= ?", new String[]{uac});
        Cursor cpw = db.rawQuery("select password from user where account= ?", new String[]{uac});
        Cursor cpo = db.rawQuery("select position from user where account= ?", new String[]{uac});
        while(cna.moveToNext()){Strna=cna.getString(0);}
        while(cpw.moveToNext()){Strpw=cpw.getString(0);}
        while(cpo.moveToNext()){Strpo=cpo.getString(0);}
        setValues(Strna,Strpw,Strpo);
        // 创建一个List集合，List集合的元素是Map
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < names.length; i++)
        {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("header", imageIds[i]);
            listItem.put("personName", names[i]);
            listItem.put("desc", descs[i]);
            listItems.add(listItem);
        }
        // 创建一个SimpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
                R.layout.simple_item,
                new String[] { "personName", "header" , "desc"},
                new int[] { R.id.name, R.id.header , R.id.desc });
        ListView list = (ListView) findViewById(R.id.mylist);
        // 为ListView设置Adapter
        list.setAdapter(simpleAdapter);

        // 为ListView的列表项的单击事件绑定事件监听器
        list.setOnItemClickListener(new OnItemClickListener()
        {
            // 第position项被单击时激发该方法
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                System.out.println(names[position] +"被单击了");
                flag=position;
                customView();
            }
        });

        // 为ListView的列表项的选中事件绑定事件监听器
        list.setOnItemSelectedListener(new OnItemSelectedListener()
        {
            // 第position项被选中时激发该方法
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                System.out.println(names[position]
                        + "被选中了");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

    }
    public void alterString(int tag,String message) {
        String col="";
        switch(tag){
            case 0:
                col="name";
                break;
            case 1:
                col="password";
                break;
            case 2:
                col="position";
                break;
        }
        Cursor cursor = db.rawQuery("select ? from user where account= ?",new String[]{col,uac});
        cursor.moveToFirst();
        ContentValues values = new ContentValues();
        values.put(col,message);//key为字段名，value为值
        db.update("user", values, "account= ?", new String[]{uac});
        String q="";
        Cursor a = db.rawQuery("select name from user where account= ?", new String[]{uac});
        while(a.moveToNext()){q=a.getString(0);}
        Toast.makeText(InformationActivity.this,q, Toast.LENGTH_SHORT).show();
    }
}
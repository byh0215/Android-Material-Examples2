package com.saulmm.material.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.saulmm.material.R;
import com.saulmm.material.User;
import com.saulmm.material.HttpUtils;

public class IndexActivity extends Activity {
    SQLiteDatabase db=null;
    EditText uac;
    EditText upw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        //打开或创建test.db数据库
        try {
            db = openOrCreateDatabase("test3.db", Context.MODE_PRIVATE, null);
            //创建user数据表
            db.execSQL("CREATE TABLE user (_id INTEGER PRIMARY KEY AUTOINCREMENT, account VARCHAR, password VARCHAR, name VARCHAR, img VARCHAR, summary VARCHAR, position VARCHAR)");
            //创建管理员账号
            User admin = new User("admin", "123", "飞翔的企鹅", "###", "我要飞的更高", "河北师大软件学院");
            //插入数据
            db.execSQL("INSERT INTO user VALUES (NULL, ?, ?, ?, ?, ?, ?)", new Object[]{admin.getAccount(), admin.getPassword(), admin.getName(), admin.getImg(), admin.getSummary(), admin.getPosition()});
        }catch (SQLException sql){
            User admin = new User("admin","123","飞翔的企鹅","###","我要飞的更高","河北师大软件学院");
            db.execSQL("INSERT INTO user VALUES (NULL, ?, ?, ?, ?, ?, ?)", new Object[]{admin.getAccount(),admin.getPassword(),admin.getName(),admin.getImg(),admin.getSummary(),admin.getPosition()});
        }
    }

    public void customView(View source)
    {
        String Strac="";
        String Strpw="";

        // 装载app\src\main\res\layout\index.xml界面布局文件
        TableLayout loginForm = (TableLayout)getLayoutInflater()
                .inflate(R.layout.login, null);
        final EditText uac=(EditText)loginForm.findViewById(R.id.user);
        final EditText upw=(EditText)loginForm.findViewById(R.id.pw);
        new AlertDialog.Builder(this)
                // 设置对话框的图标
                .setIcon(R.drawable.ic_launcher)
                // 设置对话框的标题
                .setTitle("请登录")
                // 设置对话框显示的View对象
                .setView(loginForm)
                // 为对话框设置一个“确定”按钮
                .setPositiveButton("登录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        String Strac=uac.getText().toString().trim();
                        String Strpw=upw.getText().toString().trim();
                        //判断输入的用户名是否在数据库中
                        HttpUtils httpUtils=new HttpUtils();
                        httpUtils.post_test_login();
                        //checklogin(db,Strac,Strpw)
                        Toast.makeText(IndexActivity.this,"sadasd", Toast.LENGTH_SHORT).show();
                        if (httpUtils.get_test_login()){
                            String Strna="";
                            Cursor c = db.rawQuery("select name from user where account= ?", new String[]{Strac});
                            while(c.moveToNext()){
                                Strna=c.getString(0);
                            }
                            Toast.makeText(IndexActivity.this,"欢迎你"+Strna, Toast.LENGTH_SHORT).show();
                            Intent i = new Intent( );
                            i.setClass(getBaseContext(),SlidingActivity.class);
                            i.putExtra("account",Strac);
                            startActivity(i);
                            IndexActivity.this.finish();
                        }
                        else{
                            Toast.makeText(IndexActivity.this,"用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }
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
    }
    public void customView2(View source)
    {
        String Strac="";
        String Strpw="";
        String Strrpw="";

        // 装载app\src\main\res\layout\index.xml界面布局文件
        TableLayout registerForm = (TableLayout)getLayoutInflater()
                .inflate(R.layout.register, null);
        final EditText uac=(EditText)registerForm.findViewById(R.id.user);
        final EditText upw=(EditText)registerForm.findViewById(R.id.pw);
        final EditText urpw=(EditText)registerForm.findViewById(R.id.rpw);
        new AlertDialog.Builder(this)
                // 设置对话框的图标
                .setIcon(R.drawable.ic_launcher)
                // 设置对话框的标题
                .setTitle("请注册")
                // 设置对话框显示的View对象
                .setView(registerForm)
                // 为对话框设置一个“确定”按钮
                .setPositiveButton("注册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        String Strac=uac.getText().toString().trim();
                        String Strpw=upw.getText().toString().trim();
                        String Strrpw=urpw.getText().toString().trim();
                        //判断输入的用户名是否在数据库中
                        if (checkregister(db,Strac,Strpw,Strrpw)){
                            Toast.makeText(IndexActivity.this,"注册成功", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(IndexActivity.this,"注册失败", Toast.LENGTH_SHORT).show();
                        }
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
    }
    private boolean checklogin(SQLiteDatabase db,String ac,String pw) {
        Cursor cursor = db.rawQuery("select * from user where account = ? and password= ?",new String[]{ac,pw});
        if(cursor.moveToNext()){
            return true;
        }
        return false;
    }
    private boolean checkregister(SQLiteDatabase db,String ac,String pw,String rpw) {
        try{
            if(ac!=""&&pw!=""){
                if(pw.equals(rpw)){
                    User au = new User(ac,pw,"默认昵称","###","默认","默认");
                    db.execSQL("INSERT INTO user VALUES (NULL, ?, ?, ?, ?, ?, ?)", new Object[]{au.getAccount(),au.getPassword(),au.getName(),au.getImg(),au.getSummary(),au.getPosition()});
                    return true;
                }
                else{
                    Toast.makeText(IndexActivity.this,"两次密码输入不一致", Toast.LENGTH_SHORT).show();
                }
            }else{Toast.makeText(IndexActivity.this,"用户名或密码不能为空", Toast.LENGTH_SHORT).show();}
        }catch (SQLException sql){
            Toast.makeText(IndexActivity.this,"用户已经存在", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
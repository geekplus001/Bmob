package com.ben.bmob;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText editText_name,editText_password,editText_email,editText_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editText_name = (EditText) findViewById(R.id.editText_username);
        editText_password = (EditText) findViewById(R.id.editText_password);
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_icon = (EditText) findViewById(R.id.editText_icon);
    }

    public void register2Click(View view)
    {
        final String name = editText_name.getText().toString();
        final String pass = editText_password.getText().toString();
        final String email = editText_email.getText().toString();
//        String icon = editText_icon.getText().toString();

        User user = new User();
        user.setUsername(name);
        user.setPassword(pass);
        user.setEmail(email);
        //获取图片文件保存路径
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/o.jpg";
        //图片上传
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.upload(this, new UploadFileListener() {
            @Override
            public void onSuccess() {
                User user = new User();
                user.setUsername(name);
                user.setPassword(pass);
                user.setEmail(email);
                user.setIcon(bmobFile);
                //注册
                user.signUp(RegisterActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(int i, String s) {
                        System.out.println(i+"----"+s);
                        Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                System.out.println(i+"----"+s);
                Toast.makeText(RegisterActivity.this,"头像图片找不到",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

package com.ben.bmob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {


    private EditText editText_username,editText_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 初始化 Bmob SDK
        Bmob.initialize(this, "de7231860f3c2a22302f31bd1179b3ae");
        editText_username = (EditText) findViewById(R.id.editText_loginname);
        editText_password = (EditText) findViewById(R.id.editText_loginpassword);
    }

    public void loginClick(View view)
    {
        String username = editText_username.getText().toString();
        String password = editText_password.getText().toString();

        if(username!=null&&password!=null)
        {
           final BmobUser bmobUser = new BmobUser();
            bmobUser.setUsername(username);
            bmobUser.setPassword(password);
            bmobUser.login(this, new SaveListener() {
                @Override
                public void onSuccess() {
                    User user = BmobUser.getCurrentUser(LoginActivity.this,User.class);
//                    StringBuffer userMessage = new StringBuffer();
//                    userMessage.append(user.getUsername());
//                    userMessage.append(user.getEmail()).append("欢迎你");

                   if(user.getEmailVerified())
                   {
                       Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                       intent.putExtra("user",user);
                       startActivity(intent);
                   }
                    else
                   {
                       Toast.makeText(LoginActivity.this,"邮箱未激活",Toast.LENGTH_SHORT).show();
                   }
                }

                @Override
                public void onFailure(int i, String s) {
                    Toast.makeText(LoginActivity.this,"登陆失败",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
        }
    }

    public void registerClick(View view)
    {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}

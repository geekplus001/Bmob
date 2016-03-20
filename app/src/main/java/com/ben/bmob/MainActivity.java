package com.ben.bmob;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化 Bmob SDK
        // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
//        Bmob.initialize(this, "de7231860f3c2a22302f31bd1179b3ae");
        textView = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView_icon);

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");
        textView.setText(user.toString());

        BmobFile bf = user.getIcon();
//        bf.loadImage(this,imageView);
        bf.loadImageThumbnail(this,imageView,48,48,50);
    }


    public void addClick(View view)
    {
        Person p = new Person();
        p.setName("斌哥");
        p.setAddress("郑州");
        p.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this,"添加数据成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MainActivity.this,"添加数据失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void delClick(View view)
    {
        Person p = new Person();
        p.setObjectId("72bbebc0e0");
        p.delete(this, new DeleteListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this,"删除数据成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MainActivity.this,"删除数据失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateClick(View view)
    {
        Person p = new Person();
        p.setAddress("郑州大学");
        p.update(this, "72bbebc0e0", new UpdateListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this,"更新数据成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MainActivity.this,"更新数据失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void queryClick(View view)
    {
        BmobQuery<Person> query = new BmobQuery<>();
        query.getObject(this, "72bbebc0e0", new GetListener<Person>() {
            @Override
            public void onSuccess(Person person) {
                Toast.makeText(MainActivity.this,person.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MainActivity.this,"查询数据失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

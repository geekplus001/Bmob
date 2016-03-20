package com.ben.bmob;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;

import static com.ben.bmob.R.menu.menu_note_list;

public class NoteListActivity extends AppCompatActivity {

    private static final int DEL_ITEM = 0x1;
    private List<Note> notes = new ArrayList<>();
    private ListView listView;
    NoteAdapter na;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        Bmob.initialize(this, "de7231860f3c2a22302f31bd1179b3ae");

        listView = (ListView) findViewById(R.id.listView_note);
//        loadData();

//        NoteAdapter na = new NoteAdapter(this,notes);
//        listView.setAdapter(na);
        //进入NoteDetailActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView_content = (TextView) view.findViewById(R.id.textView_content);
                String content = textView_content.getText().toString();
                String objectId = (String) view.getTag();
                Intent intent  = new Intent(NoteListActivity.this,NoteDetailActivity.class);
                intent.putExtra("content",content);
                intent.putExtra("objectId",objectId);
                startActivity(intent);
            }
        });

        //长按删除  先注册上下文
        registerForContextMenu(listView);
    }

    //创建
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(1,DEL_ITEM,100,"删除");
    }
    //选中
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case DEL_ITEM:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                View view = info.targetView;
                String objectId = (String) view.getTag();
                Note note = new Note();
                note.delete(this, objectId, new DeleteListener() {
                    @Override
                    public void onSuccess() {
                        loadData();
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void loadData() {
        BmobQuery<Note> query = new BmobQuery<>();
        query.setLimit(50);//设置查询大小
        query.findObjects(this, new FindListener<Note>() {
            @Override
            public void onSuccess(List<Note> list) {
                notes = list;
                 na = new NoteAdapter(NoteListActivity.this,notes);
                listView.setAdapter(na);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


    static class NoteAdapter extends BaseAdapter{

        private Context context;
        private List<Note> list;
        public NoteAdapter(Context context,List<Note> list)
        {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null)
            {
                convertView = LayoutInflater.from(context).inflate(R.layout.layout_note_item,null);
            }
            Note note = list.get(position);

            TextView content = (TextView) convertView.findViewById(R.id.textView_content);
            content.setText(note.getContent());
            convertView.setTag(note.getObjectId());

            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_list,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.add_note:
                Intent intent = new Intent(NoteListActivity.this,NoteNewActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    //返回再读便签
    @Override
    protected void onResume() {
        super.onResume();
        loadData();

    }
}

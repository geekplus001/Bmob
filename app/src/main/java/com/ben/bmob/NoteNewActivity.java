package com.ben.bmob;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.EditText;

import cn.bmob.v3.listener.SaveListener;

public class NoteNewActivity extends AppCompatActivity {

    private EditText editText_new;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_new);

        editText_new = (EditText) findViewById(R.id.editText_new);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            String content = editText_new.getText().toString();
            if(!TextUtils.isEmpty(content))
            {
                Note note =new Note();
                note.setContent(content);
                note.save(this, new SaveListener() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

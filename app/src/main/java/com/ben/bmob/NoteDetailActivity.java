package com.ben.bmob;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.EditText;

import cn.bmob.v3.listener.UpdateListener;

public class NoteDetailActivity extends AppCompatActivity {

    private EditText editText_contentdetail;
    private String objectId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        editText_contentdetail = (EditText) findViewById(R.id.editText_content);
        objectId = getIntent().getStringExtra("objectId");
        editText_contentdetail.setText(getIntent().getStringExtra("content"));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {

            String contentdetail = editText_contentdetail.getText().toString();
            if(!TextUtils.isEmpty(contentdetail))
            {
                Note note = new Note();
                note.setContent(contentdetail);
                note.update(this, objectId, new UpdateListener() {
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
}

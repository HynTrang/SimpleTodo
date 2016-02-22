package com.example.user.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    EditText etEditItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        etEditItem = (EditText)findViewById(R.id.etEditItem);
        String nameItem = getIntent().getStringExtra("nameItem");

        etEditItem.setText(nameItem);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setupSaveClick(View view) {
//        Intent i = new Intent(EditActivity.this,MainActivity.class);
//        startActivity(i);
        Intent data = new Intent();
        data.putExtra("name",etEditItem.getText().toString());
        int codeInt = getIntent().getIntExtra("code",0);
        data.putExtra("code",codeInt);
        setResult(RESULT_OK, data);
        this.finish();
    }
}

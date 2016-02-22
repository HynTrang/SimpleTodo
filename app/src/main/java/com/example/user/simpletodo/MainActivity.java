package com.example.user.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        items = new ArrayList<>();
        readItems();
        itemsAdapter = new ArrayAdapter<>(this,R.layout.list_layout,R.id.textView, items);
        lvItems.setAdapter(itemsAdapter);
        items.add("First Item");
        setupListViewListener();
        setupItemClick();
    }
    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                        items.remove(position);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );
    }
    private final int REQUEST_CODE = 20;
    private void setupItemClick(){
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this,EditActivity.class);
                String pos = items.get(position);

                i.putExtra("nameItem",pos);//Lay du lieu tu item da duoc chon
                i.putExtra("code",position);// Lay vi tri tu Item da duoc chon
                //startActivity(i);
                startActivityForResult(i,REQUEST_CODE);

               //Toast.makeText(MainActivity.this, "String is:" + pos+"number is:"+position, Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            String name = data.getExtras().getString("name");
            int code = data.getExtras().getInt("code",0);
            //Toast.makeText(this, name + "pos is:" + code, Toast.LENGTH_SHORT).show();
          // itemsAdapter.add(name);
            items.set(code,name);
            itemsAdapter.notifyDataSetChanged();
            writeItems();

        }
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch (IOException e){
            items = new ArrayList<String>();
        }
    }
    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try{
            FileUtils.writeLines(todoFile,items);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onAddItem(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }
}

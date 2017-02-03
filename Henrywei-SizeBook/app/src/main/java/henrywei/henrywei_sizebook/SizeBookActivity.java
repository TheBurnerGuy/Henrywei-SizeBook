package henrywei.henrywei_sizebook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SizeBookActivity extends AppCompatActivity {

    private ArrayList<Record> recordList;
    private ArrayAdapter<Record> adapter;
    private Record currentRecord;
    private final static String FILENAME = "file.sav";
    private final static int RECORD_REQUEST = 1;

    private TextView count = (TextView) findViewById(R.id.count);
    private ListView userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_book);

        userList = (ListView) findViewById(R.id.userList);
        Button addButton = (Button) findViewById(R.id.add);

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentRecord = adapter.getItem(position);
                editRecord(currentRecord);
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();

        //occupy list view from file
        loadFromFile();

        adapter = new ArrayAdapter<Record>(this, R.layout.list_item, recordList);
        userList.setAdapter(adapter);

        count.setText("Record Count: "+recordList.size());
    }

    public void addRecord(View view){
        currentRecord = null; //make sure currentrecord is not selecting a record
        Intent intent = new Intent(this, EditRecords.class);
        startActivity(intent);
    }

    /**
     * Used to edit an existing record
     *
     */
    public void editRecord(Record record){
        Intent intent = new Intent(this, EditRecords.class);
        //Taken from http://stackoverflow.com/questions/5265913/how-to-use-putextra-and-getextra-for-string-data
        //1-31-2017
        intent.putExtra("NAME", record.name);
        intent.putExtra("DATE", record.date);
        intent.putExtra("NECK", record.neck);
        intent.putExtra("BUST", record.bust);
        intent.putExtra("CHEST", record.chest);
        intent.putExtra("WAIST", record.waist);
        intent.putExtra("HIP", record.hip);
        intent.putExtra("INSEAM", record.inseam);
        intent.putExtra("COMMENT", record.comment);
        startActivityForResult(intent, RECORD_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && resultCode == RECORD_REQUEST){
            Bundle extras = data.getExtras();
            if (currentRecord == null){
                //add a new record
                if (extras != null){
                    setResult(RESULT_OK);

                    recordList.add(new Record(extras.getString("NAME"),extras.getString("DATE"),extras.getDouble("NECK"),extras.getDouble("BUST"),
                            extras.getDouble("CHEST"),extras.getDouble("WAIST"),extras.getDouble("HIP"),extras.getDouble("INSEAM"),extras.getString("COMMENT")));

                    adapter.notifyDataSetChanged();
                    saveInFile();
                    count.setText("Record Count: "+recordList.size());
                }
            } else {
                //check if editing record or deleting record
                if (extras == null){
                    //currentRecord is being deleted
                    setResult(RESULT_OK);

                    recordList.remove(currentRecord);

                    adapter.notifyDataSetChanged();
                    saveInFile();
                    count.setText("Record Count: "+recordList.size());
                } else {
                    //currentRecord is being edited
                    setResult(RESULT_OK);

                    currentRecord.updateRecord(extras.getString("NAME"),extras.getString("DATE"),extras.getDouble("NECK"),extras.getDouble("BUST"),
                            extras.getDouble("CHEST"),extras.getDouble("WAIST"),extras.getDouble("HIP"),extras.getDouble("INSEAM"),extras.getString("COMMENT"));

                    adapter.notifyDataSetChanged();
                    saveInFile();
                    count.setText("Record Count: "+recordList.size());
                }
            }
        }
    }

    private void loadFromFile(){
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-01-24 18:19
            Type listType = new TypeToken<ArrayList<Record>>(){}.getType();
            recordList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            recordList = new ArrayList<Record>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

    private void saveInFile(){
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(recordList, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

}

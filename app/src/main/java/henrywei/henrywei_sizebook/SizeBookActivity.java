package henrywei.henrywei_sizebook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

/**
 * This class is the main view class of the project. <br/> In this class, user interaction to
 * add, view records, and file manipulation is performed.
 * All files are in the form of *json* files that are stored in Emulator's' accessible from Android Device Monitor.
 * <br/>
 * This class calls the Activity class EditRecords to view/edit/remove records;
 * Uses the listview's ItemClickListener to view records and a button's clickListener to add records.
 *
 * @author henrywei
 * @version 1.0
 * @see Record
 * @since 0.5
 */

public class SizeBookActivity extends AppCompatActivity {

    /**
     * The file that all the records are saved there. The format of the file is JSON.
     */
    private final static String FILENAME = "file.sav";
    private final static int RECORD_REQUEST = 1;

    private ArrayList<Record> recordList;
    private ArrayAdapter<Record> adapter;
    private Record currentRecord;

    private TextView count;
    private ListView userList;
    Button addButton;

    /**
     * Initializes all the views and sets the list's (userList) ItemClickListener.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_book);

        userList = (ListView) findViewById(R.id.userList);
        count = (TextView) findViewById(R.id.count);
        addButton = (Button) findViewById(R.id.add);

        /**
         * AddButton finds the record to be clicked on and calls editRecord
         * @see editRecord
         */
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentRecord = adapter.getItem(position);
                editRecord(currentRecord);
            }
        });
    }

    /**
     * Loads data into arrayAdapter to fill the program's list view
     */

    @Override
    protected void onStart(){
        super.onStart();

        //occupy list view from file
        loadFromFile();

        adapter = new ArrayAdapter<Record>(this, R.layout.list_item, recordList);
        userList.setAdapter(adapter);

        count.setText("Record Count: "+recordList.size());
    }

    /**
     * Add button's click function
     * Calls the class EditRecords to add a new record
     * @see EditRecords
     */

    public void addRecord(View view){
        currentRecord = null; //make sure currentrecord is not selecting a record
        Intent intent = new Intent(this, EditRecords.class);
        startActivityForResult(intent, RECORD_REQUEST);
    }

    /**
     * Used to edit an existing record
     * Called by the list view's OnItemClickListener
     * @see EditRecords
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

    /**
     * Called when EditRecord finishes and returns to this main class
     * @param requestCode - returns the requestCode in RECORD_REQUEST
     * @param resultCode - returns RESULT_OK if record is added, edited, or removed. If no changes occur_CANCELED
     * @param data - the intent that contains (or doesn't) the added/changed values of a record
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == RECORD_REQUEST){
            //Get extras from intent (or lack thereof)
            Bundle extras;
            if (data == null) {
                extras = null;
            } else {
                extras = data.getExtras();
            }
            if (currentRecord == null){
                //add a new record if extras contains the parameters needed to fill a record
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


    /**
     * Loads records from specified file.
     *
     * @exception FileNotFoundException if the file is not created first.
     */
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

    /**
     * Saves records to a specified file in JSON format.
     * @throws FileNotFoundException if file folder doesn't exist
     */
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

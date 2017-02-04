package henrywei.henrywei_sizebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Size;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * The class is a view class called by SizeBookActivity to edit an existing or new record.
 * Created by henrywei on 1/26/17.
 */

public class EditRecords extends AppCompatActivity{

    EditText nameField;
    EditText dateField;
    EditText neckField;
    EditText bustField;
    EditText chestField;
    EditText waistField;
    EditText hipField;
    EditText inseamField;
    EditText commentField;

    /**
     * Initalizes all the textfields and buttons, and sets both button's clickListener to return to
     * the main Activity class SizeBookActivity.
     * @see SizeBookActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_details);

        nameField = (EditText) findViewById(R.id.name);
        dateField = (EditText) findViewById(R.id.date);
        neckField = (EditText) findViewById(R.id.neck);
        bustField = (EditText) findViewById(R.id.bust);
        chestField = (EditText) findViewById(R.id.chest);
        waistField = (EditText) findViewById(R.id.waist);
        hipField = (EditText) findViewById(R.id.hip);
        inseamField = (EditText) findViewById(R.id.inseam);
        commentField = (EditText) findViewById(R.id.comment);

        Button finishButton = (Button) findViewById(R.id.finish);
        Button removeButton = (Button) findViewById(R.id.remove);

        //If MainClass sent extras containing an existing record's name/dimensions,
        //use it to fill in the textfields.
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            //if record already exists, fill in fields
            nameField.setText(extras.getString("NAME"));
            dateField.setText(extras.getString("DATE")); //??? special parameter for date?
            setDoubleField(neckField, extras.getDouble("NECK"));
            setDoubleField(bustField, extras.getDouble("BUST"));
            setDoubleField(chestField, extras.getDouble("CHEST"));
            setDoubleField(waistField, extras.getDouble("WAIST"));
            setDoubleField(hipField, extras.getDouble("HIP"));
            setDoubleField(inseamField, extras.getDouble("INSEAM"));
            commentField.setText(extras.getString("COMMENT"));
        }

        //If removeButton is clicked on, return a proper intent to signal deleteRecord
        removeButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK, new Intent()); //return empty intent

                finish();
            }
        });

        //If finishButton is clicked on, return a proper intent that
        //sets up a record to be edited/added in SizeBookActivity
        finishButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (nameField.getText().toString().length()>0) {

                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);

                    intent.putExtra("NAME", nameField.getText().toString());
                    intent.putExtra("DATE", dateField.getText().toString());
                    putDoubleExtra(intent, "NECK", neckField.getText().toString());
                    putDoubleExtra(intent, "BUST", bustField.getText().toString());
                    putDoubleExtra(intent, "CHEST", chestField.getText().toString());
                    putDoubleExtra(intent, "WAIST", waistField.getText().toString());
                    putDoubleExtra(intent, "HIP", hipField.getText().toString());
                    putDoubleExtra(intent, "INSEAM", inseamField.getText().toString());
                    intent.putExtra("COMMENT", commentField.getText().toString());

                    finish();
                }
            }
        });
    }

    /**
     * Used in onCreate to fill in a double's textfield if given double
     * is not a zero (means missing value).
     * @param textField - textfield to fill
     * @param value - value to check
     */
    private void setDoubleField(EditText textField, double value){
        if(value != 0.0){
            textField.setText(Double.toString(value));
        }
    }

    /**
     * Used in onCreate to check if the text field has a proper double typed in.
     * @param intent - intent to put information from the textfield in
     * @param name - the name of the extra in the intent
     * @param value - the double TextField converted into a String
     */
    private void putDoubleExtra(Intent intent, String name, String value){
        try{
            intent.putExtra(name, Double.parseDouble(value));
        }catch(NumberFormatException e){
            intent.putExtra(name, 0.0);
        }
    }

}

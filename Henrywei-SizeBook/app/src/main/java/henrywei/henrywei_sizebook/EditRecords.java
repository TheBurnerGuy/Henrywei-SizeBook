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

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            //if record already exists, fill in fields
            nameField.setText(extras.getString("NAME"));
            dateField.setText(extras.getString("DATE")); //??? special parameter for date?
            neckField.setText(Double.toString(extras.getDouble("NECK")));
            bustField.setText(Double.toString(extras.getDouble("BUST")));
            chestField.setText(Double.toString(extras.getDouble("CHEST")));
            waistField.setText(Double.toString(extras.getDouble("WAIST")));
            hipField.setText(Double.toString(extras.getDouble("HIP")));
            inseamField.setText(Double.toString(extras.getDouble("INSEAM")));
            commentField.setText(extras.getString("COMMENT"));
        }

        //If removeButton is clicked on, return a proper intent to signal deleteRecord
        removeButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                setIntent(new Intent()); //return empty intent

                finish();
            }
        });

        //If finishButton is clicked on, return a proper intent that
        //sets up a record to be edited/added in SizeBookActivity
        finishButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (nameField.getText().toString().length()>0) {
                    setResult(RESULT_OK);

                    Intent intent = new Intent();
                    intent.putExtra("NAME", nameField.getText().toString());
                    intent.putExtra("DATE", dateField.getText().toString());
                    intent.putExtra("NECK", Double.parseDouble(neckField.getText().toString()));
                    intent.putExtra("BUST", Double.parseDouble(bustField.getText().toString()));
                    intent.putExtra("CHEST", Double.parseDouble(chestField.getText().toString()));
                    intent.putExtra("WAIST", Double.parseDouble(waistField.getText().toString()));
                    intent.putExtra("HIP", Double.parseDouble(hipField.getText().toString()));
                    intent.putExtra("INSEAM", Double.parseDouble(inseamField.getText().toString()));
                    intent.putExtra("COMMENT", commentField.getText().toString());

                    setIntent(intent);
                    finish();
                }
            }
        });
    }

}

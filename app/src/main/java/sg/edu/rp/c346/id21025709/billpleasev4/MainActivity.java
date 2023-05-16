package sg.edu.rp.c346.id21025709.billpleasev4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;


public class MainActivity extends AppCompatActivity {

    //Step 1: Declare the field Variables
    EditText amount;
    EditText pax;
    ToggleButton tbgst;
    ToggleButton tbsvs;
    TextView totalBill;
    TextView eachPays;
    Button split;
    Button reset;
    EditText discount;
    RadioGroup payMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Step 2: Link the field Variables to UI components in layout
        amount = findViewById(R.id.input_TotalAmt);
        pax = findViewById(R.id.input_people);
        totalBill = findViewById(R.id.total_bill);
        eachPays = findViewById(R.id.each_pays);
        tbgst = findViewById(R.id.GST);
        tbsvs = findViewById(R.id.servicefee);
        split = findViewById(R.id.split);
        reset = findViewById(R.id.reset);
        discount = findViewById(R.id.discount);
        payMode = findViewById(R.id.payMode);

        split.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount.getText().toString().trim().length() != 0 && pax.getText().toString().trim().length() != 0) {
                    double origAmt = Double.parseDouble(amount.getText().toString());
                    double newAmt = 0.0;
                    if (tbsvs.isChecked() && tbgst.isChecked()) {
                        newAmt = origAmt * 1.1 * 1.18;
                    } else if (tbsvs.isChecked() && !tbgst.isChecked()) {
                        newAmt = origAmt * 1.1;
                    } else if (!tbsvs.isChecked() && tbgst.isChecked()) {
                        newAmt = origAmt * 1.08;
                    } else {
                        newAmt = origAmt;
                    }

                    //Calculate with discount
                    if (discount.getText().toString().trim().length() != 0) {
                        newAmt *= 1 - Double.parseDouble(discount.getText().toString()) / 100;
                    } else {
                        newAmt = origAmt;
                    }

                    //payment Mode
                    String mode = " in Cash";
                    if (payMode.getCheckedRadioButtonId() == R.id.PayNow) {
                        mode = " via PayNow";
                    }

                    //Displaying result to user
                    totalBill.setText("Total Bill: $" + String.format("%. 2f", newAmt));
                    int numPerson = Integer.parseInt(pax.getText().toString());
                    if (numPerson != 1) {
                        eachPays.setText("Each Pays: $" + String.format("%.2f", newAmt / numPerson) + mode);
                    } else {
                        eachPays.setText("Each Pays: $" + newAmt + mode);
                    }
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText(" ");
                pax.setText(" ");
                tbgst.setChecked(false);
                tbsvs.setChecked(false);
                discount.setText(" ");
                payMode.check(R.id.cash);
            }
        });
    }
}
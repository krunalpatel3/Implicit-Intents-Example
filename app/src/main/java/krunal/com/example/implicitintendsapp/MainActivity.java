package krunal.com.example.implicitintendsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mBtnOpenWeb, mBtnOpenInMap, mBtnShareInApp, mBtnDial;

    private EditText mEditTextOpenWeb, mEditTextOpenInMap, mEditTextShareInApp, mEditTextDial;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button.
        mBtnOpenWeb = findViewById(R.id.buttonWeb);

        mBtnOpenInMap = findViewById(R.id.buttonMap);

        mBtnShareInApp = findViewById(R.id.buttonShare);

        mBtnDial = findViewById(R.id.buttonDial);

        // EditText.
        mEditTextOpenWeb = findViewById(R.id.editTextWeb);

        mEditTextOpenInMap = findViewById(R.id.editTextMap);

        mEditTextShareInApp = findViewById(R.id.editTextShare);

        mEditTextDial = findViewById(R.id.editTextDialNumber);


        // For Opening the Website.
        mBtnOpenWeb.setOnClickListener(v -> {
            String getWebSiteUrl = mEditTextOpenWeb.getText().toString();

            // Checking for Null.
            if (!getWebSiteUrl.matches("")) {

                // Parse the URI and create the intent.
                Uri webpage = Uri.parse(getWebSiteUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

                // Find an activity to hand the intent and start that activity.
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Log.d("ImplicitIntents", "Can't handle this intent!");
                }
            } else {
                Toast.makeText(this, "No Url!", Toast.LENGTH_LONG).show();
            }

        });

        // Pass the word in map.
        mBtnOpenInMap.setOnClickListener(v -> {
            // Get the string indicating a location.  Input is not validated; it is
            // passed to the location handler intact.
            String getMapLoction = mEditTextOpenInMap.getText().toString();

            // Check for null.
            if (!getMapLoction.matches("")) {

                // Parse the location and create the intent.
                Uri addressUri = Uri.parse("geo:0,0?q=" + getMapLoction);
                Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

                // Find an activity to handle the intent, and start that activity.
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Log.d("ImplicitIntents", "Can't handle this intent!");
                }
            } else {
                Toast.makeText(this, "No Location Name!", Toast.LENGTH_LONG).show();
            }
        });

        // Share Text to other app.
        mBtnShareInApp.setOnClickListener(v -> {
            // Get the shared text.
            String getText = mEditTextShareInApp.getText().toString();

            // Cheking if the Text is null.
            if (!getText.matches("")) {
                // Build the share intent with the mimetype text/plain and launch
                // a chooser for the user to pick an app.
                ShareCompat.IntentBuilder
                        .from(this)
                        .setType("text/plain")
                        .setChooserTitle("Share this text with: ")
                        .setText(getText)
                        .startChooser();
            } else {
                Toast.makeText(this, "No Text!", Toast.LENGTH_LONG).show();
            }

        });

        // When user click no this it Dials the Number.
        mBtnDial.setOnClickListener(v -> {
            DialNumber();
        });



    }

    /**
     * Creates a string (mPhoneNum) for the phone number to dial,
     * and performs an implicit intent to dial the number.
     */
        private void DialNumber() {

            String FinalNumber = null;

            if (!mEditTextDial.getText().toString().matches("")) {
                // If the editText field is not null, concatenate "tel: " with the phone number string.
                if (mEditTextDial != null) {
                    FinalNumber = "tel:" + mEditTextDial.getText().toString();
                    // Specify the intent.
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    // Set the data for the intent as the phone number.
                    intent.setData(Uri.parse(FinalNumber));
                    // If the intent resolves to a package (app), start the activity with the intent.
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Log.d(TAG, "ImplicitIntents: Can't handle this!");
                    }
                }
            } else {
                Toast.makeText(this, "No Phone Number!", Toast.LENGTH_LONG).show();
            }
        }
    }

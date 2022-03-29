package ro.pub.cs.systems.eim.lab03.phonedialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class PhoneDialerActivity extends AppCompatActivity {
    class PhoneNumberListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Button pressedButton = (Button) view;
            ((EditText) findViewById(R.id.phone_number_edit_text)).append(pressedButton.getText());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);

        EditText phoneNumberEditText = findViewById(R.id.phone_number_edit_text);

        phoneNumberEditText.setInputType(InputType.TYPE_NULL);

        List<Integer> numberButtonsIdList = Arrays.asList(
            R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6,
            R.id.button7, R.id.button8, R.id.button9,
            R.id.button_asterisk, R.id.button0, R.id.button_hashtag
        );

        View.OnClickListener phoneNumberListener = new PhoneNumberListener();
        for (Integer id : numberButtonsIdList) {
            findViewById(id).setOnClickListener(phoneNumberListener);
        }

        findViewById(R.id.delete_image_button).setOnClickListener((v) -> {
            int textLength = phoneNumberEditText.getText().length();

            if (textLength > 0) {
                phoneNumberEditText.getText().delete(textLength-1, textLength);
            }
        });

        findViewById(R.id.call_image_button).setOnClickListener((v) -> {
            if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        PhoneDialerActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        Constants.PERMISSION_REQUEST_CALL_PHONE);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phoneNumberEditText.getText().toString()));
                startActivity(intent);
            }
        });

        findViewById(R.id.hangup_image_button).setOnClickListener((v) -> this.finish());
        findViewById(R.id.contacts_image_button).setOnClickListener((v) -> {
            String phoneNumber = phoneNumberEditText.getText().toString();
            if (phoneNumber.length() > 0) {
                Intent intent = new Intent("ro.pub.cs.systems.eim.lab04.contactsmanager.intent.action.ContactsManagerActivity");
                intent.putExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY", phoneNumber);
                startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE);
            } else {
                Toast.makeText(getApplication(), getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }
        });
    }
}
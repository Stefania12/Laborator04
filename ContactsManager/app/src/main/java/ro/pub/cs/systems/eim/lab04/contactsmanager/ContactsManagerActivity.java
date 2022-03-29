package ro.pub.cs.systems.eim.lab04.contactsmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText addressEditText;
    private EditText jobTitleEditText;
    private EditText companyEditText;
    private EditText websiteEditText;
    private EditText imEditText;

    private class ButtonListener implements View.OnClickListener {
        private int show_hide_state = 0;
        private final String[] show_hide_messages = {getResources().getString(R.string.show_additional_fields), getResources().getString(R.string.hide_additional_fields)};
        private final int[] visibilities = {View.GONE, View.VISIBLE};

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.show_hide_button) {
                show_hide_state = (show_hide_state + 1) % 2;
                ((Button) view).setText(show_hide_messages[show_hide_state]);
                ((LinearLayout) findViewById(R.id.additional_fields_layout)).setVisibility(visibilities[show_hide_state]);
                return;
            }

            if (view.getId() == R.id.cancel_button) {
                finish();
                return;
            }

            if (view.getId() == R.id.save_button) {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String jobTitle = jobTitleEditText.getText().toString();
                String company = companyEditText.getText().toString();
                String website = websiteEditText.getText().toString();
                String im = imEditText.getText().toString();
                if (!name.equals("")) {
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                }
                if (!phone.equals("")) {
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                }
                if (!email.equals("")) {
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                }
                if (!address.equals("")) {
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                }
                if (!jobTitle.equals("")) {
                    intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                }
                if (!company.equals("")) {
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                }

                ArrayList<ContentValues> contactData = new ArrayList<>();
                if (!website.equals("")) {
                    ContentValues websiteRow = new ContentValues();
                    websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                    websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                    contactData.add(websiteRow);
                }
                if (!im.equals("")) {
                    ContentValues imRow = new ContentValues();
                    imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                    imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                    contactData.add(imRow);
                }
                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        nameEditText = (EditText)findViewById(R.id.name_edit_text);
        phoneEditText = (EditText)findViewById(R.id.phone_number_edit_text);
        emailEditText = (EditText)findViewById(R.id.email_edit_text);
        addressEditText = (EditText)findViewById(R.id.address_edit_text);
        jobTitleEditText = (EditText)findViewById(R.id.job_title_edit_text);
        companyEditText = (EditText)findViewById(R.id.company_edit_text);
        websiteEditText = (EditText)findViewById(R.id.website_edit_text);
        imEditText = (EditText)findViewById(R.id.im_edit_text);

        ButtonListener buttonListener = new ButtonListener();
        findViewById(R.id.show_hide_button).setOnClickListener(buttonListener);
        findViewById(R.id.save_button).setOnClickListener(buttonListener);
        findViewById(R.id.cancel_button).setOnClickListener(buttonListener);
    }
}
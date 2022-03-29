package ro.pub.cs.systems.eim.lab04.contactsmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ContactsManagerActivity extends AppCompatActivity {

    static class AdditionalFieldsToggler implements View.OnClickListener {
        private int currentState = 0;
        private String[] messages = {"SHOW ADDITIONAL FIELDS", "HIDE ADDITIONAL FIELDS"};
        private int[] visibilities = {View.INVISIBLE, View.VISIBLE};
        private LinearLayout additionalFieldsLayout;

        public AdditionalFieldsToggler(LinearLayout additionalFieldsLayout)  {
            this.additionalFieldsLayout = additionalFieldsLayout;
        }

        @Override
        public void onClick(View view) {
            currentState = (currentState + 1) % 2;
            ((Button)view).setText(messages[currentState]);
            additionalFieldsLayout.setVisibility(visibilities[currentState]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        Button showHideButton = findViewById(R.id.show_hide_button);
        showHideButton.setOnClickListener(new AdditionalFieldsToggler(findViewById(R.id.additional_fields_layout)));
    }
}
package com.example.everydayneed;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ContactDetailActivity extends AppCompatActivity {

    private int contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_contact_detail);

        try (DBHelper dbHelper = new DBHelper(this)) {
            ImageButton imageButtonContacts = findViewById(R.id.imageButtonContacts);
            ImageButton imageButtonBack = findViewById(R.id.imageButtonBack);
            ImageButton imageButtonEdit = findViewById(R.id.imageButtonEdit);
            ImageButton imageButtonDelete = findViewById(R.id.imageButtonDelete);

            TextView textViewName = findViewById(R.id.textViewName);
            TextView textViewSurname = findViewById(R.id.textViewSurname);
            TextView textViewPhone = findViewById(R.id.textViewPhone);

            contactId = getIntent().getIntExtra("contactId", -1);

            Contact contact = dbHelper.getContactById(contactId);

            textViewName.setText(contact.getName());
            textViewSurname.setText(contact.getSurname());
            textViewPhone.setText(contact.getPhone());

            imageButtonContacts.setOnClickListener(v -> goToHomePage());

            imageButtonBack.setOnClickListener(v -> goToContactsPage());

            imageButtonEdit.setOnClickListener(v -> goToEditContactPage());

            imageButtonDelete.setOnClickListener(v -> goToDeleteContactPage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToHomePage() {
        Intent intent = new Intent(ContactDetailActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToContactsPage() {
        Intent intent = new Intent(ContactDetailActivity.this, ContactsActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToEditContactPage() {
        Intent intent = new Intent(ContactDetailActivity.this, EditContactActivity.class);
        intent.putExtra("contactId", contactId);
        startActivity(intent);
        finish();
    }

    private void goToDeleteContactPage() {
        Intent intent = new Intent(ContactDetailActivity.this, ContactDeleteActivity.class);
        intent.putExtra("contactId", contactId);
        startActivity(intent);
        finish();
    }
}

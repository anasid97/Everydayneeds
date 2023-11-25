package com.example.everydayneed;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private LinearLayout contactsListLayout;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_contacts);

        dbHelper = new DBHelper(this);

        ImageButton imageButtonContacts = findViewById(R.id.imageButtonContacts);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ImageButton imageButtonAddContact = findViewById(R.id.imageButtonAddContact);
        contactsListLayout = findViewById(R.id.contactsListLayout);

        Adapter contactAdapter = new Adapter(dbHelper.getAllContacts(), dbHelper);
        recyclerView.setAdapter(contactAdapter);

        imageButtonContacts.setOnClickListener(v -> goToHomePage());

        imageButtonAddContact.setOnClickListener(v -> goToAddContactPage());

        displayExistingContacts();
    }

    private void goToHomePage() {
        Intent intent = new Intent(ContactsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToAddContactPage() {
        Intent intent = new Intent(ContactsActivity.this, AddContactActivity.class);
        startActivity(intent);
    }

    private void displayExistingContacts() {
        contactsListLayout.removeAllViews();

        List<Contact> contacts = dbHelper.getAllContacts();

        if (contacts != null && !contacts.isEmpty()) {
            for (Contact contact : contacts) {
                TextView textViewContact = new TextView(this);
                textViewContact.setText(contact.getName() + " " + contact.getSurname() + "\n" + contact.getPhone());


                contactsListLayout.addView(textViewContact);
            }
        } else {
            TextView textViewNoContacts = new TextView(this);
            textViewNoContacts.setText("Нет доступных контактов");

            contactsListLayout.addView(textViewNoContacts);
        }
    }
}

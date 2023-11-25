package com.example.everydayneed;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ContactDeleteActivity extends AppCompatActivity {

    private int contactId;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_contact_delete);

        dbHelper = new DBHelper(this);

        ImageButton imageButtonContacts = findViewById(R.id.imageButtonContacts);
        ImageButton imageButtonBack = findViewById(R.id.imageButtonBack);
        ImageButton imageButtonDelete = findViewById(R.id.imageButtonDelete);
        ImageButton imageButtonDetail = findViewById(R.id.imageButtonDetail);

        TextView textViewName = findViewById(R.id.textViewName);
        TextView textViewSurname = findViewById(R.id.textViewSurname);
        TextView textViewPhone = findViewById(R.id.textViewPhone);

        contactId = getIntent().getIntExtra("contactId", -1);

        Contact contact = dbHelper.getContactById(contactId);

        textViewName.setText(contact.getName());
        textViewSurname.setText(contact.getSurname());
        textViewPhone.setText(contact.getPhone());

        imageButtonContacts.setOnClickListener(v -> goToHomePage());

        imageButtonBack.setOnClickListener(v -> goToContactDetailPage());

        imageButtonDelete.setOnClickListener(v -> confirmDeleteContact());

        imageButtonDetail.setOnClickListener(v -> goToContactDetailPage());
    }

    private void goToHomePage() {
        Intent intent = new Intent(ContactDeleteActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToContactDetailPage() {
        Intent intent = new Intent(ContactDeleteActivity.this, ContactDetailActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToContactsPage() {
        Intent intent = new Intent(ContactDeleteActivity.this, ContactsActivity.class);
        startActivity(intent);
        finish();
    }

    private void confirmDeleteContact() {
        boolean isDeleted = dbHelper.deleteContact(contactId);

        if (isDeleted) {
            Toast.makeText(this, "Контакт успешно удален", Toast.LENGTH_SHORT).show();
            goToContactsPage();
        } else {
            Toast.makeText(this, "Не удалось удалить контакт", Toast.LENGTH_SHORT).show();
        }
    }

}

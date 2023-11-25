package com.example.everydayneed;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class EditContactActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextLastName;
    private EditText editTextPhone;

    private int contactId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_edit_contact);

        ImageButton imageButtonContacts = findViewById(R.id.imageButtonContacts);
        ImageButton imageButtonBack = findViewById(R.id.imageButton3);
        editTextName = findViewById(R.id.editTextText);
        editTextLastName = findViewById(R.id.editTextText2);
        editTextPhone = findViewById(R.id.editTextPhone);
        ImageButton imageButtonEditContact = findViewById(R.id.imageButton);

        contactId = getIntent().getIntExtra("CONTACT_ID", -1);

        if (contactId == -1) {
            Toast.makeText(this, "Error getting contact ID", Toast.LENGTH_SHORT).show();
            finish();
        }

        imageButtonContacts.setOnClickListener(v -> goToHomePage());

        imageButtonBack.setOnClickListener(v -> goToContactDetailPage());

        imageButtonEditContact.setOnClickListener(v -> confirmEditContact());
    }

    private void goToHomePage() {
        Intent intent = new Intent(EditContactActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToContactDetailPage() {
        Intent intent = new Intent(EditContactActivity.this, ContactDetailActivity.class);
        intent.putExtra("CONTACT_ID", contactId);
        startActivity(intent);
        finish();
    }

    private void confirmEditContact() {
        String name = editTextName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String phone = editTextPhone.getText().toString();

        DBHelper dbHelper = new DBHelper(this);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("last_name", lastName);
        values.put("phone", phone);

        String whereClause = "contact_id = ?";
        String[] whereArgs = {String.valueOf(contactId)};

        int rowsUpdated = db.update("contacts", values, whereClause, whereArgs);

        if (rowsUpdated > 0) {
            Toast.makeText(this, "Контакт обновлен успешно", Toast.LENGTH_SHORT).show();
            goToContactDetailPage();
        } else {
            Toast.makeText(this, "Не удалось обновить контакт", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}

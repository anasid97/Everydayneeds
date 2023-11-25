package com.example.everydayneed;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

public class AddContactActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextPhone;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_add_contact);

        dbHelper = new DBHelper(this);

        ImageButton imageButtonContacts = findViewById(R.id.imageButtonContacts);
        ImageButton imageButtonBack = findViewById(R.id.imageButton3);
        ImageButton imageButtonAdd = findViewById(R.id.imageButton);

        editTextName = findViewById(R.id.editTextText);
        editTextSurname = findViewById(R.id.editTextText2);
        editTextPhone = findViewById(R.id.editTextPhone);

        imageButtonContacts.setOnClickListener(v -> goToHomePage());

        imageButtonBack.setOnClickListener(v -> goToContactsPage());

        imageButtonAdd.setOnClickListener(v -> confirmAddContact());
    }

    private void goToHomePage() {
        Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToContactsPage() {
        Intent intent = new Intent(AddContactActivity.this, ContactsActivity.class);
        startActivity(intent);
        finish();
    }

    private void confirmAddContact() {
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        if (name.isEmpty() || surname.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        dbHelper.addContact(name, surname, phone);

        Toast.makeText(this, "Контакт успешно добавлен", Toast.LENGTH_SHORT).show();

        goToContactsPage();
    }
}

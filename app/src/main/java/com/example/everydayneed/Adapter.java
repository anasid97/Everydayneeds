package com.example.everydayneed;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ContactViewHolder> {

    private final List<Contact> contactList;
    private final DBHelper dbHelper;

    public Adapter(List<Contact> contactList, DBHelper dbHelper) {
        this.contactList = contactList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.textViewContact.setText(contact.getName() + " " + contact.getSurname());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView textViewContact;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewContact = itemView.findViewById(R.id.textViewContact);
        }
    }

    public void updateContactList() {
        contactList.clear();
        contactList.addAll(dbHelper.getAllContacts());
        notifyDataSetChanged();
    }
}

package com.example.simpleinstagram;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private ArrayList<User> users;

    UserAdapter(ArrayList<User> users) {
        this.users = users;
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView text;

        UserViewHolder(View view) {
            super(view);
            photo = view.findViewById(R.id.photo);
            text = view.findViewById(R.id.text);
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.photo.setImageBitmap(users.get(position).getPhoto());
        holder.text.setText(users.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}

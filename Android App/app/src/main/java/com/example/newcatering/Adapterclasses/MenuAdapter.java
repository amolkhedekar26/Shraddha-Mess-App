package com.example.newcatering.Adapterclasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newcatering.R;
import com.example.newcatering.datapackages.Menu;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {
    Menu menu;
    ArrayList<Menu> menuArrayList;

    public MenuAdapter(ArrayList<Menu> menuArrayList) {
        this.menuArrayList = menuArrayList;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_card, parent, false);
        MenuViewHolder menuViewHolder = new MenuViewHolder(v);
        return menuViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        menu = menuArrayList.get(position);
        holder.day.setText(menu.getDay());
        holder.noon.setText(menu.getAfternoon());
        holder.night.setText(menu.getNight());
    }

    @Override
    public int getItemCount() {
        return menuArrayList.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView day, noon, night;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day_name);
            noon = itemView.findViewById(R.id.noon_menu);
            night = itemView.findViewById(R.id.night_menu);

        }
    }
}

package com.fyp.kitchenpal.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.kitchenpal.Model.ShoppingItem;
import com.fyp.kitchenpal.R;
import com.fyp.kitchenpal.Views.ShoppingActivity;

import java.util.List;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ShoppingViewHolder> {

    List<ShoppingItem> list;
    private Context context;

    public ShoppingAdapter(List<ShoppingItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ShoppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_cardview, parent, false);
        ShoppingViewHolder holder = new ShoppingViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingViewHolder holder, final int position) {
        holder.itemName.setText(list.get(holder.getAdapterPosition()).getItemName());
//        String id = String.valueOf(list.get(holder.getAdapterPosition()).getId());
        holder.itemID.setText(String.valueOf(position+1));
        holder.itemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShoppingItem item = list.get(position);
                if (context instanceof ShoppingActivity) {
                    ((ShoppingActivity)context).deleteItem(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public ShoppingItem getItemAtPosition(int position) {
        return list.get(position);
    }


    public void addItems(List<ShoppingItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ShoppingViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemID;
        ImageButton itemDelete;

        public ShoppingViewHolder(@NonNull View itemView) {
            super(itemView);
//            itemView.setOnClickListener(this);
            itemName = itemView.findViewById(R.id.shoppingItemTV);
            itemID = itemView.findViewById(R.id.itemIdTV);
            itemDelete = itemView.findViewById(R.id.deleteBT);
        }
    }

}

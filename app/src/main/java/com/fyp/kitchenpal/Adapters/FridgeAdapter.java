package com.fyp.kitchenpal.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.kitchenpal.Model.FridgeItem;
import com.fyp.kitchenpal.R;
import com.fyp.kitchenpal.ViewModels.DateConverter;
import com.fyp.kitchenpal.Views.FridgeActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FridgeAdapter extends RecyclerView.Adapter<FridgeAdapter.FridgeViewHolder> {

    private List<FridgeItem> items;
    private DateConverter dateConverter;
    private Context context;
    private int themeID;

    public FridgeAdapter(List<FridgeItem> items, Context context, DateConverter dateConverter) {
        this.dateConverter = dateConverter;
        this.items = items;
        this.context = context;
    }


    //  layout used for each food in the list
    @NonNull
    @Override
    public FridgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new FridgeViewHolder(view);

    }

    // binds data to the cards in the recyclerview.
    @Override
    public void onBindViewHolder(@NonNull final FridgeViewHolder holder, final int position) {
        holder.food_Name.setText(items.get(holder.getAdapterPosition()).getFridgeName());

//        Long dateAdded = items.get(holder.getAdapterPosition()).getStartDate();
//        holder.start_Date.setText(dateConverter.getAddedString(dateAdded));

        Long expiryDate = items.get(holder.getAdapterPosition()).getExpiryDate();
//        String toDisplay = dateConverter.getExpiryString(expiryDate);
//        holder.expiration_Date.setText(toDisplay);

        Bitmap bitmap = getBitmapFromEncodedString(items.get(holder.getAdapterPosition()).getBytes());
        holder.categoryImage.setImageBitmap(bitmap);

        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FridgeItem item = items.get(position);
                if(context instanceof FridgeActivity){
                    ((FridgeActivity)context).deleteFoodItem(item);
                }
            }
        });

        String toDisplay = dateConverter.getExpiryString(expiryDate);
        holder.expiration_Date.setText(toDisplay);
        Map<String, Integer> colors = generateColors();

        switch (toDisplay) {
            case "Tomorrow":
                setColor(holder, colors.get(toDisplay));
                break;
            case "Expired!!":
                setColor(holder, colors.get(toDisplay));
                break;
            case "Today":
                setColor(holder, colors.get(toDisplay));
                break;
            default:
                setColor(holder, colors.get("Default"));
        }
    }

    private Bitmap getBitmapFromEncodedString(String encodedString){

        byte[] arr = Base64.decode(encodedString, Base64.URL_SAFE);

        return BitmapFactory.decodeByteArray(arr, 0, arr.length);
    }

    private void setColor(FridgeViewHolder holder, int color){
        holder.expiration_Date.setTextColor(ContextCompat.getColor(context,color));
        holder.expiryColor.setBackgroundColor(ContextCompat.getColor(context,color));
    }

    private Map<String,Integer> generateColors(){
        Map<String,Integer> colorMap = new HashMap<>();
        colorMap.put("Expired!!",R.color.light_mode_red);
        colorMap.put("Today",R.color.light_mode_red);
        colorMap.put("Tomorrow",R.color.light_mode_amber);
        colorMap.put("Default",R.color.light_mode_green);
        return colorMap;
    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    public FridgeItem getItemAtPosition(int position) {
        return items.get(position);
    }

    public void addItems(List<FridgeItem> List) {
        this.items = List;
        notifyDataSetChanged();
    }


    // non static to allow access to enclosing fields/methods
    public class FridgeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView food_Name;
        TextView start_Date;
        TextView expiration_Date;
        CardView cardView;
        ImageView expiryColor;
        ImageView categoryImage;
        ImageButton btndelete;

        public FridgeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            food_Name = itemView.findViewById(R.id.food_name);
//            start_Date = itemView.findViewById(R.id.date_added);
            expiration_Date = itemView.findViewById(R.id.expiration_date);
            cardView = itemView.findViewById(R.id.card_view);
            expiryColor = itemView.findViewById(R.id.expiry_color);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            btndelete = itemView.findViewById(R.id.btndelete);
        }


        //button on click listener

        @Override
        public void onClick(View v) {
        /*    FridgeItem item = getItemAtPosition(getAdapterPosition());
            Intent intent = new Intent(v.getContext(), AddActivity.class);
            intent.putExtra("id", item.getId());
            intent.putExtra("name", item.getFridgeName());
            intent.putExtra("added", item.getStartDate());
            intent.putExtra("expiry", item.getExpiryDate());*/
//            v.getContext().startActivity(intent);
        }
    }
}

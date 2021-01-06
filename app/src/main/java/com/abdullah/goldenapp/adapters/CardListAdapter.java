package com.abdullah.goldenapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abdullah.goldenapp.R;
import com.abdullah.goldenapp.models.CardElementData;

import java.util.List;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<CardElementData> data;

    public CardListAdapter(Context context, List<CardElementData> cardDataList){
        this.context = context;
        this.mInflater = LayoutInflater.from(this.context);
        this.data = cardDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = mInflater.inflate(R.layout.card_element, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(data.get(position).getTitle());
        holder.price.setText(data.get(position).getPrice()+" ريال ");
        holder.changeRate.setText(data.get(position).getChangePrice()+" ("+data.get(position).getChangeRate()+")٪");
        if(data.get(position).getChangeRate()>0){ // positive
            holder.iconUp.setVisibility(View.VISIBLE);
        } else {
            holder.iconDown.setVisibility(View.VISIBLE);
        }
        holder.weight.setText(data.get(position).getWeight());
        holder.k21.setText(data.get(position).getK21());
        holder.k22.setText(data.get(position).getK18());
        holder.k24.setText(data.get(position).getK24());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView price;
        TextView weight;
        TextView changeRate;
        TextView k21;
        TextView k22;
        TextView k24;
        ImageView iconUp;
        ImageView iconDown;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            weight = itemView.findViewById(R.id.weight);
            changeRate = itemView.findViewById(R.id.changeRate);
            iconDown = itemView.findViewById(R.id.iconDown);
            iconUp = itemView.findViewById(R.id.iconUp);
            k21 = itemView.findViewById(R.id.k21);
            k22 = itemView.findViewById(R.id.k18);
            k24 = itemView.findViewById(R.id.k24);

        }
    }
}

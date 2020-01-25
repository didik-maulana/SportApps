package com.codingtive.consumer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codingtive.consumer.R;
import com.codingtive.consumer.model.Sport;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SportAdapter extends RecyclerView.Adapter<SportAdapter.SportViewHolder> {

    private List<Sport> sports = new ArrayList<>();


    public void setSportList(List<Sport> sports) {
        this.sports = sports;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sport_layout, parent, false);
        return new SportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SportViewHolder holder, int position) {
        holder.bindItem(sports.get(position));
    }

    @Override
    public int getItemCount() {
        return sports.size();
    }

    class SportViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView sportImageView;
        private TextView titleTextView;

        SportViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_sport);
            sportImageView = itemView.findViewById(R.id.img_sport);
            titleTextView = itemView.findViewById(R.id.tv_sport_title);
        }

        void bindItem(Sport sport) {
            titleTextView.setText(sport.getStrSport());

            Glide.with(itemView.getContext())
                    .load(sport.getStrSportThumb())
                    .centerCrop()
                    .into(sportImageView);
        }
    }
}

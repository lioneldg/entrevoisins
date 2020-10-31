package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyNeighbourRecyclerViewAdapter.ViewHolder> {

    private final List<Neighbour> mNeighbours;

    public MyNeighbourRecyclerViewAdapter(List<Neighbour> items) {
        mNeighbours = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_neighbour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);

        //mise en place d'un clickListener pour chaque item
        holder.itemView.setOnClickListener(v -> {
            Intent myIntent = new Intent(v.getContext(), DetailActivity.class);                            //création d'un intent
            myIntent.putExtra("id", neighbour.getId());
            myIntent.putExtra("aboutMe", neighbour.getAboutMe());                             //passage d’informations
            myIntent.putExtra("address", neighbour.getAddress());
            myIntent.putExtra("name", neighbour.getName());
            myIntent.putExtra("avatarUrl", neighbour.getAvatarUrl());
            myIntent.putExtra("phoneNumber", neighbour.getPhoneNumber());
            v.getContext().startActivity(myIntent);                                                        //startActivity à partir du contexte récupéré
        });


        holder.mDeleteButton.setOnClickListener(v -> EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour)));
    }

    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

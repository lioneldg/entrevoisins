package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

public class DetailActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_neighbour);

        Long id = getIntent().getLongExtra("id", -1);
        String name = getIntent().getStringExtra("name");
        String address = getIntent().getStringExtra("address");
        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        String aboutMe = getIntent().getStringExtra("aboutMe");
        String avatarUrl = getIntent().getStringExtra("avatarUrl");
        final Boolean[] isFavorite = {DI.getNeighbourApiService().isFavoriteNeighbour(id)};

        ImageView avatar;
        ImageButton backButton;
        FloatingActionButton favorite;
        TextView firstName, firstNameInWhiteView, addressTextView, phoneNumberTextView, aboutMeTextView, facebook;

        avatar = (ImageView) findViewById(R.id.photo);
        Glide.with(avatar.getContext())
                .load(avatarUrl)
                .apply(RequestOptions.centerCropTransform())
                .into(avatar);

        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.getBackground().setAlpha(0);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        favorite = (FloatingActionButton) findViewById(R.id.favoriteButton);
        favorite.setImageResource(isFavorite[0] ? R.drawable.ic_star_white_24dp : R.drawable.ic_star_border_white_24dp);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavorite[0] = !isFavorite[0];
                favorite.setImageResource(isFavorite[0] ? R.drawable.ic_star_white_24dp : R.drawable.ic_star_border_white_24dp);
                Neighbour neighbour = DI.getNeighbourApiService().findNeighbourById(id);                                    //retrouve ce contact grace à l'id
                if(isFavorite[0]){                                                                                          //ajoute ou supprime de la liste en fct de isFavorite[0]
                    DI.getNeighbourApiService().addFavoriteNeighbour(neighbour);
                }
                else{
                    DI.getNeighbourApiService().deleteFavoriteNeighbour(neighbour);
                }
            }
        });

        firstName = (TextView)findViewById(R.id.firstName);
        firstName.setText(name);

        firstNameInWhiteView = (TextView) findViewById(R.id.firstNameInViewWhite);
        firstNameInWhiteView.setText(name);

        addressTextView = (TextView) findViewById(R.id.address);
        addressTextView.setText("   "+address);

        phoneNumberTextView = (TextView)findViewById(R.id.phoneNumber);
        phoneNumberTextView.setText("   "+phoneNumber);

        aboutMeTextView = (TextView)findViewById(R.id.description);
        aboutMeTextView.setText(aboutMe);

        facebook = (TextView) findViewById(R.id.facebook);
        facebook.setText("   www.facebook.fr/"+name.toLowerCase());
    }
}

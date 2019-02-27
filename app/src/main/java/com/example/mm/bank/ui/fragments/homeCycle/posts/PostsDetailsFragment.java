package com.example.mm.bank.ui.fragments.homeCycle.posts;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mm.bank.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.mm.bank.ui.fragments.homeCycle.posts.OnItemPostDetailsSend.POST_TITLE;
import static com.example.mm.bank.ui.fragments.homeCycle.posts.OnItemPostDetailsSend.THUMBNAIL_FULL_PATH;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsDetailsFragment extends Fragment {


    @BindView(R.id.Posts_Details_ImageView)
    ImageView PostsDetailsImageView;
    @BindView(R.id.Posts_Details_TV_Title)
    TextView PostsDetailsTVTitle;
    Unbinder unbinder;

    public PostsDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts_details, container, false);
        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = getArguments();

        Glide.with(getContext())
                .load(bundle.getString(THUMBNAIL_FULL_PATH))
                .into(PostsDetailsImageView);

        PostsDetailsTVTitle.setText(bundle.getString(POST_TITLE));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

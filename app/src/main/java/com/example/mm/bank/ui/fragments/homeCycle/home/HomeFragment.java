package com.example.mm.bank.ui.fragments.homeCycle.home;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mm.bank.R;
import com.example.mm.bank.adapter.HomeFragmentAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @BindView(R.id.Home_Fragment_TabLayout_tabs)
    TabLayout HomeFragmentTabLayoutTabs;
    @BindView(R.id.Home_Fragment_ViewPager)
    ViewPager HomeFragmentViewPager;
    Unbinder unbinder;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);



        setupViewPager(HomeFragmentViewPager);
        HomeFragmentTabLayoutTabs.setupWithViewPager(HomeFragmentViewPager);
        return view;
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        HomeFragmentAdapter adapter = new HomeFragmentAdapter(getChildFragmentManager());
        adapter.addFragment(new OrdersFragment(), "Orders");
        adapter.addFragment(new PostsFragment(), "Posts");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

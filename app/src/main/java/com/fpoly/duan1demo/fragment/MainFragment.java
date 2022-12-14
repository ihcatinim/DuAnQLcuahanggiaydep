package com.fpoly.duan1demo.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fpoly.duan1demo.R;
import com.fpoly.duan1demo.adapter.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    ViewPager2 viewPager2;
    TabLayout tabLayout;
    FragmentAdapter adapter;

    public MainFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.TabLayout);
        viewPager2 = view.findViewById(R.id.ViewPager2);
        adapter = new FragmentAdapter(getActivity());
        viewPager2.setAdapter(adapter);

        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setIcon(R.drawable.iconbanhang); //???nh t????ng ???ng
                        tab.setText("B??n h??ng"); //T??n t????ng ???ng
                        break;
                    case 1:
                        tab.setIcon(R.drawable.iconhoadon); //???nh t????ng ???ng
                        tab.setText("H??a ????n"); //T??n t????ng ???ng
                        break;
                    case 2:
                        tab.setIcon(R.drawable.iconthem); //???nh t????ng ???ng
                        tab.setText("Th??m"); //T??n t????ng ???ng
                        break;
                    //T????ng t???
                }
            }
        });
        mediator.attach();
    }
}
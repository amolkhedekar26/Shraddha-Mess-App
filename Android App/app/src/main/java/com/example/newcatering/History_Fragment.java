package com.example.newcatering;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.newcatering.animation.DepthTransformation;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.newcatering.Constants.Constant.MONTH_NAME;
import static com.example.newcatering.Constants.Constant.MY_PREF_NAME;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link History_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class History_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public History_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment History.
     */
    // TODO: Rename and change types and number of parameters
    public static History_Fragment newInstance(String param1, String param2) {
        History_Fragment fragment = new History_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.history_viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) view.findViewById(R.id.historytabs);
        tabs.setupWithViewPager(viewPager);
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        final Adapter adapter = new Adapter(getChildFragmentManager());
        DepthTransformation depthTransformation = new DepthTransformation();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(new Date().getMonth(),true);
        viewPager.setPageTransformer(true,depthTransformation);

    }

    static class Adapter extends FragmentPagerAdapter {
        List<String> months = Arrays.asList("January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October", "November", "December");
        Date mDate = new Date(System.currentTimeMillis());
        Adapter(FragmentManager manager) {
            super(manager);

        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            for(int i=0;i<mDate.getMonth()+1;i++)
            {
                if(position==i){
                    fragment=new Table(months.get(i));
                }
            }
            assert fragment != null;
            return fragment;
        }

        @Override
        public int getCount() {
            return mDate.getMonth()+1;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return months.get(position);
        }
    }


}

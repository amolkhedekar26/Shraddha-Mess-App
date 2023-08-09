package com.example.newcatering;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newcatering.api.cateringapi;
import com.example.newcatering.datapackages.Attendance;
import com.example.newcatering.datapackages.Post_Put_Response;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.newcatering.Constants.Constant.MONTH_NAME;
import static com.example.newcatering.Constants.Constant.MY_PREF_NAME;
import static com.example.newcatering.Constants.Constant.USER_TOKEN;
import static com.example.newcatering.Constants.Constant.retrofit;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Table#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Table extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SharedPreferences SP;
    String token;
    String mon;
    TableLayout historytable;
    SwipeRefreshLayout swipeRefreshLayout;
    public Table() {
        // Required empty public constructor
    }
    public Table(String month) {
        this.mon=month;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Table.
     */
    // TODO: Rename and change types and number of parameters
    public Table newInstance(String param1, String param2) {
        Table fragment = new Table();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        historytable = view.findViewById(R.id.history_table);
        swipeRefreshLayout=view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadHistory();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        loadHistory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_table, container, false);
    }
    private void loadHistory()
    {
        SP = getActivity().getSharedPreferences(MY_PREF_NAME, MODE_PRIVATE);
        if (SP.contains(USER_TOKEN)) {
            token = SP.getString(USER_TOKEN, null);
        }
        final cateringapi cateringapi = retrofit.create(cateringapi.class);
        Call<List<Attendance>> call = cateringapi.getAttendance("Token " + token, this.mon);
        call.enqueue(new Callback<List<Attendance>>() {
            @Override
            public void onResponse(Call<List<Attendance>> call, Response<List<Attendance>> response) {
                List<Attendance> attendanceList = response.body();
                assert attendanceList != null;
                for (Attendance attendance : attendanceList) {
                    TableRow tableRow = new TableRow(getContext());
                    TableLayout.LayoutParams tableRowParams =
                            new TableLayout.LayoutParams
                                    (TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT,3f);

                    int leftMargin = 0;
                    int topMargin = 20;
                    int rightMargin = 0;
                    int bottomMargin = 10;
                    View v = new View(getContext());
                    v.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            2
                    ));

                    v.setBackgroundColor(getResources().getColor(R.color.dark_gray_1));
                    tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
                    tableRow.setLayoutParams(tableRowParams);
                    TextView date = new TextView(getContext());
                    TableRow.LayoutParams params2 = new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
                    date.setLayoutParams(params2);
                    date.setText(attendance.getDate());
                    date.setTextColor(getResources().getColor(R.color.black));
                    date.setGravity(Gravity.CENTER);
                    ArrayList<String> attend = (ArrayList<String>) attendance.getAttendance();
                    TextView afternoon = new TextView(getContext());
                    TextView night = new TextView(getContext());
                    TableRow.LayoutParams params3 = new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
                    TableRow.LayoutParams params4 = new TableRow.LayoutParams(0, TableLayout.LayoutParams.WRAP_CONTENT, 1f);
                    afternoon.setLayoutParams(params3);
                    night.setLayoutParams(params4);
                    if(attend.get(0).equals("0"))
                    {
                        afternoon.setText("Absent");
                        afternoon.setTextColor(Color.RED);
                    }
                    else
                    {
                        afternoon.setText("Present");
                        afternoon.setTextColor(getResources().getColor(R.color.black));
                    }
                    afternoon.setGravity(Gravity.CENTER);
                    if(attend.get(1).equals("0"))
                    {
                        night.setText("Absent");
                        night.setTextColor(Color.RED);
                    }
                    else
                    {
                        night.setText("Present");
                        night.setTextColor(getResources().getColor(R.color.black));
                    }
                    night.setGravity(Gravity.CENTER);
                    tableRow.addView(date);
                    tableRow.addView(afternoon);
                    tableRow.addView(night);
                    historytable.addView(tableRow);
                    historytable.addView(v);
                }
            }

            @Override
            public void onFailure(Call<List<Attendance>> call, Throwable t) {

            }
        });
    }
}

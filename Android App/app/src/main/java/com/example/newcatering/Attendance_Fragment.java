package com.example.newcatering;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newcatering.api.cateringapi;
import com.example.newcatering.datapackages.Post_Put_Response;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.newcatering.Constants.Constant.MY_PREF_NAME;
import static com.example.newcatering.Constants.Constant.USER_TOKEN;
import static com.example.newcatering.Constants.Constant.retrofit;
import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Attendance_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Attendance_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Calendar myCalendar;
    private DatePicker mydatepicker;
    private EditText datepicker;
    private Switch noon, night;
    private Button save;
    private SharedPreferences SP;
    private DatePickerDialog.OnDateSetListener date;
    private String a = "1", b = "1", mydate, token, current_Date;
    private TextView noondanger, nightdanger;
    private int morning_limit = 10, evening_limit = 16;

    public Attendance_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Attendance.
     */
    // TODO: Rename and change types and number of parameters
    public static Attendance_Fragment newInstance(String param1, String param2) {
        Attendance_Fragment fragment = new Attendance_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        myCalendar = Calendar.getInstance();
        mydatepicker = new DatePicker(getContext());
        datepicker = view.findViewById(R.id.datepicker);
        noon = view.findViewById(R.id.switchnoon);
        night = view.findViewById(R.id.switchnight);
        save = view.findViewById(R.id.save_attndance);
        noondanger = view.findViewById(R.id.afternoondangertext);
        nightdanger = view.findViewById(R.id.nightdangertext);
        updateDate();
        date = new DatePickerDialog.OnDateSetListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
                checkLimit();
            }

        };

        datepicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stubs
                DatePickerDialog dpd = new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dpd.getDatePicker().setMinDate(new Date().getTime());
                dpd.show();
            }
        });
        SP = getActivity().getSharedPreferences(MY_PREF_NAME, MODE_PRIVATE);
        if (SP.contains(USER_TOKEN)) {
            token = SP.getString(USER_TOKEN, null);
        }
        mydate = datepicker.getText().toString();
        String myFormat = "MMM dd, yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        current_Date = sdf.format(new Date());
        Date dd = new Date();
        if (mydate.equals(current_Date) && dd.getHours() >= morning_limit) {
            noon.setEnabled(false);
            noondanger.setVisibility(View.VISIBLE);
        }
        if (mydate.equals(current_Date) && dd.getHours() >= evening_limit) {
            night.setEnabled(false);
            nightdanger.setVisibility(View.VISIBLE);
        }
        if (!noon.isEnabled() && !night.isEnabled()) {
            save.setEnabled(false);
        }
        PushDownAnim.setPushDownAnimTo(save)
                .setScale(MODE_SCALE, 1.03f)
                .setDurationPush(80)
                .setDurationRelease(80)
                .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (noon.isChecked()) {
                            a = "0";
                        } else {
                            a = "1";
                        }
                        if (night.isChecked()) {
                            b = "0";
                        } else {
                            b = "1";
                        }
                        mydate = datepicker.getText().toString();
                        final cateringapi cateringapi = retrofit.create(cateringapi.class);
                        Call<Post_Put_Response> call = cateringapi.save_attendance("Token " + token, mydate, a, b);
                        call.enqueue(new Callback<Post_Put_Response>() {
                            @Override
                            public void onResponse(Call<Post_Put_Response> call, Response<Post_Put_Response> response) {
                                Post_Put_Response result = response.body();
                                assert result != null;
                                if (result.getResult().equals("Success")) {
                                    Toast.makeText(getContext(), "Attendnace Saved", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Post_Put_Response> call, Throwable t) {

                            }
                        });
                    }
                });
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
        return inflater.inflate(R.layout.fragment_attendance, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateDate() {
        String myFormat = "MMM dd, yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        datepicker.setText(sdf.format(myCalendar.getTime()));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void checkLimit() {
        mydate = datepicker.getText().toString();
        String myFormat = "MMM dd, yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        current_Date = sdf.format(new Date());
        Date dd = new Date();
        if (mydate.equals(current_Date) && dd.getHours() >= morning_limit) {
            noon.setEnabled(false);
            noondanger.setVisibility(View.VISIBLE);
        } else {
            noon.setEnabled(true);
            noondanger.setVisibility(View.INVISIBLE);
            save.setEnabled(true);
        }
        if (mydate.equals(current_Date) && dd.getHours() >= evening_limit) {
            night.setEnabled(false);
            nightdanger.setVisibility(View.VISIBLE);
        } else {
            night.setEnabled(true);
            save.setEnabled(true);
            nightdanger.setVisibility(View.INVISIBLE);
        }
        if (!noon.isEnabled() && !night.isEnabled()) {
            save.setEnabled(false);
        }
    }
}

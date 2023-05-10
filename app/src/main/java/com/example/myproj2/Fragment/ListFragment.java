package com.example.myproj2.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myproj2.Adapters.RecordAdapter;
import com.example.myproj2.Models.Record;
import com.example.myproj2.R;
import com.example.myproj2.Utilities.ImageLoader;
import com.example.myproj2.Utilities.MySP3;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.util.ArrayList;

public class ListFragment extends Fragment {

    private RecyclerView recordRV;

    private RecordAdapter recordAdapter;
    private View view;

    private AppCompatImageView background;

    public ListFragment(Record record) {

        ArrayList<Record> recyclerViewData = new ArrayList<>();
        recyclerViewData.add(record);

        recordAdapter = new RecordAdapter(recyclerViewData);
    }

    public RecordAdapter getRecordAdapter() {
        return recordAdapter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews();
        ImageLoader.getInstance().loadImage(R.drawable.road, background);
        return view;
    }

    private void initViews() {
        getLstFromJson();
        putLstInJson();
        recordAdapter.TopTenRecords();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext().getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recordRV.setAdapter(recordAdapter);
        recordRV.setLayoutManager(linearLayoutManager);

    }

    private void findViews(View view) {
        recordRV = view.findViewById(R.id.main_LST_records);
        background = view.findViewById(R.id.fragment_list_IMG_background);
    }

    public void getLstFromJson() {
        String jsonData = MySP3.getInstance().getString("Project2", "");
        if (!jsonData.isEmpty()) {
            Gson gson = new Gson();
            recordAdapter.setRecordLst(gson.fromJson(jsonData, new TypeToken<ArrayList<Record>>() {
            }.getType()));

        }

    }

    public void putLstInJson() {
        Gson gson = new Gson();
        String jsonData = gson.toJson(recordAdapter.getRecordLst());
        MySP3.getInstance().putString("Project2", jsonData);
    }

}






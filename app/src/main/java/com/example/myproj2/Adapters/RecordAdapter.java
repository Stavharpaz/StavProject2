package com.example.myproj2.Adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myproj2.Interfaces.RecordCallback;



import com.example.myproj2.R;
import com.example.myproj2.Models.Record;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordHolder> {

    private ArrayList<Record> recordLst;
    public RecordAdapter(ArrayList<Record> recordLst) {
        if(recordLst!=null)
            this.recordLst = recordLst;
        else
            this.recordLst=new ArrayList<>();



    }

    private RecordCallback recordCallback;

    public ArrayList<Record> getRecordLst() {
        return recordLst;
    }

    public void setRecordLst(ArrayList<Record> recordLst) {
        this.recordLst.addAll(recordLst);

    }





    public void setRecordCallback(RecordCallback recordCallback) {
        this.recordCallback = recordCallback;
    }

    @NonNull
    @Override
    public RecordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_item, parent, false);
        RecordHolder recordHolder = new RecordHolder(view);
        return recordHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordHolder holder, int position) {
        Record record = getItem(position);
        holder.RecordName.setText(record.getName());
        holder.RecordDistance.setText(record.getDistance() + "");

    }

    public void TopTenRecords(){
        Collections.sort(recordLst, new Comparator<Record>() {
            @Override
            public int compare(Record r1, Record r2) {
                return Integer.compare(r1.getDistance(), r2.getDistance());
            }
        });
        Collections.reverse(recordLst);
        for(int i= recordLst.size()-1; i>=10; i--)
            recordLst.remove(i);
    }

    @Override
    public int getItemCount() {
        return this.recordLst == null ? 0 : this.recordLst.size();
    }

    private Record getItem(int position) {
        return this.recordLst.get(position);
    }

    public class RecordHolder extends RecyclerView.ViewHolder {
        private MaterialTextView RecordName;
        private MaterialTextView RecordDistance;



        public RecordHolder(@NonNull View itemView) {
            super(itemView);

            RecordDistance = itemView.findViewById(R.id.record_LBL_record);
            RecordName = itemView.findViewById(R.id.record_LBL_name);

            itemView.setOnClickListener(v -> {
                if (recordCallback != null)
                    recordCallback.itemClicked(getItem(getAdapterPosition()));
            });
        }
    }
}

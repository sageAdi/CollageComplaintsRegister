package com.example.complaintsregisterpart2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class Adapter extends FirestoreRecyclerAdapter<Model,Adapter.ViewHolder> {

    private OnItemClickListener listener;
    public static final int No_Position=-1;
    public Adapter(@NonNull FirestoreRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull final Model model) {

        holder.roomDetail.setText(model.getRoomDetail());
        holder.complaint.setText(model.getComplaint());
        holder.hostel_name.setText(model.getHostel());
        holder.date.setText(model.getDate());
        holder.electrician_name.setText(model.getElectrician());
        if (model.getStatus().equals("0")){
            holder.ch1.setChecked(true);
        }
        else if (model.getStatus().equals("1")){
            holder.ch1.setChecked(true);
            holder.ch2.setChecked(true);
        }
        else {
            holder.ch1.setChecked(true);
            holder.ch2.setChecked(true);
            holder.ch3.setChecked(true);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView roomDetail,complaint,hostel_name,date,electrician_name;
        CheckBox ch1,ch2,ch3;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomDetail=itemView.findViewById(R.id.roomDetailsText);
            complaint=itemView.findViewById(R.id.complaintDetailsText);
            hostel_name=itemView.findViewById(R.id.hostel_details);
            date=itemView.findViewById(R.id.date_details);
            electrician_name=itemView.findViewById(R.id.electricianName);
            ch1=itemView.findViewById(R.id.check1);
            ch2=itemView.findViewById(R.id.check2);
            ch3=itemView.findViewById(R.id.check3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION && listener!=null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot,int position);
    }
    public void setOnClickListener(OnItemClickListener listener ){
        this.listener=listener;
    }
}

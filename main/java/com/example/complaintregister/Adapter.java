package com.example.complaintregister;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class Adapter extends FirestoreRecyclerAdapter<Model,Adapter.ViewHolder> {
    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

    public Adapter(@NonNull FirestoreRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Model model) {

        if (model.getEmail().equals(firebaseUser.getEmail())){
            holder.roomDetail.setText(model.getRoomDetail());
            holder.complaint.setText(model.getComplaint());
            holder.electName.setText(model.getElectrician());
            holder.otp.setText(model.getOtp());
            holder.date.setText(model.getDate());
            holder.hostel.setText(model.getHostel());
            if (model.getElectrician()!=null){
                holder.electName.setVisibility(View.VISIBLE);
                holder.electName.setText(model.getElectrician());
            }

            if (model.getStatus().equals("0")){
                holder.ch1.setChecked(true);
            }
            if (model.getStatus().equals("1")){
                holder.ch1.setChecked(true);
                holder.ch2.setChecked(true);
            }
            if (model.getStatus().equals("2")){
                holder.ch1.setChecked(true);
                holder.ch2.setChecked(true);
                holder.ch3.setChecked(true);
            }
        }
        else {
            //Delete document
            //getSnapshots().getSnapshot(position).getReference().delete();
            holder.cardView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView roomDetail,complaint,electName,otp,date,hostel;
        CheckBox ch1,ch2,ch3;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomDetail=itemView.findViewById(R.id.roomDetailsText);
            complaint=itemView.findViewById(R.id.complaintDetailsText);
            electName=itemView.findViewById(R.id.electricianName);
            date=itemView.findViewById(R.id.complaintDate);
            otp=itemView.findViewById(R.id.otpName);
            ch1=itemView.findViewById(R.id.check1);
            ch2=itemView.findViewById(R.id.check2);
            ch3=itemView.findViewById(R.id.check3);
            cardView=itemView.findViewById(R.id.linear);
            hostel=itemView.findViewById(R.id.hostelName);
        }
    }
}

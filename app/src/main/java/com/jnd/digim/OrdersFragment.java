package com.jnd.digim;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class OrdersFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_fragment,container,false);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders").child(firebaseUser.getDisplayName()+"_"+firebaseUser.getEmail().substring(0,firebaseUser.getEmail().indexOf("."))+"_"+firebaseUser.getUid());
        databaseReference.keepSynced(true);
        recyclerView = (RecyclerView)view.findViewById(R.id.orderRecycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<OrderGet,OrderViewHolder>FirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<OrderGet, OrderViewHolder>
                (OrderGet.class,R.layout.order_card,OrderViewHolder.class,databaseReference) {
            @Override
            protected void populateViewHolder(OrderViewHolder orderViewHolder, OrderGet orderGet, int i) {
                orderViewHolder.setOrderType(orderGet.getOrderType());
                orderViewHolder.setOrder(orderGet.getOrder());
                orderViewHolder.setOrderId(orderGet.getOrderId());
                orderViewHolder.setTransactionId(orderGet.getTransactionId());
                orderViewHolder.setUrlLink(orderGet.getUrlLink());
                orderViewHolder.setTimeStamp(orderGet.getTimeStamp());
                orderViewHolder.setOrderReviewed(orderGet.getOrderReviewed());
            }
        };
        recyclerView.setAdapter(FirebaseRecyclerAdapter);
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        public OrderViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
        public void setOrderType(String orderType){
            TextView ordersType = (TextView)view.findViewById(R.id.orderTypeTxt);
            ordersType.setText("Order type           -      " + orderType);
        }
        public void setOrder(String order){
            TextView orders = (TextView)view.findViewById(R.id.orderTxt);
            orders.setText("Order                    -      " + order);
        }
        public void setOrderId(String orderId){
            TextView ordersId = (TextView)view.findViewById(R.id.orderIdTxt);
            ordersId.setText("Order id                -      " + orderId);
        }
        public void setTransactionId(String transactionId){
            TextView ordersTransaction = (TextView)view.findViewById(R.id.transactionIdTxt);
            ordersTransaction.setText("Transaction id    -      " + transactionId);
        }
        public void setUrlLink(String urlLink){
            TextView ordersURL = (TextView)view.findViewById(R.id.urlTxt);
            ordersURL.setText("URL                       -      " + urlLink);
        }
        public void setTimeStamp(long timeStamp){
            TextView ordersTime = (TextView)view.findViewById(R.id.orderTimeTxt);
            ordersTime.setText("Order time           -      " + TimeAgo.from(timeStamp));
        }
        public void setOrderReviewed(boolean orderReviewed){
            TextView review = (TextView)view.findViewById(R.id.orderStatusTxt);
            if(orderReviewed == true){
                review.setText(Html.fromHtml("Status &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;" + "<font color=green>" + "Approved" + "</font>"));
            }
            else{
                review.setText(Html.fromHtml("Status &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- &nbsp;&nbsp;&nbsp;&nbsp;" + "<font color=red>" + "Pending" + "</font>"));
            }
        }
    }
}

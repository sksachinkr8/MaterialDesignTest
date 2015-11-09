package com.sachinkumar.materialdesigntest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by sachinkumar on 06/11/15.
 */
public class NavAdapter extends RecyclerView.Adapter<NavAdapter.MyViewHolder> {

    List<Information> data = Collections.emptyList();

    private LayoutInflater inflater;

    private Context context;

    private ClickListener clickListener; //To use the itemClicked method inside the adapter

    public NavAdapter(Context context, List<Information> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_custom_row, parent, false);
//        Log.d("VIVZ", "onCreateViewHolder called");
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Information current = data.get(position);
//        Log.d("VIVZ", "onBindViewHolder called " + position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);

//        holder.icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Item clicked at "+position, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    public void setClickListener(ClickListener clickListener) { //Here you get a reference to the fragment
        this.clickListener = clickListener;                     // which is stored in this clickListener variable.
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);

        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(context, "Item clicked at "+getPosition(), Toast.LENGTH_SHORT).show();
//            context.startActivity(new Intent(context, SubActivity.class));

            if (clickListener!=null) {
                clickListener.itemClicked(v, getPosition());
            }
        }
    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }
}





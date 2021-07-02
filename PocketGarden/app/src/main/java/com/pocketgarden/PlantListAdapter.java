package com.pocketgarden;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PlantListAdapter extends RecyclerView.Adapter<PlantListAdapter.ViewHolder>{

    private ArrayList<Plant> plantList;
    private LayoutInflater mInflater;
    File internalDir;
    Context c;
    OnDataChangeListener mOnDataChangeListener;

    // data is passed into the constructor
    PlantListAdapter(Context context, ArrayList<Plant> data, File internalStorageDir) {
        this.mInflater = LayoutInflater.from(context);
        this.plantList = data;
        c = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.plantlist_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the text view in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Plant p = plantList.get(position);
        String name = p.plantName;
        if(getItem(position).isOutside())
        {
            //holder.outside.setVisibility(View.VISIBLE);
            //holder.inside.setVisibility(View.INVISIBLE);
            //Swaps image to outline version when disabled
            //Swaps image to filled version when enabled
            holder.outside.setImageResource(R.drawable.ic_wb_sunny_24px);
            holder.inside.setImageResource(R.drawable.ic_house_outline_24px_);
        }
        if(getItem(position).isInside())
        {
            //holder.inside.setVisibility(View.VISIBLE);
            //holder.outside.setVisibility(View.INVISIBLE);
            //Swaps image to outline version when disabled
            //Swaps image to filled version when enabled
            holder.inside.setImageResource(R.drawable.ic_house_24px);
            holder.outside.setImageResource(R.drawable.ic_wb_sunny_outlined_24px);
        }
        holder.nametxv.setText(name);
    }

    // get total number of rows
    public int getItemCount() {
        return plantList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nametxv;
        ImageButton edit, outside, inside;

        //constructor
        public ViewHolder(View itemView) {
            super(itemView);
            nametxv = itemView.findViewById(R.id.plantName);
            edit = itemView.findViewById(R.id.editButton);
            inside = itemView.findViewById(R.id.insideButton);
            outside = itemView.findViewById(R.id.outsideButton);

            itemView.setOnClickListener(this);
            edit.setOnClickListener(this);
            inside.setOnClickListener(this);
            outside.setOnClickListener(this);
        }

        //when item is clicked on
        @Override
        public void onClick(View view) {
            if(view == edit)
            {
                Intent i = new Intent(view.getContext(), EditPlantInfo.class);
                i.putExtra("list", plantList);
                i.putExtra("index", getAdapterPosition());
                i.putExtra("previous", "ListActivity");
                view.getContext().startActivity(i);


            }
            else if(view == inside)
            {
                //inside.setVisibility(View.INVISIBLE);
                //outside.setVisibility(View.VISIBLE);
                //Swaps image to outline version when disabled
                //Swaps image to filled version when enabled
                inside.setImageResource(R.drawable.ic_house_24px);
                outside.setImageResource(R.drawable.ic_wb_sunny_outlined_24px);
                plantList.get(getAdapterPosition()).setOutside();
                Toast.makeText(c, plantList.get(getAdapterPosition()).isOutside() + "", Toast.LENGTH_SHORT).show();
                save();

            }
            else if(view == outside)
            {
                //inside.setVisibility(View.VISIBLE);
                //outside.setVisibility(View.INVISIBLE);
                //Swaps image to outline version when disabled
                //Swaps image to filled version when enabled
                inside.setImageResource(R.drawable.ic_house_outline_24px_);
                outside.setImageResource(R.drawable.ic_wb_sunny_24px);

                plantList.get(getAdapterPosition()).setInside();
                Toast.makeText(c, plantList.get(getAdapterPosition()).isOutside() + "", Toast.LENGTH_SHORT).show();
                save();

            }else{
                return;
            }
        }

    }

    // get data at click position
    Plant getItem(int id) {
        return plantList.get(id);
    }

    public interface OnDataChangeListener{
        public void onDataChanged(int size);
    }

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }

    public void save()
    {
        File internalStorageDir = c.getFilesDir();
        File plants = new File(internalStorageDir, "plants_file.csv");
        if(plants.exists())
        {
            //removes the previous file so it does not keep the old plant list in the file
            plants.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(plants);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(plantList);
            oos.close();
        } catch (Exception e) {
            Toast.makeText(c.getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

}
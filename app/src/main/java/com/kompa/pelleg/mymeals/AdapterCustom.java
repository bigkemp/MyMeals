package com.kompa.pelleg.mymeals;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdapterCustom extends ArrayAdapter<Data>  {
    public AdapterCustom(Context context, ArrayList<Data> resource ) {
        super(context,R.layout.row_log ,resource);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getPosition( @Nullable Data item ) {
        return super.getPosition(item);
    }

    @Nullable
    @Override
    public Data getItem( int position ) {
        return super.getItem(position);
    }


    @NonNull
    @Override
    public View getView( int position, @Nullable View convertView, @NonNull ViewGroup parent ) {
        LayoutInflater Inflater = LayoutInflater.from(getContext());
        View Custom;
        Data singleItem = getItem(position);

            Custom = Inflater.inflate(R.layout.row_log, parent,false);

            TextView date = (TextView) Custom.findViewById(R.id.tvDate);
            TextView time = (TextView) Custom.findViewById(R.id.tvTime);
            ImageView image = (ImageView) Custom.findViewById(R.id.ivImage);
         //   TextView comments = (TextView) Custom.findViewById(R.id.tvComments);
            ListView listView = (ListView) Custom.findViewById(R.id.lvComments);
            TextView calories = (TextView) Custom.findViewById(R.id.tvCalories);
            if (singleItem.getComments() != null) {
                String[] split = singleItem.getComments().split("-");
                split = Arrays.copyOfRange(split,1,split.length);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Custom.getContext(),android.R.layout.simple_list_item_1, split);
                listView.setAdapter(adapter);
            }
            try {
                String split2 = singleItem.getCalories();
               // split2 = Arrays.copyOfRange(split2, 1, split2.length);

                calories.setText(split2);
            }catch (Exception e){}

        date.setText(singleItem.getDate());
        time.setText(String.valueOf(singleItem.getTime()));

        File imgFile = new  File(singleItem.getImage());

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            image.setImageBitmap(myBitmap);
        }
     //   comments.setText(String.valueOf(singleItem.getComments()));




        //  Exercise singleItem = getItem(position-1);


        return Custom;
    }


}

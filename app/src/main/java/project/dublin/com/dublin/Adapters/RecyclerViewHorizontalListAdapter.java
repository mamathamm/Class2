package project.dublin.com.dublin.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import project.dublin.com.dublin.Pojo.Images;
import project.dublin.com.dublin.R;



public class RecyclerViewHorizontalListAdapter extends RecyclerView.Adapter<RecyclerViewHorizontalListAdapter.GroceryViewHolder>{
private ArrayList<String> horizontalGrocderyList;
        Context context;


public RecyclerViewHorizontalListAdapter(ArrayList<String> horizontalGrocderyList, Context context){
        this.horizontalGrocderyList= horizontalGrocderyList;
        this.context = context;
        }

@Override
public GroceryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View groceryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.captureimages_model, parent, false);
        GroceryViewHolder gvh = new GroceryViewHolder(groceryProductView);
        return gvh;
        }

@Override
public void onBindViewHolder(final GroceryViewHolder holder, final int position) {

    byte[] decodedString = Base64.decode(horizontalGrocderyList.get(position), Base64.DEFAULT);
    final Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    holder.capture_imageview.setImageBitmap(decodedByte);
        holder.capture_imageview.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
    // Create custom dialog object
    final Dialog dialog = new Dialog(context);
    // Include dialog.xml file
    dialog.setContentView(R.layout.zoomimagelayout);
    ImageView image = (ImageView) dialog.findViewById(R.id.dialog_imageview);
    image.setImageBitmap(decodedByte);
    dialog.show();



}

        });
        }



@Override
public int getItemCount() {
        return horizontalGrocderyList.size();
        }

public class GroceryViewHolder extends RecyclerView.ViewHolder {
    ImageView capture_imageview;

    public GroceryViewHolder(View view) {
        super(view);
        capture_imageview=view.findViewById(R.id.capture_imageview);

    }
}
}

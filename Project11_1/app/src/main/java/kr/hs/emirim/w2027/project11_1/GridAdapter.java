package kr.hs.emirim.w2027.project11_1;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GridAdapter extends BaseAdapter {
    Context context;
    int[] posterIds = {R.drawable.mv1, R.drawable.mv2, R.drawable.mv3, R.drawable.mv4, R.drawable.mv5,
            R.drawable.mv6, R.drawable.mv7, R.drawable.mv8, R.drawable.mv9, R.drawable.mv10,
            R.drawable.mv1, R.drawable.mv2, R.drawable.mv3, R.drawable.mv4, R.drawable.mv5,
            R.drawable.mv6, R.drawable.mv7, R.drawable.mv8, R.drawable.mv9, R.drawable.mv10};

    public GridAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return posterIds.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imgV = new ImageView(context);
        imgV.setLayoutParams(new ViewGroup.LayoutParams(200,300));
        imgV.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imgV.setPadding(5,5,5,5);
        imgV.setImageResource(posterIds[i]);

        final int pos = i;
        imgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                View dialogView = View.inflate(context, R.layout.dialog1, null);
                ImageView imgLarge = dialogView.findViewById(R.id.imgv_large);
                imgLarge.setImageResource(posterIds[pos]);
                dialog.setTitle("Large Poster");
                dialog.setIcon(R.drawable.mvicon);
                dialog.setView(dialogView);
                dialog.setNegativeButton("close",null);
                dialog.show();
            }
        });
        return imgV;
    }
}

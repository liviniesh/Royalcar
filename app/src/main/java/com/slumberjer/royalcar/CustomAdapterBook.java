package com.slumberjer.royalcar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomAdapterBook extends SimpleAdapter {

    private Context mContext;
    public LayoutInflater inflater=null;
    public CustomAdapterBook(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        try{
            if(convertView==null)
                vi = inflater.inflate(R.layout.user_booking_list, null);
            HashMap<String, Object> data = (HashMap<String, Object>) getItem(position);
            TextView tvcarname = vi.findViewById(R.id.textView);
            TextView tvrentalperhour = vi.findViewById(R.id.textView2);
            TextView tvhours = vi.findViewById(R.id.textView3);
            TextView tvstatus = vi.findViewById(R.id.textView4);
            CircleImageView imgcar =vi.findViewById(R.id.imageView2);
            String dcarname = (String) data.get("carname");
            String drentalperhour =(String) data.get("rentalperhour");
            String dhours =(String) data.get("hours");
            String dcid=(String) data.get("carid");
            String dcst=(String) data.get("status");
            String dbookid=(String) data.get("bookid");
            tvcarname.setText(dcarname);
            tvrentalperhour.setText(drentalperhour);
            tvhours.setText(dhours);
            tvstatus.setText(dcst);
            String image_url = "https://royalcar.000webhostapp.com/royalcar/carimages/"+dcid+".jpg";
            Picasso.with(mContext).load(image_url)
                    .fit().into(imgcar);
//                    .memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE)

        }catch (IndexOutOfBoundsException e){

        }

        return vi;
    }
}
package com.slumberjer.royalcar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CompanyActivity extends AppCompatActivity {
TextView tvrname,tvrphone,tvraddress,tvrloc;
ImageView imgCompany;
ListView lvcar;
    Dialog myDialogWindow;
    ArrayList<HashMap<String, String>> carlist;
    String userid,companyid,userphone;
    double total;
    EditText date,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        companyid = bundle.getString("companyid");
        String rname = bundle.getString("name");
        String rphone = bundle.getString("phone");
        String raddress = bundle.getString("address");
        String rlocation = bundle.getString("location");
        userid = bundle.getString("userid");
        userphone = bundle.getString("userphone");
        initView();
        tvrname.setText(rname);
        tvraddress.setText(raddress);
        tvrphone.setText(rphone);
        tvrloc.setText(rlocation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Picasso.with(this).load("https://royalcar.000webhostapp.com/royalcar/images/"+companyid+".jpg")
                .fit().into(imgCompany);
 //  .memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE)

        lvcar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showCarDetail(position);
            }
        });
        loadCars(companyid);

    }

    private void showCarDetail(int p) {
            myDialogWindow = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);//Theme_DeviceDefault_Dialog_NoActionBar
            myDialogWindow.setContentView(R.layout.dialog_window);
            myDialogWindow.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            TextView tvcarname,tvrentalperhour,tvhours;

            ImageView imgcar = myDialogWindow.findViewById(R.id.imageViewCar);
            final Spinner sphours = myDialogWindow.findViewById(R.id.spinner2);

            Button btnbook = myDialogWindow.findViewById(R.id.button2);
            tvcarname= myDialogWindow.findViewById(R.id.textView12);
            tvrentalperhour = myDialogWindow.findViewById(R.id.textView13);
            tvhours = myDialogWindow.findViewById(R.id.textView14);
            date = myDialogWindow.findViewById(R.id.editText3);
            time = myDialogWindow.findViewById(R.id.editText4);
            tvcarname.setText(carlist.get(p).get("carname"));
            tvrentalperhour.setText(carlist.get(p).get("rentalperhour"));
            tvhours.setText(carlist.get(p).get("hours"));
            final String carid =(carlist.get(p).get("carid"));
            final String carname = carlist.get(p).get("carname");
            final String rentalperhour = carlist.get(p).get("rentalperhour");

            btnbook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String chours = sphours.getSelectedItem().toString();
                    String tdate = date.getText().toString();
                    String ttime = time.getText().toString();
                    dialogBook(carid,carname,chours,rentalperhour,tdate,ttime);
                }
            });
            int hour = Integer.parseInt(carlist.get(p).get("hours"));
            List<String> list = new ArrayList<String>();
            for (int i = 1; i<=hour;i++){
                list.add(""+i);
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sphours.setAdapter(dataAdapter);

            Picasso.with(this).load("https://royalcar.000webhostapp.com/royalcar/carimages/"+carid+".jpg")
                .memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE)
                .fit().into(imgcar);
            myDialogWindow.show();
    }

    private void dialogBook(final String carid, final String carname, final String hours, final String rentalperhour, final String tdate,final String ttime) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Book "+carname+ " with "+hours + " hours");

        alertDialogBuilder
                .setMessage("Are you sure?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        insertBooking(carid,carname,hours,rentalperhour,tdate,ttime);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void insertBooking(final String carid, final String carname, final String hours, final String rentalperhour,final String tdate, final String ttime) {
        class InsertBook extends AsyncTask<Void,Void,String> {

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("carid",carid);
                hashMap.put("companyid",companyid);
                hashMap.put("carname",carname);
                hashMap.put("hours",hours);
                hashMap.put("rentalperhour",rentalperhour);
                hashMap.put("userid",userid);
                hashMap.put("date", tdate);
                hashMap.put("time", ttime);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest("https://royalcar.000webhostapp.com/royalcar/insert_booking.php",hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(RestaurantActivity.this,s, Toast.LENGTH_SHORT).show();
                if (s.equalsIgnoreCase("success")){
                    Toast.makeText(CompanyActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    myDialogWindow.dismiss();
                    loadCars(companyid);
                }else{
                    Toast.makeText(CompanyActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }

        }
        InsertBook insertBooking = new InsertBook();
        insertBooking.execute();
    }

    private void loadCars(final String companyid) {
        class LoadCar extends AsyncTask<Void,Void,String> {

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("companyid",companyid);
                RequestHandler requestHandler = new RequestHandler();
                String s = requestHandler.sendPostRequest("https://royalcar.000webhostapp.com/royalcar/load_cars.php",hashMap);
                return s;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                carlist.clear();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray cararray = jsonObject.getJSONArray("car");
                    for (int i = 0; i < cararray.length(); i++) {
                        JSONObject c = cararray.getJSONObject(i);
                        String jsid = c.getString("carid");
                        String jscarname = c.getString("carname");
                        String jsrentalperhour = c.getString("rentalperhour");
                        String jshours = c.getString("hours");
                        HashMap<String,String> carlisthash = new HashMap<>();
                        carlisthash.put("carid",jsid);
                        carlisthash.put("carname",jscarname);
                        carlisthash.put("rentalperhour",jsrentalperhour);
                        carlisthash.put("hours",jshours);
                        carlist.add(carlisthash);
                    }
                }catch(JSONException e){}
                ListAdapter adapter = new CustomAdapterCar(
                        CompanyActivity.this, carlist,
                        R.layout.car_list_company, new String[]
                        {"carname","rentalperhour","hours"}, new int[]
                        {R.id.textView,R.id.textView2,R.id.textView3});
                lvcar.setAdapter(adapter);

            }
        }
        LoadCar loadCar = new LoadCar();
        loadCar.execute();
    }

    private void initView() {
        imgCompany = findViewById(R.id.imageView3);
        tvrname = findViewById(R.id.textView6);
        tvrphone = findViewById(R.id.textView7);
        tvraddress = findViewById(R.id.textView8);
        tvrloc = findViewById(R.id.textView9);
        lvcar = findViewById(R.id.listviewcar);
        carlist = new ArrayList<>();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(CompanyActivity.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userid",userid);
                bundle.putString("phone",userphone);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package com.slumberjer.royalcar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    ListView lvcompany;
    ArrayList<HashMap<String, String>> companylist;
    ArrayList<HashMap<String, String>> booklist;
    ArrayList<HashMap<String, String>> bookhistorylist;
    double total,totalhistory;
    Spinner sploc;
    String userid,name,phone;
    Dialog myDialogBook,myDialogHistory;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvcompany = findViewById(R.id.listviewCompany);
        booklist = new ArrayList<>();
        bookhistorylist= new ArrayList<>();
        sploc = findViewById(R.id.spinner);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userid = bundle.getString("userid");
        name = bundle.getString("name");
        phone = bundle.getString("phone");
        loadCompany(sploc.getSelectedItem().toString());
        lvcompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,CompanyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("companyid", companylist.get(position).get("companyid"));
                bundle.putString("name", companylist.get(position).get("name"));
                bundle.putString("phone", companylist.get(position).get("phone"));
                bundle.putString("address", companylist.get(position).get("address"));
                bundle.putString("location", companylist.get(position).get("location"));
                bundle.putString("userid", userid);
                bundle.putString("userphone",phone);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        sploc.setSelection(0, false);
        sploc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadCompany(sploc.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void loadCompany(final String loc) {
        class LoadCompany extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("location", loc);
                RequestHandler rh = new RequestHandler();
                companylist = new ArrayList<>();
                String s = rh.sendPostRequest
                        ("https://royalcar.000webhostapp.com/royalcar/load_company.php", hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
                companylist.clear();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray companyarray = jsonObject.getJSONArray("company");
                    Log.e("LIVI", jsonObject.toString());
                    for (int i = 0; i < companyarray.length(); i++) {
                        JSONObject c = companyarray.getJSONObject(i);
                        String rid = c.getString("companyid");
                        String rname = c.getString("name");
                        String rphone = c.getString("phone");
                        String raddress = c.getString("address");
                        String rlocation = c.getString("location");
                        HashMap<String, String> companylisthash = new HashMap<>();
                        companylisthash.put("companyid", rid);
                        companylisthash.put("name", rname);
                        companylisthash.put("phone", rphone);
                        companylisthash.put("address", raddress);
                        companylisthash.put("location", rlocation);
                        companylist.add(companylisthash);
                    }
                } catch (final JSONException e) {
                    Log.e("JSONERROR", e.toString());
                }
                ListAdapter adapter = new CustomAdapter(
                        MainActivity.this, companylist,
                        R.layout.cust_list_company, new String[]
                        {"name", "phone", "address", "location"}, new int[]
                        {R.id.textView, R.id.textView2, R.id.textView3, R.id.textView4});
                lvcompany.setAdapter(adapter);

            }

        }
        LoadCompany loadCompany = new LoadCompany();
        loadCompany.execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mybook:
                loadBookData();
                return true;
            case R.id.myprofile:
                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userid",userid);
                bundle.putString("username",name);
                bundle.putString("phone",phone);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            case R.id.myhistory:
                loadHistoryBookData();
                return true;
            case R.id.logout:
                userlogout();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void userlogout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage("Are you sure want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        loading = ProgressDialog.show(MainActivity.this, "Logout","...",false,false);
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
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

    private void loadHistoryBookData() {
        class LoadBookData extends AsyncTask<Void,String,String>{

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("userid",userid);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest("https://royalcar.000webhostapp.com/royalcar/load_book_history.php",hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                bookhistorylist.clear();
                totalhistory = 0;
                try{
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray bookarray = jsonObject.getJSONArray("history");

                    for (int i=0;i<bookarray  .length();i++) {
                        JSONObject c = bookarray  .getJSONObject(i);
                        String jsbookid = c.getString("bookid");
                        String jstotal = c.getString("total");
                        String jsdate = c.getString("date");
                        HashMap<String,String> histlisthash = new HashMap<>();
                        histlisthash  .put("bookid",jsbookid);
                        histlisthash  .put("total",jstotal);
                        histlisthash  .put("date",jsdate);
                        bookhistorylist.add(histlisthash);
                        totalhistory = Double.parseDouble(jstotal) + totalhistory;
                    }
                }catch (JSONException e){}
                super.onPostExecute(s);
                if (bookhistorylist.size()>0){
                    loadHistoryWindow();
                }else{
                    Toast.makeText(MainActivity.this, "No booked history", Toast.LENGTH_SHORT).show();
                }

            }

        }
        LoadBookData loadBookData = new LoadBookData();
        loadBookData.execute();
    }

    private void loadHistoryWindow() {
        myDialogHistory = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);//Theme_DeviceDefault_Dialog_NoActionBar
        myDialogHistory.setContentView(R.layout.hist_window);
        myDialogHistory.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ListView lvhist = myDialogHistory.findViewById(R.id.lvhistory);
        TextView tvtotal = myDialogHistory.findViewById(R.id.textViewTotal);
        Button btnclose = myDialogHistory.findViewById(R.id.btnClose);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialogHistory.dismiss();
            }
        });
        ListAdapter adapter = new SimpleAdapter(
                MainActivity.this, bookhistorylist,
                R.layout.hist_book_list, new String[]
                {"bookid","total","date"}, new int[]
                {R.id.textView,R.id.textView2,R.id.textView33});
        lvhist.setAdapter(adapter);
        tvtotal.setText("RM"+totalhistory);
        myDialogHistory.show();
    }

    private void loadBookWindow() {
        myDialogBook = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);//Theme_DeviceDefault_Dialog_NoActionBar
        myDialogBook.setContentView(R.layout.booking_window);
        myDialogBook.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ListView lvbook = myDialogBook.findViewById(R.id.lvmybook);
        TextView tvtotal = myDialogBook.findViewById(R.id.textViewTotal);
        TextView tvbookid = myDialogBook.findViewById(R.id.textBookId);
        Button btnpay = myDialogBook.findViewById(R.id.btnPay);
        Log.e("LIVI","SIZE:"+booklist.size());
        lvbook.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dialogDeleteCar(position);
                return false;
            }
        });
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPay();
            }
        });
        ListAdapter adapter = new CustomAdapterBook(
                MainActivity.this, booklist,
                R.layout.user_booking_list, new String[]
                {"carname","rentalperhour","hours","status","date", "time"}, new int[]
                {R.id.textView,R.id.textView2,R.id.textView3,R.id.textView4});
        lvbook.setAdapter(adapter);
        tvtotal.setText("RM "+total);
        tvbookid.setText(booklist.get(0).get("bookid"));
        myDialogBook.show();

    }

    private void dialogDeleteCar(final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Delete Car "+booklist.get(position).get("carname")+"?");
        alertDialogBuilder
                .setMessage("Are you sure")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        deleteBookCar(position);
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

    private void deleteBookCar(final int position) {
        class DeleteBookCar extends AsyncTask<Void,Void,String>{

            @Override
            protected String doInBackground(Void... voids) {
                String carid = booklist.get(position).get("carid");
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("carid",carid);
                hashMap.put("userid",userid);
                RequestHandler requestHandler = new RequestHandler();
                String s = requestHandler.sendPostRequest("https://royalcar.000webhostapp.com/royalcar/delete_book.php",hashMap);
                return s;
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s.equalsIgnoreCase("success")){
                    myDialogBook.dismiss();
                    loadBookData();
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
        DeleteBookCar deleteBookCar = new DeleteBookCar();
        deleteBookCar.execute();
    }

    private void dialogPay() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Proceed with payment?");

        alertDialogBuilder
                .setMessage("Are you sure")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(MainActivity.this,PaymentActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("userid",userid);
                        bundle.putString("name",name);
                        bundle.putString("phone",phone);
                        bundle.putString("total", String.valueOf(total));
                        bundle.putString("bookid", booklist.get(0).get("bookid"));
                        intent.putExtras(bundle);
                        myDialogBook.dismiss();
                        startActivity(intent);
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
    private void loadBookData() {
        class LoadBookData extends AsyncTask<Void,Void,String>{

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("userid",userid);
                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest("https://royalcar.000webhostapp.com/royalcar/load_book.php",hashMap);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                booklist.clear();
                total = 0;
                try{
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray bookarray = jsonObject.getJSONArray("book");

                    for (int i=0;i<bookarray .length();i++) {
                        JSONObject c = bookarray .getJSONObject(i);
                        String jfid = c.getString("carid");
                        String jfn = c.getString("carname");
                        String jfp = c.getString("rentalperhour");
                        String jfq = c.getString("hours");
                        String jst = c.getString("status");
                        String joid = c.getString("bookid");
                        HashMap<String,String> booklisthash = new HashMap<>();
                        booklisthash .put("carid",jfid);
                        booklisthash .put("carname",jfn);
                        booklisthash .put("rentalperhour","RM "+jfp);
                        booklisthash .put("hours",jfq);
                        booklisthash .put("status",jst);
                        booklisthash .put("bookid",joid);
                        booklist.add(booklisthash);
                        total = total + (Double.parseDouble(jfp) * Double.parseDouble(jfq));
                    }
                }catch (JSONException e){}
                super.onPostExecute(s);
                if (total>0){
                    loadBookWindow();
                }else{
                    Toast.makeText(MainActivity.this, "Booking is feeling empty", Toast.LENGTH_SHORT).show();
                }

            }
        }
        LoadBookData loadBookData = new LoadBookData();
        loadBookData.execute();
    }

}
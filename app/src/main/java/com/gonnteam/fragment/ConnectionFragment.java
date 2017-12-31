package com.gonnteam.fragment;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gonnteam.smartlamp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by MrThien on 2017-12-17.
 */

public class ConnectionFragment extends Fragment {
    Button btnConnect;
    EditText edtSsid,edtPass;
    TextView txtResult;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase=database.getReference();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.connection_fragment, container, false);


        init(rootView);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ssid=edtSsid.getText().toString();
                String pass=edtPass.getText().toString();
                String url="http://192.168.4.1/change?ssid="+ssid+"&&pass="+pass;

                new ConnectWifi().execute(url.toString().trim());
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Kết nối");

    }
    void init(View viewGroup){
        btnConnect=(Button) viewGroup.findViewById(R.id.btnConnect);
        edtSsid=(EditText)viewGroup.findViewById(R.id.edtSsid);
        edtPass=(EditText)viewGroup.findViewById(R.id.edtPass);
        txtResult=(TextView)viewGroup.findViewById(R.id.txtResult);

    }


    class  ConnectWifi extends AsyncTask<String,String,String> {
        OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        @Override
        protected String doInBackground(String... strings) {
            Request.Builder builder=new Request.Builder();
            builder.url(strings[0]);
            Request request=builder.build();
            try {
                Response response=okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return  null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(!s.equals("")){
                txtResult.setText(s);
            }
            else {
                txtResult.append("Connected failed");
            }
        }
    }
}

package com.example.quizz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CodeActivity extends AppCompatActivity {

    private Button sendd;
    private EditText co;
    private Button dis;
    TextView connstat;
    ArrayList<Integer>codearr=new ArrayList<>();
    private TextView codetext1;
    private TextView codetext2;
    IntentFilter intentFilter= new IntentFilter();
    WifiManager wifiManager;
    WifiP2pManager mManager;
    int flag=0;
    WifiP2pManager.Channel mChannel;
    public InputStream inputStream;
    public OutputStream outputStream;
    BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;
    List<WifiP2pDevice>peers=new ArrayList<>();
    String[] deviceNameArray;
    WifiP2pDevice[] deviceArray;
    ListView listView;
    static final int MESSAGE_READ=1;
    int x,y=0;
    int flagg=0;
    ServerClass serverClass;
    ClientClass clientClass;
    SendReceive sendReceive;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        codetext1=findViewById(R.id.code1);
        codetext2=findViewById(R.id.code2);
        dis=findViewById(R.id.discover);
        connstat=findViewById(R.id.conn);
        wifiManager= (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mManager= (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel=mManager.initialize(this,getMainLooper(),null);
        mReceiver=new WiFiDirectBroadcastReceiver(mManager,mChannel,this);
        co=findViewById(R.id.code_val);
        sendd=findViewById(R.id.send);
        mIntentFilter=new IntentFilter();
        listView=findViewById(R.id.peerListView);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        Intent intent1 = getIntent();
        final int intValue = intent1.getIntExtra("stuff",1);

        if(intValue==1){
            connstat.setText("STUDENT");
            co.setVisibility(View.GONE);
            sendd.setText("START QUIZ");
        }else{
            connstat.setText("TEACHER");
        }

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            sendd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (intValue == 1) {
                        if(flagg==1){
                            openEnterCodeActivity();
                        }else {
                            Toast.makeText(getApplicationContext(),"Not connected to Teacher",Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        String msg = co.getText().toString();
                        sendReceive.write(msg.getBytes());
                    }
                }
            });

        }



        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        connstat.setText("Discovery Started");

                    }
                    @Override
                    public void onFailure(int reason) {
                        connstat.setText("Discovery Starting Failed");


                    }
                });

            }


        });

        for(int i=1;i<=6;i++){
            codearr.add(i);
        }
        Collections.shuffle(codearr);
        y=codearr.remove(0);
        for(int j=0;j<5;j++){
            x=codearr.remove(0);
            y=y*10+x;
        }

        exqListener();
    }

    public void openEnterCodeActivity(){
        Intent intent2= new Intent(this, RulesActivity.class);
        startActivity(intent2);
    }

    Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_READ:
                    byte[] readbuff=(byte[])msg.obj;
                    String tempMsg=new String(readbuff,0,msg.arg1);
                    codetext1.setText(tempMsg);

                    break;
            }
            return true;
        }
    });


    private void exqListener() {
        if(!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
            Toast.makeText(CodeActivity.this, "Turning WiFi on...", Toast.LENGTH_SHORT).show();

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final WifiP2pDevice device=deviceArray[i];
                WifiP2pConfig config=new WifiP2pConfig();
                config.deviceAddress=device.deviceAddress;

                mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(),"Connected to "+device.deviceName,Toast.LENGTH_SHORT).show();
                        flagg=1;
                    }

                    @Override
                    public void onFailure(int reason) {
                        Toast.makeText(getApplicationContext(),"Connection failed",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }

    WifiP2pManager.PeerListListener peerListListener=new WifiP2pManager.PeerListListener() {


        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {
            if(!peerList.getDeviceList().equals(peers)){
                peers.clear();
                peers.addAll(peerList.getDeviceList());

                deviceNameArray=new String[peerList.getDeviceList().size()];
                deviceArray=new WifiP2pDevice[peerList.getDeviceList().size()];

                int index=0;

                for(WifiP2pDevice device : peerList.getDeviceList()){
                    connstat.setText("Hello");
                    deviceNameArray[index]=device.deviceName;
                    deviceArray[index]=device;
                    index++;
                }

                ArrayAdapter<String>adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,deviceNameArray);
                listView.setAdapter(adapter);
            }
            if(peers.size()==0){
                Toast.makeText(getApplicationContext(),"No Device Found",Toast.LENGTH_SHORT).show();
                return;
            }
        }

    };


    WifiP2pManager.ConnectionInfoListener connectionInfoListener=new WifiP2pManager.ConnectionInfoListener() {
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {

            final InetAddress groupOwnerAddress=wifiP2pInfo.groupOwnerAddress;

            if(wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner){
                connstat.setText("Student");
                serverClass=new ServerClass();
                serverClass.start();
            }else if(wifiP2pInfo.groupFormed){
                connstat.setText("Teacher");
                clientClass=new ClientClass(groupOwnerAddress);
                clientClass.start();
            }

        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver,mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    public class ServerClass extends Thread{
        Socket socket;
        ServerSocket serverSocket;

        @Override
        public void run() {
            try {
                serverSocket=new ServerSocket(8888);
                socket=serverSocket.accept();
                sendReceive=new SendReceive(socket);
                sendReceive.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class SendReceive extends Thread{
        private Socket socket;

        public SendReceive(Socket skt){
            socket=skt;
            try {
                inputStream=socket.getInputStream();
                outputStream=socket.getOutputStream();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            byte[] buffer=new byte[1024];
            int bytes;
            while (socket != null) {
                try {
                    bytes=inputStream.read(buffer);
                    if(bytes>0){
                        handler.obtainMessage(MESSAGE_READ,bytes,-1,buffer).sendToTarget();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void write(byte[] bytes){
            try {
                if(bytes!=null) {
                    outputStream.write(bytes);
                }else{
                    Toast.makeText(getApplicationContext(),"Enter a code",Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public class ClientClass extends Thread{
        Socket socket;
        String hostAdd;

        public ClientClass(InetAddress hostAddress){
            hostAdd=hostAddress.getHostAddress();
            socket=new Socket();
        }

        @Override
        public void run() {
            try {
                socket.connect(new InetSocketAddress(hostAdd,8888),500);
                sendReceive=new SendReceive(socket);
                sendReceive.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}







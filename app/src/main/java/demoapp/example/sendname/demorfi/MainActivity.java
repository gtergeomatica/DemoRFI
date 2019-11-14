package demoapp.example.sendname.demorfi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
// We need to use this Handler package
import android.os.Handler;

public class MainActivity extends AppCompatActivity {


    int i =1; // counter per i punti



    private Handler mHandler =new Handler();

    String area_type_log;





    public static boolean containsWhitespace(String str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }


    public static boolean hasLength(String str) {
        return (str != null && str.length() > 0);
    }


    private WebView vistaMappa;

    URL url;
    HttpURLConnection urlConnection = null;

    // Create the Handler object (on the main thread by default)
    Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editName = (EditText) findViewById(R.id.nomeUtente);

        Button tastoAvvia = (Button) findViewById(R.id.button_avvia);
        final TextView testo_display = (TextView) findViewById(R.id.textView5);
        final Button registra_punto = (Button) findViewById(R.id.button_rec_p);
        final Button elimina_punto = (Button) findViewById(R.id.button_canc_p);
        final EditText text = (EditText) findViewById(R.id.nomePunto);
        text.setText("P"+i);


        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.tipoArea);
        //final RadioButton radioButton;


        final Button registra_area = (Button) findViewById(R.id.button_rec_a);


        tastoAvvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String name = editName.getText().toString();
                boolean test_ws =  containsWhitespace(name);

                if (name.isEmpty()) {
                    //displayText.setText("inserisci un nome!!");
                    Toast.makeText(getApplicationContext(), "inserisci un nome", Toast.LENGTH_LONG).show();

                } else if(test_ws==true){
                    Toast.makeText(getApplicationContext(), "non mettere spazi bianchi, sostituiscili con _", Toast.LENGTH_LONG).show();

                    //displayText.setText("non mettere spazi bianchi, sostituiscili con _ ");
                } else {
                    //displayText.setText("hai inserito il nome: " + name);
                    Toast.makeText(getApplicationContext(), "hai inserito il nome: " + name, Toast.LENGTH_LONG).show();


                // associa nome

                    Runnable mToastRunnable = new Runnable() {
                        @Override
                        public void run() {

                            try {
                                url = new URL("http:/gishosting.gter.it/demo_rfi/read_ip.php?n=" + name);

                                urlConnection = (HttpURLConnection) url
                                        .openConnection();
                                InputStream in = urlConnection.getInputStream();

                                InputStreamReader isw = new InputStreamReader(in);

                                int data = isw.read();
                                while (data != -1) {
                                    char current = (char) data;
                                    data = isw.read();
                                    System.out.print(current);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(MainActivity.this, "Ho inviato il nome al server", Toast.LENGTH_SHORT).show();
                            mHandler.postDelayed(this, 10000); //ogni minuto
                        }
                    };


                    mHandler.removeCallbacks(mToastRunnable); //chiude tutti i runnable attivi
                    mToastRunnable.run();

                    //reset

                            try {
                                url = new URL("http:/gishosting.gter.it/demo_rfi/reset.php?n=" + name);

                                urlConnection = (HttpURLConnection) url
                                        .openConnection();
                                //InputStream in = urlConnection.getInputStream();

                                //InputStreamReader isw = new InputStreamReader(in);

                                //int data = isw.read();
                                //while (data != -1) {
                                //    char current = (char) data;
                                //    data = isw.read();
                                //    System.out.print(current);
                                //}
                                Toast.makeText(MainActivity.this, "Resettati i dati di "+name, Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                    //vista mappa


                    vistaMappa = (WebView) findViewById(R.id.vistaWeb);
                    vistaMappa.getSettings().setJavaScriptEnabled(true);
                    vistaMappa.setWebViewClient(new WebViewClient());
                    vistaMappa.loadUrl("http://gishosting.gter.it/demo_rfi/mappa.php?n="+name);

                }
            }
        });


        registra_punto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = editName.getText().toString();
              CountDownTimer countDownTimer = new CountDownTimer(10*1000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                testo_display.setText("Registazione in corso, attendi "+millisUntilFinished/1000+" secondi");

                }

                @Override
                public void onFinish() {


                    try {
                                url = new URL("http:/gishosting.gter.it/demo_rfi/reg.php?n="+name+"?p="+text.getText().toString() );

                                urlConnection = (HttpURLConnection) url
                                        .openConnection();
                                //InputStream in = urlConnection.getInputStream();

                                //InputStreamReader isw = new InputStreamReader(in);

                                //int data = isw.read();
                                //while (data != -1) {
                                //    char current = (char) data;
                                //    data = isw.read();
                                //    System.out.print(current);
                                //}

                        testo_display.setText("http:/gishosting.gter.it/demo_rfi/reg.php?n="+name+"?p="+text.getText().toString());


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                           Toast.makeText(MainActivity.this, "Punto registrato!", Toast.LENGTH_LONG).show();

                 i++;

                 text.setText("P"+i);

                 //testo_display.setText("Scegli cosa fare ...");


                }
            }.start();


            }
        });


        elimina_punto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           final String name = editName.getText().toString();

                if(text.getText().toString().equals("P1")) {

            Toast.makeText(MainActivity.this, "Non ci sono punti da eliminare!",

                    Toast.LENGTH_LONG).show();

            }
            else{
            i--;
            text.setText("P"+i);
            try {
                 url = new URL("http:/gishosting.gter.it/demo_rfi/canc.php?n="+name+"?p="+text.getText().toString() );

                 urlConnection = (HttpURLConnection) url
                            .openConnection();
                                //InputStream in = urlConnection.getInputStream();

                                //InputStreamReader isw = new InputStreamReader(in);

                                //int data = isw.read();
                                //while (data != -1) {
                                //    char current = (char) data;
                                //    data = isw.read();
                                //    System.out.print(current);
                                //}

                 testo_display.setText("http:/gishosting.gter.it/demo_rfi/canc.php?n="+name+"?p="+text.getText().toString());


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        Toast.makeText(MainActivity.this, "Punto eliminato!",Toast.LENGTH_LONG).show();




            }



            }
        });

        //AREA


       radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){

                    case R.id.areaSafe:

                        area_type_log="Safety";
                        Toast.makeText(MainActivity.this, "hai selezionato Area "+area_type_log,Toast.LENGTH_LONG).show();
                        break;
                    case R.id.areaDanger:
                        area_type_log="Danger";
                        Toast.makeText(MainActivity.this, "hai selezionato Area "+area_type_log,Toast.LENGTH_LONG).show();
                        break;
                }


            }
        });


        registra_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            final String name = editName.getText().toString();

                //int radioId = radioGroup.getCheckedRadioButtonId();

                //radioButton =(RadioButton) v.findViewById(radioId);

                if(area_type_log==null){

                    Toast.makeText(MainActivity.this, "Selezionare il tipo di area ",Toast.LENGTH_LONG).show();

                }else{
                    testo_display.setText("gishosting.gter.it/save.php?n="+name+"&t="+area_type_log);


                }

            }
        });




        

    }
}

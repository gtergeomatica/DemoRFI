package demoapp.example.sendname.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {


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


    private WebView vistaIP;
    private WebView vistaMappa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editName = (EditText) findViewById(R.id.InsertName);
        final TextView displayText = (TextView) findViewById(R.id.textView2);
        Button tastoAvvia = (Button) findViewById(R.id.bntStartConn);



        tastoAvvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = editName.getText().toString();
                boolean test_ws =  containsWhitespace(name);

                if (name.isEmpty()) {
                    displayText.setText("inserisci un nome!!");
                } else if(test_ws==true){
                    displayText.setText("non mettere spazi bianchi, sostituiscili con _ ");
                } else {
                    displayText.setText("hai inserito il nome: " + name);

                    vistaIP = (WebView) findViewById(R.id.vistaWeb1);
                    vistaIP.setWebViewClient(new WebViewClient());
                    vistaIP.loadUrl("http:/gishosting.gter.it/demo_rfi/read_ip.php?n=" + name);


                }
            }
        });

        Button tastoMappa = (Button) findViewById(R.id.bntStartConn2);

        tastoMappa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editName.getText().toString();
                vistaMappa = (WebView) findViewById(R.id.vistaWeb2);
                vistaMappa.getSettings().setJavaScriptEnabled(true);
                vistaMappa.setWebViewClient(new WebViewClient());
                vistaMappa.loadUrl("http://gishosting.gter.it/demo_rfi/mappa.php?n="+name);
            }

        });

    }
}
/*
    btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                do {

                    System.out.println("Il bottone funziona e mi stampa questa riga");
                    schermo.setText("Il bottone funziona e stampa questa riga");
                    //Log.i("MyApp", "Il bottone funziona e mi stampa questa riga");
                    try {
                        Thread.sleep(6000); //value in ms
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } while (true);

            }

/*

                    do {

                        BufferedReader br = null;

                        try {

                            URL url = new URL("http://demo.gter.it/demo_rfi/read_ip.php?n=roberto");
                            br = new BufferedReader(new InputStreamReader(url.openStream()));

                            String line;

                            StringBuilder sb = new StringBuilder();

                            while ((line = br.readLine()) != null) {

                                sb.append(line);
                                sb.append(System.lineSeparator());
                            }

                            System.out.println(sb);
                        }

	                    catch (IOException p) {
	                        p.printStackTrace();
                            }

	                    try {
                            Thread.sleep(6000); //value in ms
                        } catch (InterruptedException ex) {


                            // TODO Auto-generated catch block
                            ex.printStackTrace();
                        }

                    } while (true);

                }




                do {
                    try {
                        URL myURL = new URL("http://demo.gter.it/demo_rfi/read_ip.php?n=roberto");
                        URLConnection myURLConnection = myURL.openConnection();
                        myURLConnection.connect();
                        System.out.println("Connesso alla pagina");
                        try {
                            Thread.sleep(6000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }//value in ms
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        //System.out.println("sono qua!");
                        // new URL() failed
                        // ...
                    } catch (IOException e) {
                        e.printStackTrace();
                        // openConnection() failed
                        // ...
                    }
                } while (true);


            }
*/

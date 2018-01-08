package praveen.winit.com.testexpadable;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<ChildDataModel>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

    }

    @Override
    protected void onResume() {
        super.onResume();


        // preparing list data
        DoSomeTask doSomeTask = new DoSomeTask();
        doSomeTask.execute();
     //   readfile();
    }

    private void runVolley() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://uat.winitsoftware.com/ThemeManager/Data/Products/Products.xml";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        Log.d("Response %s", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    class DoSomeTask extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... voids) {
            //Uses URL and HttpURLConnection for server connection.
            String result = null;
            try {
                result = sendGet(getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            try {
                InputStream is = new ByteArrayInputStream(s.getBytes());
                readFromWeb(is);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private String sendGet(Context context) throws Exception {
        String url = "http://uat.winitsoftware.com/ThemeManager/Data/Products/Products.xml";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
      //  con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        con.setReadTimeout(10000 /* milliseconds */);
        con.setConnectTimeout(15000 /* milliseconds */);

        con.setDoOutput(true);

        con.connect();
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'Get' request to URL : " + url + "--" + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println("Response : -- " + response.toString());
        return response.toString();
    }

    private void readfile() {


        try {
            InputStream is = getAssets().open("file_new.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("Product");
            readDataAndPutInExpListView(nList);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void readFromWeb(InputStream is) {


        try {
           // InputStream is = getAssets().open("file_new.xml");


            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("Product");
            readDataAndPutInExpListView(nList);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void readDataAndPutInExpListView(NodeList nList) {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        for (int i = 0; i < nList.getLength(); i++) {

            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element2 = (Element) node;
                listDataHeader.add(getValue("Name", element2));
                List<ChildDataModel> child = new ArrayList<>();
                String desc = getValue("Description", element2);
                String price = getValue("Price", element2);
                String imageUrl = getValue("ImageURL", element2);
                String bitImageUrl = getValue("BigImageURL", element2);


                ChildDataModel childDataModel = new ChildDataModel(desc, price, imageUrl, bitImageUrl);
                child.add(childDataModel);
                listDataChild.put(listDataHeader.get(i), child);

            }


        }
        listAdapter = new ExpandableListAdapter(getApplicationContext(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }


    /*
 * Preparing the list data
 */


    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


}

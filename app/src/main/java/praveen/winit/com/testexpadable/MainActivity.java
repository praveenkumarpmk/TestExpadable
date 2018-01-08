package praveen.winit.com.testexpadable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
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


       /* RequestQueue queue = Volley.newRequestQueue(this);
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
        queue.add(stringRequest);*/

        readfile();
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

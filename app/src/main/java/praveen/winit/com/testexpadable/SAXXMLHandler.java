package praveen.winit.com.testexpadable;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SAXXMLHandler extends DefaultHandler {
    private String tempVal;
    // to maintain context
    private ChildDataModel childDataModel;
    List<String> listDataHeader = new ArrayList<>();
    HashMap<String, List<ChildDataModel>> listDataChild = new HashMap<>();

    public SAXXMLHandler() {

    }

    public HashMap<String, List<ChildDataModel>> getListDataChild() {
        return listDataChild;
    }

    public List<String> getListDataHeader() {
        return listDataHeader;
    }

    // Event Handlers
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        // reset
        tempVal = "";
        if (qName.equalsIgnoreCase("Product")) {
            // create a new instance of Laptop
            //     childDataModel = new ChildDataModel();
            //   childDataModel.setModel(attributes.getValue("model"));
        }
    }

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        tempVal = new String(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        String description = "";
        String price = "";
        String imageUrl = "";
        String LargeImageUrl = "";
        List<ChildDataModel> child = new ArrayList<>();

        if (qName.equalsIgnoreCase("Description")) {
            description = tempVal;
        } else if (qName.equalsIgnoreCase("Price")) {
            price = tempVal;
        } else if (qName.equalsIgnoreCase("ImageURL")) {
            imageUrl = tempVal;
        } else if (qName.equalsIgnoreCase("BigImageURL")) {
            LargeImageUrl = tempVal;
        } else if (qName.equalsIgnoreCase("Name")) {
            // add it to the list
            listDataHeader.add(tempVal);
        }
        childDataModel = new ChildDataModel(description, price, imageUrl, LargeImageUrl);
        child.add(childDataModel);
        listDataChild.put(tempVal, child);
    }
}
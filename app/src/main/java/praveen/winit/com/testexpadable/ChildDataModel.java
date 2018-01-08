package praveen.winit.com.testexpadable;

import java.io.Serializable;

/**
 * Created by EE209278 on 08-01-2018.
 */

public class ChildDataModel implements Serializable {

    private static final long serialVersionUID = -2528418326251618040L;
    private String Description;
    private String Price;
    private String ImageURL;
    private String BigImageURL;

    public ChildDataModel(String description, String price, String imageURL, String bigImageURL) {
        Description = description;
        Price = price;
        ImageURL = imageURL;
        BigImageURL = bigImageURL;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getBigImageURL() {
        return BigImageURL;
    }

    public void setBigImageURL(String bigImageURL) {
        BigImageURL = bigImageURL;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ChildDataModel{");
        sb.append("Description='").append(Description).append('\'');
        sb.append(", Price='").append(Price).append('\'');
        sb.append(", ImageURL='").append(ImageURL).append('\'');
        sb.append(", BigImageURL='").append(BigImageURL).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

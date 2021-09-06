package in.rbofficial.myott.Models;

public class MainModel {
    private int image;
    private String brandName,priceDesc;

    public MainModel(int image, String brandName, String priceDesc) {
        this.image = image;
        this.brandName = brandName;
        this.priceDesc = priceDesc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getPriceDesc() {
        return priceDesc;
    }

    public void setPriceDesc(String priceDesc) {
        this.priceDesc = priceDesc;
    }
}

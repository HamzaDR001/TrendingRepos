package ga.hamzabenhida.trendingrepos.helpers;

import android.graphics.Bitmap;


/**
 * RepoItem
 * this class holds all in information about the repositories
 * this class will be used when loading json data into a list of objects
 *
 * also, this class has a static function called getLoadingRepo() that generates
 * a 'dummy' object to be used at the end of the list view.
 */
public class RepoItem {
    private String title;
    private String desc;
    private String ownerName;
    private String ownerImageURL;
    private String stars;
    private boolean isLoadingDummy = false;
    private Bitmap image;

    public RepoItem() {}

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public RepoItem(String title, String desc, String ownerName, String ownerImageURL, String stars, Boolean isLoadingDummy) {
        this.title = title;
        this.desc = desc;
        this.ownerName = ownerName;
        this.ownerImageURL = ownerImageURL;
        this.stars = stars;
        this.isLoadingDummy = isLoadingDummy;
    }

    public static RepoItem getLoadingRepo(){
        return new RepoItem("[...]","[Scroll down to load more repos ...]","[...]","[...]","[...]", true);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerImageURL() {
        return ownerImageURL;
    }

    public void setOwnerImageURL(String ownerImageURL) {
        this.ownerImageURL = ownerImageURL;
    }

    public String getStars() {
        if(isLoadingDummy) return stars;
        int number = Integer.parseInt(stars);
        if (number >= 1000000) {
            return  (number / 1000000) + "m";
        }
        else if (number >= 1000) {
            char c = String.valueOf(number/100).charAt(String.valueOf(number/100).length()-1);
            return (number / 1000) +"."+c+ "k";
        }else{
            return stars;
        }
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

}

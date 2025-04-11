package com.example.dandd_game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LocalImages {
    private LocalImages() {}
    private String kingURL;
    private String knightURL;
    private String clericURL;
    private String mageURL;

    private static LocalImages instance;
    public static LocalImages getInstance() {
        if (instance == null) {
            instance = new LocalImages();
        }
        return instance;
    }
    public void setKingURL(String kingURL) {
        this.kingURL = kingURL;
    }
    public void setKnightURL(String knightURL) {
        this.knightURL = knightURL;
    }
    public void setClericURL(String clericURL) {
        this.clericURL = clericURL;
    }
    public void setMageURL(String mageURL) {
        this.mageURL = mageURL;
    }
    public Image getKingImage(){
        return new Image(this.kingURL);
    }
    public Image getKnightImage(){
        return new Image(this.knightURL);
    }
    public Image getClericImage(){
        return  new Image(this.clericURL);
    }
    public Image getMageImage(){
        return new Image(this.mageURL);
    }
}

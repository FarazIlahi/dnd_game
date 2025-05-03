package com.example.dandd_game;

import com.example.dandd_game.Characters.Knight;
import javafx.scene.image.Image;

public class LocalImages {
    private LocalImages() {}
    private String kingURL;
    private String knightURL;
    private String clericURL;
    private String mageURL;
    private String goblinURL;
    private String orcURL;
    private String sorcererURL;
    private String slashURL;
    private String explosionURL;
    private String healURL;

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

    public void setGoblinURL(String goblinURL) {
        this.goblinURL = goblinURL;
    }

    public void setOrcURL(String orcURL) {
        this.orcURL = orcURL;
    }

    public void setSorcererURL(String sorcererURL) {
        this.sorcererURL = sorcererURL;
    }

    public void setSlashURL(String slashURL) {
        this.slashURL = slashURL;
    }
    public void setExplosionURL(String explosionURL) {
        this.explosionURL = explosionURL;
    }
    public void setHealURL(String healURL) {
        this.healURL = healURL;
    }
    public void ensureImagesLoaded() {
        if(kingURL == null || knightURL == null || clericURL == null || mageURL == null || goblinURL == null || orcURL == null || sorcererURL == null || slashURL == null || explosionURL == null || healURL == null) {
            try {
                setKingURL(getClass().getResource("/com/example/dandd_game/images/pixelKing.png").toExternalForm());
                setKnightURL(getClass().getResource("/com/example/dandd_game/images/pixelKnight.png").toExternalForm());
                setClericURL(getClass().getResource("/com/example/dandd_game/images/pixelCleric.png").toExternalForm());
                setMageURL(getClass().getResource("/com/example/dandd_game/images/pixelMage.png").toExternalForm());
                setGoblinURL(getClass().getResource("/com/example/dandd_game/images/goblin.png").toExternalForm());
                setOrcURL(getClass().getResource("/com/example/dandd_game/images/orc.png").toExternalForm());
                setSorcererURL(getClass().getResource("/com/example/dandd_game/images/evilEyes.png").toExternalForm());
                setSlashURL(getClass().getResource("/com/example/dandd_game/images/slash.gif").toExternalForm());
                setExplosionURL(getClass().getResource("/com/example/dandd_game/images/explode.gif").toExternalForm());
                setHealURL(getClass().getResource("/com/example/dandd_game/images/heal.png").toExternalForm());
                System.out.println("Image URLs initialized.");
            } catch (Exception e) {
                System.out.println("Error loading images: " + e.getMessage());
            }
        }
    }

    public Image getImage(String image){
        if(image.equals("King")){
            return new Image(this.kingURL);
        }
        else if (image.equals("Knight")) {
            return new Image(this.knightURL);
        }
        else if (image.equals("Cleric")) {
            return new Image(this.clericURL);
        }
        else if (image.equals("Mage")) {
            return new Image(this.mageURL);
        }
        else if (image.equals("Goblin")) {
            return new Image(this.goblinURL);
        }
        else if (image.equals("Orc")) {
            return new Image(this.orcURL);
        }
        else if (image.equals("Sorcerer")) {
            return new Image(this.sorcererURL);
        }
        else if (image.equals("Slash")) {
            return new Image(this.slashURL);
        }
        else if (image.equals("Explosion")) {
            return new Image(this.explosionURL);
        }
        else if (image.equals("Heal")) {
            return new Image(this.healURL);
        }

        else {
            return null;
        }
    }
}

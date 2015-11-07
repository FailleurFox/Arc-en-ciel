package fr.polytech.si5.arc_en_ciel;

/**
 * Created by Hugo on 07/11/2015.
 */
public class Drawing {
    private int drawableId;
    private int thumbnailId;
    private int colorsDrawableId;
    private int nbCircles;
    private String name;

    public Drawing(String name, int drawableId, int colorsId, int thumbnailId, int nbCircles) {
        this.name = name;
        this.drawableId = drawableId;
        this.colorsDrawableId = colorsId;
        this.thumbnailId = thumbnailId;
        this.nbCircles = nbCircles;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public int getThumbnailId() {
        return thumbnailId;
    }

    public int getColorsDrawableId() {
        return colorsDrawableId;
    }

    public String getName() {
        return name;
    }

    public int getNbCircles() {
        return nbCircles;
    }

}

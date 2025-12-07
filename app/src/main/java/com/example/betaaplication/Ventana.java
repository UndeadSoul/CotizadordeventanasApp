package com.example.betaaplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "windows_table",
        foreignKeys = @ForeignKey(entity = Project.class,
                                  parentColumns = "id",
                                  childColumns = "projectId",
                                  onDelete = ForeignKey.CASCADE)) // If a project is deleted, its windows are also deleted.
public class Ventana {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(index = true)
    private int projectId;

    private String height;
    private String width;
    private String line; // e.g., "Linea 20", "Linea 25"
    private String color;
    private String crystal;
    private String price;

    private boolean materialCut;
    private boolean glassCut;

    public Ventana(int projectId, String height, String width, String line, String color, String crystal, String price) {
        this.projectId = projectId;
        this.height = height;
        this.width = width;
        this.line = line;
        this.color = color;
        this.crystal = crystal;
        this.price = price;
        this.materialCut = false; // Default to false
        this.glassCut = false;    // Default to false
    }

    // --- Getters and Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getHeight() {
        return height;
    }

    public String getWidth() {
        return width;
    }

    public String getLine() {
        return line;
    }

    public String getColor() {
        return color;
    }

    public String getCrystal() {
        return crystal;
    }

    public String getPrice() {
        return price;
    }

    public boolean isMaterialCut() {
        return materialCut;
    }

    public void setMaterialCut(boolean materialCut) {
        this.materialCut = materialCut;
    }

    public boolean isGlassCut() {
        return glassCut;
    }

    public void setGlassCut(boolean glassCut) {
        this.glassCut = glassCut;
    }
}

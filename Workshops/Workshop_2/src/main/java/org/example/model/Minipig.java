package org.example.model;

/*
* {código}, {nombre (dado por el propietario)}, {Genero/sexo}, {id
microchip},{raza}, {color, peso, altura, caractreristica1, caracteristica2},{url foto}.
* */
public class Minipig {
    private String code;
    private String name;
    private String gender;
    private String microchipId;
    private String race;
    private String color;
    private double weight;
    private double height;
    private String characteristic1;
    private String characteristic2;
    private String photoUrl;

    public Minipig(String code,
                   String photoUrl, String characteristic2,
                   String characteristic1,
                   double height,
                   double weight,
                   String color,
                   String name,
                   String gender,
                   String race, String microchipId) {
        this.code = code;
        this.photoUrl = photoUrl;
        this.characteristic2 = characteristic2;
        this.characteristic1 = characteristic1;
        this.height = height;
        this.weight = weight;
        this.color = color;
        this.name = name;
        this.gender = gender;
        this.race = race;
        this.microchipId = microchipId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getMicrochipId() {
        return microchipId;
    }

    public void setMicrochipId(String microchipId) {
        this.microchipId = microchipId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getCharacteristic1() {
        return characteristic1;
    }

    public void setCharacteristic1(String characteristic1) {
        this.characteristic1 = characteristic1;
    }

    public String getCharacteristic2() {
        return characteristic2;
    }

    public void setCharacteristic2(String characteristic2) {
        this.characteristic2 = characteristic2;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}

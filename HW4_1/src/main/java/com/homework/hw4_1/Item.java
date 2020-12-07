package com.homework.hw4_1;

import java.util.Objects;

public class Item {
    private String name;
    private String info;
    private int image;

    public Item(String name, String info, int image) {
        this.name = name;
        this.info = info;
        this.image = image;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getImage() {
        return this.image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (null == object || getClass() != object.getClass())
            return false;
        Item item = (Item) object;
        if (image != item.image)
            return false;
        return (Objects.equals(name, item.name) & Objects.equals(info, item.info));
    }

    @Override
    public int hashCode() {
        return (31 * image + ((null == name) ? 0 : name.hashCode()) +
                ((null == info) ? 0 : info.hashCode()));
    }

}

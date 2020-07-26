package com.paolo.convertini.model;

public class Configuration {

    private String dir;

    private String metodo;

    private int hPosition;

    private int vPosition;

    public int gethPosition() {
        return hPosition;
    }

    public void sethPosition(int hPosition) {
        this.hPosition = hPosition;
    }

    public int getvPosition() {
        return vPosition;
    }

    public void setvPosition(int vPosition) {
        this.vPosition = vPosition;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}

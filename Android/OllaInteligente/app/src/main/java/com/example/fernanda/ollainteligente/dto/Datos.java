package com.example.fernanda.ollainteligente.dto;

public class Datos {
    private String id;
    private int pir, gas, time;
    private float temp;
    public Datos() {
    }

    public Datos(String id, int pir, int gas, float temp, int time) {
        this.id = id;
        this.pir = pir;
        this.gas = gas;
        this.temp = temp;
        this.time = time;
    }

    public Datos(int pir, int gas, float temp, int time) {
        this.pir = pir;
        this.gas = gas;
        this.temp = temp;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPir() {
        return pir;
    }

    public void setPir(int pir) {
        this.pir = pir;
    }

    public int getGas() {
        return gas;
    }

    public void setGas(int gas) {
        this.gas = gas;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}

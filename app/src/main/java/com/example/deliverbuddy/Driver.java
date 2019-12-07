package com.example.deliverbuddy;

//Not used in current version

public class Driver {
    private StringBuilder name1 = new StringBuilder();
    private StringBuilder name2 = new StringBuilder();
    private StringBuilder id = new StringBuilder();
    private StringBuilder pin = new StringBuilder();
    private Delivery[] driverDelivs;
    private static StringBuilder adminPin = new StringBuilder("1234");

    public void setName1(String name)
    {
        this.name1 = new StringBuilder(name);
    }
    public void setName2(String name)
    {
        this.name2 = new StringBuilder(name);
    }
    public void setId(String id)
    {
        this.id = new StringBuilder(id);
    }
    public void setPin(String pin)
    {
        this.pin = new StringBuilder(pin);
    }
    public StringBuilder getName1()
    {
        return name1;
    }
    public StringBuilder getName2()
    {
        return name2;
    }
    public StringBuilder getId()
    {
        return id;
    }
}

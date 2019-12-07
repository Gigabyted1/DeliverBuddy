package com.example.deliverbuddy;

//Object class for delivery data storage

class Delivery {
    private StringBuilder name1 = new StringBuilder();
    private StringBuilder name2 = new StringBuilder();
    private StringBuilder address = new StringBuilder();
    private StringBuilder phone = new StringBuilder();
    private StringBuilder no = new StringBuilder();
    private double orderTot = 0.0;

    public void setNo(int no) //Sets the delivery number as a 4-digit string, i.e. 0001, 0056, 0809
    {
        if(no < 10000 && no >= 1000)
        {
            this.no = new StringBuilder(no);
        }
        else if(no < 1000 && no >= 100)
        {
            this.no = new StringBuilder("0" + no);
        }
        else if (no < 100 && no >= 10)
        {
            this.no = new StringBuilder("00" + no);
        }
        else if (no < 10)
        {
            this.no = new StringBuilder("000" + no);
        }
    }
    public void setNo(String no)
    {
        this.no = new StringBuilder(no);
    }
    public void setName1(String name)
    {
        this.name1 = new StringBuilder(name);
    }
    public void setName2(String name)
    {
        this.name2 = new StringBuilder(name);
    }
    public void setAddress(String address)
    {
        this.address = new StringBuilder(address);
    }
    public void setPhone(String phone)
    {
        this.phone = new StringBuilder(phone);
    }
    public void setTotal(double total)
    {
        this.orderTot = total;
    }
    public StringBuilder getName1()
    {
        return name1;
    }
    public StringBuilder getName2()
    {
        return name2;
    }
    public StringBuilder getAddress()
    {
        return address;
    }
    public StringBuilder getPhone()
    {
        return phone;
    }
    public StringBuilder getNo()
    {
        return no;
    }
    public double getTotal()
    {
        return orderTot;
    }
}

package com.example.deliverbuddy;

//Object class for delivery data storage

class Delivery {
    private StringBuilder name1 = new StringBuilder();
    private StringBuilder name2 = new StringBuilder();
    private StringBuilder address1 = new StringBuilder();
    private StringBuilder address2 = new StringBuilder();
    private StringBuilder city = new StringBuilder();
    private StringBuilder zip = new StringBuilder();
    private StringBuilder phone = new StringBuilder();
    private StringBuilder no = new StringBuilder();
    private double tip = 0.0;
    private double subtotal = 0.0;
    private double total = 0.0;

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
    public void setAddress1(String address)
    {
        this.address1 = new StringBuilder(address);
    }
    public void setAddress2(String address2)
    {
        this.address2 = new StringBuilder(address2);
    }
    public void setCity(String city)
    {
        this.city = new StringBuilder(city);
    }
    public void setZip(String zip)
    {
        this.zip = new StringBuilder(zip);
    }
    public void setPhone(String phone)
    {
        this.phone = new StringBuilder(phone);
    }
    public void setSubtotal(double subtotal)
    {
        this.subtotal = subtotal;
    }
    public void setTip(double tip)
    {
        this.tip = tip;
    }
    public void calcTotal()
    {
        this.total = subtotal + tip;
    }

    public StringBuilder getName1()
    {
        return name1;
    }
    public StringBuilder getName2()
    {
        return name2;
    }
    public StringBuilder getAddress1()
    {
        return address1;
    }
    public StringBuilder getAddress2()
    {
        return address2;
    }
    public StringBuilder getCity()
    {
        return city;
    }
    public StringBuilder getZip()
    {
        return zip;
    }
    public StringBuilder getPhone()
    {
        return phone;
    }
    public StringBuilder getNo()
    {
        return no;
    }
    public double getSubtotal()
    {
        return subtotal;
    }
    public double getTip()
    {
        return tip;
    }
    public double getTotal()
    {
        return total;
    }
}

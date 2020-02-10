package com.tibco.cep.query.test.model01;

import java.io.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

public class DataReader {
    BufferedReader input;

    public DataReader(String fileName) throws FileNotFoundException {
        if(fileName != null ) {
            input = new BufferedReader(new FileReader(fileName));
        } else {
            input = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("Customer.dat")));

        }
    }

    public Collection readCustomerData() throws IOException {
        LinkedHashMap set = new LinkedHashMap();
        BufferedReader in = new BufferedReader(input);
        String line = in.readLine();
        while(line != null) {
            if(!line.startsWith("#")) {
                StringTokenizer tokenizer = new StringTokenizer(line);
                long id = Long.parseLong(tokenizer.nextToken());
                String name  = tokenizer.nextToken();
                String age   = tokenizer.nextToken();
                String sex   = tokenizer.nextToken();
                String business = tokenizer.nextToken();
                String networth = tokenizer.nextToken();
                com.tibco.cep.query.test.model01.DataReader.Customer cust = (com.tibco.cep.query.test.model01.DataReader.Customer) set.get(name);
                if(cust == null) {
                    set.put(name, new com.tibco.cep.query.test.model01.DataReader.Customer(id, name, age, sex,business,networth));
                }

            }
            line = in.readLine();
        }
        in.close();
        return set.values();
    }

    public Collection readAddressData() throws IOException {
        LinkedHashMap set = new LinkedHashMap();
        BufferedReader in = new BufferedReader(input);
        String line = in.readLine();
        while(line != null) {
            if(!line.startsWith("#")) {
                StringTokenizer tokenizer = new StringTokenizer(line);
                long addrid = Long.parseLong(tokenizer.nextToken());
                String street  = tokenizer.nextToken();
                String city   = tokenizer.nextToken();
                String state   = tokenizer.nextToken();
                String country = tokenizer.nextToken();
                long customerid = Long.parseLong(tokenizer.nextToken());
                com.tibco.cep.query.test.model01.DataReader.Address addr = (com.tibco.cep.query.test.model01.DataReader.Address) set.get(addrid);
                if(addr == null) {
                    set.put(addrid, new com.tibco.cep.query.test.model01.DataReader.Address(addrid, street, city, state,country,customerid));
                }

            }
            line = in.readLine();
        }
        in.close();
        return set.values();
    }

     public Collection readOrderData() throws IOException {
        LinkedHashMap set = new LinkedHashMap();
        BufferedReader in = new BufferedReader(input);
        String line = in.readLine();
        while(line != null) {
            if(!line.startsWith("#")) {
                StringTokenizer tokenizer = new StringTokenizer(line);
                long orderid = Long.parseLong(tokenizer.nextToken());
                long customerid  = Long.parseLong(tokenizer.nextToken());
                int items   = Integer.parseInt(tokenizer.nextToken());
                double value   = Double.parseDouble(tokenizer.nextToken());
                com.tibco.cep.query.test.model01.DataReader.Order order = (com.tibco.cep.query.test.model01.DataReader.Order) set.get(orderid);
                if(order == null) {
                    set.put(orderid, new com.tibco.cep.query.test.model01.DataReader.Order(orderid, customerid, items,value));
                }

            }
            line = in.readLine();
        }
        in.close();
        return set.values();
    }

    public void printData(Collection set) {
        Iterator ite = set.iterator();
        while(ite.hasNext()) {
            com.tibco.cep.query.test.model01.DataReader.Customer	cust = (com.tibco.cep.query.test.model01.DataReader.Customer) ite.next();
            System.out.println(cust);
        }
    }

    static public class Order {
        long orderid;
        long customerid;
        int items;
        double value;

        public Order(long orderid,long customerid, int items,  double value) {
            this.customerid = customerid;
            this.items = items;
            this.orderid = orderid;
            this.value = value;
        }

        public long getCustomerid() {
            return customerid;
        }

        public void setCustomerid(long customerid) {
            this.customerid = customerid;
        }

        public int getItems() {
            return items;
        }

        public void setItems(int items) {
            this.items = items;
        }

        public long getOrderid() {
            return orderid;
        }

        public void setOrderid(long orderid) {
            this.orderid = orderid;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }

    static public class Address {
        long addressid;
        String street;
        String city;
        String state;
        String country;
        long customerid;

        public Address(long addressid, String street,String city,String state, String country, long customerid ) {
            this.addressid = addressid;
            this.city = city;
            this.country = country;
            this.customerid = customerid;
            this.state = state;
            this.street = street;
        }

        public long getAddressid() {
            return addressid;
        }

        public void setAddressid(long addressid) {
            this.addressid = addressid;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public long getCustomerid() {
            return customerid;
        }

        public void setCustomerid(long customerid) {
            this.customerid = customerid;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String toString() {
            return addressid +"= ("+street+","+city+","+state+","+country+","+customerid+")";
        }

    }

    static public class Customer {
        long id;
        String name;
        String age;
        String sex;
        String business;
        String networth;

        public Customer(long id,String name,String age,String sex,String business,String networth) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.sex = sex;
            this.business = business;
            this.networth = networth;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getAge() {
            return age;
        }
        public void setAge(String age) {
            this.age = age;
        }
        public String getBusiness() {
            return business;
        }
        public void setBusiness(String business) {
            this.business = business;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getNetworth() {
            return networth;
        }
        public void setNetworth(String networth) {
            this.networth = networth;
        }
        public String getSex() {
            return sex;
        }
        public void setSex(String sex) {
            this.sex = sex;
        }

        public String toString() {
            return id + "=("+ name + ":" + sex + ","+age+","+business+","+ networth+ ")";
        }
    }


}

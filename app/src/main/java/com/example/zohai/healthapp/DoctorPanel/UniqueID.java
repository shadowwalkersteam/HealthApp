package com.example.zohai.healthapp.DoctorPanel;


public class UniqueID {
    int ID;
    String datasource;
    String name;

    public UniqueID()
    {

    }

    public UniqueID(int dataID, String datasource, String name) {
        this.ID = dataID;
        this.datasource = datasource;
        this.name = name;
    }
    public UniqueID(String datasource, String name)
    {
        this.datasource= datasource;
        this.name = name;
    }

    public int getID(){
        return this.ID;
    }
    public void setID(int dataID){
        this.ID = dataID;
    }
    public String getDatasource()
    {
        return this.datasource;
    }
    public void setDatasource(String datasource)
    {
        this.datasource = datasource;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
}

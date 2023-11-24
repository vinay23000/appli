package com.gumtree.addressbook.domain;

import java.time.LocalDate;

public class AddressBookEntry
{
    private String name;
    private Sex sex;
    private LocalDate dob;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Sex getSex()
    {
        return sex;
    }

    public void setSex(Sex sex)
    {
        this.sex = sex;
    }

    public LocalDate getDob()
    {
        return dob;
    }

    public void setDob(LocalDate dob)
    {
        this.dob = dob;
    }

    @Override
    public String toString()
    {
        return "AddressBook{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", dob=" + dob +
                '}';
    }
}

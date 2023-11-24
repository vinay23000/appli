package com.gumtree.addressbook.dao;

import com.gumtree.addressbook.domain.AddressBookEntry;

import java.util.List;
import java.util.Properties;
import java.util.Set;

public interface AddressBookDao
{
    List<AddressBookEntry> findAll();
    void setProperties(Properties properties);
}

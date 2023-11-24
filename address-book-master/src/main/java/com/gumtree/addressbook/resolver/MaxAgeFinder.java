package com.gumtree.addressbook.resolver;

import com.gumtree.addressbook.domain.AddressBookEntry;
import com.gumtree.addressbook.domain.Sex;

import java.util.List;

public class MaxAgeFinder implements Resolver
{
    @Override
    public String answer(List<AddressBookEntry> data, String... parms)
    {
        AddressBookEntry entry = data.stream().max((e1, e2) -> e2.getDob().compareTo(e1.getDob())).get();
        return String.format("The oldest person is %s", entry.getName());
    }
}

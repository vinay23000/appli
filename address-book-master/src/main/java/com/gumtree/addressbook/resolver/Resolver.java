package com.gumtree.addressbook.resolver;

import com.gumtree.addressbook.domain.AddressBookEntry;

import java.util.List;

public interface Resolver
{
    String answer(List<AddressBookEntry> data, String... parms);
    default void fail(String message)
    {
        throw new RuntimeException(message);
    }
}

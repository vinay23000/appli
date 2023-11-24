package com.gumtree.addressbook.resolver;

import com.gumtree.addressbook.domain.AddressBookEntry;
import com.gumtree.addressbook.domain.Sex;

import java.util.List;

import static java.lang.String.format;

public class SexCounter implements Resolver
{
    @Override
    public String answer(List<AddressBookEntry> data, String... parms)
    {
        if (parms == null || parms.length != 1)
        {
            fail("Parameter count is invalid");
        }
        Sex sex = Sex.valueOf(parms[0].toUpperCase());
        if (sex == null)
        {
            fail("Please specify MALE or FEMALE");
        }
        return count(sex, data);
    }

    private String count(Sex sex, List<AddressBookEntry> data)
    {
        long count = data.stream().filter(e -> e.getSex() == sex).count();
        return format("There are %s %s(s) in the given data", count, sex.toString().toLowerCase());
    }
}

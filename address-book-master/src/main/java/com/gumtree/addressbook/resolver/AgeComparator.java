package com.gumtree.addressbook.resolver;

import com.gumtree.addressbook.domain.AddressBookEntry;

import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class AgeComparator implements Resolver
{
    @Override
    public String answer(List<AddressBookEntry> data, String... parms)
    {
        if (parms == null || parms.length != 2)
        {
            fail("Parameter count is invalid");
        }
        String name1 = parms[0];
        String name2 = parms[1];

        AddressBookEntry e1 = data.stream().filter(e -> e.getName().equals(name1)).findAny().get();
        AddressBookEntry e2 = data.stream().filter(e -> e.getName().equals(name2)).findAny().get();

        if (e1 == null || e2 == null)
        {
            fail("Cannot find entries for comparison");
        }
        long days = DAYS.between(e1.getDob(), e2.getDob());
        String answer = String.format("%s is %s days older than %s", e1.getName(), days, e2.getName());
        return answer;
    }
}

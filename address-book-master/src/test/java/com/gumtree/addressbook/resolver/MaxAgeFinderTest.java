package com.gumtree.addressbook.resolver;

import com.gumtree.addressbook.domain.AddressBookEntry;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.gumtree.addressbook.domain.Sex.FEMALE;
import static com.gumtree.addressbook.domain.Sex.MALE;
import static org.junit.Assert.*;

public class MaxAgeFinderTest
{
    @Test
    public void testAnswer()
    {
        List<AddressBookEntry> list = new ArrayList<>();
        AddressBookEntry entry = new AddressBookEntry();
        entry.setDob(LocalDate.of(2002, 02, 02));
        entry.setName("Eney");
        list.add(entry);

        entry = new AddressBookEntry();
        entry.setDob(LocalDate.of(1999, 03, 05));        entry.setName("Eney");
        entry.setName("Meeny");
        list.add(entry);

        entry = new AddressBookEntry();
        entry.setDob(LocalDate.of(1998, 12, 6));
        entry.setName("Miny");
        list.add(entry);

        entry = new AddressBookEntry();
        entry.setDob(LocalDate.of(1981, 04, 9));
        entry.setName("Mo");
        list.add(entry);

        Resolver resolver = new MaxAgeFinder();
        String answer = resolver.answer(list);
        assertTrue(answer.contains("Mo"));
        System.out.println(answer);

    }

}
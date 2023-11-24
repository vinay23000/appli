package com.gumtree.addressbook.resolver;

import com.gumtree.addressbook.domain.AddressBookEntry;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AgeComparatorTest
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
        entry.setDob(LocalDate.of(1981, 04, 1));        entry.setName("Eney");
        entry.setName("Meeny");
        list.add(entry);

        entry = new AddressBookEntry();
        entry.setDob(LocalDate.of(1981, 04, 9));
        entry.setName("Miny");
        list.add(entry);

        entry = new AddressBookEntry();
        entry.setDob(LocalDate.of(1981, 04, 6));
        entry.setName("Mo");
        list.add(entry);

        Resolver resolver = new AgeComparator();
        String answer = resolver.answer(list, "Mo", "Miny");
        assertTrue(answer.contains("Mo is 3 days older than Miny"));

        answer = resolver.answer(list,"Miny", "Meeny");

        assertTrue(answer.contains("Miny is -8 days older than Meeny"));
    }

}
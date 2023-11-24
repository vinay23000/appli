package com.gumtree.addressbook.resolver;

import com.gumtree.addressbook.domain.AddressBookEntry;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.gumtree.addressbook.domain.Sex.FEMALE;
import static com.gumtree.addressbook.domain.Sex.MALE;
import static org.junit.Assert.*;

public class SexCounterTest
{
    @Test
    public void testAnswer()
    {
        List<AddressBookEntry> list = new ArrayList<>();
        AddressBookEntry entry = new AddressBookEntry();
        entry.setSex(MALE);
        list.add(entry);

        entry = new AddressBookEntry();
        entry.setSex(FEMALE);
        list.add(entry);

        entry = new AddressBookEntry();
        entry.setSex(MALE);
        list.add(entry);

        entry = new AddressBookEntry();
        entry.setSex(FEMALE);
        list.add(entry);

        entry = new AddressBookEntry();
        entry.setSex(FEMALE);
        list.add(entry);

        Resolver resolver = new SexCounter();
        String answer = resolver.answer(list, "male");
        assertTrue(answer.contains("2 male(s)"));
        System.out.println(answer);

        answer = resolver.answer(list, "fEmaLe");
        assertTrue(answer.contains("3 female(s)"));
        System.out.println(answer);
    }

}
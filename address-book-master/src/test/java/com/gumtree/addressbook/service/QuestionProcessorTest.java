package com.gumtree.addressbook.service;

import com.gumtree.addressbook.domain.AddressBookEntry;
import com.gumtree.addressbook.domain.Question;
import com.gumtree.addressbook.domain.Sex;
import com.gumtree.addressbook.resolver.SexCounter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.*;
import static org.junit.Assert.*;

public class QuestionProcessorTest
{
    private static QuestionProcessor qp = new QuestionProcessor();
    @Test
    public void testProcess() throws Exception
    {
        List<AddressBookEntry> data = new ArrayList<>();
        AddressBookEntry entry = new AddressBookEntry();
        entry.setSex(Sex.MALE);
        data.add(entry);

        entry = new AddressBookEntry();
        entry.setSex(Sex.FEMALE);
        data.add(entry);

        entry = new AddressBookEntry();
        entry.setSex(Sex.FEMALE);
        data.add(entry);

        qp.setResolvers(singletonMap("sex.counter", new SexCounter()));
        Question q = new Question();
        q.setResolverKey("sex.counter");
        q.setParms(new String[] {"FEMALE"});

        String answer = qp.process(q, data);
        assertTrue(answer.contains("2 female"));
    }

    public void testResolverMissing()
    {
        List<AddressBookEntry> data = new ArrayList<>();
        AddressBookEntry entry = new AddressBookEntry();
        entry.setSex(Sex.MALE);
        data.add(entry);

        qp.setResolvers(emptyMap());

        Question q = new Question();
        q.setResolverKey("sex.counter");
        q.setParms(new String[] {"FEMALE"});

        String answer = qp.process(q, data);
        assertTrue(answer.contains("I cannot answer this question"));
    }
}
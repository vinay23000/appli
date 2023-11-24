package com.gumtree.addressbook;

import com.gumtree.addressbook.dao.AddressBookDao;
import com.gumtree.addressbook.dao.AddressBookFlatFileDao;
import com.gumtree.addressbook.domain.Question;
import com.gumtree.addressbook.resolver.Resolver;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.*;

public class ConfigTest
{
    private Config config = new Config("test-app.properties", "test-questions");

    @Test
    public void getQuestions() throws Exception
    {
        List<Question> questions = config.getQuestions();

        assertEquals("QA", questions.get(0).getKey());
        assertEquals("Question #1", questions.get(0).getQuestion());
        assertEquals("key1", questions.get(0).getResolverKey());
        assertEquals("P1", questions.get(0).getParms()[0]);
        assertEquals("P2", questions.get(0).getParms()[1]);

        assertEquals("QB", questions.get(1).getKey());
        assertEquals("Question #2", questions.get(1).getQuestion());
        assertEquals("key2", questions.get(1).getResolverKey());
        assertEquals("P3", questions.get(1).getParms()[0]);

        assertEquals("QC", questions.get(2).getKey());
        assertEquals("Question #3", questions.get(2).getQuestion());
        assertEquals("key3", questions.get(2).getResolverKey());
    }

    @Test
    public void getResolvers() throws Exception
    {
        Map<String, Resolver> map = config.getResolvers();
        assertEquals(map.size(), 2);
        assertEquals(map.get("sex.counter").getClass().getSimpleName(), "SexCounter");
    }

    @Test
    public void getAddressBookDao() throws Exception
    {
        AddressBookDao dao = config.getAddressBookDao();
        assertTrue(dao instanceof AddressBookFlatFileDao);
    }

    @Test
    public void testGetProperties()
    {
        Properties props = config.getProperties();
        assertEquals(5, props.size());

        assertEquals( props.getProperty("addressbookflatfiledao.filename"), "AddressBook");
        assertEquals( props.getProperty("resolver.sex.counter"), "com.gumtree.addressbook.resolver.SexCounter");
        assertEquals( props.getProperty("addressbookflatfiledao.assume20thCentuaryAfter"), "17");
    }
}
package com.gumtree.addressbook.dao;

import com.gumtree.addressbook.domain.AddressBookEntry;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static com.gumtree.addressbook.dao.AddressBookFlatFileDao.ASSUME_20TH_CENT_AFTER;
import static com.gumtree.addressbook.dao.AddressBookFlatFileDao.DATA_FILE_KEY;
import static com.gumtree.addressbook.domain.Sex.FEMALE;
import static com.gumtree.addressbook.domain.Sex.MALE;
import static org.junit.Assert.*;

public class AddressBookFlatFileDaoTest
{

    private static AddressBookFlatFileDao dao;

    @BeforeClass
    public static void init() {

        Properties props = new Properties();
        props.setProperty(DATA_FILE_KEY, "testAddressBook");
        props.setProperty(ASSUME_20TH_CENT_AFTER, "17");
        dao = new AddressBookFlatFileDao();
        dao.setProperties(props);
    }

    @Test
    public void testFindAll() throws Exception
    {
        List<AddressBookEntry> entries = dao.findAll();
        assertEquals(5, entries.size());
        assertEquals(entries.get(1).getName(), "Paul Robinson");
        assertEquals(entries.get(1).getSex(), MALE);
        assertEquals(entries.get(1).getDob(), LocalDate.of(1985, 1, 15));
        assertEquals(entries.get(3).getName(), "Sarah Stone");
        assertEquals(entries.get(3).getSex(), FEMALE);
        assertEquals(entries.get(3).getDob(), LocalDate.of(1980, 9, 20));
        assertEquals(entries.get(4).getName(), "Michael Jackson");
        assertEquals(entries.get(4).getSex(), MALE);
        assertEquals(entries.get(4).getDob(), LocalDate.of(2016, 8, 14));
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidDataFile()
    {
        AddressBookFlatFileDao badDao = new AddressBookFlatFileDao();
        badDao.setProperties(null);
    }
}
package com.gumtree.addressbook.dao;

import com.gumtree.addressbook.Config;
import com.gumtree.addressbook.domain.AddressBookEntry;
import com.gumtree.addressbook.domain.Sex;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static com.gumtree.addressbook.Config.*;
import static java.lang.Integer.*;
import static java.lang.String.format;
import static java.time.LocalDate.*;
import static java.util.stream.Collectors.toList;

public class AddressBookFlatFileDao implements AddressBookDao
{
    public static final String DATA_FILE_KEY = "addressbookflatfiledao.filename";
    public static final String ASSUME_20TH_CENT_AFTER = "addressbookflatfiledao.assume20thCentuaryAfter";

    List<AddressBookEntry> addressBook;
    private String dataSource;
    private int assume20thCentuaryAfter;

    @Override
    public void setProperties(Properties properties)
    {
        this.dataSource = properties.getProperty(DATA_FILE_KEY);
        this.assume20thCentuaryAfter = parseInt(properties.getProperty(ASSUME_20TH_CENT_AFTER));
        loadFromFlatFile();
    }

    private void loadFromFlatFile()
    {
        try
        {
            List<String> contents = readFile(dataSource, this);
            addressBook = parseContents(contents);
        }
        catch (Exception e)
        {
            throw new RuntimeException(format("Fatal error passing datafile: %s", dataSource), e);
        }
    }

    private List<AddressBookEntry> parseContents(List<String> contents)
    {
        return contents.stream().map(d -> parseEntry(d)).collect(toList());
    }

    /**
     * Function to parse a text address book entry delimited by commas (name, sex, dob).
     * Date of birth (dob) is delimited by "/" for each date component
     * @param detail - string containing name, sex and dob (comma delimited)
     * @return an AddressBookEntry
     */
    private AddressBookEntry parseEntry(String detail)
    {
        String[] values = detail.split(",");
        AddressBookEntry entry = new AddressBookEntry();
        entry.setName(values[0].trim());
        entry.setSex(Sex.valueOf(values[1].trim().toUpperCase()));
        String[] dob = values[2].trim().split("/");
        int year = parseInt(dob[2]);
        year = year > assume20thCentuaryAfter ? year + 1900 : year + 2000;
        LocalDate dobDate = of(year, parseInt(dob[1]), parseInt(dob[0]));
        entry.setDob(dobDate);
        return entry;
    }

    @Override
    public List<AddressBookEntry> findAll()
    {
        return addressBook;
    }
}

package com.gumtree.addressbook;

import com.gumtree.addressbook.dao.AddressBookDao;
import com.gumtree.addressbook.domain.AddressBookEntry;
import com.gumtree.addressbook.domain.Question;
import com.gumtree.addressbook.service.QuestionProcessor;

import java.util.List;

import static java.lang.String.*;

public class App
{
    public static void main(String[] args)
    {
        Config config = new Config("app.properties", "questions");
        QuestionProcessor qp = new QuestionProcessor();
        qp.setResolvers(config.getResolvers());
        AddressBookDao dao = config.getAddressBookDao();
        dao.setProperties(config.getProperties());

        System.out.println();

        List<AddressBookEntry> data = dao.findAll();
        for (Question q : config.getQuestions()) {
            System.out.println(format("%s - %s", q.getKey(), q.getQuestion()));
            String answer = qp.process(q, data);
            System.out.println(answer);
            System.out.println();
        }
    }
}

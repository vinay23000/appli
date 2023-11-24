package com.gumtree.addressbook.service;

import com.gumtree.addressbook.domain.AddressBookEntry;
import com.gumtree.addressbook.domain.Question;
import com.gumtree.addressbook.resolver.Resolver;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class QuestionProcessor
{
    private Map<String, Resolver> resolvers;
    public String process(Question question, List<AddressBookEntry> data)
    {
        Resolver resolver = resolvers.get(question.getResolverKey());
        if (resolver == null) {
            return format("I cannot answer this question - no resolver found for key: %s", question.getResolverKey());
        }
        return resolver.answer(data, question.getParms());
    }

    public void setResolvers(Map<String, Resolver> resolvers)
    {
        this.resolvers = resolvers;
    }
}

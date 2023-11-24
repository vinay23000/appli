package com.gumtree.addressbook.domain;

public class Question
{
    private String question;
    private String resolverKey;
    private String[] parms = {};
    private String key;

    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String question)
    {
        this.question = question;
    }

    public String getResolverKey()
    {
        return resolverKey;
    }

    public void setResolverKey(String resolverKey)
    {
        this.resolverKey = resolverKey;
    }

    public String[] getParms()
    {
        return parms;
    }

    public void setParms(String[] parms)
    {
        this.parms = parms;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }
}

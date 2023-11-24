package com.gumtree.addressbook;

import com.gumtree.addressbook.dao.AddressBookDao;
import com.gumtree.addressbook.domain.Question;
import com.gumtree.addressbook.resolver.Resolver;

import java.io.StringReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Configuration class to read property files and prepare data needed for
 * the application, namely:
 *
 * The dao to use to access the address book data
 * The resolvers needed to answer required questions
 * Property values from property file
 * Questions given to the application
 *
 */

public class Config
{
    private Properties properties;
    private Map<String, Resolver> resolvers;
    private List<Question> questions;
    private AddressBookDao dao;

    public Config(String configFile, String questionFile)
    {
        properties = loadProperties(configFile, this);
        Properties questions = loadProperties(questionFile, this);
        prepareQuestions(questions);
        loadResolvers();
        createDao();
    }

    private void createDao()
    {
        String c1ass = properties.get("addressbookdao").toString();
        try
        {
            dao = (AddressBookDao) Class.forName(c1ass).newInstance();
        }
        catch (Exception e)
        {
            throw new RuntimeException(format("Failed to create dao: %s", c1ass), e);
        }
    }

    /**
     * Find all resolver properties and store in a map with the given key. Create the resolver instance and
     * store as the value rather tahn the class name.
     */
    private void loadResolvers()
    {
        resolvers = new HashMap<>();

        // Find all resolvers - store in Map
        Map<String, String> map = properties.entrySet().stream()
                .filter(e -> e.getKey().toString().contains("resolver"))
                .collect(toMap(e->e.getKey().toString(), e-> e.getValue().toString()));

        // Instanciate and restore
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String className = entry.getValue();
            Resolver r = null;
            try
            {
                r = (Resolver) Class.forName(className).newInstance();
            }
            catch (Exception e)
            {
                throw new RuntimeException(format("Cannot instanciate: %s", className),  e);
            }
            String key = entry.getKey().replace("resolver.", "");
            resolvers.put(key, r);
        }
    }

    public Properties getProperties()
    {
        return this.properties;
    }

    /**
     * Format the questions from a property file into a List of String arrays.
     * Each question has a key (Question number) a resolver key to find the Resolver to use
     * to answer the question. And zero or more parameters to pass into the question.
     * For example:
     * Q1 = resolverX; Fred; Harry
     *
     * item[0] = Q1
     * item[1] = resolverX
     * item[2] = Fred
     * item[3] = Harry
     *
     * @param questions - a property file containing the questions
     */
    private void prepareQuestions(Properties questions)
    {
        List<Question> list = new ArrayList<>();
        for (Map.Entry<Object, Object> entry : questions.entrySet())
        {
            Question q = new Question();
            String[] values = entry.getValue().toString().split(";");
            q.setKey(entry.getKey().toString().trim());
            q.setQuestion(values[0].trim());
            q.setResolverKey(values[1].trim());
            if (values.length > 2)
            {
                int len = values.length;
                String[] parms = new String[len - 2];
                IntStream.range(2, len).forEach(i -> parms[i - 2] = values[i].trim());
                q.setParms(parms);
            }
            list.add(q);
        }
        this.questions = list.stream().sorted((a,b) -> a.getKey().compareTo(b.getKey())).collect(toList());
    }

    public List<Question> getQuestions()
    {
        return questions;
    }

    public Map<String, Resolver> getResolvers()
    {
        return resolvers;
    }

    public AddressBookDao getAddressBookDao()
    {
        return dao;
    }

    public static List<String> readFile(String filename, Object c1ass)
    {
        URL url = c1ass.getClass()
                .getClassLoader()
                .getResource(filename);
        Path data = Paths.get(url.getPath());
        try
        {
            return Files.readAllLines(data);
        }
        catch (Exception e)
        {
            throw new RuntimeException(format("Error reading file: %s", filename), e);
        }
    }

    public static Properties loadProperties(String filename, Object c1ass)
    {
        Properties props = new Properties();
        URL url = c1ass.getClass()
                .getClassLoader()
                .getResource(filename);
        Path data = Paths.get(url.getPath());
        try
        {
            props.load(new StringReader(new String(Files.readAllBytes(data))));
            return props;
        }
        catch (Exception e)
        {
            throw new RuntimeException(format("Error opening properties file: %s", filename), e);
        }
    }
}

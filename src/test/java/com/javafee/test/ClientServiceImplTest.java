package com.javafee.test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.javafee.hibernate.dao.HibernateDao;
import com.javafee.hibernate.dto.association.City;
import com.javafee.hibernate.dto.common.Address;
import com.javafee.hibernate.dto.eeatery.Client;
import com.javafee.service.impl.ClientServiceImpl;

public class ClientServiceImplTest {
    private HibernateDao<Client, Integer> dao = new HibernateDao<>(Client.class);

    @Test
    public void changePassword() {
        Client client = new Client();
        client.setName("Test");
        client.setSurname("Test");
        client.setLogin("Test");
        client.setRegisterDate(new Date());
        client.setEMail("test@test.pl");

        dao.save(client);

        Assert.assertEquals(client.getName(), dao.findByPrimaryKey(client.getIdUsers()).getName());

        ClientServiceImpl clientService = new ClientServiceImpl(dao);
        clientService.changePassword(client,"Haslo121!");
        dao.save(client);

        Assert.assertEquals("8022CCA4F50796C71BD15C48C8F50B89".toLowerCase(), client.getPassword());

    }

    @Test
    public void changeAddress() {
        Client client = new Client();
        client.setName("Test");
        client.setSurname("Test");
        client.setLogin("Test");
        client.setRegisterDate(new Date());
        client.setEMail("test@test.pl");

        dao.save(client);

        Assert.assertEquals(client.getName(), dao.findByPrimaryKey(client.getIdUsers()).getName());

        Address address = new Address();
        City city = new City();
        address.setCity(city);
        city.setName("Katowice");
        ClientServiceImpl clientService = new ClientServiceImpl(dao);
        clientService.changeAddress(client,address);
        dao.save(client);
        Assert.assertEquals("Katowice", client.getAddress().getCity().toString());

    }

    @Test
    public void findClientById() {
        Client client = new Client();
        client.setName("Test");
        client.setSurname("Test");
        client.setLogin("Test");
        client.setRegisterDate(new Date());
        client.setEMail("test@test.pl");

        dao.save(client);

        Assert.assertEquals(client.getName(), dao.findByPrimaryKey(client.getIdUsers()).getName());

        ClientServiceImpl clientService = new ClientServiceImpl(dao);
        clientService.changePassword(client,"Haslo121!");
        dao.save(client);
    }
}
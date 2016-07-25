package ru.parsentev.store;

import org.junit.Ignore;
import org.junit.Test;
import ru.parsentev.models.Message;
import ru.parsentev.models.Role;
import ru.parsentev.models.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * TODO: comment
 *
 * @author parsentev
 * @since 01.05.2015
 */
public class HibernateStorageTest {

    @Test
    public void testCreate() throws Exception {
        Role role = new Role();
        role.setId(2);
        role.setName("test");
        User user = new User(-1, "hibernate", null);
        user.setRole(role);

        final HibernateStorage storage = new HibernateStorage();
        final int id = storage.add(user);
        final User userActual = storage.get(id);
        assertEquals(id, userActual.getId());
        assertEquals(id, storage.findByLogin("hibernate").getId());
        storage.delete(id);
        assertNull(storage.get(id));
        storage.close();
    }

    @Test
    public void testCreateUser() throws Exception {
        final HibernateStorage storage = new HibernateStorage();

        Role role = new Role();
        role.setName("admin");

        User user = new User();
        user.setLogin("test");
        user.setEmail("test@test");
        user.setRole(role);

        final int id = storage.add(user);
        user = storage.get(id);

        Message message = new Message();
        message.setUser(user);
        message.setText("text");
//        HashSet<Message> messages = new HashSet<>();
//        messages.add(message);
//
//        user.setMessages(messages);
//        storage.edit(user);

        assertEquals(1, storage.get(id).getMessages().size());
        storage.delete(id);
        storage.close();
    }
}
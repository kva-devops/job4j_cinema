package ru.job4j.cinema.store;

import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Ticket;

import java.util.Collection;

public interface Store {
    Collection<Integer> findAllTicket();
    void saveTicket(Ticket ticket);
    void saveAccount(Account account);
    int findIdAccountByPhone(String phone);
}

package ru.job4j.cinema.store;

import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Ticket;

import java.util.Collection;

public interface Store {
    Collection<Integer> findAllTicket();
    Ticket createTicket(Ticket ticket);
    Account createAccount(Account account);
}

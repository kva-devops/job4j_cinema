package ru.job4j.cinema.store;

import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Ticket;

import java.sql.SQLException;
import java.util.Collection;

public interface Store {
    Collection<Integer> findAllTicket();
    boolean saveTicket(Ticket ticket) throws SQLException;
    void saveAccount(Account account);
    int findIdAccountByPhone(String phone);
    int checkFreePlace(int row, int seat);
}

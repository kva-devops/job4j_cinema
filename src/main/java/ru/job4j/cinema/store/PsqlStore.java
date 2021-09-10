package ru.job4j.cinema.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Ticket;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class PsqlStore implements Store {

    private static final Logger LOG = LoggerFactory.getLogger(PsqlStore.class.getName());

    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Integer> findAllTicket() {
        Collection<Integer> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM ticket")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(it.getInt("row") * 10 + it.getInt("cell"));
                }
            }
        } catch (Exception e) {
            LOG.error("Error in FIND ALL TICKET method", e);
        }
        return tickets;
    }

    @Override
    public void saveTicket(Ticket ticket) {
        if (ticket.getId() == 0) {
            create(ticket);
        } else {
            update(ticket);
        }
    }

    private Ticket create(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO ticket (row, cell) VALUES (?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getRow());
            ps.setInt(2, ticket.getCell());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                }
            }

        } catch (Exception e) {
            LOG.error("Exception in TICKET CREATE method.", e);
        }
        return ticket;
    }

    private void update(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE ticket SET row=?, cell=? WHERE id=?"
             )) {
                ps.setInt(1, ticket.getRow());
                ps.setInt(2, ticket.getCell());
                ps.setInt(3, ticket.getId());
                ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in TICKET UPDATE method.", e);
        }
    }

    @Override
    public void saveAccount(Account account) {
        if (account.getId() == 0) {
            create(account);
        } else {
            update(account);
        }
    }

    private Account create(Account account) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO account (username, phone) VALUES (?, ?)",
                        PreparedStatement.RETURN_GENERATED_KEYS
             )) {
                ps.setString(1, account.getName());
                ps.setString(2, account.getPhone());
                ps.execute();
                try (ResultSet id = ps.getGeneratedKeys()) {
                    if (id.next()) {
                        account.setId(id.getInt(1));
                    }
                }

        } catch (Exception e) {
            LOG.error("Exception in ACCOUNT CREATE method.", e);
        }
        return account;
    }

    private void update(Account account) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE account SET username=?, phone=? WHERE id=?"
             )) {
            ps.setString(1, account.getName());
            ps.setString(2, account.getPhone());
            ps.setInt(3, account.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception in ACCOUNT UPDATE method.", e);
        }
    }
}

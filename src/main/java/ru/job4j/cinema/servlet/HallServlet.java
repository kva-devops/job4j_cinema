package ru.job4j.cinema.servlet;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import ru.job4j.cinema.model.Account;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.store.PsqlStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class HallServlet extends HttpServlet {
    public final static Logger LOGGER = Logger.getLogger(HallServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Integer> list = new ArrayList<>(PsqlStore.instOf().findAllTicket());
        String json = new Gson().toJson(list);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = new PrintWriter(resp.getOutputStream());
        out.println(json);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String userName = req.getParameter("name");
        String userPhone = req.getParameter("phone");
        String row = req.getParameter("row");
        String seat = req.getParameter("seat");
        Account account = PsqlStore.instOf().createAccount(
                new Account(
                        Integer.parseInt(req.getParameter("id")),
                        userName,
                        userPhone));
        Ticket ticket = PsqlStore.instOf().createTicket(
                new Ticket(
                        Integer.parseInt(req.getParameter("id")),
                        1,
                        Integer.parseInt(row),
                        Integer.parseInt(seat),
                        account.getId()));
    }
}

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
    public final static Logger LOG = Logger.getLogger(HallServlet.class.getName());

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
        req.setCharacterEncoding("UTF-8");
        PsqlStore.instOf().saveAccount(
                new Account(
                        0,
                        req.getParameter("name"),
                        req.getParameter("phone")));

        int accountId = PsqlStore.instOf().findIdAccountByPhone(req.getParameter("phone"));
        PsqlStore.instOf().saveTicket(
                new Ticket(
                        0,
                        Integer.parseInt(req.getParameter("row")),
                        Integer.parseInt(req.getParameter("seat")),
                        accountId
                        ));
    }
}

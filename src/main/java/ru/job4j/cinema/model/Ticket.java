package ru.job4j.cinema.model;

import java.util.Objects;

public class Ticket {
    private int id;
    private int row;
    private int cell;

    public Ticket(int id, int row, int cell) {
        this.id = id;
        //this.sessionId = sessionId;
        this.row = row;
        this.cell = cell;
        //this.accountId = accountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return id == ticket.id
                && row == ticket.row
                && cell == ticket.cell;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, row, cell);
    }
}

CREATE TABLE account (
    id SERIAL PRIMARY KEY,
    username VARCHAR NOT NULL,
    phone VARCHAR NOT NULL
);

CREATE TABLE ticket (
    id SERIAL PRIMARY KEY,
    row INT NOT NULL,
    cell INT NOT NULL,
    account_id INT NOT NULL REFERENCES account(id),
    UNIQUE (row, cell)
);


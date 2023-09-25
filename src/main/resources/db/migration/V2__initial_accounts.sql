CREATE TABLE IF NOT EXISTS accounts (
    id bigserial NOT NULL PRIMARY KEY,
    email text NOT NULL UNIQUE,
    hashedPassword text NOT NULL,
    role int DEFAULT 0
);

CREATE TABLE IF NOT EXISTS profiles (
    id bigserial NOT NULL PRIMARY KEY REFERENCES accounts(id),
    firstname text,
    lastname text,
    role int
)
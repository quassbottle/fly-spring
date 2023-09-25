CREATE TABLE IF NOT EXISTS accounts (
    id bigserial NOT NULL PRIMARY KEY,
    email text NOT NULL UNIQUE,
    hashed_password text NOT NULL,
    role text DEFAULT 'default',
    created_at timestamp DEFAULT now()
);

/*CREATE TABLE IF NOT EXISTS profiles (
    id bigserial PRIMARY KEY,
    firstname text,
    lastname text,
    is_worker boolean DEFAULT true,
    account_id bigserial,
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE ON UPDATE CASCADE
);*/

CREATE TABLE IF NOT EXISTS profiles (
    id bigserial PRIMARY KEY,
    username text,
    firstname text,
    lastname text,
    is_worker boolean DEFAULT true,
    FOREIGN KEY (id) references accounts(id) ON DELETE CASCADE ON UPDATE CASCADE
);

--ALTER TABLE accounts ADD FOREIGN KEY (profile_id) REFERENCES profiles(id) ON DELETE CASCADE ON UPDATE CASCADE ;
--ALTER TABLE profiles ADD FOREIGN KEY (account_id) references accounts(id);


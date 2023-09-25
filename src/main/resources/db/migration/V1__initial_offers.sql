CREATE TABLE IF NOT EXISTS offers (
    id bigserial NOT NULL PRIMARY KEY,
    title text,
    description text,
    created_at timestamp DEFAULT now(),
    updated_at timestamp DEFAULT now(),
    latitude numeric,
    longitude numeric,
    price numeric,
    is_hourly boolean DEFAULT false
);
create table tiny_user(
    user_id UUID primary key,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email_id VARCHAR(255),
    active BOOLEAN
);
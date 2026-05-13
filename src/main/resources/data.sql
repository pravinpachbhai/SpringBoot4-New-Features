INSERT INTO users (username, password, enabled)
VALUES ('pravin', '$2a$12$gG9V.SuDt36jBB.cJg/LX.lDiWppGZi0GEobPiLN5DMMOBJYkAFKG', true),
       ('john', 'john', true);

INSERT INTO authorities VALUES
    ('pravin', 'ROLE_USER');
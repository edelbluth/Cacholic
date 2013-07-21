CREATE TABLE DB_VERSION
(
    VERSION_NO BIGINT NOT NULL,
    INSTALLATION TIMESTAMP NOT NULL,
    UNIQUE(VERSION_NO)
);

-- ::Next:STATEMENT:: --

INSERT INTO DB_VERSION(VERSION_NO, INSTALLATION) VALUES (1, CURRENT_TIMESTAMP);
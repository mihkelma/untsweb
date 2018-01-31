DROP SCHEMA PUBLIC CASCADE;
CREATE SEQUENCE seq1 AS INTEGER START WITH 1;
CREATE SEQUENCE seq2 AS INTEGER START WITH 1;

CREATE TABLE UNIT (
  id BIGINT NOT NULL PRIMARY KEY,
  name VARCHAR(255),
  size DOUBLE,
  price DOUBLE
);


CREATE TABLE USERS (
     username VARCHAR(255) NOT NULL PRIMARY KEY,
     password VARCHAR(255) NOT NULL,
     enabled BOOLEAN NOT NULL,
     name VARCHAR(255) NOT NULL
   );

CREATE TABLE AUTHORITIES (
     username VARCHAR(50) NOT NULL,
     authority VARCHAR(50) NOT NULL,
     FOREIGN KEY (username) REFERENCES USERS
       ON DELETE CASCADE
);

CREATE UNIQUE INDEX ix_auth_username ON AUTHORITIES (username, authority);

CREATE TABLE USERUNIT (
  user_username VARCHAR(255) NOT NULL,
  unit_id BIGINT NOT NULL,
  created DATE,
  status INTEGER,
  FOREIGN KEY (user_username) REFERENCES USERS,
  FOREIGN KEY (unit_id) REFERENCES UNIT,
  PRIMARY KEY (user_username, unit_id)
);

--INSERT INTO CUSTOMER VALUES (NEXT VALUE FOR seq1,'Brid','Patt','1234441111111','customer_type.private');
INSERT INTO UNIT VALUES (NEXT VALUE FOR seq2, 'Tehnika 21', 69.0, 570.00);
INSERT INTO UNIT VALUES (NEXT VALUE FOR seq2, 'Pärnapuu 79', 23.0, 200.00);

INSERT INTO users (USERNAME, PASSWORD, ENABLED, NAME) VALUES ('user', '$2a$10$FgFxjdC6w8l.89PcTuCUWeAGpDiOA.XKv.yOm8l5TWjPDvEUurkwK', true, 'Jack');
INSERT INTO users (USERNAME, PASSWORD, ENABLED, NAME) VALUES ('admin', '$2a$10$UxZKMrq.6ccx4gYYWkY1me4v8T6xE28Ch0IoQfFTWmjnZEG3agt3i', true, 'Jill');
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('user', 'ROLE_USER');
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO USERUNIT VALUES ('admin', 1, NOW(), 1);
INSERT INTO USERUNIT VALUES ('user', 2, NOW(), 1);
INSERT INTO USERUNIT VALUES ('user', 1, NOW(), 0);

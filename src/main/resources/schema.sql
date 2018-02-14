DROP SCHEMA PUBLIC CASCADE;
CREATE SEQUENCE seq1 AS INTEGER START WITH 1;
CREATE SEQUENCE seq2 AS INTEGER START WITH 1;
CREATE SEQUENCE seq3 AS INTEGER START WITH 1;
CREATE SEQUENCE seq4 AS INTEGER START WITH 1;
CREATE SEQUENCE seq5 AS INTEGER START WITH 1;

CREATE TABLE USERS (
    username VARCHAR(255) NOT NULL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    enabled BOOLEAN NOT NULL,
    firstname VARCHAR(50),
    lastname VARCHAR(70),
    phone VARCHAR(50),
    email VARCHAR(120) NOT NULL
   );

CREATE TABLE AUTHORITIES (
     username VARCHAR(50) NOT NULL,
     authority VARCHAR(50) NOT NULL,
     FOREIGN KEY (username) REFERENCES USERS
       ON DELETE CASCADE
);

CREATE UNIQUE INDEX ix_auth_username ON AUTHORITIES (username, authority);

CREATE TABLE UNIT (
  id BIGINT NOT NULL PRIMARY KEY,
  name VARCHAR(255),
  address VARCHAR(255),
  size DOUBLE,
  price DOUBLE,
  buildyear DATE,
  status INTEGER,
  floor INTEGER,
  rooms INTEGER,
  type VARCHAR(255),
  username VARCHAR(255) NOT NULL,
  FOREIGN KEY (username) REFERENCES USERS
    ON DELETE CASCADE
);

CREATE TABLE CUSTOMER (
  customerEmail VARCHAR(120) NOT NULL PRIMARY KEY,
  customerName VARCHAR(100) NOT NULL,
  customerAddress VARCHAR(255),
  customerPhone VARCHAR(50) NOT NULL,
  customerReference VARCHAR(100),
  ownerRef VARCHAR(255) NOT NULL
);

CREATE TABLE CONTRACT (
  id BIGINT NOT NULL PRIMARY KEY,
  created DATE NOT NULL,
  isActive BOOLEAN NOT NULL,
  closed DATE,
  dueDate Date,
  invoiceDate INTEGER,
  ownerName VARCHAR(100) NOT NULL,
  ownerAddress VARCHAR(255) NOT NULL,
  ownerPhone VARCHAR(50) NOT NULL,
  ownerEmail VARCHAR(120) NOT NULL,
  ownerIBAN VARCHAR(34),
  ownerBank VARCHAR(50),
  ownerNotes VARCHAR(120),
  ownerSalesName VARCHAR(100),
  isVATRequired BOOLEAN,
  ownerRef VARCHAR(255) NOT NULL,
  unit_id BIGINT NOT NULL,
  customer_id VARCHAR(120) NOT NULL,
  FOREIGN KEY (unit_id) REFERENCES UNIT(id)
    ON DELETE CASCADE,
  FOREIGN KEY (customer_id) REFERENCES CUSTOMER(customerEmail)
    ON DELETE CASCADE
);

CREATE TABLE INVOICE (
  id       BIGINT NOT NULL PRIMARY KEY,
  dateCreated  DATE,
  dateDue DATE,
  ownerName VARCHAR(100) NOT NULL,
  ownerAddress VARCHAR(255) NOT NULL,
  ownerPhone VARCHAR(50) NOT NULL,
  ownerEmail VARCHAR(120) NOT NULL,
  ownerIBAN VARCHAR(34),
  ownerBank VARCHAR(50),
  ownerNotes VARCHAR(120),
  ownerSalesName VARCHAR(100),
  isVATRequired BOOLEAN,
  customerEmail VARCHAR(120) NOT NULL,
  customerName VARCHAR(100) NOT NULL,
  customerAddress VARCHAR(255),
  customerPhone VARCHAR(50) NOT NULL,
  customerReference VARCHAR(100),
  ownerRef VARCHAR(255) NOT NULL,
  contract_id BIGINT NOT NULL,
  FOREIGN KEY (contract_id) REFERENCES CONTRACT
    ON DELETE CASCADE
);

CREATE TABLE INVOICEROW (
  id BIGINT NOT NULL PRIMARY KEY,
  title VARCHAR(150) NOT NULL,
  description VARCHAR(255) NOT NULL,
  quantity REAL,
  unitprice REAL,
  taxamount REAL,
  ownerRef VARCHAR(255) NOT NULL,
  invoice_id BIGINT NOT NULL,
  FOREIGN KEY (invoice_id) REFERENCES INVOICE
    ON DELETE CASCADE
);

INSERT INTO users (USERNAME, PASSWORD, ENABLED, EMAIL) VALUES ('user', '$2a$10$FgFxjdC6w8l.89PcTuCUWeAGpDiOA.XKv.yOm8l5TWjPDvEUurkwK', true, 'u@u.ee');
INSERT INTO users (USERNAME, PASSWORD, ENABLED, EMAIL) VALUES ('admin', '$2a$10$UxZKMrq.6ccx4gYYWkY1me4v8T6xE28Ch0IoQfFTWmjnZEG3agt3i', true, 'a@a.ee');
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('user', 'ROLE_USER');
INSERT INTO AUTHORITIES (USERNAME, AUTHORITY) VALUES ('admin', 'ROLE_ADMIN');

INSERT INTO UNIT VALUES (NEXT VALUE FOR seq1, 'Tehnika 21', 'Address', 69.0, 570.00, NOW(), 1, 5, 3, 'Korter' ,'user');
INSERT INTO UNIT VALUES (NEXT VALUE FOR seq1, 'Tehnika 22', 'Address', 39.0, 400.00, NOW(), 1, 2, 2, 'Korter' ,'user');
INSERT INTO UNIT VALUES (NEXT VALUE FOR seq1, 'Tehnika 23', 'Address', 17.0, 60.00, NOW(), 1, 1, 1, 'Garaaz' ,'user');
INSERT INTO UNIT VALUES (NEXT VALUE FOR seq1, 'Tehnika 24', 'Address', 109.0, 870.00, NOW(), 1, 3, 4, 'Korter' ,'admin');
INSERT INTO UNIT VALUES (NEXT VALUE FOR seq1, 'Tehnika 25', 'Address', 48.2, 295.00, NOW(), 1, 1, 2, 'Korter' ,'admin');


INSERT INTO CUSTOMER VALUES ('c@c.cc','Juhan', 'Coopa 17', '1111','','user');
INSERT INTO CONTRACT VALUES (NEXT VALUE FOR seq2, now(), TRUE, NULL, ADD_MONTHS ( NOW() , 2), 5, 'Kalle','Tehnika 211-1',
                                                      '3725500333','@a','1','AS LHV Pank','','Kalle', FALSE,'user',1,'c@c.cc');

--INSERT INTO CONTRACT VALUES (NEXTVAL ('seq2'), now(), TRUE, NULL, NOW(), 5, 'Kalle','Tehnika 211-1','3725500333','@a','1','AS LHV Pank','','Kalle', FALSE,'user',7,'c@c.cc');
/*
V0.2.3
1. Created admin user.
2. Created tables:
    Employee
    EmpPLevel
    PLevel
    Project
    ProAssi
    ProEmp
    WorkPackage
    WPEmp
    Timesheet
    TimesheetRow
    Role
*/


DROP DATABASE APMS;
CREATE DATABASE APMS;

DROP USER 'employee'@'localhost';
DROP USER 'employee'@'%';
CREATE USER 'employee'@'localhost' IDENTIFIED BY 'P@$$w0rd';
CREATE USER 'employee'@'%' IDENTIFIED BY 'P@$$w0rd';
GRANT ALL PRIVILEGES ON APMS.* TO 'employee'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON APMS.* TO 'employee'@'%' WITH GRANT OPTION;

USE APMS;


CREATE TABLE Employee
(
    EmpNo           INTEGER         NOT NULL,
    EmpFirstName    VARCHAR(100)    NOT NULL,
    EmpLastName     VARCHAR(100)    NOT NULL,
    EmpUserName     VARCHAR(50)     NOT NULL UNIQUE,
    Password        VARCHAR(512)    NOT NULL,
    SuperEmpNo      INTEGER         NOT NULL,
    ApproEmpNo      INTEGER         NOT NULL,
    State           VARCHAR(30)     NOT NULL,
    Comment         VARCHAR(1000)   ,
    PRIMARY KEY(EmpNo)
);

<<<<<<< HEAD
INSERT INTO APMS.Employee VALUES ('1', 'Admin', 'Admin', 'admin', 'sha1:64000:18:N0Iez+Sidz0ul3va+Qz94inOsDZZsf8E:K2muqi03BKskKSFzVhYpwqLh', '4', '67', 'Left', 'Occaecati odit quia.', NULL, NULL);
INSERT INTO APMS.Employee VALUES ('2', 'Amy', 'Hong', 'amy', 'sha1:64000:18:RGTXMi9UDbk4UBr+4Py1yjz7NITF8tqR:P3mGVCiKBFoZB3/oKVyJQ+0I', '1', '71', 'Active', 'Sint vel dolorem iste necessitatibus et alias quia voluptas.', NULL, NULL);
INSERT INTO APMS.Employee VALUES ('3', 'Andy', 'Tang', 'andy', 'sha1:64000:18:pzaYQSaQXWFxRtgxPgTlA2f89Zq11ngF:qoYwJ1clkjm0E5qpemvRyrkh', '14', '31', 'Active', 'Nesciunt id fugiat aut aliquam maiores dolores consequuntur accusamus.', NULL, NULL);
INSERT INTO APMS.Employee VALUES ('4', 'Aska', 'Fang', 'aska', 'sha1:64000:18:Kdf52cVlVCfnTthr1/+ZLFhoHLzdarpi:s6cPghO8LiPAH90qPhpp0SU7', '11', '40', 'Active', 'Omnis in ipsum id quas error.', NULL, NULL);
INSERT INTO APMS.Employee VALUES ('5', 'Eva', 'Au', 'eva', 'sha1:64000:18:5NFKhO5HdEu3xfNmnc8IOvztcj3m/ALF:Et2SP6Wy8HIoUDIZIuNBztS2', '6', '47', 'Retired', 'Et sit quos et.', NULL, NULL);
INSERT INTO APMS.Employee VALUES ('6', 'Greg', 'Mercer', 'greg', 'sha1:64000:18:rw1b/bF0ke3Br42BLIErOlAQj40XaEkd:eNjaQLp82C4br0w0h9XEyEDx', '25', '95', 'Active', 'Et omnis eos modi ut quia rerum aut facere.', NULL, NULL);
INSERT INTO APMS.Employee VALUES ('7', 'Hank', 'Chang', 'hank', 'sha1:64000:18:0p9Ll2s4sFDPkRYd8jWikMYYKhiJqzPh:2MBr1VoQbV41007o20BvYD5l', '4', '81', 'Deleted', 'Molestias quis laborum alias aut necessitatibus non soluta sint.', NULL, NULL);
INSERT INTO APMS.Employee VALUES ('8', 'Helen', 'Han', 'helen', 'sha1:64000:18:zLg6vAfZGt1oV9Hh/K/VskKG/W5YPGL9:OB+sufOeYvKW71OQua+2ZyC0', '17', '3', 'Left', 'Sunt quasi atque minus qui eum qui vero.', NULL, NULL);
INSERT INTO APMS.Employee VALUES ('9', 'Karanveer', 'Khanna', 'karan', 'sha1:64000:18:dDr0eN1tjJmxu6i9HNR5qjfSmbiMGb19:BXPqMAyFJr8BBS4bPk+c3TSE', '17', '48', 'Left', 'Repudiandae libero quibusdam suscipit culpa alias commodi atque harum.', NULL, NULL);
INSERT INTO APMS.Employee VALUES ('10', 'Mike', 'Hoang', 'mike', 'sha1:64000:18:isoZHrHsHNL7fK3E7EFWj0V+WmFLjOyp:Qlau9OZSnbiRyND9iPH5sapi', '10', '56', 'Left', 'Praesentium cumque pariatur impedit tempora optio aut quo ea id.', NULL, NULL);
INSERT INTO APMS.Employee VALUES ('11', 'Nirajan', 'Manandhar', 'nirajan', 'sha1:64000:18:+XO6H1APKuKYrietN6YsM2weZkCsYmqV:uY7NMteEx3hyFsrTnPXhD42d', '24', '12', 'Active', 'Qui reiciendis qui.', NULL, NULL);
INSERT INTO APMS.Employee VALUES ('12', 'Ryan', 'Liang', 'ryan', 'sha1:64000:18:XABnXYtLhiRlzuBIBmVcDCdwftZm3vJR:CUaIjka40w2/x/t4ZreN0cZW', '24', '5', 'Active', 'Omnis voluptas ut doloribus nesciunt id numquam qui architecto.', NULL, NULL);
INSERT INTO APMS.Employee VALUES ('13', 'Tommy', 'Yeh', 'tommy', 'sha1:64000:18:rn+JlfNIudfumliiBpwoyMpuGYZR/o61:PikclcAyFNt7cjgidwYmHmBd', '6', '84', 'Deleted', 'Neque dolores id magni nobis et sit debitis.', NULL, NULL);
INSERT INTO APMS.Employee VALUES ('14', 'Test', 'Test', 'test', 'sha1:64000:18:Gva6Da/2KniKBQFetzoF/ApJppU18smV:DWMsNXsx4DtG0BJlLRDB7Ngf', '6', '60', 'Left', 'Nihil cumque dolorum doloribus adipisci.', NULL, NULL);
INSERT INTO APMS.Employee VALUES ('15', 'Bruce', 'Link', 'bruce', 'sha1:64000:18:Vk1SQ24USA0J1vlnf9Ayb1O+SVRrImXE:hEZt0FSf1PvMq5GpdkK6HLky', '8', '10', 'Retired', 'Numquam doloribus harum.', NULL, NULL);
=======
INSERT INTO APMS.Employee VALUES ('1', 'Admin', 'Admin', 'admin', 'sha1:64000:18:N0Iez+Sidz0ul3va+Qz94inOsDZZsf8E:K2muqi03BKskKSFzVhYpwqLh', '4', '67', 'Left', 'Occaecati odit quia.');
INSERT INTO APMS.Employee VALUES ('2', 'Amy', 'Hong', 'amy', 'sha1:64000:18:RGTXMi9UDbk4UBr+4Py1yjz7NITF8tqR:P3mGVCiKBFoZB3/oKVyJQ+0I', '1', '71', 'Active', 'Sint vel dolorem iste necessitatibus et alias quia voluptas.');
INSERT INTO APMS.Employee VALUES ('3', 'Andy', 'Tang', 'andy', 'sha1:64000:18:pzaYQSaQXWFxRtgxPgTlA2f89Zq11ngF:qoYwJ1clkjm0E5qpemvRyrkh', '14', '31', 'Active', 'Nesciunt id fugiat aut aliquam maiores dolores consequuntur accusamus.');
INSERT INTO APMS.Employee VALUES ('4', 'Aska', 'Fang', 'aska', 'sha1:64000:18:Kdf52cVlVCfnTthr1/+ZLFhoHLzdarpi:s6cPghO8LiPAH90qPhpp0SU7', '11', '40', 'Active', 'Omnis in ipsum id quas error.');
INSERT INTO APMS.Employee VALUES ('5', 'Eva', 'Au', 'eva', 'sha1:64000:18:5NFKhO5HdEu3xfNmnc8IOvztcj3m/ALF:Et2SP6Wy8HIoUDIZIuNBztS2', '6', '47', 'Retired', 'Et sit quos et.');
INSERT INTO APMS.Employee VALUES ('6', 'Greg', 'Mercer', 'greg', 'sha1:64000:18:rw1b/bF0ke3Br42BLIErOlAQj40XaEkd:eNjaQLp82C4br0w0h9XEyEDx', '25', '95', 'Active', 'Et omnis eos modi ut quia rerum aut facere.');
INSERT INTO APMS.Employee VALUES ('7', 'Hank', 'Chang', 'hank', 'sha1:64000:18:0p9Ll2s4sFDPkRYd8jWikMYYKhiJqzPh:2MBr1VoQbV41007o20BvYD5l', '4', '81', 'Deleted', 'Molestias quis laborum alias aut necessitatibus non soluta sint.');
INSERT INTO APMS.Employee VALUES ('8', 'Helen', 'Han', 'helen', 'sha1:64000:18:zLg6vAfZGt1oV9Hh/K/VskKG/W5YPGL9:OB+sufOeYvKW71OQua+2ZyC0', '17', '3', 'Left', 'Sunt quasi atque minus qui eum qui vero.');
INSERT INTO APMS.Employee VALUES ('9', 'Karanveer', 'Khanna', 'karan', 'sha1:64000:18:dDr0eN1tjJmxu6i9HNR5qjfSmbiMGb19:BXPqMAyFJr8BBS4bPk+c3TSE', '17', '48', 'Left', 'Repudiandae libero quibusdam suscipit culpa alias commodi atque harum.');
INSERT INTO APMS.Employee VALUES ('10', 'Mike', 'Hoang', 'mike', 'sha1:64000:18:isoZHrHsHNL7fK3E7EFWj0V+WmFLjOyp:Qlau9OZSnbiRyND9iPH5sapi', '10', '56', 'Left', 'Praesentium cumque pariatur impedit tempora optio aut quo ea id.');
INSERT INTO APMS.Employee VALUES ('11', 'Nirajan', 'Manandhar', 'nirajan', 'sha1:64000:18:+XO6H1APKuKYrietN6YsM2weZkCsYmqV:uY7NMteEx3hyFsrTnPXhD42d', '24', '12', 'Active', 'Qui reiciendis qui.');
INSERT INTO APMS.Employee VALUES ('12', 'Ryan', 'Liang', 'ryan', 'sha1:64000:18:XABnXYtLhiRlzuBIBmVcDCdwftZm3vJR:CUaIjka40w2/x/t4ZreN0cZW', '24', '5', 'Active', 'Omnis voluptas ut doloribus nesciunt id numquam qui architecto.');
INSERT INTO APMS.Employee VALUES ('13', 'Tommy', 'Yeh', 'tommy', 'sha1:64000:18:rn+JlfNIudfumliiBpwoyMpuGYZR/o61:PikclcAyFNt7cjgidwYmHmBd', '6', '84', 'Deleted', 'Neque dolores id magni nobis et sit debitis.');
INSERT INTO APMS.Employee VALUES ('14', 'Test', 'Test', 'test', 'sha1:64000:18:Gva6Da/2KniKBQFetzoF/ApJppU18smV:DWMsNXsx4DtG0BJlLRDB7Ngf', '6', '60', 'Left', 'Nihil cumque dolorum doloribus adipisci.');
INSERT INTO APMS.Employee VALUES ('15', 'Bruce', 'Link', 'bruce', 'sha1:64000:18:Vk1SQ24USA0J1vlnf9Ayb1O+SVRrImXE:hEZt0FSf1PvMq5GpdkK6HLky', '8', '10', 'Retired', 'Numquam doloribus harum.');
>>>>>>> parent of 6b2e2b4... Merge pull request #52 from Karanveer-1/Karan_Dev



-- Create table EmpPLevel
CREATE TABLE EmpPLevel
(
    EmpNo       INTEGER       NOT NULL,
    StartDate   DATE          NOT NULL,
    PLevel      VARCHAR(30)   NOT NULL,
    PRIMARY KEY(EmpNo, StartDate)
);

-- Load data into table EmpPLevel




-- Create table PLevel
CREATE TABLE PLevel
(
    StartDate     DATE       NOT NULL,
    PLevel        VARCHAR(30)   NOT NULL,
    Wage          FLOAT         NOT NULL,
    PRIMARY KEY(StartDate, PLevel)
);

-- Load data into table PLevel




-- Create table Project
CREATE TABLE Project
(
    ProNo           INTEGER         NOT NULL,
    ProMgrEmpNo     INTEGER         NOT NULL,
    ProName         VARCHAR(200)    NOT NULL,
    ProDescription  VARCHAR(1000)   NOT NULL,
    Budget          FLOAT           NOT NULL,
    State           VARCHAR(30)     NOT NULL,
    Comment         VARCHAR(1000)   ,
    PRIMARY KEY(ProNo)
);

-- Load data into table Project

INSERT INTO APMS.Project VALUES('100', '4', 'Macaroni', 'First Project', '123.45','OPEN', 'First Project' );
INSERT INTO APMS.Project VALUES('102', '2', 'Pizza', 'Second Project', '14.45','OPEN', 'Second Project' );



-- Create table ProAssi
CREATE TABLE ProAssi
(
    ProNo         INTEGER         NOT NULL,
    ProAssiEmpNo  INTEGER         NOT NULL,
    PRIMARY KEY(ProNo, ProAssiEmpNo)
);

-- Load data into table ProAssi




-- Create table ProEmp
CREATE TABLE ProEmp
(
    ProNo        INTEGER       NOT NULL,
    EmpNo        INTEGER       NOT NULL,
    PRIMARY KEY(ProNo, EmpNo)
);

-- Load data into table ProEmp




-- Create table WorkPackage
CREATE TABLE WorkPackage
(
    ProNo          INTEGER         NOT NULL,
    WPID           VARCHAR(30)     NOT NULL,
    REEmpNo        INTEGER         NOT NULL,
    WPTitle        VARCHAR(200)    NOT NULL,
    WPDescription  VARCHAR(1000)   NOT NULL,
    ParentWPID     VARCHAR(30)     ,
    Budget         FLOAT           NOT NULL,
    State          VARCHAR(30)     NOT NULL,
    Comment        VARCHAR(1000)   ,
    PRIMARY KEY(ProNo, WPID)
);

INSERT INTO APMS.WorkPackage VALUES('100', 'COMP101', '1','COMP101','COMP101',NULL,'10','OPEN',NULL);
INSERT INTO APMS.WorkPackage VALUES('100', 'COMP201', '1','COMP201','COMP201','COMP101','10','OPEN',NULL);
INSERT INTO APMS.WorkPackage VALUES('100', 'COMP303', '1','COMP303','COMP303','COMP201','10','OPEN',NULL);
INSERT INTO APMS.WorkPackage VALUES('100', 'COMP305', '1','COMP305','COMP305','COMP201','10','OPEN',NULL);
INSERT INTO APMS.WorkPackage VALUES('100', 'COMP204', '1','COMP204','COMP204','COMP101','10','OPEN',NULL);
INSERT INTO APMS.WorkPackage VALUES('100', 'BUSI101', '1','BUSI101','BUSI101',NULL,'10','OPEN',NULL);
INSERT INTO APMS.WorkPackage VALUES('100', 'COMM101', '1','COMM101','COMM101',NULL,'10','OPEN',NULL);



-- Create table WPEmp
CREATE TABLE WPEmp
(
    ProNo        INTEGER       NOT NULL,
    WPID         VARCHAR(30)   NOT NULL,
    EmpNo        INTEGER       NOT NULL,
    PRIMARY KEY(ProNo, WPID, EmpNo)
);

-- Load data into table WPEmp



-- Create table Timesheet
CREATE TABLE Timesheet
(
    EmpNo          INTEGER         NOT NULL,
    StartDate      DATE            NOT NULL,
    Signature      VARCHAR(100)    ,
    ApprovedEmpNo  INTEGER         ,
    State          VARCHAR(30)     NOT NULL,
    Comment        VARCHAR(1000)   ,
    PRIMARY KEY(EmpNo, StartDate)
);

-- Load data into table Timesheet





-- Create table TimesheetRow
CREATE TABLE TimesheetRow
(
    EmpNo        INTEGER         NOT NULL,
    StartDate    DATE            NOT NULL,
    ProNo        INTEGER         NOT NULL,
    WPID         VARCHAR(30)     NOT NULL,
    Sat          FLOAT           ,
    Sun          FLOAT           ,
    Mon          FLOAT           ,
    Tue          FLOAT           ,
    Wed          FLOAT           ,
    Thu          FLOAT           ,
    Fri          FLOAT           ,
    Note         VARCHAR(1000)   ,
    State        VARCHAR(30)     NOT NULL,
    Comment      VARCHAR(1000)   ,
    PRIMARY KEY(EmpNo, StartDate, ProNo, WPID)
);

-- Load data into table TimesheetRow




-- Create table Role
CREATE TABLE Role
(
    EmpNo          INTEGER         NOT NULL,
    Role           VARCHAR(100)    NOT NULL,
    PRIMARY KEY(EmpNo, Role)
);

-- Load data into table Role



CREATE TABLE Signature(
	EmpNo          INTEGER         NOT NULL,
    StartDate      DATE            NOT NULL,
	signature 	   TINYBLOB 	   NOT NULL, 
	publicKey 	   BLOB 		   NOT NULL,
	PRIMARY KEY(EmpNo, StartDate)
);

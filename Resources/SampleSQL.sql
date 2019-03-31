/*
V0.4.1
1. Created admin user.
2. Created tables:
    Employee
    EmpPLevel
    PLevel
    Project
    ProEmp
    WorkPackage
    WPEmp
	WPNeed
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
    Passphrase        VARCHAR(512)  NULL,
    PRIMARY KEY(EmpNo)
);

-- Load data into table Employee
INSERT INTO APMS.Employee VALUES('1', 'Admin', 'Admin', 'admin', 'sha1:64000:18:6BMHQNrAa7QUpYtffRlt8RSaxuoeoy6o:nn4ooDmw3agMFtiFfwcyiok6', '1', '1', 'Active', '', NULL);
INSERT INTO APMS.Employee VALUES('2', 'Karanveer', 'Khanna', 'karan', 'sha1:64000:18:XLut11LQ4mTSfG/qtpIlx+0pV22c3CWw:TLNw6ESsnrM512eCYCSl+8bu', '1', '1', 'Active', '', NULL);
INSERT INTO APMS.Employee VALUES('3', 'Ryan', 'Liang', 'ryan', 'sha1:64000:18:CoHbU5wqpLwD9vhz0ristkDVhHcDrNa/:w8M4ttFIRj2KgUjgSrTFTDZI', '1', '2', 'Active', '', NULL);
INSERT INTO APMS.Employee VALUES('4', 'Test', 'Test', 'test', 'sha1:64000:18:Gva6Da/2KniKBQFetzoF/ApJppU18smV:DWMsNXsx4DtG0BJlLRDB7Ngf', '2', '3', 'Active', '', NULL);



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
	ProName         VARCHAR(200)    NOT NULL,
	ProMgrEmpNo     INTEGER         NOT NULL,
	ProAssiEmpNo    INTEGER         NOT NULL,
    ProDescription  VARCHAR(1000)   NOT NULL,
    StartDate       DATE            NOT NULL,
	EndDate         DATE            NOT NULL,
    State           VARCHAR(30)     NOT NULL,
    Comment         VARCHAR(1000)   ,
    PRIMARY KEY(ProNo)
);

-- Load data into table Project
INSERT INTO APMS.Project VALUES('100', 'Macaroni','4', '3', 'First Project', '2018-01-06', '2019-12-27', 'OPEN', 'First Project' );
INSERT INTO APMS.Project VALUES('101', 'Pizza','2', '3', 'Second Project', '2018-01-06', '2019-12-27', 'OPEN', 'Second Project' );




-- Create table ProEmp
CREATE TABLE ProEmp
(
    ProNo        INTEGER       NOT NULL,
    EmpNo        INTEGER       NOT NULL,
    PRIMARY KEY(ProNo, EmpNo)
);

-- Load data into table ProEmp
INSERT INTO APMS.ProEmp VALUES('100', '1');
INSERT INTO APMS.ProEmp VALUES('101', '1');



-- Create table WorkPackage
CREATE TABLE WorkPackage
(
    ProNo          INTEGER         NOT NULL,
    WPID           VARCHAR(30)     NOT NULL,
    REEmpNo        INTEGER         NOT NULL,
    WPTitle        VARCHAR(200)    NOT NULL,
    WPDescription  VARCHAR(1000)   NOT NULL,
	StartDate      DATE            NOT NULL,
	EndDate        DATE            NOT NULL,
	IsLeaf         BOOLEAN         NOT NULL,	
    ParentWPID     VARCHAR(30)     ,
	PMEstP1        INTEGER         ,
	PMEstP2        INTEGER         ,
	PMEstP3        INTEGER         ,
	PMEstP4        INTEGER         ,
	PMEstP5        INTEGER         ,
	PMEstP6        INTEGER         ,
	PMEstDS        INTEGER         ,
	PMEstSS        INTEGER         ,
	PMEstJS        INTEGER         ,
	REEstP1        INTEGER         ,
	REEstP2        INTEGER         ,
	REEstP3        INTEGER         ,
	REEstP4        INTEGER         ,
	REEstP5        INTEGER         ,
	REEstP6        INTEGER         ,
	REEstDS        INTEGER         ,
	REEstSS        INTEGER         ,
	REEstJS        INTEGER         ,
    State          VARCHAR(30)     NOT NULL,
    Comment        VARCHAR(1000)   ,
    PRIMARY KEY(ProNo, WPID)
);

-- Load data into table WorkPackage
INSERT INTO APMS.WorkPackage VALUES('100', 'COMP101', '1', 'COMP101', 'COMP101', '2018-01-06', '2019-12-27', false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'OPEN', NULL);
INSERT INTO APMS.WorkPackage VALUES('100', 'COMP201', '1', 'COMP201', 'COMP201', '2018-01-06', '2019-12-27', false, 'COMP101', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'OPEN', NULL);
INSERT INTO APMS.WorkPackage VALUES('100', 'COMP303', '1', 'COMP303', 'COMP303', '2018-01-06', '2019-12-27', true, 'COMP201', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'OPEN', NULL);
INSERT INTO APMS.WorkPackage VALUES('100', 'COMP305', '1', 'COMP305', 'COMP305', '2018-01-06', '2019-12-27', true, 'COMP201', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'OPEN', NULL);
INSERT INTO APMS.WorkPackage VALUES('100', 'COMP204', '1', 'COMP204', 'COMP204', '2018-01-06', '2019-12-27', true, 'COMP101', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'OPEN', NULL);
INSERT INTO APMS.WorkPackage VALUES('101', 'BUSI101', '1', 'BUSI101', 'BUSI101', '2018-01-06', '2019-12-27', true, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'OPEN', NULL);




-- Create table WPEmp
CREATE TABLE WPEmp
(
    ProNo        INTEGER       NOT NULL,
    WPID         VARCHAR(30)   NOT NULL,
    EmpNo        INTEGER       NOT NULL,
    PRIMARY KEY(ProNo, WPID, EmpNo)
);

-- Load data into table WPEmp




-- Create table WPNeed
CREATE TABLE WPNeed
(
    ProNo          INTEGER         NOT NULL,
    WPID           VARCHAR(30)     NOT NULL,
	StartDate      DATE            NOT NULL,
	RENeedP1       INTEGER         ,
	RENeedP2       INTEGER         ,
	RENeedP3       INTEGER         ,
	RENeedP4       INTEGER         ,
	RENeedP5       INTEGER         ,
	RENeedP6       INTEGER         ,
	RENeedDS       INTEGER         ,
	RENeedSS       INTEGER         ,
	RENeedJS       INTEGER         ,
    State          VARCHAR(30)     NOT NULL,
    Comment        VARCHAR(1000)   ,
    PRIMARY KEY(ProNo, WPID, StartDate)
);

-- Load data into table WPNeed



-- Create table Timesheet
CREATE TABLE Timesheet
(
    EmpNo          INTEGER         NOT NULL,
    StartDate      DATE            NOT NULL,
    Overtime       FLOAT           DEFAULT '0',
	Flextime       FLOAT           DEFAULT '0',
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
    Sat          FLOAT           DEFAULT '0',
    Sun          FLOAT           DEFAULT '0',
    Mon          FLOAT           DEFAULT '0',
    Tue          FLOAT           DEFAULT '0',
    Wed          FLOAT           DEFAULT '0',
    Thu          FLOAT           DEFAULT '0',
    Fri          FLOAT           DEFAULT '0',
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
INSERT INTO APMS.Role VALUES('1', 'SYSTEM ADMIN');
INSERT INTO APMS.Role VALUES('2', 'HUMAN RESOURCE');


CREATE TABLE Signature(
	EmpNo          INTEGER         NOT NULL,
    StartDate      DATE            NOT NULL,
	Signature 	   TINYBLOB 	   NOT NULL,
	PublicKey 	   BLOB 		   NOT NULL,
	PRIMARY KEY(EmpNo, StartDate)
);

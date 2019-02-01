/*
V0.1.4
1. Created admin user.
2. Created Employee, Project, ProjectForm, ProEmp, WorkPackage, WorkPackageRelationship, Timesheet, and TimesheetRow which are for phase 1.
*/


-- Create database
DROP DATABASE APMS;
CREATE DATABASE APMS;

-- Create admin user
DROP USER 'employee'@'localhost';
DROP USER 'employee'@'%';
CREATE USER 'employee'@'localhost' IDENTIFIED BY 'password';
CREATE USER 'employee'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON APMS.* TO 'employee'@'localhost' WITH GRANT OPTION;
GRANT ALL PRIVILEGES ON APMS.* TO 'employee'@'%' WITH GRANT OPTION;

USE APMS;

-- Create table Employee
CREATE TABLE Employee
(
	EmpNo        	INTEGER      	NOT NULL,
    EmpFirstName  	VARCHAR(100) 	NOT NULL,
    EmpLastName   	VARCHAR(100) 	NOT NULL,
	EmpUserName  	VARCHAR(50)     NOT NULL UNIQUE,
    Password     	VARCHAR(50)     NOT NULL,
	PRIMARY KEY(EmpNo)
);

-- Load data into table Employee
INSERT INTO APMS.EMPLOYEE VALUES('1', 'Admin', 'Admin' , 'admin', 'password');
INSERT INTO APMS.EMPLOYEE VALUES('2', 'Karanveer', 'Khanna', 'karan', 'password');
INSERT INTO APMS.EMPLOYEE VALUES('3', 'Ryan', 'Liang', 'ryan', 'password');
INSERT INTO APMS.EMPLOYEE VALUES('4', 'Test', 'Test', 'test', 'password');


-- Create table Project
CREATE TABLE Project
(
	ProNo        INTEGER       NOT NULL,
    ProMgrEmpNo  INTEGER       NOT NULL,
    State        VARCHAR(30)   NOT NULL,
	PRIMARY KEY(ProNo)
);

-- Load data into table Project


-- Create table ProjectForm
CREATE TABLE ProjectForm
(
	ProNo           INTEGER        NOT NULL,
    Name            VARCHAR(100)   NOT NULL,
    Position        VARCHAR(50)    NOT NULL,
	ReportsTo       VARCHAR(100)   NOT NULL,
    ProName         VARCHAR(200)   NOT NULL,
	ProMgr          VARCHAR(100)   NOT NULL,
	JobDescription  VARCHAR(1000)  NOT NULL,
	Duration        VARCHAR(1000)  NOT NULL,
	Milestone       VARCHAR(1000)  NOT NULL,
	Training        VARCHAR(1000)  NOT NULL,
	Special         VARCHAR(1000)  NOT NULL,
	Criteria        VARCHAR(1000)  NOT NULL,
	Signatures      VARCHAR(1000)  NOT NULL,
	Dates           DATE           NOT NULL,
	PRIMARY KEY(ProNo)
);

-- Load data into table ProjectForm





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
	ProNo        INTEGER       NOT NULL,
    WPID         VARCHAR(30)   NOT NULL,
    REEmpNo      INTEGER       NOT NULL,
	State        VARCHAR(30)   NOT NULL,
	PRIMARY KEY(ProNo, WPID)
);

-- Load data into table WorkPackage





-- Create table WorkPackageRelationship
CREATE TABLE WorkPackageRelationship
(
	ProNo        INTEGER       NOT NULL,
    ChildWPID    VARCHAR(30)   NOT NULL,
    ParentWPID   VARCHAR(30)   NOT NULL,
	PRIMARY KEY(ProNo, ChildWPID)
);

-- Load data into table WorkPackageRelationship





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
	EmpNo          INTEGER       NOT NULL,
	Year           INTEGER       NOT NULL,
    WeekNo         INTEGER       NOT NULL,
	ApprovedEmpNo  INTEGER       NOT NULL,
    State          VARCHAR(30)   NOT NULL,
	PRIMARY KEY(EmpNo, Year, WeekNo)
);

-- Load data into table Timesheet





-- Create table TimesheetRow
CREATE TABLE TimesheetRow
(
	EmpNo        INTEGER       NOT NULL,
	Year         INTEGER       NOT NULL,
    WeekNo       INTEGER       NOT NULL,
    ProNo        INTEGER       NOT NULL,
	WPID         VARCHAR(30)   NOT NULL,
	Sat          FLOAT         NOT NULL,
	Sun          FLOAT         NOT NULL,
	Mon          FLOAT         NOT NULL,
	Tue          FLOAT         NOT NULL,
	Wed          FLOAT         NOT NULL,
	Thu          FLOAT         NOT NULL,
	Fri          FLOAT         NOT NULL,
	PRIMARY KEY(EmpNo, Year, WeekNo, ProNo, WPID)
);

-- Load data into table TimesheetRow






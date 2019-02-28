/*
V0.2.1
1. Created admin user.
2. Created Employee, SuperEmp, ApproEmp, PLevel, EmpVacSick, LeaveData,
   Project, ProAssi, ProjectForm, ProEmp, WorkPackage, WorkPackageForm,
   WorkPackageRelationship, WPEmp, Timesheet, TimesheetRow, and Role tables.
*/


-- Create database
DROP DATABASE APMS;
CREATE DATABASE APMS;

-- Create admin user
DROP USER 'employee'@'localhost';
DROP USER 'employee'@'%';
CREATE USER 'employee'@'localhost' IDENTIFIED BY 'P@$$w0rd';
CREATE USER 'employee'@'%' IDENTIFIED BY 'P@$$w0rd';
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
    Password     	VARCHAR(512)     NOT NULL,
	PLevel          VARCHAR(30)     NOT NULL,
	State           VARCHAR(30)     NOT NULL,
	Comment         VARCHAR(1000)   ,
	PRIMARY KEY(EmpNo)
);


-- Load data into table Employee
INSERT INTO APMS.Employee VALUES('1', 'Admin', 'Admin' , 'admin', 'password', 'P1', 'Active', '');
INSERT INTO APMS.Employee VALUES('2', 'Karanveer', 'Khanna', 'karan', 'password', 'P1', 'Active', '');
INSERT INTO APMS.Employee VALUES('3', 'Ryan', 'Liang', 'ryan', 'password', 'P1', 'Active', '');
INSERT INTO APMS.Employee VALUES('4', 'Test', 'Test', 'test', 'password', 'P1', 'Active', '');



-- Create table SuperEmp
CREATE TABLE SuperEmp
(
	SuperEmpNo     INTEGER       NOT NULL,
    EmpNo          INTEGER       NOT NULL,
	PRIMARY KEY(SuperEmpNo, EmpNo)
);

-- Load data into table SuperEmp




-- Create table ApproEmp
CREATE TABLE ApproEmp
(
	ApproEmpNo     INTEGER       NOT NULL,
    EmpNo          INTEGER       NOT NULL,
	PRIMARY KEY(ApproEmpNo, EmpNo)
);

-- Load data into table ApproEmp




-- Create table PLevel
CREATE TABLE PLevel
(
	Year          INTEGER       NOT NULL,
	PLevel        VARCHAR(30)   NOT NULL,
    Wage          FLOAT         NOT NULL,
	PRIMARY KEY(Year, PLevel)
);

-- Load data into table PLevel




-- Create table EmpVacSick
CREATE TABLE EmpVacSick
(
	EmpNo           INTEGER       NOT NULL,
    Year            INTEGER       NOT NULL,
    VacationDays    INTEGER       NOT NULL,
	SickDays        INTEGER       NOT NULL,
	PRIMARY KEY(EmpNo, Year)
);

-- Load data into table EmpVacSick




-- Create table LeaveData
CREATE TABLE LeaveData
(
	EmpNo          INTEGER         NOT NULL,
	StartDate      DATE            NOT NULL,
	Days           INTEGER         NOT NULL,
	Category       VARCHAR(100)    NOT NULL,
	Details        VARCHAR(1000)   NOT NULL,
	ApprovedEmpNo  INTEGER         ,
	State          VARCHAR(30)     NOT NULL,
	Comment        VARCHAR(1000)   ,
	PRIMARY KEY(EmpNo, StartDate)
);

-- Load data into table LeaveData




-- Create table Project
CREATE TABLE Project
(
	ProNo        INTEGER         NOT NULL,
    ProMgrEmpNo  INTEGER         NOT NULL,
    State        VARCHAR(30)     NOT NULL,
	Comment      VARCHAR(1000)   ,
	PRIMARY KEY(ProNo)
);

-- Load data into table Project




-- Create table ProAssi
CREATE TABLE ProAssi
(
	ProNo         INTEGER         NOT NULL,
    ProAssiEmpNo  INTEGER         NOT NULL,
	PRIMARY KEY(ProNo, ProAssiEmpNo)
);

-- Load data into table ProAssi



-- Create table ProjectForm
CREATE TABLE ProjectForm
(
	ProNo           INTEGER        NOT NULL,
    Name            VARCHAR(100)   NOT NULL,
    Position        VARCHAR(50)    ,
	ReportsTo       VARCHAR(100)   ,
    ProName         VARCHAR(200)   ,
	ProMgrEmpNo     INTEGER        ,
	ProMgr          VARCHAR(100)   ,
	JobDescription  VARCHAR(1000)  ,
	Duration        VARCHAR(1000)  ,
	Milestone       VARCHAR(1000)  ,
	Training        VARCHAR(1000)  ,
	Special         VARCHAR(1000)  ,
	Criteria        VARCHAR(1000)  ,
	Signatures      VARCHAR(1000)  ,
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
	ProNo        INTEGER         NOT NULL,
    WPID         VARCHAR(30)     NOT NULL,
    REEmpNo      INTEGER         NOT NULL,
	State        VARCHAR(30)     NOT NULL,
	Comment      VARCHAR(1000)   ,
	PRIMARY KEY(ProNo, WPID)
);

-- Load data into table WorkPackage





-- Create table WorkPackageForm
CREATE TABLE WorkPackageForm
(
	ProNo         INTEGER         NOT NULL,
    WPID          VARCHAR(30)     NOT NULL,
    Title         VARCHAR(200)    NOT NULL,
	Contractor    VARCHAR(200)    ,
    REEmpNo       INTEGER         ,
	IssueDate     Date            NOT NULL,
	Purpose       VARCHAR(1000)   ,
	Inputs        VARCHAR(1000)   ,
	Activates     VARCHAR(1000)   ,
	Outputs       VARCHAR(1000)   ,
	PRIMARY KEY(ProNo, WPID)
);

-- Load data into table WorkPackageForm




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
	EmpNo          INTEGER         NOT NULL,
	Year           INTEGER         NOT NULL,
    WeekNo         INTEGER         NOT NULL,
    Wage           FLOAT           NOT NULL,
    Signature      VARCHAR(100)    ,
	ApprovedEmpNo  INTEGER         ,
    State          VARCHAR(30)     NOT NULL,
	Comment        VARCHAR(1000)   ,
	PRIMARY KEY(EmpNo, Year, WeekNo)
);

-- Load data into table Timesheet





-- Create table TimesheetRow
CREATE TABLE TimesheetRow
(
	EmpNo        INTEGER         NOT NULL,
	Year         INTEGER         NOT NULL,
    WeekNo       INTEGER         NOT NULL,
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
	PRIMARY KEY(EmpNo, Year, WeekNo, ProNo, WPID)
);

-- Load data into table TimesheetRow




-- Create table Role
CREATE TABLE Role
(
	EmpNo          INTEGER         NOT NULL,
	Role           VARCHAR(100)    NOT NULL,
    State          VARCHAR(30)     NOT NULL,
    Comment        VARCHAR(1000)   ,
	PRIMARY KEY(EmpNo, Role)
);

-- Load data into table Role






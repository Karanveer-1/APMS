/*
V0.3.1
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
3. Add testing data for:
	Employee
	Timesheet
	P Level

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

-- System Admin. For all tests.
INSERT INTO APMS.Employee VALUES('11', 'SA1', 'SA1', 'sa1', 'sha1:64000:18:Bv2zMxO8gtVFJeM794JsD7rTztDh+4WB:fWaZ6oviwtTPg3yCvMVgNDlD', '1', '2', 'Active', '', NULL);
INSERT INTO APMS.Employee VALUES('12', 'SA2', 'SA2', 'sa2', 'sha1:64000:18:2xoADUeLtES05x5TlZ8pT3iBIL10pAGl:QnH4YCjiC0So7CftSUdJyBLB', '1', '2', 'Active', '', NULL);

-- For Employee testing. (Including assignment of P Level)
INSERT INTO APMS.Employee VALUES('21', 'HR1', 'HR1', 'hr1', 'sha1:64000:18:WAudHJO15MJTBP7F4gy68n0OgeIErgMV:hN3jDNmKaw9GTcCQSYSPlpWg', '21', '21', 'Active', '', NULL);
INSERT INTO APMS.Employee VALUES('22', 'HR2', 'HR2', 'hr2', 'sha1:64000:18:lWfvuc+LNXndSdw42mTZ8nAh8oSS6W05:folBNpIAZB3s5//BrF484js+', '21', '22', 'Active', '', NULL);
INSERT INTO APMS.Employee VALUES('23', 'E23', 'E23', 'e23', 'sha1:64000:18:tI+CuNB4cvuLixUTCAsEWL5VCrwOauxJ:k7kxVFbfW1soTYrClxJyuBfq', '21', '22', 'Active', '', NULL);
INSERT INTO APMS.Employee VALUES('24', 'E24', 'E24', 'e24', 'sha1:64000:18:a8Hsn6cygYXRU2BM3+j1DeS9ocUe+yVo:PXAYInCMkHueYDuxO57vrU9n', '22', '21', 'Active', '', NULL);
INSERT INTO APMS.Employee VALUES('25', 'E25', 'E25', 'e25', 'sha1:64000:18:zmqKS9royPEHkkhfaXr0U6g3Ai8k4OJM:FLN/DM+sGlt6ymh/RI0CjNti', '23', '25', 'Active', '', NULL);
INSERT INTO APMS.Employee VALUES('26', 'E26', 'E26', 'e26', 'sha1:64000:18:i3ZYGlPBypR4fgH3muQeqLkol4jPA65p:gEuzKXUOpRoqNG3I7WDervXX', '23', '24', 'Active', '', NULL);
INSERT INTO APMS.Employee VALUES('27', 'E27', 'E27', 'e27', 'sha1:64000:18:q9ceJ//IkYa0FqYbkLZ2/b3q4QHCbTEZ:M6mFKUX9m6M7JG7Zz/cK83Ce', '26', '24', 'Active', '', NULL);
INSERT INTO APMS.Employee VALUES('28', 'E28', 'E28', 'e28', 'sha1:64000:18:Ox+scAvdNXOKQwqb0niFVOYEB0YV5y9j:XnAz/5jS7zYR89rb+5cTeBZ4', '23', '27', 'Active', '', NULL);

-- For Timesheet testing.
INSERT INTO APMS.Employee VALUES('31', 'Appr1', 'Appr1', 'appr1', 'sha1:64000:18:2qoiP12Sj4PETz4TAkVKAWPgaFtI+8Wj:Fn4SKfh04/fErB+nwNjyhJ3/', '31', '31', 'Active', '', NULL);
INSERT INTO APMS.Employee VALUES('32', 'Appr2', 'Appr2', 'appr2', 'sha1:64000:18:dxIXIVfbcUVVJWH8U4IWaoAyqVbML6nQ:buO83rjexg7NfIerwcI6E2o6', '31', '31', 'Active', '', NULL);
INSERT INTO APMS.Employee VALUES('33', 'E33', 'E33', 'e33', 'sha1:64000:18:DnFCwC5kxO73LR4w6Nc/WELkuLlgbuLf:fk64jwaOBH9Hto41nsGhmEOG', '31', '31', 'Active', '', NULL);
INSERT INTO APMS.Employee VALUES('34', 'E34', 'E34', 'e34', 'sha1:64000:18:GN/pTRaXtmhyiqTkpTDj9i6jzT+4DQ5H:pX1fCgi64FFHFm9R3tagdm8d', '31', '31', 'Active', '', NULL);
INSERT INTO APMS.Employee VALUES('35', 'E35', 'E35', 'e35', 'sha1:64000:18:CXAEandChhphPrE7o15jd6uEwbCpZVaF:cQoVvPOi5tJMB3Hl2BuCzY3d', '31', '32', 'Active', '', NULL);

-- For P Level testing.
INSERT INTO APMS.Employee VALUES('41', 'HR41', 'HR41', 'hr41', 'sha1:64000:18:qCRRRHG9Rc86nRR3LzTD88pWBz8JjOI8:X17uf7/NL6zS83In3fwH2dor', '21', '21', 'Active', '', NULL);
INSERT INTO APMS.Employee VALUES('42', 'HR42', 'HR42', 'hr42', 'sha1:64000:18:AA5Cyb/H8RVwZIZDHlUZM4uf7ctRDq50:MLeE+lv/QpENjSz3Rd35b7fD', '21', '21', 'Active', '', NULL);







-- Create table EmpPLevel
CREATE TABLE EmpPLevel
(
    EmpNo       INTEGER       NOT NULL,
    StartDate   DATE          NOT NULL,
    PLevel      VARCHAR(30)   NOT NULL,
    PRIMARY KEY(EmpNo, StartDate)
);

-- Load data into table EmpPLevel
INSERT INTO APMS.EmpPLevel VALUES('23', '2018-01-01', 'P1');
INSERT INTO APMS.EmpPLevel VALUES('24', '2018-01-15', 'P1');
INSERT INTO APMS.EmpPLevel VALUES('25', '2018-03-21', 'P2');
INSERT INTO APMS.EmpPLevel VALUES('26', '2018-04-17', 'P2');
INSERT INTO APMS.EmpPLevel VALUES('27', '2018-05-21', 'P3');
INSERT INTO APMS.EmpPLevel VALUES('28', '2018-04-20', 'P3');
INSERT INTO APMS.EmpPLevel VALUES('29', '2018-07-15', 'P4');

INSERT INTO APMS.EmpPLevel VALUES('23', '2018-05-01', 'P2');
INSERT INTO APMS.EmpPLevel VALUES('24', '2019-03-22', 'P3');
INSERT INTO APMS.EmpPLevel VALUES('25', '2019-07-21', 'P3');
INSERT INTO APMS.EmpPLevel VALUES('26', '2018-12-17', 'P1');

INSERT INTO APMS.EmpPLevel VALUES('23', '2018-12-01', 'P3');
INSERT INTO APMS.EmpPLevel VALUES('24', '2019-04-22', 'P4');


-- Create table PLevel
CREATE TABLE PLevel
(
    StartDate     DATE       NOT NULL,
    PLevel        VARCHAR(30)   NOT NULL,
    Wage          FLOAT         NOT NULL,
    PRIMARY KEY(StartDate, PLevel)
);

-- Load data into table PLevel

-- For P Level testing.
INSERT INTO APMS.PLevel VALUES('2017-01-02', 'P1', '115');
INSERT INTO APMS.PLevel VALUES('2017-01-02', 'P2', '221.5');
INSERT INTO APMS.PLevel VALUES('2017-01-08', 'P3', '312');
INSERT INTO APMS.PLevel VALUES('2017-01-02', 'P4', '418.69');

INSERT INTO APMS.PLevel VALUES('2018-01-05', 'P1', '128');
INSERT INTO APMS.PLevel VALUES('2018-01-05', 'P2', '237.5');
INSERT INTO APMS.PLevel VALUES('2018-01-05', 'P3', '328');
INSERT INTO APMS.PLevel VALUES('2018-01-05', 'P4', '430');
INSERT INTO APMS.PLevel VALUES('2018-01-06', 'P5', '552');

INSERT INTO APMS.PLevel VALUES('2019-01-15', 'P1', '128');
INSERT INTO APMS.PLevel VALUES('2019-01-15', 'P2', '275');
INSERT INTO APMS.PLevel VALUES('2019-01-15', 'P3', '356.24');
INSERT INTO APMS.PLevel VALUES('2019-01-15', 'P4', '455');
INSERT INTO APMS.PLevel VALUES('2019-01-15', 'P5', '562');





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
INSERT INTO APMS.Project VALUES('100', '1', 'Macaroni', 'First Project', '123.45','OPEN', 'First Project' );
INSERT INTO APMS.Project VALUES('101', '2', 'Pizza', 'Second Project', '14.45','OPEN', 'Second Project' );

-- For timesheet testing
INSERT INTO APMS.Project VALUES('301', '31', 'APMS', 'Good project', '1000000.98','OPEN', 'Hi' );
INSERT INTO APMS.Project VALUES('302', '32', 'Voting tool', 'Nice project', '2000000.50','OPEN', 'Hi' );
INSERT INTO APMS.Project VALUES('303', '33', 'OS', 'Hard project', '3000000.12','OPEN', 'Hi' );






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
INSERT INTO APMS.ProEmp VALUES('100', '1');
INSERT INTO APMS.ProEmp VALUES('101', '1');


-- For timesheet testing
INSERT INTO APMS.ProEmp VALUES('301', '31');
INSERT INTO APMS.ProEmp VALUES('302', '31');

INSERT INTO APMS.ProEmp VALUES('301', '32');

INSERT INTO APMS.ProEmp VALUES('301', '33');
INSERT INTO APMS.ProEmp VALUES('302', '33');

INSERT INTO APMS.ProEmp VALUES('301', '34');
INSERT INTO APMS.ProEmp VALUES('302', '34');

INSERT INTO APMS.ProEmp VALUES('301', '35');
INSERT INTO APMS.ProEmp VALUES('302', '35');



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
-- Load data into table WorkPackage
INSERT INTO APMS.WorkPackage VALUES('100', 'COMP101', '1','COMP101','COMP101',NULL,'10','OPEN',NULL);

INSERT INTO APMS.WorkPackage VALUES('100', 'COMP201', '1','COMP201','COMP201','COMP101','10','OPEN',NULL);

INSERT INTO APMS.WorkPackage VALUES('100', 'COMP303', '1','COMP303','COMP303','COMP201','10','OPEN',NULL);

INSERT INTO APMS.WorkPackage VALUES('100', 'COMP305', '1','COMP305','COMP305','COMP201','10','OPEN',NULL);

INSERT INTO APMS.WorkPackage VALUES('100', 'COMP204', '1','COMP204','COMP204','COMP101','10','OPEN',NULL);

INSERT INTO APMS.WorkPackage VALUES('100', 'BUSI101', '1','BUSI101','BUSI101',NULL,'10','OPEN',NULL);

INSERT INTO APMS.WorkPackage VALUES('100', 'COMM101', '1','COMM101','COMM101',NULL,'10','OPEN',NULL);


-- For timesheet testing
INSERT INTO APMS.WorkPackage VALUES('301', 'Employee', '31', 'Employee', 'Employee', NULL, '1000', 'OPEN', 'Hi');
INSERT INTO APMS.WorkPackage VALUES('301', 'Project', '31', 'Project', 'Project', NULL, '2000', 'OPEN', 'Hi');
INSERT INTO APMS.WorkPackage VALUES('301', 'Timesheet', '31', 'Timesheet', 'Timesheet', NULL, '3000', 'OPEN', 'Hi');
INSERT INTO APMS.WorkPackage VALUES('301', 'PLevel', '31', 'PLevel', 'PLevel', NULL, '4000', 'OPEN', 'Hi');
INSERT INTO APMS.WorkPackage VALUES('302', 'Frontend', '31', 'Frontend', 'Frontend', NULL, '1000', 'OPEN', 'Hi');
INSERT INTO APMS.WorkPackage VALUES('302', 'Backend', '31', 'Backend', 'Backend', NULL, '2000', 'OPEN', 'Hi');

INSERT INTO APMS.WorkPackage VALUES('301', 'E101', '31', 'EmployeeCreate', 'EmployeeCreate', 'Employee', '100', 'OPEN', 'Hi');
INSERT INTO APMS.WorkPackage VALUES('301', 'E201', '31', 'EmployeeRead', 'EmployeeRead', 'Employee', '200', 'OPEN', 'Hi');
INSERT INTO APMS.WorkPackage VALUES('301', 'E301', '31', 'EmployeeEdit', 'EmployeeEdit', 'Employee', '300', 'OPEN', 'Hi');
INSERT INTO APMS.WorkPackage VALUES('301', 'P201', '31', 'ProjectCreate', 'ProjectCreate', 'Project', '200', 'OPEN', 'Hi');
INSERT INTO APMS.WorkPackage VALUES('301', 'P301', '31', 'ProjectRead', 'ProjectRead', 'Project', '300', 'OPEN', 'Hi');
INSERT INTO APMS.WorkPackage VALUES('302', 'F101', '31', 'MainPage', 'MainPage', 'Frontend', '100', 'OPEN', 'Hi');

INSERT INTO APMS.WorkPackage VALUES('301', 'E101_1', '31', 'E101_1', 'E101_1', 'E101', '10', 'OPEN', 'Hi');
INSERT INTO APMS.WorkPackage VALUES('301', 'E101_2', '31', 'E101_2', 'E101_2', 'E101', '20', 'OPEN', 'Hi');







-- Create table WPEmp
CREATE TABLE WPEmp
(
    ProNo        INTEGER       NOT NULL,
    WPID         VARCHAR(30)   NOT NULL,
    EmpNo        INTEGER       NOT NULL,
    PRIMARY KEY(ProNo, WPID, EmpNo)
);

-- Load data into table WPEmp

-- For timesheet testing
INSERT INTO APMS.WPEmp VALUES('301', 'Employee', '31');
INSERT INTO APMS.WPEmp VALUES('301', 'Project', '31');
INSERT INTO APMS.WPEmp VALUES('301', 'E101', '31');
INSERT INTO APMS.WPEmp VALUES('301', 'P201', '31');
INSERT INTO APMS.WPEmp VALUES('301', 'P301', '31');
INSERT INTO APMS.WPEmp VALUES('302', 'Frontend', '31');
INSERT INTO APMS.WPEmp VALUES('302', 'Backend', '31');

INSERT INTO APMS.WPEmp VALUES('301', 'Employee', '32');
INSERT INTO APMS.WPEmp VALUES('301', 'E301', '32');

INSERT INTO APMS.WPEmp VALUES('301', 'Employee', '33');
INSERT INTO APMS.WPEmp VALUES('301', 'E301', '33');
INSERT INTO APMS.WPEmp VALUES('301', 'E101_1', '33');
INSERT INTO APMS.WPEmp VALUES('301', 'P301', '33');
INSERT INTO APMS.WPEmp VALUES('302', 'Frontend', '33');
INSERT INTO APMS.WPEmp VALUES('302', 'Backend', '33');

INSERT INTO APMS.WPEmp VALUES('301', 'Employee', '34');
INSERT INTO APMS.WPEmp VALUES('301', 'Project', '34');
INSERT INTO APMS.WPEmp VALUES('301', 'Timesheet', '34');
INSERT INTO APMS.WPEmp VALUES('301', 'PLevel', '34');
INSERT INTO APMS.WPEmp VALUES('301', 'E101', '34');
INSERT INTO APMS.WPEmp VALUES('301', 'P201', '34');
INSERT INTO APMS.WPEmp VALUES('302', 'Backend', '34');

INSERT INTO APMS.WPEmp VALUES('301', 'Employee', '35');
INSERT INTO APMS.WPEmp VALUES('301', 'E101', '35');
INSERT INTO APMS.WPEmp VALUES('301', 'E201', '35');
INSERT INTO APMS.WPEmp VALUES('302', 'Frontend', '35');
INSERT INTO APMS.WPEmp VALUES('302', 'Backend', '35');
INSERT INTO APMS.WPEmp VALUES('302', 'F101', '35');



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

-- For timesheet testing
INSERT INTO APMS.Timesheet VALUES('31', '2019-03-16', NULL, NULL, 'Draft', 'HIHI');
INSERT INTO APMS.Timesheet VALUES('31', '2019-03-23', NULL, NULL, 'Draft', 'HIHI');

INSERT INTO APMS.Timesheet VALUES('33', '2019-03-16', NULL, NULL, 'Draft', 'HIHI');
INSERT INTO APMS.Timesheet VALUES('33', '2019-03-23', NULL, NULL, 'Draft', 'HIHI');
INSERT INTO APMS.Timesheet VALUES('33', '2019-03-30', NULL, NULL, 'Draft', 'HIHI');
INSERT INTO APMS.Timesheet VALUES('33', '2019-04-06', NULL, NULL, 'Draft', 'HIHI');

INSERT INTO APMS.Timesheet VALUES('34', '2019-03-16', NULL, NULL, 'Draft', 'HIHI');
INSERT INTO APMS.Timesheet VALUES('34', '2019-03-23', NULL, NULL, 'Draft', 'HIHI');

INSERT INTO APMS.Timesheet VALUES('35', '2019-03-16', NULL, NULL, 'Draft', 'HIHI');
INSERT INTO APMS.Timesheet VALUES('35', '2019-03-23', NULL, NULL, 'Draft', 'HIHI');




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

-- For timesheet testing
INSERT INTO APMS.TimesheetRow VALUES('31', '2019-03-16', '301', 'Employee', NULL, NULL, '2', NULL, '3', NULL, NULL, 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('31', '2019-03-16', '301', 'Project', NULL, NULL, NULL, NULL, '2', NULL, NULL, 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('31', '2019-03-16', '301', 'E101', NULL, NULL, NULL, NULL, NULL, '2', NULL, 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('31', '2019-03-16', '302', 'Frontend', '1.5', NULL, '1', NULL, NULL, '1.5', NULL, 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('31', '2019-03-16', '302', 'Backend', NULL, NULL, '1.25', NULL, NULL, '2', NULL, 'note', 'Draft', 'HIHI');

INSERT INTO APMS.TimesheetRow VALUES('31', '2019-03-23', '301', 'E101', NULL, NULL, NULL, '1.75', '2', NULL, NULL, 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('31', '2019-03-23', '301', 'P201', NULL, NULL, NULL, NULL, NULL, '3', NULL, 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('31', '2019-03-23', '301', 'P301', NULL, NULL, NULL, '10', NULL, NULL, NULL, 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('31', '2019-03-23', '302', 'Frontend', '1.5', NULL, NULL, NULL, '5', NULL, NULL, 'note', 'Draft', 'HIHI');

INSERT INTO APMS.TimesheetRow VALUES('33', '2019-03-16', '302', 'Frontend', NULL, NULL, NULL, NULL, '2.25', NULL, NULL, 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('33', '2019-03-16', '302', 'Backend', '1.5', NULL, NULL, '1.5', NULL, NULL, NULL, 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('33', '2019-03-16', '301', 'Employee', NULL, NULL, '6', NULL, NULL, NULL, NULL, 'note', 'Draft', 'HIHI');

INSERT INTO APMS.TimesheetRow VALUES('33', '2019-03-30', '301', 'E301', NULL, NULL, NULL, '2', NULL, NULL, '10', 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('33', '2019-03-30', '301', 'E101_1', NULL, NULL, NULL, NULL, '4', NULL, NULL, 'note', 'Draft', 'HIHI');

INSERT INTO APMS.TimesheetRow VALUES('33', '2019-04-06', '301', 'P301', NULL, NULL, '1', '2', '10', '2', NULL, 'note', 'Draft', 'HIHI');

INSERT INTO APMS.TimesheetRow VALUES('34', '2019-03-16', '301', 'Employee', NULL, NULL, NULL, NULL, '1', NULL, '10', 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('34', '2019-03-16', '301', 'Project', '2.5', NULL, NULL, NULL, '1.5', '4.5', NULL, 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('34', '2019-03-16', '301', 'Timesheet', NULL, NULL, NULL, NULL, NULL, '2', NULL, 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('34', '2019-03-16', '301', 'PLevel', NULL, NULL, '1', NULL, NULL, '1', NULL, 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('34', '2019-03-16', '301', 'E101', '1.5', NULL, NULL, NULL, '2', '2', NULL, 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('34', '2019-03-16', '301', 'P201', NULL, '4', NULL, NULL, NULL, NULL, NULL, 'note', 'Draft', 'HIHI');

INSERT INTO APMS.TimesheetRow VALUES('34', '2019-03-23', '301', 'Employee', '1.5', NULL, 3.5, NULL, NULL, NULL, NULL, 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('34', '2019-03-23', '302', 'Backend', NULL, '2', NULL, NULL, NULL, '2', NULL, 'note', 'Draft', 'HIHI');

INSERT INTO APMS.TimesheetRow VALUES('35', '2019-03-16', '301', 'Employee', NULL, NULL, '3', NULL, '5', '4', NULL, 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('35', '2019-03-16', '302', 'Frontend', NULL, NULL, '7', NULL, '8', NULL, NULL, 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('35', '2019-03-16', '302', 'Backend', '1.5', NULL, '6', NULL, NULL, NULL, NULL, 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('35', '2019-03-16', '302', 'F101', NULL, NULL, NULL, '9', NULL, '1', NULL, 'note', 'Draft', 'HIHI');

INSERT INTO APMS.TimesheetRow VALUES('35', '2019-03-23', '301', 'Employee', NULL, '10', '10', NULL, '10', NULL, NULL, 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('35', '2019-03-23', '301', 'E101', NULL, NULL, NULL, '10', NULL, '10', NULL, 'note', 'Draft', 'HIHI');
INSERT INTO APMS.TimesheetRow VALUES('35', '2019-03-23', '301', 'E201', NULL, NULL, '10', NULL, NULL, '10', NULL, 'note', 'Draft', 'HIHI');







-- Create table Role
CREATE TABLE Role
(
    EmpNo          INTEGER         NOT NULL,
    Role           VARCHAR(100)    NOT NULL,
    PRIMARY KEY(EmpNo, Role)
);

-- Load data into table Role
INSERT INTO APMS.Role VALUES('11', 'System Admin');
INSERT INTO APMS.Role VALUES('12', 'System Admin');
INSERT INTO APMS.Role VALUES('13', 'Human Resource');
INSERT INTO APMS.Role VALUES('14', 'Human Resource');


CREATE TABLE Signature(
	EmpNo          INTEGER         NOT NULL,
    StartDate      DATE            NOT NULL,
	Signature 	   TINYBLOB 	   NOT NULL,
	PublicKey 	   BLOB 		   NOT NULL,
	PRIMARY KEY(EmpNo, StartDate)
);

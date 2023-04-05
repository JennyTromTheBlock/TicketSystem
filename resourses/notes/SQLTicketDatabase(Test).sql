USE [master]
GO


IF DB_ID('EventSystemTestDB') IS NOT NULL
BEGIN
	DROP DATABASE EventSystemTestDB
END

CREATE DATABASE EventSystemTestDB
GO

USE EventSystemTestDB
GO


CREATE TABLE Event(
    ID                  int             PRIMARY KEY IDENTITY(1, 1),
	EventName			nvarchar(100)	NOT NULL,
	EventDescription	nvarchar(MAX)	NULL,
	EventLocation 		nvarchar(200)   NULL,
	EventDate			datetime		NOT NULL,
	MaxParticipant		int				NULL,
	Price 				int				NULL
)
GO

CREATE TABLE Roles(
    RoleName            nvarchar(100)   PRIMARY KEY
)
GO

INSERT INTO Roles (RoleName) VALUES ('Administrator')
GO
INSERT INTO Roles (RoleName) VALUES ('Event Coordinator')
GO

CREATE TABLE SystemUsers(
    Email               nvarchar(100)   PRIMARY KEY,
    FirstName           nvarchar(100)   NOT NULL,
    LastName            nvarchar(100)   NOT NULL,
    Password            nvarchar(100)   NOT NULL
)
GO

INSERT INTO SystemUsers (Email, FirstName, LastName, Password) VALUES (
    'patand01@easv365.dk',
    'Patrick Darling',
    'Andersen',
    '1234'

)
GO

CREATE TABLE SystemUserRoles(
    SystemUserEmail     nvarchar(100) FOREIGN KEY REFERENCES SystemUsers(Email),
    RoleName            nvarchar(100) FOREIGN KEY REFERENCES Roles(RoleName),
    PRIMARY KEY (SystemUserEmail, RoleName)
)
GO

INSERT INTO SystemUserRoles (SystemUserEmail, RoleName) VALUES ('patand01@easv365.dk', 'Administrator')
GO

CREATE TABLE SpecialTicketTypes (
    Type                nvarchar(100)   PRIMARY KEY,
    Price               int             NOT NULL
)
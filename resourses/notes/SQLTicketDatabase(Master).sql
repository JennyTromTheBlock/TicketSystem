USE [master]
GO


IF DB_ID('EventSystemDB') IS NOT NULL  
BEGIN
	DROP DATABASE EventSystemDB	
END

CREATE DATABASE EventSystemDB
GO

USE EventSystemDB
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
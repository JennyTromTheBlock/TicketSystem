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
	EventName			nvarchar(100)	NOT NULL,
	EventDescription	nvarchar(MAX)	NULL,
	EventLocation 		nvarchar(200)   NULL,
	EventDate			datetime		NOT NULL,
	MaxParticipant		int				NULL,
	Price 				int				NULL,
	CONSTRAINT PK_Event PRIMARY KEY (EventName)
)
GO
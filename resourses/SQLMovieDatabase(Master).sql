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
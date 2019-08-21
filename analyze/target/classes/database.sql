create database songci default character set utf8 collate utf8_general_ci;
use songci;

create table if not exists poetry_info(

 title varchar (64)NOT NULL ,
 dynasty varchar (32)NOT NULL ,
 author varchar (12)NOT NULL ,
 content varchar (1024)NOT NULL
);
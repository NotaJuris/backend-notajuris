CREATE TABLE IF NOT EXISTS endereco (
endereco_id int primary key auto_increment,
rua varchar(255) not null,
numero varchar(255) not null,
bairro varchar(255) not null, 
cidade varchar(255) not null,
estado varchar(255) not null,
complemento varchar(255),
ativo int not null default 1
);
CREATE TABLE IF NOT EXISTS usuario(
usuario_id int primary key auto_increment,
nome varchar(255) not null, 
periodo int, 
matricula varchar(255) not null unique, 
senha varchar(255) not null,
ativo tinyint not null default 1,
fk_cargo_id int,
FOREIGN KEY (fk_cargo_id) references cargo(cargo_id) on delete cascade on update cascade
);
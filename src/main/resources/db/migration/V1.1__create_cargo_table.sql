CREATE TABLE IF NOT EXISTS cargo (
cargo_id int primary key auto_increment,
nome_cargo varchar(255) not null unique,
ativo tinyint not null default(1)
);
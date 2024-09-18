CREATE TABLE IF NOT EXISTS anexos (
anexo_id int primary key auto_increment,
nome varchar(255) not null,
extensao varchar(255) not null,
`url` varchar(255) not null,
ativo tinyint not null default 1,
fk_atividade_id int,
FOREIGN KEY (fk_atividade_id) references atividade(atividade_id) on delete cascade on update cascade
);
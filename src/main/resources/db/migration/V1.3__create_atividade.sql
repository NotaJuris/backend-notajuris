CREATE TABLE IF NOT EXISTS atividade(
atividade_id int primary key auto_increment,
tipo varchar(255) not null,
descricao varchar(255),
`status` int not null default 0,
`data-hora` datetime not null,
carga_horaria int,
ativo tinyint not null default 1,
fk_usuario_id int,
FOREIGN KEY (fk_usuario_id) references usuario(usuario_id) on delete cascade on update cascade
);
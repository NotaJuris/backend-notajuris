CREATE TABLE IF NOT EXISTS notificacao(
    notificacao_id int primary key auto_increment,
    titulo varchar(255),
    mensagem varchar(255),
    visto tinyint not null default 0,
    fk_usuario_id int,
    FOREIGN KEY (fk_usuario_id) references usuario(usuario_id) on delete cascade on update cascade
);
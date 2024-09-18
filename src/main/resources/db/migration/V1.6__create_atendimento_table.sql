CREATE TABLE IF NOT EXISTS atendimento (
atendimento_id int primary key auto_increment,
nome_atendido varchar(255) not null,
estadocivil_atendido varchar(255) not null,
profissao_atendido varchar(255) not null,
data_nasc_atendido date not null,
naturalidade_atendido varchar(255) not null,
cpf_atendido varchar(255) not null,
rg_atendido varchar(255),
celular_atendido varchar(255) not null,
filiacao_atendido varchar(255),
observacoes varchar(255),
ativo tinyint not null default 1,
fk_atividade_id int,
fk_endereco_id int,
FOREIGN KEY (fk_atividade_id) references atividade(atividade_id) on delete cascade on update cascade,
FOREIGN KEY (fk_endereco_id) references endereco(endereco_id) on delete cascade on update cascade
);
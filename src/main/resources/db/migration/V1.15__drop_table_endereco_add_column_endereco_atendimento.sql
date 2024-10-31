ALTER TABLE atendimento
DROP COLUMN fk_endereco_id;

ALTER TABLE atendimento
ADD COLUMN endereco VARCHAR(255);

DROP TABLE endereco IF EXISTS;
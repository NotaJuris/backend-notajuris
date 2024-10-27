ALTER TABLE atividade
DROP COLUMN `data-hora`;

ALTER TABLE atividade
ADD COLUMN `data_atividade` DATE NOT NULL;

ALTER TABLE atividade
ADD COLUMN `hora_atividade` TIME NOT NULL;
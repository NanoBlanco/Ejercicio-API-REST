ALTER TABLE medicos ADD estado BOOLEAN NOT NULL DEFAULT 1;
update medicos set estado = 1;

CREATE  DATABASE excel;
USE EXCEL;
CREATE TABLE tblcontenido(
fila  INT(3) NOT NULL,
colum 	INT(3) NOT NULL,
codarch INT(3) NOT NULL,
contenido VARCHAR(50),
tamano INT,
tipoletra VARCHAR(15),
colorletra VARCHAR(15),
colorcelda VARCHAR(15),
negrita INT,
cursiva INT,
subr INT,
codtipod INT(2),
codalinea INT(2),
primary key(fila, colum, codarch),
CONSTRAINT fk1 foreign key (codarch) REFERENCES tblarchivo(codarch),
CONSTRAINT fk2 foreign key (codtipod) REFERENCES tbltipodato(codtipod),
CONSTRAINT fk3 foreign key (codalinea) REFERENCES tblalineacion(codalinea)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE tblalineacion(
codalinea INT(2) NOT NULL,
nombre VARCHAR(15),
primary key(codalinea)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE tbltipodato(
codtipod INT(2) NOT NULL,
nombre VARCHAR(15),
primary key(codtipod)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE tblarchivo(
codarch INT(3) NOT NULL,
nombre VARCHAR(15),
fecha VARCHAR(10),
primary key(codarch)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;
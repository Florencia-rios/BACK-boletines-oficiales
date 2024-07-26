-- Inserts para la tabla cargos
INSERT INTO cargos ("CODIGO", "NOMBRE") VALUES
('AB', 'ABSORBIDA'),
('GT', 'GERENTE'),
('DT', 'Director Titular'),
('PR', 'Presidente'),
('LR', 'Representante Legal'),
('SA', 'Socio Solidario'),
('SB', 'Socio Comanditado'),
('SC', 'Socio Comanditario'),
('SG', 'Socio Gerente'),
('SO', 'UNICAMENTE PARA SOCIEDADES DE HECHO Y COLECTIVA'),
('DA', 'DENOMINACION ANTERIOR'),
('ES', 'ESCINDIDA'),
('V0', 'Vicepresidente'),
('V1', 'Vicepresidente Primero'),
('V2', 'Vicepresidente Segundo'),
('V3', 'Vicepresidente Tercero'),
('V4', 'Vicepresidente Cuarto'),
('FU', 'FUSION'),
('UT', 'UTE'),
('DO', 'Directivo');

-- Inserts para la tabla estado_civil
INSERT INTO estado_civil ("CODIGO", "NOMBRE") VALUES
('S', 'Soltero'),
('C', 'Casado'),
('D', 'Divorciado');

-- Inserts para la tabla provincias
INSERT INTO provincias ("CODIGO", "NOMBRE") VALUES
('A', 'SALTA'),
('B', 'BUENOS AIRES'),
('C', 'CAPITAL FEDERAL'),
('D', 'SAN LUIS'),
('E', 'ENTRE RIOS'),
('F', 'LA RIOJA'),
('G', 'SANTIAGO DEL ESTERO'),
('H', 'CHACO'),
('J', 'SAN JUAN'),
('K', 'CATAMARCA'),
('L', 'LA PAMPA'),
('M', 'MENDOZA'),
('N', 'MISIONES'),
('P', 'FORMOSA'),
('Q', 'NEUQUEN'),
('R', 'RIO NEGRO'),
('S', 'SANTA FE'),
('T', 'TUCUMAN'),
('U', 'CHUBUT'),
('V', 'TIERRA DEL FUEGO'),
('W', 'CORRIENTES'),
('X', 'CORDOBA'),
('Y', 'JUJUY'),
('Z', 'SANTA CRUZ');

-- Inserts para la tabla sexo
INSERT INTO sexo ("NOMBRE", "CODIGO") VALUES
('MASCULINO', 'M'),
('FEMENINO', 'F'),
('NO APORTADO', 'I'),
('SOCIEDAD', 'S');

-- Inserts para la tabla nacionalidades
INSERT INTO nacionalidades ("CODIGO", "NOMBRE") VALUES
('A', 'ARGENTINA'),
('AL', 'ALEMANIA'),
('AU', 'AUSTRALIA'),
('BO', 'BOLIVIA'),
('BR', 'BRASIL'),
('C', 'COLOMBIA'),
('CB', 'CUBA'),
('CD', 'CANADA'),
('CE', 'CHECOSLOVAQUIA'),
('CH', 'CHILE'),
('CN', 'CHINA'),
('E', 'ESPAÑA'),
('EC', 'ECUADOR'),
('F', 'FRANCIA'),
('GB', 'GRAN BRETAÑA'),
('H', 'HOLANDA'),
('I', 'ITALIA'),
('IR', 'IRLANDA'),
('J', 'JAPON'),
('K', 'KOREA'),
('M', 'MEXICO'),
('O', 'EXTRANJERO'),
('P', 'PERU'),
('PG', 'PORTUGAL'),
('PR', 'PARAGUAY'),
('S', 'SUECIA'),
('SZ', 'SUIZA'),
('TW', 'TAIWAN'),
('U', 'URUGUAY'),
('US', 'ESTADOS UNIDOS'),
('XX', 'EXTRANJERO'),
('YU', 'YUGOSLAVIA');

COMMIT;
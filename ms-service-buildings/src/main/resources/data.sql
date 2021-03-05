INSERT INTO materiales (id, nombre, descripcion, cantidad, estado) VALUES (1, 'Cemento', 'Cemento', 1000,'CREATE');
INSERT INTO materiales (id, nombre, descripcion, cantidad, estado) VALUES (2, 'Grava', 'Grava', 1000,'CREATE');
INSERT INTO materiales (id, nombre, descripcion, cantidad, estado ) VALUES (3, 'Arena', 'Arena', 1000,'CREATE');
INSERT INTO materiales (id, nombre, descripcion, cantidad, estado) VALUES (4, 'Madera', 'Madera', 1000,'CREATE');
INSERT INTO materiales (id, nombre, descripcion, cantidad, estado ) VALUES (5, 'Adobe', 'Adobe', 1000,'CREATE');

INSERT INTO construccion (id, nombre, descripcion, cantidad_dias, estado) VALUES (1, 'Casa', 'Casa', 3,'CREATE');
INSERT INTO construccion (id, nombre, descripcion, cantidad_dias, estado) VALUES (2, 'Lago', 'Lago', 2,'CREATE');
INSERT INTO construccion (id, nombre, descripcion, cantidad_dias, estado ) VALUES (3, 'Cancha de fútbol', 'Cancha de fútbol', 1,'CREATE');
INSERT INTO construccion (id, nombre, descripcion, cantidad_dias, estado) VALUES (4, 'Edificio', 'Edificio', 6,'CREATE');
INSERT INTO construccion (id, nombre, descripcion, cantidad_dias, estado ) VALUES (5, 'Gimnasio', 'Gimnasio', 2,'CREATE');

INSERT INTO construccion_X_material (id, id_Construccion, id_Material, cantidad_Material_Requerido, estado) VALUES (1, 1, 1, 100,'CREATE');
INSERT INTO construccion_X_material (id, id_Construccion, id_Material, cantidad_Material_Requerido, estado) VALUES (2, 1, 2, 50,'CREATE');
INSERT INTO construccion_X_material (id, id_Construccion, id_Material, cantidad_Material_Requerido, estado) VALUES (3, 1, 3, 90,'CREATE');
INSERT INTO construccion_X_material (id, id_Construccion, id_Material, cantidad_Material_Requerido, estado) VALUES (4, 1, 4, 20,'CREATE');
INSERT INTO construccion_X_material (id, id_Construccion, id_Material, cantidad_Material_Requerido, estado) VALUES (5, 1, 5, 100,'CREATE');

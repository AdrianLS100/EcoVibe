-- ROLES
INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_PERSONAL');
INSERT INTO roles (name) VALUES ('ROLE_FAMILIAR');
INSERT INTO roles (name) VALUES ('ROLE_INSTITUCION');

-- USERS (agrega email, edad, distrito, emailverificado)
INSERT INTO users(username, password, email, edad, distrito, emailverificado) VALUES ('user1',     '$2a$12$Pi5U/8Y0kYX43k45MSzP7Od2heK7Ps6V3z/m2m8MKCv2sGq1/Z5NW', 'user1@ecovibe.com',     22, 'San Miguel', false);
INSERT INTO users(username, password, email, edad, distrito, emailverificado) VALUES ('admin',     '$2a$12$Pi5U/8Y0kYX43k45MSzP7Od2heK7Ps6V3z/m2m8MKCv2sGq1/Z5NW', 'admin@ecovibe.com',     30, 'Miraflores', false);
INSERT INTO users(username, password, email, edad, distrito, emailverificado) VALUES ('personal1', '$2a$12$Pi5U/8Y0kYX43k45MSzP7Od2heK7Ps6V3z/m2m8MKCv2sGq1/Z5NW', 'personal1@ecovibe.com', 25, 'Pueblo Libre', false);
INSERT INTO users(username, password, email, edad, distrito, emailverificado) VALUES ('familiar1', '$2a$12$Pi5U/8Y0kYX43k45MSzP7Od2heK7Ps6V3z/m2m8MKCv2sGq1/Z5NW', 'fam1@ecovibe.com',      28, 'Surco', false);
INSERT INTO users(username, password, email, edad, distrito, emailverificado) VALUES ('mama', '$2a$12$Pi5U/8Y0kYX43k45MSzP7Od2heK7Ps6V3z/m2m8MKCv2sGq1/Z5NW', 'mama@ecovibe.com',      28, 'Surco', true);
INSERT INTO users(username, password, email, edad, distrito, emailverificado) VALUES ('papa', '$2a$12$Pi5U/8Y0kYX43k45MSzP7Od2heK7Ps6V3z/m2m8MKCv2sGq1/Z5NW', 'papa@ecovibe.com',      28, 'Surco', true);
INSERT INTO users(username, password, email, edad, distrito, emailverificado) VALUES ('hermana', '$2a$12$Pi5U/8Y0kYX43k45MSzP7Od2heK7Ps6V3z/m2m8MKCv2sGq1/Z5NW', 'hermana@ecovibe.com',      28, 'Surco', true);

-- USER_ROLES
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 3);
INSERT INTO user_roles (user_id, role_id) VALUES (4, 4);
INSERT INTO user_roles (user_id, role_id) VALUES (5,1);
INSERT INTO user_roles (user_id, role_id) VALUES (6,1);
INSERT INTO user_roles (user_id, role_id) VALUES (7,1);

CREATE UNIQUE INDEX IF NOT EXISTS ux_factores ON factores_emision(categoria, subcategoria, unidad_base);

INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_por_unidad, fuente, vigente) VALUES ('transporte','auto','km',0.20,'demo',true) ON CONFLICT (categoria, subcategoria, unidad_base) DO NOTHING;
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_por_unidad, fuente, vigente) VALUES ('transporte','bus','km',0.08,'demo',true) ON CONFLICT (categoria, subcategoria, unidad_base) DO NOTHING;
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_por_unidad, fuente, vigente) VALUES ('transporte','tren','km',0.05,'demo',true) ON CONFLICT (categoria, subcategoria, unidad_base) DO NOTHING;
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_por_unidad, fuente, vigente) VALUES ('transporte','metropolitano','km',0.06,'demo',true) ON CONFLICT (categoria, subcategoria, unidad_base) DO NOTHING;

INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_por_unidad, fuente, vigente) VALUES ('energia','electricidad','kWh',0.45,'demo',true) ON CONFLICT (categoria, subcategoria, unidad_base) DO NOTHING;
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_por_unidad, fuente, vigente) VALUES ('energia','glp','kg',2.90,'demo',true) ON CONFLICT (categoria, subcategoria, unidad_base) DO NOTHING;
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_por_unidad, fuente, vigente) VALUES ('energia','gasNatural','m3',1.90,'demo',true) ON CONFLICT (categoria, subcategoria, unidad_base) DO NOTHING;

INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_por_unidad, fuente, vigente) VALUES ('alimentacion','omnivora','dia',0.80,'demo',true) ON CONFLICT (categoria, subcategoria, unidad_base) DO NOTHING;
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_por_unidad, fuente, vigente) VALUES ('alimentacion','vegetariana','dia',0.50,'demo',true) ON CONFLICT (categoria, subcategoria, unidad_base) DO NOTHING;
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_por_unidad, fuente, vigente) VALUES ('alimentacion','vegana','dia',0.40,'demo',true) ON CONFLICT (categoria, subcategoria, unidad_base) DO NOTHING;

INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_por_unidad, fuente, vigente) VALUES ('residuos','general','kg',0.02,'demo',true) ON CONFLICT (categoria, subcategoria, unidad_base) DO NOTHING;
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_por_unidad, fuente, vigente) VALUES ('residuos','bolsa5L','kg',0.02,'demo',true) ON CONFLICT (categoria, subcategoria, unidad_base) DO NOTHING;
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_por_unidad, fuente, vigente) VALUES ('residuos','bolsa10L','kg',0.02,'demo',true) ON CONFLICT (categoria, subcategoria, unidad_base) DO NOTHING;
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_por_unidad, fuente, vigente) VALUES ('residuos','bolsa20L','kg',0.02,'demo',true) ON CONFLICT (categoria, subcategoria, unidad_base) DO NOTHING;

INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_por_unidad, fuente, vigente) VALUES ('alimentacion','carne','porcion',5.00,'demo',true) ON CONFLICT (categoria, subcategoria, unidad_base) DO NOTHING;
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_por_unidad, fuente, vigente) VALUES ('alimentacion','carne','porción',5.00,'demo',true) ON CONFLICT (categoria, subcategoria, unidad_base) DO NOTHING;
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_por_unidad, fuente, vigente) VALUES ('alimentacion','carne','porciones',5.00,'demo',true) ON CONFLICT (categoria, subcategoria, unidad_base) DO NOTHING;
INSERT INTO factores_emision (categoria, subcategoria, unidad_base, factor_kgco2e_por_unidad, fuente, vigente) VALUES ('transporte','van','km',0.12,'demo',true) ON CONFLICT (categoria, subcategoria, unidad_base) DO NOTHING;

-- =========================================================
-- DEMO: Usuarios extra + actividades para comparativas
-- =========================================================

-- 1) Usuarios (misma clave que usas en tus ejemplos)
--    Password (BCrypt) corresponde a "72739044" como en tus inserts
INSERT INTO users (username, password, email, edad, distrito, emailverificado) VALUES ('amigo1', '$2a$12$Pi5U/8Y0kYX43k45MSzP7Od2heK7Ps6V3z/m2m8MKCv2sGq1/Z5NW', 'amigo1@ecovibe.com', 26, 'San Miguel', true);
INSERT INTO users (username, password, email, edad, distrito, emailverificado) VALUES ('amigo2', '$2a$12$Pi5U/8Y0kYX43k45MSzP7Od2heK7Ps6V3z/m2m8MKCv2sGq1/Z5NW', 'amigo2@ecovibe.com', 31, 'Miraflores', true);
INSERT INTO users (username, password, email, edad, distrito, emailverificado) VALUES ('amigo3', '$2a$12$Pi5U/8Y0kYX43k45MSzP7Od2heK7Ps6V3z/m2m8MKCv2sGq1/Z5NW', 'amigo3@ecovibe.com', 22, 'Pueblo Libre', true);
INSERT INTO users (username, password, email, edad, distrito, emailverificado) VALUES ('amigo4', '$2a$12$Pi5U/8Y0kYX43k45MSzP7Od2heK7Ps6V3z/m2m8MKCv2sGq1/Z5NW', 'amigo4@ecovibe.com', 29, 'Surco', true);


INSERT INTO user_roles (user_id, role_id) VALUES (8, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (9, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (10, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (11, 1);

-- 3) Actividades del día (2025-10-05) para cada amigo
--    Usamos IDs explícitos para no depender de secuencias
INSERT INTO actividades_diarias (actividad_id, usuario_id, fecha, descripcion, familia_id, miembro_id) VALUES(1, 5, '2025-10-05', 'Rutina diaria amigo1', NULL, NULL);
INSERT INTO actividades_diarias (actividad_id, usuario_id, fecha, descripcion, familia_id, miembro_id) VALUES(2, 6, '2025-10-05', 'Rutina diaria amigo2', NULL, NULL);
INSERT INTO actividades_diarias (actividad_id, usuario_id, fecha, descripcion, familia_id, miembro_id) VALUES (3, 7, '2025-10-05', 'Rutina diaria amigo3', NULL, NULL);
INSERT INTO actividades_diarias (actividad_id, usuario_id, fecha, descripcion, familia_id, miembro_id) VALUES(4, 8, '2025-10-05', 'Rutina diaria amigo4', NULL, NULL);
INSERT INTO transporte (transporte_id, actividad_id, medio, distancia_km, frecuencia, creado_en, miembro_id) VALUES (1, 1, 'bus',18, 'diaria', now(), NULL);
INSERT INTO transporte (transporte_id, actividad_id, medio, distancia_km, frecuencia, creado_en, miembro_id) VALUES (2, 1, 'tren', 6, 'diaria', now(), NULL);
INSERT INTO transporte (transporte_id, actividad_id, medio, distancia_km, frecuencia, creado_en, miembro_id) VALUES (3, 1, 'auto', 2, 'diaria', now(), NULL);
INSERT INTO transporte (transporte_id, actividad_id, medio, distancia_km, frecuencia, creado_en, miembro_id) VALUES (4, 2, 'auto', 22, 'diaria', now(), NULL);
INSERT INTO transporte (transporte_id, actividad_id, medio, distancia_km, frecuencia, creado_en, miembro_id) VALUES (5, 2, 'bus', 4, 'diaria', now(), NULL);
INSERT INTO transporte (transporte_id, actividad_id, medio, distancia_km, frecuencia, creado_en, miembro_id) VALUES(6, 3, 'metropolitano',14, 'diaria', now(), NULL);
INSERT INTO transporte (transporte_id, actividad_id, medio, distancia_km, frecuencia, creado_en, miembro_id) VALUES (7, 4, 'bus' 10, 9,'diaria', now(), NULL);
INSERT INTO transporte (transporte_id, actividad_id, medio, distancia_km, frecuencia, creado_en, miembro_id) VALUES (8, 4, 'auto', 8, 'diaria', now(), NULL);
INSERT INTO energia (energia_id, actividad_id, tipo, consumo, unidad, frecuencia, creado_en, miembro_id) VALUES (1, 1, 'electricidad', 180, 'kWh', 'mensual', now(), NULL);
INSERT INTO energia (energia_id, actividad_id, tipo, consumo, unidad, frecuencia, creado_en, miembro_id) VALUES (2, 2, 'electricidad', 240, 'kWh', 'mensual', now(), NULL);
INSERT INTO energia (energia_id, actividad_id, tipo, consumo, unidad, frecuencia, creado_en, miembro_id) VALUES (3, 3, 'electricidad', 120, 'kWh', 'mensual', now(), NULL);
INSERT INTO energia (energia_id, actividad_id, tipo, consumo, unidad, frecuencia, creado_en, miembro_id) VALUES (4, 4, 'electricidad', 200, 'kWh', 'mensual', now(), NULL);
INSERT INTO energia (energia_id, actividad_id, tipo, consumo, unidad, frecuencia, creado_en, miembro_id) VALUES (5, 2, 'glp', 8, 'kg', 'mensual', now(), NULL);
INSERT INTO energia (energia_id, actividad_id, tipo, consumo, unidad, frecuencia, creado_en, miembro_id) VALUES (6, 4, 'glp', 5, 'kg', 'mensual', now(), NULL);
INSERT INTO residuos (residuo_id, actividad_id, tipo, peso_kg, reciclaje, creado_en, miembro_id) VALUES (1, 1, 'mixto', 0.6, false, now(), NULL);
INSERT INTO energia (energia_id, actividad_id, tipo, consumo, unidad, frecuencia, creado_en, miembro_id) VALUES (7, 2, 'electricidad', 0.9, 'kWh', false, now(), NULL);
INSERT INTO energia (energia_id, actividad_id, tipo, consumo, unidad, frecuencia, creado_en, miembro_id) VALUES (8, 3, 'electricidad',0.9, 'kg', true,  now(), NULL);
INSERT INTO energia (energia_id, actividad_id, tipo, consumo, unidad, frecuencia, creado_en, miembro_id) VALUES (9, 4, 'electricidad',0.8, 'kg', false, now(), NULL);
INSERT INTO alimentacion (alimentacion_id, actividad_id, tipo_dieta, frecuencia, creado_en, miembro_id) VALUES (1, 1, 'omnivora','diario',     now(), NULL);
INSERT INTO alimentacion (alimentacion_id, actividad_id, tipo_dieta, frecuencia, creado_en, miembro_id) VALUES (2, 2, 'omnivora', 'diario',    now(), NULL);
INSERT INTO alimentacion (alimentacion_id, actividad_id, tipo_dieta, frecuencia, creado_en, miembro_id) VALUES (3, 3, 'vegetariana', 'diario',   now(), NULL);
INSERT INTO alimentacion (alimentacion_id, actividad_id, tipo_dieta, frecuencia, creado_en, miembro_id) VALUES (4, 4, 'vegana',       'diario', now(), NULL);

-- ACTIVIDADES
SELECT setval(pg_get_serial_sequence('actividades_diarias','actividad_id'), COALESCE((SELECT MAX(actividad_id) FROM actividades_diarias), 0), true);

-- TRANSPORTE
SELECT setval(pg_get_serial_sequence('transporte','transporte_id'), COALESCE((SELECT MAX(transporte_id) FROM transporte), 0), true);

-- ENERGIA
SELECT setval(pg_get_serial_sequence('energia','energia_id'), COALESCE((SELECT MAX(energia_id) FROM energia), 0), true);

-- RESIDUOS
SELECT setval(pg_get_serial_sequence('residuos','residuo_id'),COALESCE((SELECT MAX(residuo_id) FROM residuos), 0), true);

-- ALIMENTACION
SELECT setval(pg_get_serial_sequence('alimentacion','alimentacion_id'),COALESCE((SELECT MAX(alimentacion_id) FROM alimentacion), 0), true);

-- USERS (si también insertaste con id manual)
SELECT setval(pg_get_serial_sequence('users','id'), COALESCE((SELECT MAX(id) FROM users), 0), true);


-- Recursos educativos (ejemplos en español)
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, idioma, tema, creado_en) VALUES (0,'Guía rápida para reducir tu huella de carbono en casa','https://www.youtube.com/watch?v=w9W3A-xotDg','EcoBlog','es','hogar', now());
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, idioma, tema, creado_en) VALUES (1,'Reciclaje 101: lo que sí y lo que no','https://www.youtube.com/watch?v=3LitMXABG6M','Canal Verde','es','residuos', now());
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, idioma, tema, creado_en) VALUES (2,'Movilidad sostenible en ciudades','https://www.youtube.com/watch?v=ebnd01JKf6Q','Planeta Podcast','es','transporte', now());
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, idioma, tema, creado_en) VALUES (0,'Cómo bajar tu recibo de luz con eficiencia','https://www.youtube.com/shorts/9SHh8zZlOzk','EnerTips','es','energia', now());
INSERT INTO recursos_educativos (tipo, titulo, url, fuente, idioma, tema, creado_en) VALUES (1,'Compostaje en casa paso a paso','https://www.youtube.com/watch?v=KLyM2s6XtjE','Huerto Urbano','es','residuos', now());

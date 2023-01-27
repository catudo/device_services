INSERT INTO devices_services.devices ( system_name, type) VALUES ('HP Pavilon', 'WINDOWS');
INSERT INTO devices_services.devices ( system_name, type) VALUES ('Mac book Pro', 'MAC');

INSERT INTO devices_services.services ( type) VALUES ('Antivirus');
INSERT INTO devices_services.services ( type) VALUES ('Backup');
INSERT INTO devices_services.services ( type) VALUES ('Screen Share');


INSERT INTO devices_services.devices_services (device_id, service_id, price, insert_date) VALUES (1, 1, 31, '2023-01-26 18:47:04');
INSERT INTO devices_services.devices_services (device_id, service_id, price, insert_date) VALUES (1, 2, 9, '2023-01-26 18:51:54');
INSERT INTO devices_services.devices_services (device_id, service_id, price, insert_date) VALUES (1, 3, 4, '2023-01-26 18:54:50');
INSERT INTO devices_services.devices_services (device_id, service_id, price, insert_date) VALUES (2, 1, 31, '2023-01-26 18:56:24');
INSERT INTO devices_services.devices_services (device_id, service_id, price, insert_date) VALUES (2, 2, 9, '2023-01-26 18:57:54');
INSERT INTO devices_services.devices_services (device_id, service_id, price, insert_date) VALUES (2, 3, 4, '2023-01-26 18:58:51');


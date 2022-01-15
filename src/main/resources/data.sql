INSERT INTO person(id, active_status, days, email_address, first_name, user_password, last_name, type_of_user, reference_id) VALUES(2345, true, 20, 'admin@codeit.com', 'Admin', '$2a$10$Cqv3qCw6WwH02Oms89TDieWJbvrqqWWc6se7Ty7ocsMJ5EwswK7Tu', 'Admin', 'EMPLOYEE', '12Hk');            -- pw: "password" roles: USER
INSERT INTO role(name) VALUES('USER');
INSERT INTO role(name) VALUES('ADMIN');
INSERT INTO user_role(id, role_id) VALUES(2345, 2);

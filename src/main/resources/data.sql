INSERT INTO `country` (`id`, `name`)
VALUES ('1', 'Argentina');
INSERT INTO `country` (`id`, `name`)
VALUES ('2', 'Chile');
INSERT INTO `country` (`id`, `name`)
VALUES ('3', 'Uruguay');
INSERT INTO `country` (`id`, `name`)
VALUES ('4', 'Colombia');

INSERT INTO `account` (`id`, `password`, `rol`, `username`, `id_country_fk`)
VALUES ('1', 'contra123', '1', 'user_one', '1');
INSERT INTO `account` (`id`, `password`, `rol`, `username`, `id_country_fk`)
VALUES ('2', 'contra123', '1', 'user_two', '2');
INSERT INTO `account` (`id`, `password`, `rol`, `username`, `id_country_fk`)
VALUES ('3', 'contra123', '1', 'user_three', '3');
INSERT INTO `account` (`id`, `password`, `rol`, `username`, `id_country_fk`)
VALUES ('4', 'contra123', '1', 'user_four', '4');

INSERT INTO representative (id,name,account_id,warehouse_id) VALUES (1,'Name', 1,null);
INSERT INTO warehouse (id,name, country_id, representative_id) VALUES (1,'Name', 1, 1);
UPDATE representative SET warehouse_id = '1' WHERE (`id` = '1');

INSERT INTO warehouse (id,name, country_id, representative_id) VALUES (2,'100mq', 1, null);
INSERT INTO sector (`id`, `max_quantity_batches`, `warehouse_id`) VALUES ('1', '50', '1');
INSERT INTO sector (`id`, `max_quantity_batches`, `warehouse_id`) VALUES ('2', '50', '2');
INSERT INTO sector (`id`, `max_quantity_batches`, `warehouse_id`) VALUES ('3', '1', '1');
INSERT INTO `seller` (`name`,`account_id`) VALUES ('Nycolas Vieira',2);
INSERT INTO product (id,description,name,price,seller_id) VALUES (1,'Produto cadastrado','Produto', 10.0, 1);
INSERT INTO product (id,description,name,price,seller_id) VALUES (3,'Produto cadastrado','Produto', 10.0, 1);

INSERT INTO sector_product_types (product_id,types) VALUES (1,  1);
INSERT INTO sector_product_types (product_id,types) VALUES (3,  2);
INSERT INTO sector_types (sector_id,types) VALUES (1,1);
INSERT INTO sector_types (sector_id,types) VALUES (2,2);
INSERT INTO sector_types (sector_id,types) VALUES (3,1);

-- to test update inbound Order
INSERT INTO inbound_order (order_date, representative_id) values (now(),1);
INSERT INTO inbound_order (order_date, representative_id) values (now(),1);
INSERT INTO batch (current_quantity, current_temperature, due_date, inbound_order_id, initial_quantity, manufacturing_date, manufacturing_time, minimum_temperature, product_id, sector_id) values (10, 10.0, now(), 1, 10, now(), now(), 10.0, 1, 1);
INSERT INTO batch (current_quantity, current_temperature, due_date, inbound_order_id, initial_quantity, manufacturing_date, manufacturing_time, minimum_temperature, product_id, sector_id) values (10, 10.0, now(), 1, 10, now(), now(), 10.0, 1, 1);
INSERT INTO batch (current_quantity, current_temperature, due_date, inbound_order_id, initial_quantity, manufacturing_date, manufacturing_time, minimum_temperature, product_id, sector_id) values (10, 10.0, now(), 1, 10, now(), now(), 10.0, 1, 1);
INSERT INTO batch (current_quantity, current_temperature, due_date, inbound_order_id, initial_quantity, manufacturing_date, manufacturing_time, minimum_temperature, product_id, sector_id) values (10, 10.0, now(), 1, 10, now(), now(), 10.0, 1, 1);
INSERT INTO batch (current_quantity, current_temperature, due_date, inbound_order_id, initial_quantity, manufacturing_date, manufacturing_time, minimum_temperature, product_id, sector_id) values (10, 10.0, now(), 1, 10, now(), now(), 10.0, 1, 1);
INSERT INTO batch (id,current_quantity, current_temperature, due_date, inbound_order_id, initial_quantity, manufacturing_date, manufacturing_time, minimum_temperature, product_id, sector_id) values (6,10, 10.0, now(), 2, 10, now(), now(), 10.0, 1, 1);
--

--INSERT INTO `seller` (`name`) VALUES ('Carolina Fugita');
--INSERT INTO `seller` (`name`) VALUES ('Gustavo França');
--INSERT INTO `seller` (`name`) VALUES ('Leonardo Carias');

INSERT INTO country (`id`, `name`) VALUES ('1', 'Argentina');
INSERT INTO country (`id`, `name`) VALUES ('2', 'Chile');
INSERT INTO country (`id`, `name`) VALUES ('3', 'Uruguay');
INSERT INTO country (`id`, `name`) VALUES ('4', 'Colombia');
INSERT INTO country (`id`, `name`) VALUES ('5', 'Brazil');

INSERT INTO account (`id`, `password`, `rol`, `username`, `id_country_fk`) VALUES ('1', 'pass123', '1', 'onias-rocha', '1');
INSERT INTO account (`id`, `password`, `rol`, `username`, `id_country_fk`) VALUES ('2', 'pass123', '1', 'nyvieirameli', '2');
INSERT INTO account (`id`, `password`, `rol`, `username`, `id_country_fk`) VALUES ('3', 'pass123', '1', 'gustavo-franca-meli', '3');
INSERT INTO account (`id`, `password`, `rol`, `username`, `id_country_fk`) VALUES ('4', 'pass123', '1', 'LeonardoBatistaCariasMeli', '4');
INSERT INTO account (`id`, `password`, `rol`, `username`, `id_country_fk`) VALUES ('5', 'pass123', '1', 'cfugita', '5');

INSERT INTO seller (name, account_id) VALUES ("Onias da Rocha Filho", 1);
INSERT INTO seller (name, account_id) VALUES ("Nycolas Vieira", 2);
INSERT INTO seller (name, account_id) VALUES ("Gustavo França", 3);
INSERT INTO seller (name, account_id) VALUES ("Leonardo Carias", 4);
INSERT INTO seller (name, account_id) VALUES ("Carol Fugita", 5);

INSERT INTO product (description, name, price, product_type, seller_id)
VALUES ( "A bisteca é uma carne suína muito saborosa e que pode ser preparada na panela de pressão, assada e muito mais. Alguns ingredientes proporcionam um sabor ainda mais especial a essa carne como o mel, maçã e sucos de frutas cítricas.", "Bisteca Suína", 13.89, 3, 1);
INSERT INTO product (description, name, price, product_type, seller_id)
VALUES ( "A batata é um alimento muito versátil e pode ser preparado de diferentes maneiras como assada, cozida, frita ou até mesmo recheada. Além disso, a batata é repleta de vitamina e também do Complexo B.", "Batata Lavada 1 Unidade 300g", 0.90, 1, 2);

INSERT INTO representative (name,account_id,warehouse_id) VALUES ('Onias da Rocha', 1 , null);
INSERT INTO representative (name,account_id,warehouse_id) VALUES ('Gustavo França', 3 , null);

INSERT INTO warehouse (name, country_id, representative_id) VALUES ("Centro de Tratamento - Cuiabá", 5, 1);
INSERT INTO warehouse (name, country_id, representative_id) VALUES ("Centro de Tratamento - Medelin", 4, 2);

UPDATE representative SET warehouse_id = 1 WHERE (`id` = 1);
UPDATE representative SET warehouse_id = 2 WHERE (`id` = 2);

INSERT INTO sector (max_quantity_batches, warehouse_id, sector_type) VALUES (50, 1, 1);
INSERT INTO sector (max_quantity_batches, warehouse_id, sector_type) VALUES (15, 2, 2);

INSERT INTO inbound_order (order_date, representative_id) values (now(),1);
INSERT INTO inbound_order (order_date, representative_id) values (now(),1);

INSERT INTO batch
    (`id`, `current_quantity`, `current_temperature`, `due_date`, `initial_quantity`, `manufacturing_date`, `manufacturing_time`, `minimum_temperature`, `inbound_order_id`, `product_id`, `sector_id`)
VALUES
    (1, 10, 20.0, '2021-08-08', 100, '2021-04-10', '2021-04-10', 10.0, 1, 1, 1);
INSERT INTO batch
    (`id`, `current_quantity`, `current_temperature`, `due_date`, `initial_quantity`, `manufacturing_date`, `manufacturing_time`, `minimum_temperature`, `inbound_order_id`, `product_id`, `sector_id`)
VALUES
    (2, 70, 20.0, '2021-09-08', 100, '2021-04-10', '2021-04-10', 10.0, 1, 2, 2);
INSERT INTO batch
    (`id`, `current_quantity`, `current_temperature`, `due_date`, `initial_quantity`, `manufacturing_date`, `manufacturing_time`, `minimum_temperature`, `inbound_order_id`, `product_id`, `sector_id`)
VALUES
    (3, 40, 20.0, '2021-07-30', 100, '2021-04-10', '2021-04-10', 10.0, 1, 1, 1);
INSERT INTO batch
    (`id`, `current_quantity`, `current_temperature`, `due_date`, `initial_quantity`, `manufacturing_date`, `manufacturing_time`, `minimum_temperature`, `inbound_order_id`, `product_id`, `sector_id`)
VALUES
    (4, 150, 20.0, '2021-12-08', 100, '2021-04-10', '2021-04-10', 10.0, 1, 2, 2);
INSERT INTO batch
    (`id`, `current_quantity`, `current_temperature`, `due_date`, `initial_quantity`, `manufacturing_date`, `manufacturing_time`, `minimum_temperature`, `inbound_order_id`, `product_id`, `sector_id`)
VALUES
    (5, 10, 20.0, '2021-08-08', 100, '2021-04-10', '2021-04-10', 10.0, 1, 1, 2);

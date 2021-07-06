INSERT INTO `country` (`id`, `name`) VALUES ('1', 'Argentina');
INSERT INTO `country` (`id`, `name`) VALUES ('2', 'Chile');
INSERT INTO `country` (`id`, `name`) VALUES ('3', 'Uruguay');
INSERT INTO `country` (`id`, `name`) VALUES ('4', 'Colombia');

INSERT INTO `account` (`id`, `password`, `rol`, `username`, `id_country_fk`) VALUES ('1', 'contra123', '1', 'user_one', '1');
INSERT INTO `account` (`id`, `password`, `rol`, `username`, `id_country_fk`) VALUES ('2', 'contra123', '1', 'user_two', '2');
INSERT INTO `account` (`id`, `password`, `rol`, `username`, `id_country_fk`) VALUES ('3', 'contra123', '1', 'user_three', '3');
INSERT INTO `account` (`id`, `password`, `rol`, `username`, `id_country_fk`) VALUES ('4', 'contra123', '1', 'user_four', '4');
--
--INSERT INTO `seller` (`name`) VALUES ('Carolina Fugita');
--INSERT INTO `seller` (`name`) VALUES ('Gustavo França');
--INSERT INTO `seller` (`name`) VALUES ('Leonardo Carias');
--INSERT INTO `seller` (`name`) VALUES ('Nycolas Vieira');
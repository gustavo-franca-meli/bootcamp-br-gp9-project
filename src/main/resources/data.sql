INSERT INTO `country_houses` (`id`, `country`, `name`) VALUES ('1', 'Argentina', 'Casa central de Argentina');
INSERT INTO `country_houses` (`id`, `country`, `name`) VALUES ('2', 'Chile', 'Casa central de Chile');
INSERT INTO `country_houses` (`id`, `country`, `name`) VALUES ('3', 'Uruguay', 'Casa central de Uruguay');
INSERT INTO `country_houses` (`id`, `country`, `name`) VALUES ('4', 'Colombia', 'Casa central de Colombia');


INSERT INTO `accounts` (`id`, `password`, `rol`, `username`, `id_country_house_fk`) VALUES ('1', 'contra123', '1', 'user_one', '1');
INSERT INTO `accounts` (`id`, `password`, `rol`, `username`, `id_country_house_fk`) VALUES ('2', 'contra123', '1', 'user_two', '2');
INSERT INTO `accounts` (`id`, `password`, `rol`, `username`, `id_country_house_fk`) VALUES ('3', 'contra123', '1', 'user_three', '3');
INSERT INTO `accounts` (`id`, `password`, `rol`, `username`, `id_country_house_fk`) VALUES ('4', 'contra123', '1', 'user_four', '4');
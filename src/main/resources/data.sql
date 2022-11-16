/*Insertion des données dans les tables*/

INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('jboyd@email.com','jboyd', 'John', 'Boyd');
INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('ecadigan@email.com','ecadigan', 'Eric', 'Cadigan');
INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('tcarman@email.com','tcarman', 'Tessa', 'Carman');
INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('lcooper@email.com','lcooper', 'Lily', 'Cooper');
INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('rpeters@email.com','rpeter', 'Ron', 'Peter');
INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('szemicks@email.com', 'szemicks', 'Sophia', 'Zemicks');
INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('cferguson@email.com', 'cferguson', 'Clive', 'Ferguson');

INSERT INTO `operation` (`user_id`, `buddy_id`, `date`, `type`, `amount`, `description`) VALUES ('1', '2', '2022-10-21', 'op', '10', 'Addition restaurant');
INSERT INTO `operation` (`user_id`, `buddy_id`, `date`, `type`, `amount`, `description`) VALUES ('1', '3', '2022-10-22', 'op', '10', 'Cinema');
INSERT INTO `operation` (`user_id`, `buddy_id`, `date`, `type`, `amount`, `description`) VALUES ('1', '4', '2022-10-23', 'op', '10', 'Parking');
INSERT INTO `operation` (`user_id`, `buddy_id`, `date`, `type`, `amount`, `description`) VALUES ('3', '1', '2022-10-21', 'op', '50', 'Week-end');

INSERT INTO `user_buddy` (`user_id`, `buddy_id`) VALUES ('1', '2');
INSERT INTO `user_buddy` (`user_id`, `buddy_id`) VALUES ('1', '3');
INSERT INTO `user_buddy` (`user_id`, `buddy_id`) VALUES ('1', '4');
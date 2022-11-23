/*Insertion des données dans les tables*/

INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('bpaid@email.com','bpaid', 'Bank', 'App');
INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('jboyd@email.com','jboyd', 'John', 'Boyd');
INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('ecadigan@email.com','ecadigan', 'Eric', 'Cadigan');
INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('tcarman@email.com','tcarman', 'Tessa', 'Carman');
INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('lcooper@email.com','lcooper', 'Lily', 'Cooper');
INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('rpeters@email.com','rpeter', 'Ron', 'Peter');
INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('szemicks@email.com', 'szemicks', 'Sophia', 'Zemicks');
INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('cferguson@email.com', 'cferguson', 'Clive', 'Ferguson');

INSERT INTO `authorities` (`email`, `authority`) VALUES ('jboyd@email.com', 'USER');

INSERT INTO `operation` (`user_id`, `buddy_id`, `date`, `amount`, `description`) VALUES ('2', '3', '2022-10-21', '10', 'Addition restaurant');
INSERT INTO `operation` (`user_id`, `buddy_id`, `date`, `amount`, `description`) VALUES ('2', '4', '2022-10-22', '10', 'Cinema');
INSERT INTO `operation` (`user_id`, `buddy_id`, `date`, `amount`, `description`) VALUES ('2', '5', '2022-10-23', '10', 'Parking');
INSERT INTO `operation` (`user_id`, `buddy_id`, `date`, `amount`, `description`) VALUES ('4', '2', '2022-10-21', '50', 'Week-end');

INSERT INTO `user_buddy` (`user_id`, `buddy_id`) VALUES ('2', '3');
INSERT INTO `user_buddy` (`user_id`, `buddy_id`) VALUES ('2', '4');
INSERT INTO `user_buddy` (`user_id`, `buddy_id`) VALUES ('2', '5');
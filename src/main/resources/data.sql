/*Insertion des données dans les tables*/

INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('bpaid@email.com', '$2a$10$lr1QLlbssfqupUpJSCx.2up09FLJ372DesaZdwyn2QJ8s4suIwtxe', 'Bank', 'App');
INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('jboyd@email.com', '$2a$10$qIoAE2PvpjJZYhXKUuR48uXKxhIbVQGGTfinwtz2gsLuAUO3V23ny', 'John', 'Boyd');
INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('ecadigan@email.com', '$2a$10$tTzSRYrlzgPJEIurP/SY9O4L5E2vniyjsyUT/iIq6OJXITffPcLN2', 'Eric', 'Cadigan');
INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('tcarman@email.com', '$2a$10$QJVtGYNnKosBH0gQKPgmSeqpXx8hM6RtkM9VebU8qe/kyxJBbU3WW', 'Tessa', 'Carman');
INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('lcooper@email.com', '$2a$10$FdlOLnf5AzWudE79mVkvO.g2L8ZEFTXNWCx1uLCd0p1DCIaBdcHmS', 'Lily', 'Cooper');
INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('rpeters@email.com', '$2a$10$KbgAC6DhMGpBG/r0mYcZN.Y7BQy1yP2l5lISFTydYD4FOOYciHk0u', 'Ron', 'Peter');
INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('szemicks@email.com', '$2a$10$MHMEX3DZpHhuYbpA/iRLO.6go2P0mu5bCBVMRuobTXuqunYZM.T.u', 'Sophia', 'Zemicks');
INSERT INTO `user` (`login`, `password`,`first_name`, `last_name`) VALUES ('cferguson@email.com', '$2a$10$L8RCKlu2xzIwYvuTkyi9XuRpPyOB6GL38UaQuHYfT514puQLGjtTG', 'Clive', 'Ferguson');

INSERT INTO `operation` (`user_id`, `buddy_id`, `date`, `amount`, `description`) VALUES ('2', '3', '2022-10-21', '10', 'Addition restaurant');
INSERT INTO `operation` (`user_id`, `buddy_id`, `date`, `amount`, `description`) VALUES ('2', '4', '2022-10-22', '10', 'Cinema');
INSERT INTO `operation` (`user_id`, `buddy_id`, `date`, `amount`, `description`) VALUES ('2', '5', '2022-10-23', '10', 'Parking');
INSERT INTO `operation` (`user_id`, `buddy_id`, `date`, `amount`, `description`) VALUES ('4', '2', '2022-10-21', '50', 'Week-end');

INSERT INTO `user_account` (`user_id`, `date`, `amount`) VALUES ('2', '2022-10-21', '50');

INSERT INTO `user_buddy` (`user_id`, `buddy_id`) VALUES ('2', '3');
INSERT INTO `user_buddy` (`user_id`, `buddy_id`) VALUES ('2', '4');
INSERT INTO `user_buddy` (`user_id`, `buddy_id`) VALUES ('2', '5');



/*Fin du script*/
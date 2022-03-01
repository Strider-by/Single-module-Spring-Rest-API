SET REFERENTIAL_INTEGRITY FALSE;


INSERT INTO `gift_certificate` (`id`, `name`, `price`, `duration`, `create_date`, `last_update_date`) VALUES
	(1, 'Swimming pool holidays', 10, 2, '2022-02-28 18:21:01.000', '2022-02-28 18:21:01.000'),
	(2, 'Zoo friday', 7, 1, '2022-02-28 18:24:11.000', '2022-02-28 18:24:11.000'),
	(3, 'Circus', 9, 1, '2022-02-28 18:26:00.000', '2022-02-28 18:26:00.000'),
	(4, 'Forest adventures', 15, 3, '2022-02-28 18:31:34.000', '2022-02-28 18:31:34.000');

INSERT INTO `tag` (`id`, `name`) VALUES
	(5, 'animals'),
	(13, 'birds'),
	(6, 'children allowed'),
	(9, 'clowns'),
	(14, 'forest'),
	(15, 'outdoor'),
	(8, 'photo'),
	(16, 'river'),
	(3, 'sauna'),
	(2, 'swimming'),
	(4, 'swimming-pool'),
	(1, 'water'),
	(7, 'zoo');

INSERT INTO `tags_to_certificates` (`id`, `tag_id`, `certificate_id`) VALUES
	(1, 3, 1),
	(2, 2, 1),
	(3, 4, 1),
	(4, 1, 1),
	(8, 5, 2),
	(9, 6, 2),
	(10, 8, 2),
	(11, 7, 2),
	(15, 5, 3),
	(16, 6, 3),
	(17, 9, 3),
	(18, 8, 3),
	(22, 13, 4),
	(23, 14, 4),
	(24, 15, 4),
	(25, 16, 4);


SET REFERENTIAL_INTEGRITY TRUE;
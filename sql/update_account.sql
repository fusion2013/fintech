

update account set password = 'ea7cb1c6afc7d2d397f3c63c51b21a41' where account_name='QuantResearch';
update account set password = '2d0156a7df679126a093340f5067f0c6' where account_name='FinBackTesting';
update account set password = 'c8cd1446019a02aadcc17c2e2405a01e' where account_name='mkyong';



INSERT INTO `app_role` (`code`, `name`) VALUES
('ROLE_ADMIN', 'ROLE_ADMIN');

INSERT INTO `account_roles` (`account_id`, `role`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_ADMIN'),
(3, 'ROLE_ADMIN');
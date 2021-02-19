drop table if exists tbl_user;

create table tbl_user (
	id bigint not null auto_increment,
	approved bit,
	blocked bit,
	date_approval datetime(6),
	date_blocked datetime(6),
	date_last_login datetime(6),
	date_validation datetime(6),
	display_name varchar(255),
	email varchar(255),
	failed_attempts integer,
	id_role bigint,
	name varchar(255),
	password varchar(255),
	username varchar(255),
	validated bit,
	primary key (id)
);

create index idx_user_username on tbl_user (username);
create index idx_user_email on tbl_user (email);

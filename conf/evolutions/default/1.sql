-- Created by Ebean DDL
-- To stop Ebean DDL generation, remove this comment (both lines) and start using Evolutions

-- !Ups

-- init script create procs
-- Inital script to create stored procedures etc for mysql platform
DROP PROCEDURE IF EXISTS usp_ebean_drop_foreign_keys;

delimiter $$
--
-- PROCEDURE: usp_ebean_drop_foreign_keys TABLE, COLUMN
-- deletes all constraints and foreign keys referring to TABLE.COLUMN
--
CREATE PROCEDURE usp_ebean_drop_foreign_keys(IN p_table_name VARCHAR(255), IN p_column_name VARCHAR(255))
BEGIN
DECLARE done INT DEFAULT FALSE;
DECLARE c_fk_name CHAR(255);
DECLARE curs CURSOR FOR SELECT CONSTRAINT_NAME from information_schema.KEY_COLUMN_USAGE
WHERE TABLE_SCHEMA = DATABASE() and TABLE_NAME = p_table_name and COLUMN_NAME = p_column_name
AND REFERENCED_TABLE_NAME IS NOT NULL;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

OPEN curs;

read_loop: LOOP
FETCH curs INTO c_fk_name;
IF done THEN
LEAVE read_loop;
END IF;
SET @sql = CONCAT('ALTER TABLE `', p_table_name, '` DROP FOREIGN KEY ', c_fk_name);
PREPARE stmt FROM @sql;
EXECUTE stmt;
END LOOP;

CLOSE curs;
END
$$

DROP PROCEDURE IF EXISTS usp_ebean_drop_column;

delimiter $$
--
-- PROCEDURE: usp_ebean_drop_column TABLE, COLUMN
-- deletes the column and ensures that all indices and constraints are dropped first
--
CREATE PROCEDURE usp_ebean_drop_column(IN p_table_name VARCHAR(255), IN p_column_name VARCHAR(255))
BEGIN
CALL usp_ebean_drop_foreign_keys(p_table_name, p_column_name);
SET @sql = CONCAT('ALTER TABLE `', p_table_name, '` DROP COLUMN `', p_column_name, '`');
PREPARE stmt FROM @sql;
EXECUTE stmt;
END
$$
-- apply changes
create table sessions (
  id                            bigint auto_increment not null,
  user_id                       bigint,
  title                         varchar(255),
  description                   varchar(255),
  session_datetime              varchar(255),
  duration_minutes              bigint,
  constraint pk_sessions primary key (id)
);

create table users (
  id                            bigint auto_increment not null,
  full_name                     varchar(255),
  email                         varchar(255),
  role                          varchar(255),
  password                      varchar(255),
  constraint pk_users primary key (id)
);

-- foreign keys and indices
create index ix_sessions_user_id on sessions (user_id);
alter table sessions add constraint fk_sessions_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;


-- !Downs

-- drop all foreign keys
alter table sessions drop foreign key fk_sessions_user_id;
drop index ix_sessions_user_id on sessions;

-- drop all
drop table if exists sessions;

drop table if exists users;


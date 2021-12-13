-- -----------------------------------------------------
-- Table "profile_Pic"
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `blog_db`.`profil_pic` (
  `id` VARCHAR(255) NOT NULL,
  `data` LONGBLOB NULL DEFAULT NULL,
  `name` VARCHAR(255) NULL DEFAULT NULL,
  `type` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`));

-- -----------------------------------------------------
-- Table "appUser"
-- -----------------------------------------------------
create table if not exists `blog_db`.`app_user`
(
    `id` BIGINT auto_increment primary key,
    `name`     VARCHAR(255) null,
    `password` VARCHAR(255) not null,
    `username` VARCHAR(255) not null,
    `profile_picture_id` VARCHAR(255) NULL DEFAULT NULL,
  INDEX `FK_profil_pic_id` (`profile_picture_id` ASC),
  CONSTRAINT `FK_profil_pic_id`
    FOREIGN KEY (`profile_picture_id`)
    REFERENCES `blog_db`.`profil_pic` (`id`)
);

ALTER TABLE `blog_db`.`app_user` ADD UNIQUE `unique_index`(`username`);

-- -----------------------------------------------------
-- Table "categorie"
-- -----------------------------------------------------
create table if not exists `blog_db`.`categorie`
(
    `id`  BIGINT auto_increment primary key,
    `nom` VARCHAR(255) not null
);

ALTER TABLE `blog_db`.`categorie` ADD UNIQUE `unique_index`(`nom`);

-- -----------------------------------------------------
-- Table `blog_db`.`article`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `blog_db`.`article` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `contenu` VARCHAR(2000) NULL DEFAULT NULL,
  `date` VARCHAR(255) NULL DEFAULT NULL,
  `titre` VARCHAR(255) NULL DEFAULT NULL,
  `article_picture_id` VARCHAR(255) NULL DEFAULT NULL,
  `auteur_id` BIGINT NULL DEFAULT NULL,
  `categorie_id` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_article_picture_id` (`article_picture_id` ASC) VISIBLE,
  INDEX `FK_auteur_id` (`auteur_id` ASC) VISIBLE,
  INDEX `FK_categorie_id` (`categorie_id` ASC) VISIBLE,
  CONSTRAINT `FK_article_picture_id`
    FOREIGN KEY (`article_picture_id`)
    REFERENCES `blog_db`.`profil_pic` (`id`),
  CONSTRAINT `FK_auteur_id`
    FOREIGN KEY (`auteur_id`)
    REFERENCES `blog_db`.`app_user` (`id`),
  CONSTRAINT `FK_categorie_id`
    FOREIGN KEY (`categorie_id`)
    REFERENCES `blog_db`.`categorie` (`id`));


-- -----------------------------------------------------
-- Table "role"
-- -----------------------------------------------------
create table if not exists `blog_db`.`role`
(
    `id`   BIGINT auto_increment primary key,
    `name` varchar(255) null
);

ALTER TABLE `blog_db`.`role` ADD UNIQUE `unique_index`(`name`);

create table if not exists `blog_db`.`app_user_roles`
(
    `app_user_id` BIGINT not null,
    `roles_id`    BIGINT not null,
    constraint `KF_role_id`
        foreign key (`roles_id`) references role (`id`),
    constraint `FK_user_id`
        foreign key (`app_user_id`) references `app_user` (`id`)
);

create table if not exists `blog_db`.`hibernate_sequence`
(
    `next_val` bigint null
);

INSERT INTO `blog_db`.`hibernate_sequence` (`next_val`) VALUES (0);

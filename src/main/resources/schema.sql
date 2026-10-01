-- Schema SeuNutri
CREATE SCHEMA IF NOT EXISTS `SeuNutri` DEFAULT CHARACTER SET utf8;
USE `SeuNutri`;

-- Table `Ranque`
CREATE TABLE IF NOT EXISTS `SeuNutri`.`Ranque` (
  `imc` INT NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  `pontuacao` DOUBLE NOT NULL,
  `Ranquecol` VARCHAR(45) NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- Table `Usuario`
CREATE TABLE IF NOT EXISTS `SeuNutri`.`Usuario` (
  `cpf` BIGINT NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `localizacao` VARCHAR(45) NOT NULL,
  `idade` INT NOT NULL,
  `peso` DOUBLE NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `sexo` VARCHAR(45) NOT NULL,
  `id_ranque` INT NOT NULL,
  PRIMARY KEY (`cpf`),
  CONSTRAINT `fk_Usuario_Ranque1`
    FOREIGN KEY (`id_ranque`)
    REFERENCES `SeuNutri`.`Ranque` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

CREATE INDEX `fk_Usuario_Ranque1_idx` ON `SeuNutri`.`Usuario` (`id_ranque` ASC);

-- Table `Prato`
CREATE TABLE IF NOT EXISTS `SeuNutri`.`Prato` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `ingredientes` VARCHAR(255) NOT NULL,
  `valor_nutricional` FLOAT NOT NULL,
  `receita` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- Table `Ficha`
CREATE TABLE IF NOT EXISTS `SeuNutri`.`Ficha` (
  `imc` INT NOT NULL,
  `peso` DOUBLE NOT NULL,
  `ranque` VARCHAR(45) NOT NULL,
  `usuario_cpf` BIGINT NOT NULL,
  `id_prato` BIGINT NOT NULL,
  `id_ranque` INT NOT NULL,
  PRIMARY KEY (`usuario_cpf`),
  CONSTRAINT `fk_Ficha_Usuario`
    FOREIGN KEY (`usuario_cpf`)
    REFERENCES `SeuNutri`.`Usuario` (`cpf`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ficha_Prato1`
    FOREIGN KEY (`id_prato`)
    REFERENCES `SeuNutri`.`Prato` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ficha_Ranque1`
    FOREIGN KEY (`id_ranque`)
    REFERENCES `SeuNutri`.`Ranque` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

CREATE INDEX `fk_Ficha_Prato1_idx` ON `SeuNutri`.`Ficha` (`id_prato` ASC);
CREATE INDEX `fk_Ficha_Ranque1_idx` ON `SeuNutri`.`Ficha` (`id_ranque` ASC);

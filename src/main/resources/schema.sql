-- Schema SeuNutri
CREATE SCHEMA IF NOT EXISTS `SeuNutri` DEFAULT CHARACTER SET utf8;
USE `SeuNutri`;

-- Table `ranque`
CREATE TABLE IF NOT EXISTS `SeuNutri`.`ranque` (
  `imc` INT NOT NULL,
  `id` INT NOT NULL AUTO_INCREMENT,
  `pontuacao` DOUBLE NOT NULL,
  `ranquecol` VARCHAR(255) NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- Table `usuario`
CREATE TABLE IF NOT EXISTS `SeuNutri`.`usuario` (
  `cpf` BIGINT NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `localizacao` VARCHAR(255) NOT NULL,
  `idade` INT NOT NULL,
  `peso` DOUBLE NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `sexo` VARCHAR(255) NOT NULL,
  `id_ranque` INT NOT NULL,
  PRIMARY KEY (`cpf`),
  CONSTRAINT `fk_Usuario_Ranque1`
    FOREIGN KEY (`id_ranque`)
    REFERENCES `SeuNutri`.`ranque` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

CREATE INDEX `fk_Usuario_Ranque1_idx` ON `SeuNutri`.`usuario` (`id_ranque` ASC);

-- Table `prato`
CREATE TABLE IF NOT EXISTS `SeuNutri`.`prato` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(255) NOT NULL,
  `ingredientes` VARCHAR(255) NOT NULL,
  `valor_nutricional` FLOAT NOT NULL,
  `receita` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- Table `ficha`
CREATE TABLE IF NOT EXISTS `SeuNutri`.`ficha` (
  `imc` INT NOT NULL,
  `peso` DOUBLE NOT NULL,
  `usuario_cpf` BIGINT NOT NULL,
  `id_prato` BIGINT NOT NULL,
  `id_ranque` INT NOT NULL,
  PRIMARY KEY (`usuario_cpf`),
  CONSTRAINT `fk_Ficha_Usuario`
    FOREIGN KEY (`usuario_cpf`)
    REFERENCES `SeuNutri`.`usuario` (`cpf`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ficha_Prato1`
    FOREIGN KEY (`id_prato`)
    REFERENCES `SeuNutri`.`prato` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ficha_Ranque1`
    FOREIGN KEY (`id_ranque`)
    REFERENCES `SeuNutri`.`ranque` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

CREATE INDEX `fk_Ficha_Prato1_idx` ON `SeuNutri`.`ficha` (`id_prato` ASC);
CREATE INDEX `fk_Ficha_Ranque1_idx` ON `SeuNutri`.`ficha` (`id_ranque` ASC);

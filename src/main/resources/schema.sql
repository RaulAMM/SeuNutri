-- Schema SeuNutri
CREATE SCHEMA IF NOT EXISTS `SeuNutri` DEFAULT CHARACTER SET utf8;
USE `SeuNutri`;

-- Table `Ranque`
CREATE TABLE IF NOT EXISTS `SeuNutri`.`Ranque` (
  `IMC` INT NOT NULL,
  `id_ranque` INT NOT NULL,
  `pontuacao` DOUBLE NOT NULL,
  `Ranquecol` VARCHAR(45) NULL,
  PRIMARY KEY (`id_ranque`)
) ENGINE = InnoDB;

-- Table `Usuario`
CREATE TABLE IF NOT EXISTS `SeuNutri`.`Usuario` (
  `CPF` INT NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Localização` VARCHAR(45) NOT NULL,
  `Idade` INT NOT NULL,
  `Peso` DOUBLE NOT NULL,
  `Email` VARCHAR(45) NOT NULL,
  `Sexo` VARCHAR(45) NOT NULL,
  `Id_ranque` INT NOT NULL,
  PRIMARY KEY (`CPF`),
  CONSTRAINT `fk_Usuario_Ranque1`
    FOREIGN KEY (`Id_ranque`)
    REFERENCES `SeuNutri`.`Ranque` (`id_ranque`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

CREATE INDEX `fk_Usuario_Ranque1_idx` ON `SeuNutri`.`Usuario` (`Id_ranque` ASC);

-- Table `Prato`
CREATE TABLE IF NOT EXISTS `SeuNutri`.`Prato` (
  `idPrato` INT NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Ingredientes` VARCHAR(45) NOT NULL,
  `valorNutricional` FLOAT NOT NULL,
  `Receita` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idPrato`)
) ENGINE = InnoDB;

-- Table `Ficha`
CREATE TABLE IF NOT EXISTS `SeuNutri`.`Ficha` (
  `IMC` INT NOT NULL,
  `Peso` DOUBLE NOT NULL,
  `Ranque` VARCHAR(45) NOT NULL,
  `Usuario_CPF` INT NOT NULL,
  `Id_prato` INT NOT NULL,
  `Id_ranque` INT NOT NULL,
  PRIMARY KEY (`Usuario_CPF`),
  CONSTRAINT `fk_Ficha_Usuario`
    FOREIGN KEY (`Usuario_CPF`)
    REFERENCES `SeuNutri`.`Usuario` (`CPF`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ficha_Prato1`
    FOREIGN KEY (`Id_prato`)
    REFERENCES `SeuNutri`.`Prato` (`idPrato`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Ficha_Ranque1`
    FOREIGN KEY (`Id_ranque`)
    REFERENCES `SeuNutri`.`Ranque` (`id_ranque`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
) ENGINE = InnoDB;

CREATE INDEX `fk_Ficha_Prato1_idx` ON `SeuNutri`.`Ficha` (`Id_prato` ASC);
CREATE INDEX `fk_Ficha_Ranque1_idx` ON `SeuNutri`.`Ficha` (`Id_ranque` ASC);
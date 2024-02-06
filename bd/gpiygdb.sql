-- MySQL Script generated by MySQL Workbench
-- Mon Jan 29 18:04:29 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema gpiygdb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema gpiygdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `gpiygdb` DEFAULT CHARACTER SET utf8 ;
USE `gpiygdb` ;

-- -----------------------------------------------------
-- Table `gpiygdb`.`proveedores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gpiygdb`.`proveedores` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(60) NOT NULL,
  `direccion` VARCHAR(80) NULL,
  `cuit` VARCHAR(15) NULL,
  `comentario` VARCHAR(150) NULL,
  `mercado` VARCHAR(120) NULL,
  `egresoIngreso` VARCHAR(1) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gpiygdb`.`transaccion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gpiygdb`.`transaccion` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descripcion` VARCHAR(120) NOT NULL,
  `egresoIngreso` VARCHAR(1) NOT NULL,
  `financiado` TINYINT(1) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gpiygdb`.`resumen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gpiygdb`.`resumen` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATE NOT NULL,
  `egresos` DOUBLE NULL,
  `ingresos` DOUBLE NULL,
  `valorInversiones` DOUBLE NULL,
  `cantInversiones` INT NULL,
  `dolares` DOUBLE NULL,
  `euros` DOUBLE NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gpiygdb`.`ingresos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gpiygdb`.`ingresos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATE NOT NULL,
  `monto` DOUBLE NOT NULL,
  `moneda` VARCHAR(10) NOT NULL,
  `cotizacion` DOUBLE NULL,
  `comentario` VARCHAR(120) NULL,
  `concepto` VARCHAR(60) NOT NULL,
  `idFuente` INT NOT NULL,
  `idFormaCobro` INT NOT NULL,
  `idResumen` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `cobro_idx` (`idFormaCobro` ASC) VISIBLE,
  INDEX `resumenIngresos_idx` (`idResumen` ASC) VISIBLE,
  INDEX `fuente_idx` (`idFuente` ASC) VISIBLE,
  CONSTRAINT `cobro`
    FOREIGN KEY (`idFormaCobro`)
    REFERENCES `gpiygdb`.`transaccion` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `resumenIngresos`
    FOREIGN KEY (`idResumen`)
    REFERENCES `gpiygdb`.`resumen` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fuente`
    FOREIGN KEY (`idFuente`)
    REFERENCES `gpiygdb`.`proveedores` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gpiygdb`.`egresos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gpiygdb`.`egresos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATE NOT NULL,
  `monto` DOUBLE NOT NULL,
  `moneda` VARCHAR(10) NOT NULL,
  `cotizacion` DOUBLE NULL,
  `comentario` VARCHAR(120) NULL,
  `tipoGasto` VARCHAR(60) NOT NULL,
  `fijo` TINYINT(1) NULL,
  `idProveedor` INT NOT NULL,
  `idFormaPago` INT NOT NULL,
  `idResumen` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `proveedor_idx` (`idProveedor` ASC) VISIBLE,
  INDEX `resumen_idx` (`idResumen` ASC) VISIBLE,
  INDEX `formaPago_idx` (`idFormaPago` ASC) VISIBLE,
  CONSTRAINT `proveedor`
    FOREIGN KEY (`idProveedor`)
    REFERENCES `gpiygdb`.`proveedores` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `resumenEgresos`
    FOREIGN KEY (`idResumen`)
    REFERENCES `gpiygdb`.`resumen` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `formaPago`
    FOREIGN KEY (`idFormaPago`)
    REFERENCES `gpiygdb`.`transaccion` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gpiygdb`.`instrumento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gpiygdb`.`instrumento` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(60) NOT NULL,
  `descripcion` VARCHAR(120) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gpiygdb`.`valores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gpiygdb`.`valores` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(60) NOT NULL,
  `cant` DOUBLE NOT NULL,
  `idTipo` INT NOT NULL,
  `idCustodia` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `custodia_idx` (`idCustodia` ASC) VISIBLE,
  INDEX `tipo_idx` (`idTipo` ASC) VISIBLE,
  CONSTRAINT `custodia`
    FOREIGN KEY (`idCustodia`)
    REFERENCES `gpiygdb`.`proveedores` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `tipo`
    FOREIGN KEY (`idTipo`)
    REFERENCES `gpiygdb`.`instrumento` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gpiygdb`.`criptoMoneda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gpiygdb`.`criptoMoneda` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `simbolo` VARCHAR(10) NOT NULL,
  `estable` TINYINT(1) NOT NULL,
  `descripcion` VARCHAR(60) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gpiygdb`.`cripto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gpiygdb`.`cripto` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idCriptoMoneda` INT NOT NULL,
  `idCustodia` INT NOT NULL,
  `cantidad` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `lugar_idx` (`idCustodia` ASC) VISIBLE,
  INDEX `cripto_idx` (`idCriptoMoneda` ASC) VISIBLE,
  CONSTRAINT `lugar`
    FOREIGN KEY (`idCustodia`)
    REFERENCES `gpiygdb`.`proveedores` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `monedaCripto`
    FOREIGN KEY (`idCriptoMoneda`)
    REFERENCES `gpiygdb`.`criptoMoneda` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gpiygdb`.`moneda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gpiygdb`.`moneda` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gpiygdb`.`fiat`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gpiygdb`.`fiat` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `idMoneda` INT NOT NULL,
  `cant` DOUBLE NOT NULL,
  `idCustodia` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `custodia_idx` (`idCustodia` ASC) VISIBLE,
  INDEX `monedaF_idx` (`idMoneda` ASC) VISIBLE,
  CONSTRAINT `localizacion`
    FOREIGN KEY (`idCustodia`)
    REFERENCES `gpiygdb`.`proveedores` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `monedaF`
    FOREIGN KEY (`idMoneda`)
    REFERENCES `gpiygdb`.`moneda` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gpiygdb`.`inmobiliario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gpiygdb`.`inmobiliario` (
  `int` INT NOT NULL AUTO_INCREMENT,
  `descripcion` VARCHAR(120) NOT NULL,
  `lugar` VARCHAR(120) NOT NULL,
  `idOperador` INT NULL,
  PRIMARY KEY (`int`),
  INDEX `operador_idx` (`idOperador` ASC) VISIBLE,
  CONSTRAINT `operador`
    FOREIGN KEY (`idOperador`)
    REFERENCES `gpiygdb`.`proveedores` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gpiygdb`.`operaciones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gpiygdb`.`operaciones` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATE NOT NULL,
  `operacion` VARCHAR(45) NOT NULL,
  `cant` DOUBLE NOT NULL,
  `precio` DOUBLE NOT NULL,
  `comision` DOUBLE NOT NULL,
  `comentario` VARCHAR(120) NULL,
  `idInversion` INT NULL,
  `idCripto` INT NULL,
  `idFiat` INT NULL,
  `idInmobiliario` INT NULL,
  `idEgreso` INT NULL,
  `idIngreso` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `inversion_idx` (`idInversion` ASC) VISIBLE,
  INDEX `egresos_idx` (`idEgreso` ASC) VISIBLE,
  INDEX `cripto_idx` (`idCripto` ASC) VISIBLE,
  INDEX `fiat_idx` (`idFiat` ASC) VISIBLE,
  INDEX `inmobiliario_idx` (`idInmobiliario` ASC) VISIBLE,
  CONSTRAINT `inversion`
    FOREIGN KEY (`idInversion`)
    REFERENCES `gpiygdb`.`valores` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `ingresos`
    FOREIGN KEY (`idIngreso`)
    REFERENCES `gpiygdb`.`ingresos` (`id`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION,
  CONSTRAINT `egresos`
    FOREIGN KEY (`idEgreso`)
    REFERENCES `gpiygdb`.`egresos` (`id`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION,
  CONSTRAINT `cripto`
    FOREIGN KEY (`idCripto`)
    REFERENCES `gpiygdb`.`cripto` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `monedas`
    FOREIGN KEY (`idFiat`)
    REFERENCES `gpiygdb`.`fiat` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `inmobiliario`
    FOREIGN KEY (`idInmobiliario`)
    REFERENCES `gpiygdb`.`inmobiliario` (`int`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gpiygdb`.`cotizaciones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `gpiygdb`.`cotizaciones` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATE NOT NULL,
  `valor` DOUBLE NOT NULL,
  `idValores` INT NULL,
  `idMoneda` INT NOT NULL,
  `idCriptoMoneda` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `Inversiones_idx` (`idValores` ASC) VISIBLE,
  INDEX `divisas_idx` (`idMoneda` ASC) VISIBLE,
  INDEX `criptos_idx` (`idCriptoMoneda` ASC) VISIBLE,
  CONSTRAINT `Inversiones`
    FOREIGN KEY (`idValores`)
    REFERENCES `gpiygdb`.`valores` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `divisas`
    FOREIGN KEY (`idMoneda`)
    REFERENCES `gpiygdb`.`moneda` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `criptos`
    FOREIGN KEY (`idCriptoMoneda`)
    REFERENCES `gpiygdb`.`criptoMoneda` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

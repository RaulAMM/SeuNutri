-- Dados iniciais para o banco de dados SeuNutri

-- Tabela `Ranque`
INSERT INTO `SeuNutri`.`Ranque` (`imc`, `id`, `pontuacao`, `Ranquecol`) VALUES
(18, 1, 3.5, 'Abaixo do peso'),
(22, 2, 5.0, 'Peso normal'),
(27, 3, 2.0, 'Sobrepeso'),
(32, 4, 1.0, 'Obesidade grau I'),
(38, 5, 0.5, 'Obesidade grau II');

-- Tabela `Usuario`
INSERT INTO `SeuNutri`.`Usuario` (`cpf`, `nome`, `localizacao`, `idade`, `peso`, `email`, `sexo`, `id_ranque`) VALUES
(12345678901, 'Ana Souza', 'São Carlos - SP', 25, 58.5, 'ana.souza@email.com', 'F', 2),
(98765432100, 'Bruno Lima', 'Araraquara - SP', 31, 82.0, 'bruno.lima@email.com', 'M', 3),
(45678912345, 'Carla Mendes', 'São Carlos - SP', 19, 47.0, 'carla.mendes@email.com', 'F', 1),
(32165498712, 'Diego Rocha', 'Rio Claro - SP', 45, 95.3, 'diego.rocha@email.com', 'M', 4),
(14725836901, 'Eduarda Alves', 'Ibaté - SP', 27, 61.2, 'eduarda.alves@email.com', 'F', 2);

-- Tabela `Prato`
INSERT INTO `SeuNutri`.`Prato` (`id`, `nome`, `ingredientes`, `valor_nutricional`, `receita`) VALUES
(1, 'Salada de Quinoa', 'Quinoa, tomate, pepino, azeite', 320.5, 'Cozinhar a quinoa e misturar aos vegetais picados com azeite.'),
(2, 'Frango Grelhado com Legumes', 'Peito de frango, brócolis, cenoura', 450.0, 'Grelhar o frango e cozinhar os legumes no vapor.'),
(3, 'Omelete de Espinafre', 'Ovos, espinafre, queijo branco', 280.0, 'Bater os ovos, adicionar espinafre refogado e queijo.'),
(4, 'Smoothie Verde', 'Couve, abacate, maçã, água de coco', 210.0, 'Bater todos os ingredientes no liquidificador.'),
(5, 'Arroz Integral com Feijão', 'Arroz integral, feijão, cebola, alho', 520.0, 'Cozinhar o arroz integral e temperar o feijão.');

-- Tabela `Ficha`
INSERT INTO `SeuNutri`.`Ficha` (`imc`, `peso`, `ranque`, `usuario_cpf`, `id_prato`, `id_ranque`) VALUES
(21, 58.5, 'Peso normal', 12345678901, 1, 2),
(28, 82.0, 'Sobrepeso', 98765432100, 2, 3),
(17, 47.0, 'Abaixo do peso', 45678912345, 4, 1),
(31, 95.3, 'Obesidade grau I', 32165498712, 5, 4),
(22, 61.2, 'Peso normal', 14725836901, 3, 2);

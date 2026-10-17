-- Dados iniciais para o banco de dados SeuNutri

-- Tabela `ranque`
INSERT INTO `SeuNutri`.`ranque` (`imc`, `id`, `pontuacao`, `ranquecol`) VALUES
(18, 1, 3.5, 'Abaixo do peso'),
(22, 2, 5.0, 'Peso normal'),
(27, 3, 2.0, 'Sobrepeso'),
(32, 4, 1.0, 'Obesidade grau I'),
(38, 5, 0.5, 'Obesidade grau II');

-- Tabela `usuario`
INSERT INTO `SeuNutri`.`usuario` (`cpf`, `nome`, `localizacao`, `idade`, `peso`, `email`, `sexo`, `nivel_atividade`, `id_ranque`) VALUES
(12345678901, 'Ana Souza', 'São Carlos - SP', 25, 58.5, 'ana.souza@email.com', 'F', 'sedentaria', 2),
(98765432100, 'Bruno Lima', 'Araraquara - SP', 31, 82.0, 'bruno.lima@email.com', 'M', 'sedentario', 3),
(45678912345, 'Carla Mendes', 'São Carlos - SP', 19, 47.0, 'carla.mendes@email.com', 'F', 'sedentaria', 1),
(32165498712, 'Diego Rocha', 'Rio Claro - SP', 45, 95.3, 'diego.rocha@email.com', 'M', 'ativo', 4),
(14725836901, 'Eduarda Alves', 'Ibaté - SP', 27, 61.2, 'eduarda.alves@email.com', 'F', 'ativa', 2);

-- Tabela `prato`
INSERT INTO `SeuNutri`.`prato` (`id`, `nome`, `ingredientes`, `valor_nutricional`, `receita`) VALUES
(1, 'Salada de Quinoa', 'Quinoa, tomate, pepino, azeite', 320.5, 'Cozinhar a quinoa e misturar aos vegetais picados com azeite.'),
(2, 'Frango Grelhado com Legumes', 'Peito de frango, brócolis, cenoura', 450.0, 'Grelhar o frango e cozinhar os legumes no vapor.'),
(3, 'Omelete de Espinafre', 'Ovos, espinafre, queijo branco', 280.0, 'Bater os ovos, adicionar espinafre refogado e queijo.'),
(4, 'Smoothie Verde', 'Couve, abacate, maçã, água de coco', 210.0, 'Bater todos os ingredientes no liquidificador.'),
(5, 'Arroz Integral com Feijão', 'Arroz integral, feijão, cebola, alho', 520.0, 'Cozinhar o arroz integral e temperar o feijão.'),
(6, 'Bife Grelhado com Batata', 'Bife, batata, azeite, alho', 650.0, 'Grelhar o bife e fritar as batatas.'),
(7, 'Macarrão ao Molho de Tomate', 'Macarrão, tomate, cebola, azeite', 380.0, 'Cozinhar o macarrão e preparar o molho de tomate.'),
(8, 'Peixe Assado com Legumes', 'Peixe, abobrinha, pimentão, azeite', 420.0, 'Assar o peixe com legumes no forno.'),
(9, 'Frango Xadrez com Arroz', 'Frango, molho xadrez, arroz, legumes', 540.0, 'Refogar o frango com molho xadrez e servir com arroz.'),
(10, 'Lasanha de Berinjela', 'Berinjela, molho de tomate, queijo', 480.0, 'Montar camadas de berinjela, molho e queijo e assar.'),
(11, 'Sopa de Legumes com Frango', 'Legumes variados, frango, água', 180.0, 'Cozinhar legumes e frango em água até formar sopa.'),
(12, 'Tapioca com Queijo e Presunto', 'Tapioca, queijo, presunto', 250.0, 'Hidratar a tapioca e rechear com queijo e presunto.'),
(13, 'Omelete de Legumes', 'Ovos, tomate, cebola, pimentão', 300.0, 'Bater os ovos e refogar com legumes picados.'),
(14, 'Salada Caesar com Frango', 'Alface, frango, croutons, molho caesar', 350.0, 'Montar salada com alface, frango grelhado e molho caesar.'),
(15, 'Strogonoff de Carne com Arroz', 'Carne, molho de tomate, creme de leite, arroz', 600.0, 'Refogar a carne com molho e creme de leite, servir com arroz.');

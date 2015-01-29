--
-- Estrutura da tabela `campeonato`
--

CREATE TABLE campeonato (
  id int NOT NULL IDENTITY,
  ano int NOT NULL,
  PRIMARY KEY (id)
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `campeonato_time`
--

CREATE TABLE campeonato_time (
  id int NOT NULL IDENTITY,
  id_campeonato int NOT NULL,
  id_time int NOT NULL,
  PRIMARY KEY (id)
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `jogo`
--

CREATE TABLE jogo (
  id_time_a int NOT NULL,
  id_time_b int NOT NULL,
  id_rodada int NOT NULL,
  nro_gols_a int NOT NULL,
  nro_gols_b int NOT NULL,
  realizado int NOT NULL
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `rodada`
--

CREATE TABLE rodada (
  id int NOT NULL IDENTITY,
  id_campeonato int NOT NULL,
  numero int NOT NULL,
  data varchar(10) NOT NULL,
  PRIMARY KEY (id)
);

-- --------------------------------------------------------

--
-- Estrutura da tabela `time`
--

CREATE TABLE time (
  id int NOT NULL IDENTITY,
  nome varchar(255) NOT NULL,
  estado char(2) NOT NULL,
  PRIMARY KEY (id)
);

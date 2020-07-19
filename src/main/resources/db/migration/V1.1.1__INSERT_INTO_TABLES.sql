INSERT INTO MARCA(NOME) VALUES
    ('Fiat'),
    ('Peugeot'),
    ('Citroen'),
    ('Toyota');

INSERT INTO MODELO(NOME, MARCA_ID) VALUES
    ('Uno', 1),
    ('307', 2),
    ('XSara', 3),
    ('Corolla', 4);

INSERT INTO VEICULO(NOME, MODELO_ID, DATA_FABRICACAO, CONSUMO_CIDADE_KML, CONSUMO_RODOVIA_KML) VALUES
    ('AAA-1111 - Carro Fiat', 1, '2020-01-01', 12, 13),
    ('BBB-2222 - Carro Peugeot', 2, '2019-01-01', 7, 9),
    ('CCC-3333 - Carro Citroen', 3, '2018-10-20', 6, 8),
    ('DDD-4444 - Carro Toyota', 4, '2017-01-01', 11, 14);

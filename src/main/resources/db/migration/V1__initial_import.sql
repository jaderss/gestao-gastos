create table cartao
(
    codigo_usuario bigint auto_increment,
    numero         bigint not null,
    cpf_titular    varchar(255) not null,
    cvc            integer not null,
    data_validade  date not null,
    nome_titular   varchar(255) not null,
    primary key (codigo_usuario)
);
insert into cartao (numero, data_validade, nome_titular, cpf_titular, cvc)
values (4582658945782145, parsedatetime('2029-07-31', 'yyyy-MM-dd'), 'Jader', '02154488741', 534);
insert into cartao (numero, data_validade, nome_titular, cpf_titular, cvc)
values (4582658945782145, parsedatetime('2029-07-31', 'yyyy-MM-dd'), 'Jader', '45122740062', 653);
insert into cartao (numero, data_validade, nome_titular, cpf_titular, cvc)
values (4582658944562145, parsedatetime('2028-08-31', 'yyyy-MM-dd'), 'Natalia', '09155803067', 253);
insert into cartao (numero, data_validade, nome_titular, cpf_titular, cvc)
values (4582658874782145, parsedatetime('2027-09-30', 'yyyy-MM-dd'), 'Eric', '48087382013', 763);
insert into cartao (numero, data_validade, nome_titular, cpf_titular, cvc)
values (4347658945782145, parsedatetime('2026-10-31', 'yyyy-MM-dd'), 'Camila', '91494891000', 536);
insert into cartao (numero, data_validade, nome_titular, cpf_titular, cvc)
values (4582575645782145, parsedatetime('2025-11-30', 'yyyy-MM-dd'), 'Thais', '96056644022', 254);

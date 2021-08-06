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
values (4111111111111111, parsedatetime('2029-07-31', 'yyyy-MM-dd'), 'Carla', '12345678909', 123);
insert into cartao (numero, data_validade, nome_titular, cpf_titular, cvc)
values (4111111111111111, parsedatetime('2029-07-31', 'yyyy-MM-dd'), 'Carla', '52998224725', 123);
insert into cartao (numero, data_validade, nome_titular, cpf_titular, cvc)
values (4111111111111111, parsedatetime('2028-08-31', 'yyyy-MM-dd'), 'Davi', '11144477735', 123);
insert into cartao (numero, data_validade, nome_titular, cpf_titular, cvc)
values (4111111111111111, parsedatetime('2027-09-30', 'yyyy-MM-dd'), 'Bruno', '12345678909', 123);
insert into cartao (numero, data_validade, nome_titular, cpf_titular, cvc)
values (4111111111111111, parsedatetime('2026-10-31', 'yyyy-MM-dd'), 'Ada', '12345678909', 123);
insert into cartao (numero, data_validade, nome_titular, cpf_titular, cvc)
values (4111111111111111, parsedatetime('2025-11-30', 'yyyy-MM-dd'), 'Elisa', '12345678909', 123);

drop table if exists tb_pets;

create table tb_pets (
	id bigserial primary key,
	name varchar(120) not null,
	species varchar(50) not null,
	breed varchar(120),
	color varchar(80),
	weight numeric(10,2),
	vaccinated boolean default false,
	birth_date date,
	notes text,
	deleted boolean default false,
	created_at timestamp default current_timestamp,
	updated_at timestamp default current_timestamp
);


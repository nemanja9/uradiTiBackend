-- public.category definition

-- Drop table

-- DROP TABLE public.category;

CREATE TABLE category
(
    category_id bigserial NOT NULL,
    licence     bool NULL,
    "name"      varchar(255) NULL,
    CONSTRAINT category_pkey PRIMARY KEY (category_id)
);


-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE users
(
    user_id         uuid NOT NULL,
    city            varchar(255) NULL,
    description     varchar(255) NULL,
    email           varchar(255) NULL,
    first_name      varchar(255) NULL,
    last_name       varchar(255) NULL,
    latitude        float8 NULL,
    longitude       float8 NULL,
    phone           varchar(255) NULL,
    rating          float8 NULL,
    user_status     varchar(255) NULL,
    user_type       varchar(255) NULL,
    number_of_tasks int4 NULL,
    CONSTRAINT users_pkey PRIMARY KEY (user_id)
);


-- public.category_tasker definition

-- Drop table

-- DROP TABLE public.category_tasker;

CREATE TABLE category_tasker
(
    category_tasker_id bigserial NOT NULL,
    category_id        int8 NULL,
    user_id            uuid NULL,
    CONSTRAINT category_tasker_pkey PRIMARY KEY (category_tasker_id),
    CONSTRAINT fkppouotu1vaijakvpa3hxla4gc FOREIGN KEY (category_id) REFERENCES public.category (category_id),
    CONSTRAINT fkrsd85woests2t55allhu4f161 FOREIGN KEY (user_id) REFERENCES public.users (user_id)
);


-- public.task definition

-- Drop table

-- DROP TABLE public.task;

CREATE TABLE task
(
    task_id       bigserial NOT NULL,
    city          varchar(255) NULL,
    client_review varchar(255) NULL,
    latitude      float8 NULL,
    longitude     float8 NULL,
    task_status   varchar(255) NULL,
    tasker_rating float8 NULL,
    tasker_review varchar(255) NULL,
    time_created  timestamp NULL,
    time_finished timestamp NULL,
    time_started  timestamp NULL,
    category_id   int8 NULL,
    client_id     uuid NULL,
    tasker_id     uuid NULL,
    CONSTRAINT task_pkey PRIMARY KEY (task_id),
    CONSTRAINT fk12so2dbse3bfdy8podu212uh4 FOREIGN KEY (tasker_id) REFERENCES public.users (user_id),
    CONSTRAINT fkfajcgals1atfo8abj18378id4 FOREIGN KEY (client_id) REFERENCES public.users (user_id),
    CONSTRAINT fkkjb4pwpo8oqc8fvkgbmiitsu9 FOREIGN KEY (category_id) REFERENCES public.category (category_id)
);
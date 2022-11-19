CREATE TABLE IF NOT EXISTS public.drone
(
    id SERIAL,
    serial_number uuid,
    model character varying(255),
    weight_limit integer,
    battery_capacity integer,
    state character varying(255),
    current_location character varying(255),
    loaded_medication_items jsonb default '{}'::jsonb,
    registered_date timestamp without time zone,
    updated_date timestamp without time zone,
    CONSTRAINT drone_pkey PRIMARY KEY (id),
    CONSTRAINT drone_serial_number_unique UNIQUE (serial_number)
)
TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public.medication
(
    id uuid NOT NULL,
    medication_name character varying(255),
    weight int4,
    code character varying(255),
    image_url character varying(255),
    CONSTRAINT medication_pkey PRIMARY KEY (id),
    CONSTRAINT medication_name_unique UNIQUE (medication_name)
)
TABLESPACE pg_default;
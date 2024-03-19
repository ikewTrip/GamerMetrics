--
-- PostgreSQL database dump
--

-- Dumped from database version 14.7
-- Dumped by pg_dump version 14.7

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: _user; Type: TABLE; Schema: public; Owner: gameruser
--

CREATE TABLE public._user (
    id integer NOT NULL,
    email character varying(255),
    first_name character varying(255),
    is_blocked boolean NOT NULL,
    last_name character varying(255),
    nick_name character varying(255),
    password character varying(255),
    role character varying(255),
    steam_id character varying(255),
    organization_id integer,
    team_id integer,
    CONSTRAINT _user_role_check CHECK (((role)::text = ANY ((ARRAY['USER'::character varying, 'ADMIN'::character varying])::text[])))
);


ALTER TABLE public._user OWNER TO gameruser;

--
-- Name: _user_seq; Type: SEQUENCE; Schema: public; Owner: gameruser
--

CREATE SEQUENCE public._user_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public._user_seq OWNER TO gameruser;

--
-- Name: invite; Type: TABLE; Schema: public; Owner: gameruser
--

CREATE TABLE public.invite (
    id integer NOT NULL,
    creation_date timestamp(6) without time zone,
    is_accepted_by_team boolean NOT NULL,
    is_accepted_by_user boolean NOT NULL,
    message character varying(255),
    organization_role_id bigint,
    team_id integer,
    user_id integer
);


ALTER TABLE public.invite OWNER TO gameruser;

--
-- Name: invite_seq; Type: SEQUENCE; Schema: public; Owner: gameruser
--

CREATE SEQUENCE public.invite_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.invite_seq OWNER TO gameruser;

--
-- Name: metrics; Type: TABLE; Schema: public; Owner: gameruser
--

CREATE TABLE public.metrics (
    id integer NOT NULL,
    end_measuring_time timestamp(6) without time zone,
    heart_rate integer,
    start_measuring_time timestamp(6) without time zone,
    user_play_id integer
);


ALTER TABLE public.metrics OWNER TO gameruser;

--
-- Name: metrics_seq; Type: SEQUENCE; Schema: public; Owner: gameruser
--

CREATE SEQUENCE public.metrics_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.metrics_seq OWNER TO gameruser;

--
-- Name: organization; Type: TABLE; Schema: public; Owner: gameruser
--

CREATE TABLE public.organization (
    id integer NOT NULL,
    creation_date timestamp(6) without time zone,
    description character varying(255),
    name character varying(255)
);


ALTER TABLE public.organization OWNER TO gameruser;

--
-- Name: organization_role; Type: TABLE; Schema: public; Owner: gameruser
--

CREATE TABLE public.organization_role (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.organization_role OWNER TO gameruser;

--
-- Name: organization_role_seq; Type: SEQUENCE; Schema: public; Owner: gameruser
--

CREATE SEQUENCE public.organization_role_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.organization_role_seq OWNER TO gameruser;

--
-- Name: organization_role_user; Type: TABLE; Schema: public; Owner: gameruser
--

CREATE TABLE public.organization_role_user (
    user_id integer NOT NULL,
    organization_role_id bigint NOT NULL
);


ALTER TABLE public.organization_role_user OWNER TO gameruser;

--
-- Name: organization_seq; Type: SEQUENCE; Schema: public; Owner: gameruser
--

CREATE SEQUENCE public.organization_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.organization_seq OWNER TO gameruser;

--
-- Name: result; Type: TABLE; Schema: public; Owner: gameruser
--

CREATE TABLE public.result (
    user_play_id integer NOT NULL,
    assists integer,
    deaths integer,
    headshots integer,
    kills integer,
    rounds_played integer
);


ALTER TABLE public.result OWNER TO gameruser;

--
-- Name: team; Type: TABLE; Schema: public; Owner: gameruser
--

CREATE TABLE public.team (
    id integer NOT NULL,
    creation_date timestamp(6) without time zone,
    description character varying(255),
    name character varying(255),
    organization_id integer
);


ALTER TABLE public.team OWNER TO gameruser;

--
-- Name: team_seq; Type: SEQUENCE; Schema: public; Owner: gameruser
--

CREATE SEQUENCE public.team_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.team_seq OWNER TO gameruser;

--
-- Name: token; Type: TABLE; Schema: public; Owner: gameruser
--

CREATE TABLE public.token (
    id integer NOT NULL,
    expired boolean NOT NULL,
    revoked boolean NOT NULL,
    token character varying(255),
    token_type character varying(255),
    user_id integer,
    CONSTRAINT token_token_type_check CHECK (((token_type)::text = 'Bearer'::text))
);


ALTER TABLE public.token OWNER TO gameruser;

--
-- Name: token_seq; Type: SEQUENCE; Schema: public; Owner: gameruser
--

CREATE SEQUENCE public.token_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.token_seq OWNER TO gameruser;

--
-- Name: training; Type: TABLE; Schema: public; Owner: gameruser
--

CREATE TABLE public.training (
    id integer NOT NULL,
    creation_date timestamp(6) without time zone,
    description character varying(255),
    end_time timestamp(6) without time zone,
    name character varying(255),
    start_time timestamp(6) without time zone,
    team_id integer
);


ALTER TABLE public.training OWNER TO gameruser;

--
-- Name: training_seq; Type: SEQUENCE; Schema: public; Owner: gameruser
--

CREATE SEQUENCE public.training_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.training_seq OWNER TO gameruser;

--
-- Name: user_play; Type: TABLE; Schema: public; Owner: gameruser
--

CREATE TABLE public.user_play (
    id integer NOT NULL,
    description character varying(255),
    end_time timestamp(6) without time zone,
    map character varying(255),
    name character varying(255),
    start_time timestamp(6) without time zone,
    user_id integer,
    team_id integer,
    CONSTRAINT user_play_map_check CHECK (((map)::text = ANY ((ARRAY['DE_OVERPASS'::character varying, 'DE_ANUBIS'::character varying, 'DE_INFERNO'::character varying, 'DE_MIRAGE'::character varying, 'DE_VERTIGO'::character varying, 'DE_NUKE'::character varying, 'DE_ANCIENT'::character varying])::text[])))
);


ALTER TABLE public.user_play OWNER TO gameruser;

--
-- Name: user_play_seq; Type: SEQUENCE; Schema: public; Owner: gameruser
--

CREATE SEQUENCE public.user_play_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_play_seq OWNER TO gameruser;

--
-- Data for Name: _user; Type: TABLE DATA; Schema: public; Owner: gameruser
--

COPY public._user (id, email, first_name, is_blocked, last_name, nick_name, password, role, steam_id, organization_id, team_id) FROM stdin;
1	admin@admin	admin1	f	admin1	admin1	$2a$10$VoacR3WfhnNgUrDeexuBJ.Tn7zi0EOkUDqxwWOi.ncWUT7yEoHrrO	ADMIN	\N	\N	\N
\.


--
-- Data for Name: invite; Type: TABLE DATA; Schema: public; Owner: gameruser
--

COPY public.invite (id, creation_date, is_accepted_by_team, is_accepted_by_user, message, organization_role_id, team_id, user_id) FROM stdin;
\.


--
-- Data for Name: metrics; Type: TABLE DATA; Schema: public; Owner: gameruser
--

COPY public.metrics (id, end_measuring_time, heart_rate, start_measuring_time, user_play_id) FROM stdin;
1	2023-12-18 19:00:08.653313	100	2023-12-18 18:59:57.781106	1
2	2023-12-18 19:00:19.699563	58	2023-12-18 19:00:08.678793	1
3	2023-12-18 19:00:30.656661	61	2023-12-18 19:00:19.723976	1
4	2023-12-18 19:00:41.586203	90	2023-12-18 19:00:30.674423	1
5	2023-12-18 19:00:53.716571	92	2023-12-18 19:00:41.601486	1
6	2023-12-18 19:01:05.72828	74	2023-12-18 19:00:53.735461	1
7	2023-12-18 19:01:17.709892	103	2023-12-18 19:01:05.749122	1
8	2023-12-18 19:01:28.763693	69	2023-12-18 19:01:17.731712	1
9	2023-12-18 19:01:39.50796	77	2023-12-18 19:01:28.784546	1
\.


--
-- Data for Name: organization; Type: TABLE DATA; Schema: public; Owner: gameruser
--

COPY public.organization (id, creation_date, description, name) FROM stdin;
\.


--
-- Data for Name: organization_role; Type: TABLE DATA; Schema: public; Owner: gameruser
--

COPY public.organization_role (id, name) FROM stdin;
1	CREATOR
2	PLAYER
3	TRAINER
\.


--
-- Data for Name: organization_role_user; Type: TABLE DATA; Schema: public; Owner: gameruser
--

COPY public.organization_role_user (user_id, organization_role_id) FROM stdin;
\.


--
-- Data for Name: result; Type: TABLE DATA; Schema: public; Owner: gameruser
--

COPY public.result (user_play_id, assists, deaths, headshots, kills, rounds_played) FROM stdin;
1	6	10	16	24	19
\.


--
-- Data for Name: team; Type: TABLE DATA; Schema: public; Owner: gameruser
--

COPY public.team (id, creation_date, description, name, organization_id) FROM stdin;
\.


--
-- Data for Name: token; Type: TABLE DATA; Schema: public; Owner: gameruser
--

COPY public.token (id, expired, revoked, token, token_type, user_id) FROM stdin;
2	t	t	eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJzdWIiOiJhZG1pbkBhZG1pbiIsImlhdCI6MTcwMjkxMDI3OCwiZXhwIjoxNzAyOTk2Njc4fQ.HwMhnXnWXhYaR9f877_8Th7MzZhC0zj1sKQnEmowqI0	Bearer	1
4	t	t	eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJzdWIiOiJhZG1pbkBhZG1pbiIsImlhdCI6MTcwMjkxMDgwMSwiZXhwIjoxNzAyOTk3MjAxfQ.cF6i_DNV4RSH-gchLorAVedzikAS44egEylcVzMlaIQ	Bearer	1
5	t	t	eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJzdWIiOiJhZG1pbkBhZG1pbiIsImlhdCI6MTcwMjkxMzUzMCwiZXhwIjoxNzAyOTk5OTMwfQ.AyiaiXen7nswYoOvBbeoFiE-hsA0_3kPrSEpLqzU7C4	Bearer	1
52	t	t	eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJzdWIiOiJhZG1pbkBhZG1pbiIsImlhdCI6MTcwMjkxODc1MywiZXhwIjoxNzAzMDA1MTUzfQ.0_7g28o5jRDtBrrNYwL24-65_No_YzlVJrAqtqu8U6Y	Bearer	1
102	f	f	eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJzdWIiOiJhZG1pbkBhZG1pbiIsImlhdCI6MTcwMjk3NDM3MCwiZXhwIjoxNzAzMDYwNzcwfQ.i5q3yTc3e8dDlhY5lHNB3DQyc9NtnVp1Ex-xunbBHsg	Bearer	1
\.


--
-- Data for Name: training; Type: TABLE DATA; Schema: public; Owner: gameruser
--

COPY public.training (id, creation_date, description, end_time, name, start_time, team_id) FROM stdin;
\.


--
-- Data for Name: user_play; Type: TABLE DATA; Schema: public; Owner: gameruser
--

COPY public.user_play (id, description, end_time, map, name, start_time, user_id, team_id) FROM stdin;
1	Play edited 1 description	2023-12-18 19:01:38.198074	DE_ANUBIS	Play edited 1	2023-12-18 18:59:57.756774	1	\N
\.


--
-- Name: _user_seq; Type: SEQUENCE SET; Schema: public; Owner: gameruser
--

SELECT pg_catalog.setval('public._user_seq', 51, true);


--
-- Name: invite_seq; Type: SEQUENCE SET; Schema: public; Owner: gameruser
--

SELECT pg_catalog.setval('public.invite_seq', 1, false);


--
-- Name: metrics_seq; Type: SEQUENCE SET; Schema: public; Owner: gameruser
--

SELECT pg_catalog.setval('public.metrics_seq', 51, true);


--
-- Name: organization_role_seq; Type: SEQUENCE SET; Schema: public; Owner: gameruser
--

SELECT pg_catalog.setval('public.organization_role_seq', 51, true);


--
-- Name: organization_seq; Type: SEQUENCE SET; Schema: public; Owner: gameruser
--

SELECT pg_catalog.setval('public.organization_seq', 1, false);


--
-- Name: team_seq; Type: SEQUENCE SET; Schema: public; Owner: gameruser
--

SELECT pg_catalog.setval('public.team_seq', 1, false);


--
-- Name: token_seq; Type: SEQUENCE SET; Schema: public; Owner: gameruser
--

SELECT pg_catalog.setval('public.token_seq', 151, true);


--
-- Name: training_seq; Type: SEQUENCE SET; Schema: public; Owner: gameruser
--

SELECT pg_catalog.setval('public.training_seq', 1, false);


--
-- Name: user_play_seq; Type: SEQUENCE SET; Schema: public; Owner: gameruser
--

SELECT pg_catalog.setval('public.user_play_seq', 51, true);


--
-- Name: _user _user_pkey; Type: CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public._user
    ADD CONSTRAINT _user_pkey PRIMARY KEY (id);


--
-- Name: invite invite_pkey; Type: CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.invite
    ADD CONSTRAINT invite_pkey PRIMARY KEY (id);


--
-- Name: metrics metrics_pkey; Type: CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.metrics
    ADD CONSTRAINT metrics_pkey PRIMARY KEY (id);


--
-- Name: organization organization_pkey; Type: CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.organization
    ADD CONSTRAINT organization_pkey PRIMARY KEY (id);


--
-- Name: organization_role organization_role_pkey; Type: CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.organization_role
    ADD CONSTRAINT organization_role_pkey PRIMARY KEY (id);


--
-- Name: organization_role_user organization_role_user_pkey; Type: CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.organization_role_user
    ADD CONSTRAINT organization_role_user_pkey PRIMARY KEY (user_id, organization_role_id);


--
-- Name: result result_pkey; Type: CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.result
    ADD CONSTRAINT result_pkey PRIMARY KEY (user_play_id);


--
-- Name: team team_pkey; Type: CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT team_pkey PRIMARY KEY (id);


--
-- Name: token token_pkey; Type: CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT token_pkey PRIMARY KEY (id);


--
-- Name: training training_pkey; Type: CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.training
    ADD CONSTRAINT training_pkey PRIMARY KEY (id);


--
-- Name: user_play user_play_pkey; Type: CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.user_play
    ADD CONSTRAINT user_play_pkey PRIMARY KEY (id);


--
-- Name: invite fk317bvwaei5duc0ldtiajlcnwx; Type: FK CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.invite
    ADD CONSTRAINT fk317bvwaei5duc0ldtiajlcnwx FOREIGN KEY (team_id) REFERENCES public.team(id);


--
-- Name: user_play fk32tywqpoxl9o2f3ig7vxl39s4; Type: FK CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.user_play
    ADD CONSTRAINT fk32tywqpoxl9o2f3ig7vxl39s4 FOREIGN KEY (team_id) REFERENCES public.team(id);


--
-- Name: user_play fk7tbiid048n0cy7dvutg1hhraf; Type: FK CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.user_play
    ADD CONSTRAINT fk7tbiid048n0cy7dvutg1hhraf FOREIGN KEY (user_id) REFERENCES public._user(id);


--
-- Name: training fk8rgw83a7d5yemb9eykc5ow01j; Type: FK CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.training
    ADD CONSTRAINT fk8rgw83a7d5yemb9eykc5ow01j FOREIGN KEY (team_id) REFERENCES public.team(id);


--
-- Name: invite fk9uuqj7i124k8kwhndn628lfky; Type: FK CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.invite
    ADD CONSTRAINT fk9uuqj7i124k8kwhndn628lfky FOREIGN KEY (organization_role_id) REFERENCES public.organization_role(id);


--
-- Name: _user fkb5axsbmwjoj1o4g7nmefi3g9r; Type: FK CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public._user
    ADD CONSTRAINT fkb5axsbmwjoj1o4g7nmefi3g9r FOREIGN KEY (team_id) REFERENCES public.team(id);


--
-- Name: invite fkbwgat4rxmrv0fpib7f6njt821; Type: FK CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.invite
    ADD CONSTRAINT fkbwgat4rxmrv0fpib7f6njt821 FOREIGN KEY (user_id) REFERENCES public._user(id);


--
-- Name: token fkiblu4cjwvyntq3ugo31klp1c6; Type: FK CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.token
    ADD CONSTRAINT fkiblu4cjwvyntq3ugo31klp1c6 FOREIGN KEY (user_id) REFERENCES public._user(id);


--
-- Name: result fkkr8ijw0en4r7qxdapd799j3cq; Type: FK CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.result
    ADD CONSTRAINT fkkr8ijw0en4r7qxdapd799j3cq FOREIGN KEY (user_play_id) REFERENCES public.user_play(id);


--
-- Name: organization_role_user fkn7vnfeq0trgytp3h50485p6e3; Type: FK CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.organization_role_user
    ADD CONSTRAINT fkn7vnfeq0trgytp3h50485p6e3 FOREIGN KEY (user_id) REFERENCES public._user(id);


--
-- Name: _user fkohw8dk9qv56k3y7gq44is9lhh; Type: FK CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public._user
    ADD CONSTRAINT fkohw8dk9qv56k3y7gq44is9lhh FOREIGN KEY (organization_id) REFERENCES public.organization(id);


--
-- Name: metrics fksdxasil40otyjhmxswqhgwvew; Type: FK CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.metrics
    ADD CONSTRAINT fksdxasil40otyjhmxswqhgwvew FOREIGN KEY (user_play_id) REFERENCES public.user_play(id);


--
-- Name: organization_role_user fksqnv2jukf927yaptpkfxqwf55; Type: FK CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.organization_role_user
    ADD CONSTRAINT fksqnv2jukf927yaptpkfxqwf55 FOREIGN KEY (organization_role_id) REFERENCES public.organization_role(id);


--
-- Name: team fkt2rwhhxcjdmje0gqqybiyjdpn; Type: FK CONSTRAINT; Schema: public; Owner: gameruser
--

ALTER TABLE ONLY public.team
    ADD CONSTRAINT fkt2rwhhxcjdmje0gqqybiyjdpn FOREIGN KEY (organization_id) REFERENCES public.organization(id);


--
-- PostgreSQL database dump complete
--


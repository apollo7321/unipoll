--
-- PostgreSQL database dump
--

-- Dumped from database version 15.7 (Debian 15.7-0+deb12u1)
-- Dumped by pg_dump version 15.7 (Debian 15.7-0+deb12u1)

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
-- Name: choice; Type: TABLE; Schema: public; Owner: unipoll
--

CREATE TABLE public.choice (
    id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.choice OWNER TO unipoll;

--
-- Name: choice_seq; Type: SEQUENCE; Schema: public; Owner: unipoll
--

CREATE SEQUENCE public.choice_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.choice_seq OWNER TO unipoll;

--
-- Name: poll; Type: TABLE; Schema: public; Owner: unipoll
--

CREATE TABLE public.poll (
    deleted boolean NOT NULL,
    id bigint NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.poll OWNER TO unipoll;

--
-- Name: poll_choices; Type: TABLE; Schema: public; Owner: unipoll
--

CREATE TABLE public.poll_choices (
    choices_id bigint NOT NULL,
    poll_id bigint NOT NULL
);


ALTER TABLE public.poll_choices OWNER TO unipoll;

--
-- Name: poll_seq; Type: SEQUENCE; Schema: public; Owner: unipoll
--

CREATE SEQUENCE public.poll_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.poll_seq OWNER TO unipoll;

--
-- Name: vote; Type: TABLE; Schema: public; Owner: unipoll
--

CREATE TABLE public.vote (
    id bigint NOT NULL,
    poll_id bigint,
    user_id character varying(255)
);


ALTER TABLE public.vote OWNER TO unipoll;

--
-- Name: vote_choices; Type: TABLE; Schema: public; Owner: unipoll
--

CREATE TABLE public.vote_choices (
    choices_id bigint NOT NULL,
    vote_id bigint NOT NULL
);


ALTER TABLE public.vote_choices OWNER TO unipoll;

--
-- Name: vote_seq; Type: SEQUENCE; Schema: public; Owner: unipoll
--

CREATE SEQUENCE public.vote_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.vote_seq OWNER TO unipoll;

--
-- Data for Name: choice; Type: TABLE DATA; Schema: public; Owner: unipoll
--

COPY public.choice (id, name) FROM stdin;
\.


--
-- Data for Name: poll; Type: TABLE DATA; Schema: public; Owner: unipoll
--

COPY public.poll (deleted, id, name) FROM stdin;
\.


--
-- Data for Name: poll_choices; Type: TABLE DATA; Schema: public; Owner: unipoll
--

COPY public.poll_choices (choices_id, poll_id) FROM stdin;
\.


--
-- Data for Name: vote; Type: TABLE DATA; Schema: public; Owner: unipoll
--

COPY public.vote (id, poll_id, user_id) FROM stdin;
\.


--
-- Data for Name: vote_choices; Type: TABLE DATA; Schema: public; Owner: unipoll
--

COPY public.vote_choices (choices_id, vote_id) FROM stdin;
\.


--
-- Name: choice_seq; Type: SEQUENCE SET; Schema: public; Owner: unipoll
--

SELECT pg_catalog.setval('public.choice_seq', 1, false);


--
-- Name: poll_seq; Type: SEQUENCE SET; Schema: public; Owner: unipoll
--

SELECT pg_catalog.setval('public.poll_seq', 1, false);


--
-- Name: vote_seq; Type: SEQUENCE SET; Schema: public; Owner: unipoll
--

SELECT pg_catalog.setval('public.vote_seq', 1, false);


--
-- Name: choice choice_pkey; Type: CONSTRAINT; Schema: public; Owner: unipoll
--

ALTER TABLE ONLY public.choice
    ADD CONSTRAINT choice_pkey PRIMARY KEY (id);


--
-- Name: poll_choices poll_choices_choices_id_key; Type: CONSTRAINT; Schema: public; Owner: unipoll
--

ALTER TABLE ONLY public.poll_choices
    ADD CONSTRAINT poll_choices_choices_id_key UNIQUE (choices_id);


--
-- Name: poll poll_pkey; Type: CONSTRAINT; Schema: public; Owner: unipoll
--

ALTER TABLE ONLY public.poll
    ADD CONSTRAINT poll_pkey PRIMARY KEY (id);


--
-- Name: vote vote_pkey; Type: CONSTRAINT; Schema: public; Owner: unipoll
--

ALTER TABLE ONLY public.vote
    ADD CONSTRAINT vote_pkey PRIMARY KEY (id);


--
-- Name: poll_choices fk2s3m71jjvf3jfpepl4pqei6lb; Type: FK CONSTRAINT; Schema: public; Owner: unipoll
--

ALTER TABLE ONLY public.poll_choices
    ADD CONSTRAINT fk2s3m71jjvf3jfpepl4pqei6lb FOREIGN KEY (choices_id) REFERENCES public.choice(id);


--
-- Name: vote_choices fkajw4c0op1loej0mabw29d2h69; Type: FK CONSTRAINT; Schema: public; Owner: unipoll
--

ALTER TABLE ONLY public.vote_choices
    ADD CONSTRAINT fkajw4c0op1loej0mabw29d2h69 FOREIGN KEY (choices_id) REFERENCES public.choice(id);


--
-- Name: vote fkc2jlu007tq2iwcwkmpdfxjh26; Type: FK CONSTRAINT; Schema: public; Owner: unipoll
--

ALTER TABLE ONLY public.vote
    ADD CONSTRAINT fkc2jlu007tq2iwcwkmpdfxjh26 FOREIGN KEY (poll_id) REFERENCES public.poll(id);


--
-- Name: vote_choices fksrtqgn4tdo63x6erlush6biux; Type: FK CONSTRAINT; Schema: public; Owner: unipoll
--

ALTER TABLE ONLY public.vote_choices
    ADD CONSTRAINT fksrtqgn4tdo63x6erlush6biux FOREIGN KEY (vote_id) REFERENCES public.vote(id);


--
-- Name: poll_choices fktmiorly5frpkb1ysrc660jq2n; Type: FK CONSTRAINT; Schema: public; Owner: unipoll
--

ALTER TABLE ONLY public.poll_choices
    ADD CONSTRAINT fktmiorly5frpkb1ysrc660jq2n FOREIGN KEY (poll_id) REFERENCES public.poll(id);


--
-- PostgreSQL database dump complete
--


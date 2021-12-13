--
-- PostgreSQL database dump
--

-- Dumped from database version 14.1
-- Dumped by pg_dump version 14.1

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
-- Name: app_user; Type: TABLE;
--

CREATE TABLE public.app_user (
    id bigint NOT NULL,
    name character varying(255),
    password character varying(255),
    username character varying(255),
    profile_picture_id character varying(255)
);

--
-- Name: app_user_role; Type: TABLE;
--

CREATE TABLE public.app_user_role (
    app_user_id bigint NOT NULL,
    roles_id bigint NOT NULL
);

--
-- Name: article; Type: TABLE;
--

CREATE TABLE public.article (
    id bigint NOT NULL,
    contenu character varying(2000),
    date character varying(255),
    titre character varying(255),
    article_picture_id character varying(255),
    auteur_id bigint,
    categorie_id bigint
);

--
-- Name: article_id_seq; Type: SEQUENCE;
--

CREATE SEQUENCE public.article_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: categorie; Type: TABLE;
--

CREATE TABLE public.categorie (
    id bigint NOT NULL,
    nom character varying(255)
);

--
-- Name: categorie_id_seq; Type: SEQUENCE;
--

CREATE SEQUENCE public.categorie_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: hibernate_sequence; Type: SEQUENCE;
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: profil_pic; Type: TABLE;
--

CREATE TABLE public.profil_pic (
    id character varying(255) NOT NULL,
    data bytea,
    name character varying(255),
    type character varying(255)
);

--
-- Name: role; Type: TABLE;
--

CREATE TABLE public.role (
    id bigint NOT NULL,
    name character varying(255)
);

--
-- Name: article id; Type: DEFAULT;
--

ALTER TABLE ONLY public.article ALTER COLUMN id SET DEFAULT nextval('public.article_id_seq'::regclass);


--
-- Name: categorie id; Type: DEFAULT;
--

ALTER TABLE ONLY public.categorie ALTER COLUMN id SET DEFAULT nextval('public.categorie_id_seq'::regclass);


--
-- Name: app_user app_user_pkey; Type: CONSTRAINT;
--

ALTER TABLE ONLY public.app_user
    ADD CONSTRAINT app_user_pkey PRIMARY KEY (id);

--
-- Name: article article_pkey; Type: CONSTRAINT;
--

ALTER TABLE ONLY public.article
    ADD CONSTRAINT article_pkey PRIMARY KEY (id);

--
-- Name: categorie categorie_pkey; Type: CONSTRAINT;
--

ALTER TABLE ONLY public.categorie
    ADD CONSTRAINT categorie_pkey PRIMARY KEY (id);

--
-- Name: profil_pic profil_pic_pkey; Type: CONSTRAINT;
--

ALTER TABLE ONLY public.profil_pic
    ADD CONSTRAINT profil_pic_pkey PRIMARY KEY (id);

--
-- Name: role role_pkey; Type: CONSTRAINT;
--

ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);


--
-- Name: app_user_role fk11mbw51w6otlim6pw3jl44sjr; Type: FK CONSTRAINT;
--

ALTER TABLE ONLY public.app_user_role
    ADD CONSTRAINT FK_role_appuser FOREIGN KEY (app_user_id) REFERENCES public.app_user(id);


--
-- Name: article fk2cyqork67cuww6ugg1up9jows; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.article
    ADD CONSTRAINT FK_article_profilpic FOREIGN KEY (article_picture_id) REFERENCES public.profil_pic(id);


--
-- Name: app_user_role fk2qwho5minu1ay6s0v1qt1gcmy; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.app_user_role
    ADD CONSTRAINT FK_appuser_role FOREIGN KEY (roles_id) REFERENCES public.role(id);


--
-- Name: article fkmxtm6iycpdgo5dd8aye0ma7h; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.article
    ADD CONSTRAINT FK_article_appuser FOREIGN KEY (auteur_id) REFERENCES public.app_user(id);


--
-- Name: article fkqnmbf0yfa804hxcw8c9gneb0v; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.article
    ADD CONSTRAINT FK_article_categorie FOREIGN KEY (categorie_id) REFERENCES public.categorie(id);


--
-- Name: app_user fkqyyhjl9lfecvy4g8swbq5o1vn; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.app_user
    ADD CONSTRAINT FK_appuser_profilpic FOREIGN KEY (profile_picture_id) REFERENCES public.profil_pic(id);
--
-- PostgreSQL database dump
--

-- Dumped from database version 9.2.4
-- Dumped by pg_dump version 9.2.4
-- Started on 2016-09-09 11:13:36

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

CREATE DATABASE tweet_annotator;

--
-- TOC entry 168 (class 1259 OID 1409866)
-- Name: annotated_tweets; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE annotated_tweets (
    annotation_id integer NOT NULL,
    status_id bigint NOT NULL,
    user_annotated_id integer NOT NULL,
    datetime timestamp with time zone NOT NULL,
    spent_time_secs bigint,
    has_semantics boolean DEFAULT false,
    has_spatial boolean DEFAULT false,
    semantic_context integer,
    spatial_context integer,
    sentiment integer,
    is_opinative boolean,
    has_sentiment boolean DEFAULT false
);


ALTER TABLE public.annotated_tweets OWNER TO postgres;

--
-- TOC entry 169 (class 1259 OID 1409872)
-- Name: annotated_tweets_annotation_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE annotated_tweets_annotation_id_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.annotated_tweets_annotation_id_seq OWNER TO postgres;

--
-- TOC entry 2039 (class 0 OID 0)
-- Dependencies: 169
-- Name: annotated_tweets_annotation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE annotated_tweets_annotation_id_seq OWNED BY annotated_tweets.annotation_id;


--
-- TOC entry 170 (class 1259 OID 1409874)
-- Name: annotated_tweets_terms; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE annotated_tweets_terms (
    annotation_id integer NOT NULL,
    context_type integer NOT NULL,
    term_position integer NOT NULL,
    term character varying NOT NULL,
    seq_selected_by_user integer NOT NULL
);


ALTER TABLE public.annotated_tweets_terms OWNER TO postgres;

--
-- TOC entry 171 (class 1259 OID 1409880)
-- Name: user_access_history; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE user_access_history (
    user_id integer NOT NULL,
    ip_address character varying NOT NULL,
    history_id integer NOT NULL,
    datetime timestamp with time zone NOT NULL,
    type character varying(3) DEFAULT 'IN'::character varying NOT NULL
);


ALTER TABLE public.user_access_history OWNER TO postgres;

--
-- TOC entry 172 (class 1259 OID 1409887)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE users (
    user_id integer NOT NULL,
    email character varying NOT NULL,
    password character varying NOT NULL,
    name character varying NOT NULL,
    trust_level double precision DEFAULT 0.0 NOT NULL,
    jobs integer DEFAULT 0 NOT NULL,
    meta integer
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 1409911)
-- Name: semantic_contexts; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE semantic_contexts (
    context_id integer NOT NULL,
    name character varying NOT NULL,
    "order" integer
);


ALTER TABLE public.semantic_contexts OWNER TO postgres;

--
-- TOC entry 178 (class 1259 OID 1409917)
-- Name: semantic_contexts_context_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE semantic_contexts_context_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.semantic_contexts_context_id_seq OWNER TO postgres;

--
-- TOC entry 2044 (class 0 OID 0)
-- Dependencies: 178
-- Name: semantic_contexts_context_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE semantic_contexts_context_id_seq OWNED BY semantic_contexts.context_id;


--
-- TOC entry 179 (class 1259 OID 1409919)
-- Name: sentiment_contexts; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE sentiment_contexts (
    context_id integer NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE public.sentiment_contexts OWNER TO postgres;

--
-- TOC entry 180 (class 1259 OID 1409925)
-- Name: sentiment_contexts_context_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sentiment_contexts_context_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sentiment_contexts_context_id_seq OWNER TO postgres;

--
-- TOC entry 2046 (class 0 OID 0)
-- Dependencies: 180
-- Name: sentiment_contexts_context_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE sentiment_contexts_context_id_seq OWNED BY sentiment_contexts.context_id;


--
-- TOC entry 181 (class 1259 OID 1409927)
-- Name: spatial_contexts; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE spatial_contexts (
    context_id integer NOT NULL,
    name character varying NOT NULL
);


ALTER TABLE public.spatial_contexts OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 1409933)
-- Name: spatial_contexts_context_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE spatial_contexts_context_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.spatial_contexts_context_id_seq OWNER TO postgres;

--
-- TOC entry 2048 (class 0 OID 0)
-- Dependencies: 182
-- Name: spatial_contexts_context_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE spatial_contexts_context_id_seq OWNED BY spatial_contexts.context_id;


--
-- TOC entry 183 (class 1259 OID 1409935)
-- Name: tweets; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tweets (
    status_id bigint NOT NULL,
    datetime timestamp with time zone NOT NULL,
    message text NOT NULL
);


ALTER TABLE public.tweets OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 1409941)
-- Name: tweets_complete; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tweets_complete (
    tid bigint NOT NULL,
    in_reply_to_screen_name character varying,
    filter_level character varying,
    lang character varying,
    in_reply_to_status_id_str character varying,
    id bigint,
    in_reply_to_user_id_str character varying,
    timestamp_ms bigint,
    in_reply_to_status_id bigint,
    created_at timestamp with time zone,
    favorite_count character varying,
    place text,
    coordinates character varying,
    text character varying,
    contributors character varying,
    geo character varying,
    entities text,
    source character varying,
    in_reply_to_user_id bigint,
    "user.location" text,
    "user" text,
    "user.lang" character varying,
    retweeted boolean,
    possibly_sensitive boolean,
    truncated boolean,
    favorited boolean,
    retweet_count integer,
    "user.geo_enabled" boolean
);


ALTER TABLE public.tweets_complete OWNER TO postgres;

--
-- TOC entry 189 (class 1259 OID 1409968)
-- Name: user_access_history_history_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_access_history_history_id_seq
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.user_access_history_history_id_seq OWNER TO postgres;

--
-- TOC entry 2051 (class 0 OID 0)
-- Dependencies: 189
-- Name: user_access_history_history_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE user_access_history_history_id_seq OWNED BY user_access_history.history_id;


--
-- TOC entry 190 (class 1259 OID 1409970)
-- Name: users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE users_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_user_id_seq OWNER TO postgres;

--
-- TOC entry 2052 (class 0 OID 0)
-- Dependencies: 190
-- Name: users_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE users_user_id_seq OWNED BY users.user_id;


--
-- TOC entry 1999 (class 2604 OID 1409972)
-- Name: annotation_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY annotated_tweets ALTER COLUMN annotation_id SET DEFAULT nextval('annotated_tweets_annotation_id_seq'::regclass);


--
-- TOC entry 2005 (class 2604 OID 1409973)
-- Name: context_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY semantic_contexts ALTER COLUMN context_id SET DEFAULT nextval('semantic_contexts_context_id_seq'::regclass);


--
-- TOC entry 2006 (class 2604 OID 1409974)
-- Name: context_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sentiment_contexts ALTER COLUMN context_id SET DEFAULT nextval('sentiment_contexts_context_id_seq'::regclass);


--
-- TOC entry 2007 (class 2604 OID 1409975)
-- Name: context_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY spatial_contexts ALTER COLUMN context_id SET DEFAULT nextval('spatial_contexts_context_id_seq'::regclass);


--
-- TOC entry 2001 (class 2604 OID 1409976)
-- Name: history_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_access_history ALTER COLUMN history_id SET DEFAULT nextval('user_access_history_history_id_seq'::regclass);


--
-- TOC entry 2004 (class 2604 OID 1409977)
-- Name: user_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY users ALTER COLUMN user_id SET DEFAULT nextval('users_user_id_seq'::regclass);


--
-- TOC entry 2009 (class 2606 OID 1410492)
-- Name: annotated_tweet_uk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY annotated_tweets
    ADD CONSTRAINT annotated_tweet_uk UNIQUE (status_id, user_annotated_id);


--
-- TOC entry 2011 (class 2606 OID 1410494)
-- Name: annotated_tweets_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY annotated_tweets
    ADD CONSTRAINT annotated_tweets_pk PRIMARY KEY (annotation_id);


--
-- TOC entry 2013 (class 2606 OID 1410496)
-- Name: annotated_tweets_terms_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY annotated_tweets_terms
    ADD CONSTRAINT annotated_tweets_terms_pk PRIMARY KEY (annotation_id, term_position);


--
-- TOC entry 2015 (class 2606 OID 1410498)
-- Name: history_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY user_access_history
    ADD CONSTRAINT history_pkey PRIMARY KEY (history_id);


--
-- TOC entry 2021 (class 2606 OID 1410502)
-- Name: semantic_context_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY semantic_contexts
    ADD CONSTRAINT semantic_context_pk PRIMARY KEY (context_id);


--
-- TOC entry 2023 (class 2606 OID 1410504)
-- Name: sentiment_contexts_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sentiment_contexts
    ADD CONSTRAINT sentiment_contexts_pk PRIMARY KEY (context_id);


--
-- TOC entry 2025 (class 2606 OID 1410506)
-- Name: spatial_context_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY spatial_contexts
    ADD CONSTRAINT spatial_context_pk PRIMARY KEY (context_id);


--
-- TOC entry 2027 (class 2606 OID 1410508)
-- Name: status_id; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tweets
    ADD CONSTRAINT status_id PRIMARY KEY (status_id);


--
-- TOC entry 2029 (class 2606 OID 1410510)
-- Name: tweets_complete_london_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tweets_complete
    ADD CONSTRAINT tweets_complete_london_pk PRIMARY KEY (tid);


--
-- TOC entry 2017 (class 2606 OID 1410514)
-- Name: user_ukey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT user_ukey UNIQUE (email);


--
-- TOC entry 2019 (class 2606 OID 1410516)
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 2033 (class 2606 OID 1410517)
-- Name: annotated_tweet_terms_fk1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY annotated_tweets_terms
    ADD CONSTRAINT annotated_tweet_terms_fk1 FOREIGN KEY (annotation_id) REFERENCES annotated_tweets(annotation_id) ON UPDATE RESTRICT ON DELETE CASCADE;


--
-- TOC entry 2030 (class 2606 OID 1410522)
-- Name: semantic_context_fk1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY annotated_tweets
    ADD CONSTRAINT semantic_context_fk1 FOREIGN KEY (semantic_context) REFERENCES semantic_contexts(context_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2031 (class 2606 OID 1410527)
-- Name: spatial_context_fk1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY annotated_tweets
    ADD CONSTRAINT spatial_context_fk1 FOREIGN KEY (spatial_context) REFERENCES spatial_contexts(context_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2032 (class 2606 OID 1410532)
-- Name: user_annotator_fk1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY annotated_tweets
    ADD CONSTRAINT user_annotator_fk1 FOREIGN KEY (user_annotated_id) REFERENCES users(user_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


-- Completed on 2016-09-09 11:13:40

--
-- PostgreSQL database dump complete
--


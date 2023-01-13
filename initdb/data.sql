CREATE TABLE public.t_user (
                               user_name character varying(255) NOT NULL,
                               supervisor character varying(255)
);

ALTER TABLE public.t_user OWNER TO postgres;

--
-- TOC entry 2984 (class 0 OID 34405)
-- Dependencies: 200
-- Data for Name: t_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.t_user VALUES ('Jonas', '');
INSERT INTO public.t_user VALUES ('Sophie', 'Jonas');
INSERT INTO public.t_user VALUES ('Nick', 'Sophie');
INSERT INTO public.t_user VALUES ('Barbara', 'Nick');
INSERT INTO public.t_user VALUES ('Peter', 'Nick');


--
-- TOC entry 2853 (class 2606 OID 34412)
-- Name: t_user t_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.t_user
    ADD CONSTRAINT t_user_pkey PRIMARY KEY (user_name);
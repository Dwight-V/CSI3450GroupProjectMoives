CREATE TABLE movie (
    m_ID INT,
    m_title VARCHAR(100),
    m_releaseDate DATE,
    m_synopsis VARCHAR(300),
    m_rating VARCHAR(10),
    m_length INT,
    cat_id VARCHAR(50), --horror, action, comedy, etc.
    productionStatus VARCHAR(50), --ADDED. Approved for filming, screened in theatres, coming soon, debut, released on Blu-Ray, Amazon Video, Netflix, etc.
    PRIMARY KEY (m_ID)
);

CREATE TABLE person (
    p_ID INT,
    p_lastName VARCHAR(50),
    p_firstName VARCHAR(50),
    p_pay DECIMAL(15, 2),
    PRIMARY KEY(p_ID)
);

CREATE TABLE theatre (
    t_ID INT,
    t_name VARCHAR(50),
    t_street VARCHAR(50),
    t_city VARCHAR(50),
    t_state VARCHAR(50),
    t_zip INT, 
    t_country VARCHAR(50),
    PRIMARY KEY (t_ID)
);

CREATE TABLE producer (
    prod_ID INT,
    prod_position VARCHAR(50), 
    p_ID INT,
    m_ID INT,
    PRIMARY KEY (prod_ID, p_id, m_ID),
    CONSTRAINT fk_has_p_ID_producer FOREIGN KEY(p_ID) REFERENCES person(p_ID),
    CONSTRAINT fk_has_m_ID_producer FOREIGN KEY (m_ID) REFERENCES movie(m_ID),
    CONSTRAINT unique_prod_id UNIQUE (prod_ID)
);

CREATE TABLE actor (
    actor_ID INT,
    actor_role VARCHAR(50),
    p_ID INT,
    m_ID INT,
    PRIMARY KEY (actor_ID, p_ID, m_ID),
    CONSTRAINT fk_has_p_ID_actor FOREIGN KEY (p_ID) REFERENCES person(p_ID),
    CONSTRAINT fk_has_m_ID_actor FOREIGN KEY (m_ID) REFERENCES movie(m_ID),
    CONSTRAINT unique_actor_id UNIQUE (actor_ID)
);

CREATE TABLE actress (
    actress_ID INT,
    actress_role VARCHAR(50),
    p_ID INT,
    m_ID INT,
    PRIMARY KEY (actress_ID, p_ID, m_ID),
    CONSTRAINT fk_has_p_ID_actress FOREIGN KEY(p_ID) REFERENCES person(p_ID),
    CONSTRAINT fk_has_m_ID_actress FOREIGN KEY (m_ID) REFERENCES movie(m_ID),
    CONSTRAINT unique_actress_ID UNIQUE (actress_ID) 
);

CREATE TABLE writer (
    writ_ID INT,
    writ_contr VARCHAR(100),
    p_ID INT,
    m_ID INT,
    PRIMARY KEY (writ_ID, p_ID, m_ID),
    CONSTRAINT fk_has_p_ID_writer FOREIGN KEY(p_ID) REFERENCES person(p_ID),
    CONSTRAINT fk_has_m_ID_writer FOREIGN KEY (m_ID) REFERENCES movie(m_ID),
    CONSTRAINT unique_writer_ID UNIQUE (writ_ID) 
);

CREATE TABLE director (
    dir_ID INT,
    dir_position VARCHAR(50),
    p_ID INT,
    m_ID INT,
    PRIMARY KEY (dir_ID, p_ID, m_ID),
    CONSTRAINT fk_has_personID_director FOREIGN KEY(p_ID) REFERENCES person(p_ID),
    CONSTRAINT fk_has_movieID_director FOREIGN KEY (m_ID) REFERENCES movie(m_ID),
    CONSTRAINT unique_director_ID UNIQUE (dir_ID) 
);

CREATE TABLE theatremovie (
    t_m_ID INT,
    t_m_start TIMESTAMP,
    t_m_end TIMESTAMP,
    m_ID INT,
    t_ID INT,
    PRIMARY KEY (t_m_ID, m_ID, t_ID),
    CONSTRAINT fk_has_m_ID_theatremovie FOREIGN KEY (m_ID) REFERENCES movie(m_ID),
    CONSTRAINT fk_has_t_ID_theatremovie FOREIGN KEY (t_ID) REFERENCES theatre(t_ID),
    CONSTRAINT unique_t_m_ID UNIQUE (t_m_ID)
);

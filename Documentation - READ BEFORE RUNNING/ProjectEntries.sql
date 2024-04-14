----------------------------------------------------------------------------------------------------------------------------------------------

-- Populate movie table

/*Produced by Joseph Lewis, Directed by Joseph Zimmer. Writers: Brandon Taylor, Dimitri Rite. Actors: Jacob Rowe, Johnny Apple, Drew Chang. 
Actresses: Kate Lisbon, Ilya Rite.*/
INSERT INTO movie (m_ID, m_title, m_releaseDate, m_synopsis, m_rating, m_length, cat_id, productionStatus) 
VALUES (1, 'Generic Action Movie', DATE '2015-12-01', 'Generic action-packed storyline, filled to the brim with cliche one liners.', 
'PG', 120, 'Action', 'Released on Blu-Ray');
INSERT INTO movie (m_ID, m_title, m_releaseDate, m_synopsis, m_rating, m_length, cat_id, productionStatus) 
VALUES (2, 'Generic Action Movie 2', DATE '2022-02-14', 'Another generic action movie with even more cliche one liners.', 
'PG', 120, 'Action', 'Screened in theatres');

/*Produced by Joseph Lewis, Directed by Joseph Zimmer. Writers: Dimitri Rite. Actors: Jacob Rowe. Actresses: Ilya Rite.*/
INSERT INTO movie (m_ID, m_title, m_releaseDate, m_synopsis, m_rating, m_length, cat_id, productionStatus) 
VALUES (3, 'Generic Comedy Film', DATE '2022-03-30', 'Filled with overused jokes so bad they circle back to becoming good.', 
'R', 90, 'Comedy', 'Coming soon to theatres');


/*Produced by Jane Doe, Directed by Shelby White. Writers: Dimitri Rite. Actors: Johnny Apple, Drew Chang. Actresses: Kate Lisbon, Angela Middleton.*/
INSERT INTO movie (m_ID, m_title, m_releaseDate, m_synopsis, m_rating, m_length, cat_id, productionStatus) 
VALUES (4, 'Generic Horror Flick', DATE '2024-03-01', 'Classic horror with jump scares and everyone dead at the end.', 
'R', 95, 'Horror', 'Screened in theatres');


/*Produced by John Doe, Directed by Mark Hanks. Writer: Taylor Li. Actors: Jacob Rowe, Drew Chang. Actress: Angela Middleton, Ilya Rite.*/
--Kate Lisbon will not be in any of these movies.
INSERT INTO movie (m_ID, m_title, m_releaseDate, m_synopsis, m_rating, m_length, cat_id, productionStatus) 
VALUES (5, 'Generic Animated Adventure', DATE '2015-06-15', 'Fun-filled animated journey. We did not take acid while making this movie.', 
'G', 110, 'Animation', 'Released on Blu-Ray');
INSERT INTO movie (m_ID, m_title, m_releaseDate, m_synopsis, m_rating, m_length, cat_id, productionStatus) 
VALUES (6, 'Generic Animated Adventure 2', DATE '2017-06-15', 'Fun-filled animated journey, again, and better with twice the m_length!', 
'G', 220, 'Animation', 'Released on Blu-Ray');
INSERT INTO movie (m_ID, m_title, m_releaseDate, m_synopsis, m_rating, m_length, cat_id, productionStatus) 
VALUES (7, 'Generic Animated Adventure 2 - Remastered', DATE '2022-01-31', 'Fun-filled animated journey. A chance for redemption.', 
'G', 60, 'Animation', 'Released on Blu-Ray');

----------------------------------------------------------------------------------------------------------------------------------------------

-- Populate person table

/*Producers*/
INSERT INTO person (p_ID, p_lastName, p_firstName, p_pay) VALUES (1, 'Doe', 'John', 1000000);
INSERT INTO person (p_ID, p_lastName, p_firstName, p_pay) VALUES (2, 'Doe', 'Jane', 1300000);
INSERT INTO person (p_ID, p_lastName, p_firstName, p_pay) VALUES (3, 'Lewis', 'Joseph', 14000000);

/*Actors*/
INSERT INTO person (p_ID, p_lastName, p_firstName, p_pay) VALUES (4, 'Rowe', 'Jacob', 12500000);
INSERT INTO person (p_ID, p_lastName, p_firstName, p_pay) VALUES (5, 'Apple', 'Johnny', 900000);
INSERT INTO person (p_ID, p_lastName, p_firstName, p_pay) VALUES (6, 'Chang', 'Drew', 950000);

/*Actresses*/
INSERT INTO person (p_ID, p_lastName, p_firstName, p_pay) VALUES (7, 'Lisbon', 'Kate', 11000000);
INSERT INTO person (p_ID, p_lastName, p_firstName, p_pay) VALUES (8, 'Middleton', 'Angela', 950000);
INSERT INTO person (p_ID, p_lastName, p_firstName, p_pay) VALUES (9, 'Rite', 'Ilya', 850000);

/*Writers*/
INSERT INTO person (p_ID, p_lastName, p_firstName, p_pay) VALUES (10, 'Rite', 'Dimitri', 700000);
INSERT INTO person (p_ID, p_lastName, p_firstName, p_pay) VALUES (11, 'Li', 'Taylor', 800000);
INSERT INTO person (p_ID, p_lastName, p_firstName, p_pay) VALUES (12, 'Taylor', 'Brandon', 1000000);

/*Directors*/
INSERT INTO person (p_ID, p_lastName, p_firstName, p_pay) VALUES (13, 'Zimmer', 'Joseph', 15000000);
INSERT INTO person (p_ID, p_lastName, p_firstName, p_pay) VALUES (14, 'Hanks', 'Mark', 14500000);
INSERT INTO person (p_ID, p_lastName, p_firstName, p_pay) VALUES (15, 'White', 'Shelby', 10000000);

----------------------------------------------------------------------------------------------------------------------------------------------

-- Populate theatre table
INSERT INTO theatre (t_ID, t_name, t_street, t_city, t_state, t_zip, t_country) 
VALUES (1, 'Generic Theatre 1', '123 Generic St.', 'Generic City', 'OH', 12345, 'USA');
INSERT INTO theatre (t_ID, t_name, t_street, t_city, t_state, t_zip, t_country) 
VALUES (2, 'Generic Theatre 2', '456 Main St.', 'Main City', 'MI', 67890, 'USA');

----------------------------------------------------------------------------------------------------------------------------------------------

-- Populate producer table

/*Joseph Lewis*/
INSERT INTO producer (prod_ID, prod_position, p_ID, m_ID) VALUES (3, 'Producer', 3, 1);
INSERT INTO producer (prod_ID, prod_position, p_ID, m_ID) VALUES (3, 'Producer', 3, 2);
INSERT INTO producer (prod_ID, prod_position, p_ID, m_ID) VALUES (3, 'Producer', 3, 3);

/*John Doe*/
INSERT INTO producer (prod_ID, prod_position, p_ID, m_ID) VALUES (1, 'Producer', 1, 5);
INSERT INTO producer (prod_ID, prod_position, p_ID, m_ID) VALUES (1, 'Producer', 1, 6);
INSERT INTO producer (prod_ID, prod_position, p_ID, m_ID) VALUES (1, 'Producer', 1, 7);

/*Jane Doe*/
INSERT INTO producer (prod_ID, prod_position, p_ID, m_ID) VALUES (2, 'Producer', 2, 4);

----------------------------------------------------------------------------------------------------------------------------------------------

-- Populate actor table

/*Jacob Rowe*/
INSERT INTO actor (actor_ID, actor_role, p_ID, m_ID) VALUES (4, 'Lead Actor', 4, 1);
INSERT INTO actor (actor_ID, actor_role, p_ID, m_ID) VALUES (4, 'Lead Actor', 4, 2);
INSERT INTO actor (actor_ID, actor_role, p_ID, m_ID) VALUES (4, 'Lead Actor', 4, 3);
INSERT INTO actor (actor_ID, actor_role, p_ID, m_ID) VALUES (4, 'Lead Actor', 4, 4);
INSERT INTO actor (actor_ID, actor_role, p_ID, m_ID) VALUES (4, 'Supporting Actor', 4, 5);
INSERT INTO actor (actor_ID, actor_role, p_ID, m_ID) VALUES (4, 'Supporting Actor', 4, 6);

/*Johnny Apple*/
INSERT INTO actor (actor_ID, actor_role, p_ID, m_ID) VALUES (5, 'Supporting Actor', 5, 1);
INSERT INTO actor (actor_ID, actor_role, p_ID, m_ID) VALUES (5, 'Supporting Actor', 5, 2);
INSERT INTO actor (actor_ID, actor_role, p_ID, m_ID) VALUES (5, 'Lead Actor', 5, 4);

/*Drew Chang*/
INSERT INTO actor (actor_ID, actor_role, p_ID, m_ID) VALUES (6, 'Lead Actor', 6, 1);
INSERT INTO actor (actor_ID, actor_role, p_ID, m_ID) VALUES (6, 'Lead Actor', 6, 2);
INSERT INTO actor (actor_ID, actor_role, p_ID, m_ID) VALUES (6, 'Supporting Actor', 6, 4);
INSERT INTO actor (actor_ID, actor_role, p_ID, m_ID) VALUES (6, 'Supporting Actor', 6, 5);
INSERT INTO actor (actor_ID, actor_role, p_ID, m_ID) VALUES (6, 'Supporting Actor', 6, 6);


----------------------------------------------------------------------------------------------------------------------------------------------

-- Populate actress table

/*Kate Lisbon*/
INSERT INTO actress (actress_ID, actress_role, p_ID, m_ID) VALUES (7, 'Lead Actress', 7, 1);
INSERT INTO actress (actress_ID, actress_role, p_ID, m_ID) VALUES (7, 'Lead Actress', 7, 2);
INSERT INTO actress (actress_ID, actress_role, p_ID, m_ID) VALUES (7, 'Lead Actress', 7, 4);

/*Angela Middleton*/
INSERT INTO actress (actress_ID, actress_role, p_ID, m_ID) VALUES (8, 'Lead Actress', 8, 4);
INSERT INTO actress (actress_ID, actress_role, p_ID, m_ID) VALUES (8, 'Lead Actress', 8, 5);
INSERT INTO actress (actress_ID, actress_role, p_ID, m_ID) VALUES (8, 'Supporting Actress', 8, 6);
INSERT INTO actress (actress_ID, actress_role, p_ID, m_ID) VALUES (8, 'Supporting Actress', 8, 7);

/*Ilya Rite*/
INSERT INTO actress (actress_ID, actress_role, p_ID, m_ID) VALUES (9, 'Supporting Actress', 9, 1);
INSERT INTO actress (actress_ID, actress_role, p_ID, m_ID) VALUES (9, 'Supporting Actress', 9, 2);
INSERT INTO actress (actress_ID, actress_role, p_ID, m_ID) VALUES (9, 'Lead Actress', 9, 3);
INSERT INTO actress (actress_ID, actress_role, p_ID, m_ID) VALUES (9, 'Supporting Actress', 9, 5);
INSERT INTO actress (actress_ID, actress_role, p_ID, m_ID) VALUES (9, 'Supporting Actress', 9, 6);
INSERT INTO actress (actress_ID, actress_role, p_ID, m_ID) VALUES (9, 'Supporting Actress', 9, 7);

----------------------------------------------------------------------------------------------------------------------------------------------

-- Populate writer table

/*Brandon Taylor*/
INSERT INTO writer (writ_ID, writ_contr, p_ID, m_ID) VALUES (12, 'Lead Writer', 12, 1);
INSERT INTO writer (writ_ID, writ_contr, p_ID, m_ID) VALUES (12, 'Lead Writer', 12, 2);

/*Dimitri Rite*/
INSERT INTO writer (writ_ID, writ_contr, p_ID, m_ID) VALUES (10, 'Supporting Writer', 10, 1);
INSERT INTO writer (writ_ID, writ_contr, p_ID, m_ID) VALUES (10, 'Supporting Writer', 10, 2);
INSERT INTO writer (writ_ID, writ_contr, p_ID, m_ID) VALUES (10, 'Lead Writer', 10, 3);
INSERT INTO writer (writ_ID, writ_contr, p_ID, m_ID) VALUES (10, 'Lead Writer', 10, 4);

/*Taylor Lee*/
INSERT INTO writer (writ_ID, writ_contr, p_ID, m_ID) VALUES (11, 'Lead Writer', 11, 5);
INSERT INTO writer (writ_ID, writ_contr, p_ID, m_ID) VALUES (11, 'Lead Writer', 11, 6);
INSERT INTO writer (writ_ID, writ_contr, p_ID, m_ID) VALUES (11, 'Lead Writer', 11, 7);

----------------------------------------------------------------------------------------------------------------------------------------------

-- Populate director table

/*Joseph Zimmer*/
INSERT INTO director (dir_ID, dir_position, p_ID, m_ID) VALUES (13, 'Lead Director', 13, 1);
INSERT INTO director (dir_ID, dir_position, p_ID, m_ID) VALUES (13, 'Lead Director', 13, 2);
INSERT INTO director (dir_ID, dir_position, p_ID, m_ID) VALUES (13, 'Lead Director', 13, 3);

/*Shleby White*/
INSERT INTO director (dir_ID, dir_position, p_ID, m_ID) VALUES (15, 'Lead Director', 15, 4);

/*Mark Hanks*/
INSERT INTO director (dir_ID, dir_position, p_ID, m_ID) VALUES (14, 'Lead Director', 14, 5);
INSERT INTO director (dir_ID, dir_position, p_ID, m_ID) VALUES (14, 'Lead Director', 14, 6);
INSERT INTO director (dir_ID, dir_position, p_ID, m_ID) VALUES (14, 'Lead Director', 14, 7);

----------------------------------------------------------------------------------------------------------------------------------------------

-- Populate theatre-movie table

/*Generic Sci-fi Thriller showing at two theaters at the same time*/
INSERT INTO theatremovie (t_m_ID, t_m_start, t_m_end, m_ID, t_ID) 
VALUES (1, TO_TIMESTAMP('2005-03-20 12:00', 'YYYY-MM-DD HH24:MI'), TO_TIMESTAMP('2005-03-20 14:00', 'YYYY-MM-DD HH24:MI'), 3, 1);
INSERT INTO theatremovie (t_m_ID, t_m_start, t_m_end, m_ID, t_ID) 
VALUES (2, TO_TIMESTAMP('2005-03-20 12:00', 'YYYY-MM-DD HH24:MI'), TO_TIMESTAMP('2005-03-20 14:00', 'YYYY-MM-DD HH24:MI'), 3, 1);


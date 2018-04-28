--
-- Fichier généré par SQLiteStudio v3.1.1 sur mer. avr. 25 15:27:08 2018
--
-- Encodage texte utilisé : UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table : AIDE
BEGIN TABLE IF EXISTS AIDE;
create table AIDE 
(Naide int(50) not null unique primary key,
Identifiant char(255) not null,
Description char(255) not null,
Activite boolean not null,
foreign key (Identifiant) references UTILISATEUR);
INSERT INTO AIDE (Naide, Identifiant, Description, Activite) VALUES ('1', 'fel98', 'Quelle couleur la robe ?', '0');
INSERT INTO AIDE (Naide, Identifiant, Description, Activite) VALUES ('2', 'margauxgerard', 'Tu préfères manger quoi ?', '1');
INSERT INTO AIDE (Naide, Identifiant, Description, Activite) VALUES ('3', 'gegedu78', 'Alors ton test de math ?', '1');
INSERT INTO AIDE (Naide, Identifiant, Description, Activite) VALUES ('4', 'Hiboule', 'Tu préfères me rembourser comment ?', '1');
INSERT INTO AIDE (Naide, Identifiant, Description, Activite) VALUES ('5', 'phephe', 'Je te prends quel type de pain ?', '0');

-- Table : LIKE_LIKE
BEGIN TABLE IF EXISTS LIKE_LIKE;
create table LIKE_LIKE
(NoptionsA int(50) not null,
Identifiant char(255) not null,
Like_Like boolean not null,
unique (Identifiant,NoptionsA),
primary key (Identifiant,NoptionsA),
foreign key (Identifiant) references UTILISATEUR,
foreign key (NoptionsA) references OPTIONA);
INSERT INTO LIKE_LIKE (NoptionsA, Identifiant, Like_Like) VALUES ('7', 'margauxgerard', '1');
INSERT INTO LIKE_LIKE (NoptionsA, Identifiant, Like_Like) VALUES ('4', 'gegedu78', '1');
INSERT INTO LIKE_LIKE (NoptionsA, Identifiant, Like_Like) VALUES ('6', 'Moustikman', '1');

-- Table : OPTION
BEGIN TABLE IF EXISTS OPTION;
create table OPTION
(Texte char(255),
Image char(255),
Veracite boolean not null,
Nquestions int(50) not null,
Noptions int(50) not null unique primary key,
foreign key (Nquestions) references QUESTION);
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('Noir', NULL, '0', '1', '1');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('Blanc', NULL, '1', '1', '2');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('Marron', NULL, '0', '1', '3');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('Rouge', NULL, '0', '1', '4');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('1', NULL, '0', '6', '5');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('2', NULL, '1', '6', '6');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('3', NULL, '0', '6', '7');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('4', NULL, '0', '6', '8');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('5', NULL, '1', '7', '9');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('0', NULL, '0', '7', '10');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('1', NULL, '0', '7', '11');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('12', NULL, '0', '7', '12');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('100 km/h', NULL, '0', '8', '13');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('245 km/h', NULL, '0', '8', '14');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('330 km/h', NULL, '1', '8', '15');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('420 km/h', NULL, '0', '8', '16');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('200 000', NULL, '0', '9', '17');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('475 000', NULL, '1', '9', '18');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('535 000', NULL, '0', '9', '19');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('760 000', NULL, '0', '9', '20');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('A vélo', NULL, '1', '10', '21');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('En vélo', NULL, '0', '10', '22');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('Dans vélo', NULL, '0', '10', '23');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('Au vélo', NULL, '0', '10', '24');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('Shire', NULL, '0', '2', '25');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('Marengo', NULL, '1', '2', '26');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('Arabe', NULL, '0', '2', '27');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('Pur-sang', NULL, '0', '2', '28');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('Bataille de Waterloo', NULL, '0', '3', '29');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('Bataille de Pur-sang', NULL, '0', '3', '30');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('Bataille de Noami', NULL, '0', '3', '31');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('Bataille de Marengo', NULL, '1', '3', '32');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('Napoléon', NULL, '1', '4', '33');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('Léopold 1er', NULL, '0', '4', '34');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('Albert', NULL, '0', '4', '35');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('Nicolas', NULL, '0', '4', '36');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('1m20 au garrot', NULL, '0', '5', '37');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('1m30 au garrot', NULL, '0', '5', '38');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('1m40 au garrot', NULL, '0', '5', '39');
INSERT INTO OPTION (Texte, Image, Veracite, Nquestions, Noptions) VALUES ('1m50 au garrot', NULL, '1', '5', '40');

-- Table : OPTIONA
BEGIN TABLE IF EXISTS OPTIONA;
create table OPTIONA
(NoptionsA int(50) not null unique primary key,
Naide int(50) not null,
Texte char(255),
Image char(255),
foreign key (Naide) references AIDE);
INSERT INTO OPTIONA (NoptionsA, Naide, Texte, Image) VALUES ('1', '1', 'Bleu', NULL);
INSERT INTO OPTIONA (NoptionsA, Naide, Texte, Image) VALUES ('2', '1', 'Vert', NULL);
INSERT INTO OPTIONA (NoptionsA, Naide, Texte, Image) VALUES ('3', '2', 'Poulet', NULL);
INSERT INTO OPTIONA (NoptionsA, Naide, Texte, Image) VALUES ('4', '2', 'Boeuf', NULL);
INSERT INTO OPTIONA (NoptionsA, Naide, Texte, Image) VALUES ('5', '3', 'Réussi', NULL);
INSERT INTO OPTIONA (NoptionsA, Naide, Texte, Image) VALUES ('6', '3', 'Raté', NULL);
INSERT INTO OPTIONA (NoptionsA, Naide, Texte, Image) VALUES ('7', '4', 'En liquide', NULL);
INSERT INTO OPTIONA (NoptionsA, Naide, Texte, Image) VALUES ('8', '4', 'Par virement', NULL);
INSERT INTO OPTIONA (NoptionsA, Naide, Texte, Image) VALUES ('9', '5', 'Pain complet', NULL);
INSERT INTO OPTIONA (NoptionsA, Naide, Texte, Image) VALUES ('10', '5', 'Pain aux céréales', NULL);

-- Table : PARTICIPANTS_AIDE
BEGIN TABLE IF EXISTS PARTICIPANTS_AIDE;
create table PARTICIPANTS_AIDE 
(Naide int(50) not null,
Identifiant char(255) not null,
unique (Identifiant,Naide),
primary key (Identifiant,Naide),
foreign key (Identifiant) references UTILISATEUR,
foreign key (Naide) references AIDE);
INSERT INTO PARTICIPANTS_AIDE (Naide, Identifiant) VALUES ('1', 'margauxgerard');
INSERT INTO PARTICIPANTS_AIDE (Naide, Identifiant) VALUES ('2', 'gegedu78');
INSERT INTO PARTICIPANTS_AIDE (Naide, Identifiant) VALUES ('3', 'Moustikman');
INSERT INTO PARTICIPANTS_AIDE (Naide, Identifiant) VALUES ('4', 'margauxgerard');
INSERT INTO PARTICIPANTS_AIDE (Naide, Identifiant) VALUES ('5', 'fel98');

-- Table : PARTICIPANTS_QUESTIONNAIRE
BEGIN TABLE IF EXISTS PARTICIPANTS_QUESTIONNAIRE;
create table PARTICIPANTS_QUESTIONNAIRE
(Identifiant char(255) not null,
Nquestionnaire int(50) not null,
unique (Identifiant,Nquestionnaire),
primary key (Identifiant,Nquestionnaire),
foreign key (Identifiant) references UTILISATEUR,
foreign key (Nquestionnaire) references QUESTIONNAIRE);
INSERT INTO PARTICIPANTS_QUESTIONNAIRE (Identifiant, Nquestionnaire) VALUES ('fel98', '1');
INSERT INTO PARTICIPANTS_QUESTIONNAIRE (Identifiant, Nquestionnaire) VALUES ('margauxgerard', '1');
INSERT INTO PARTICIPANTS_QUESTIONNAIRE (Identifiant, Nquestionnaire) VALUES ('margauxgerard', '2');
INSERT INTO PARTICIPANTS_QUESTIONNAIRE (Identifiant, Nquestionnaire) VALUES ('Moustikman', '2');
INSERT INTO PARTICIPANTS_QUESTIONNAIRE (Identifiant, Nquestionnaire) VALUES ('phephe', '2');
INSERT INTO PARTICIPANTS_QUESTIONNAIRE (Identifiant, Nquestionnaire) VALUES ('fel98', '2');
INSERT INTO PARTICIPANTS_QUESTIONNAIRE (Identifiant, Nquestionnaire) VALUES ('gegedu78', '2');
INSERT INTO PARTICIPANTS_QUESTIONNAIRE (Identifiant, Nquestionnaire) VALUES ('gegedu78', '1');
INSERT INTO PARTICIPANTS_QUESTIONNAIRE (Identifiant, Nquestionnaire) VALUES ('Hiboule', '1');

-- Table : PARTICIPANTS_SONDAGE
BEGIN TABLE IF EXISTS PARTICIPANTS_SONDAGE;
create table PARTICIPANTS_SONDAGE
(Identifiant char(255) not null,
Nsondage int (50) not null,
unique (Identifiant,Nsondage),
primary key (Identifiant, Nsondage),
foreign key (Identifiant, Nsondage) references UTILISATEUR);
INSERT INTO PARTICIPANTS_SONDAGE (Identifiant, Nsondage) VALUES ('margauxgerard', '1');
INSERT INTO PARTICIPANTS_SONDAGE (Identifiant, Nsondage) VALUES ('fel98', '1');
INSERT INTO PARTICIPANTS_SONDAGE (Identifiant, Nsondage) VALUES ('Eriko99', '1');
INSERT INTO PARTICIPANTS_SONDAGE (Identifiant, Nsondage) VALUES ('gegedu78', '2');
INSERT INTO PARTICIPANTS_SONDAGE (Identifiant, Nsondage) VALUES ('phephe', '2');
INSERT INTO PARTICIPANTS_SONDAGE (Identifiant, Nsondage) VALUES ('Hiboule', '2');
INSERT INTO PARTICIPANTS_SONDAGE (Identifiant, Nsondage) VALUES ('Moustikman', '2');
INSERT INTO PARTICIPANTS_SONDAGE (Identifiant, Nsondage) VALUES ('Moustikman', '3');
INSERT INTO PARTICIPANTS_SONDAGE (Identifiant, Nsondage) VALUES ('fel98', '3');
INSERT INTO PARTICIPANTS_SONDAGE (Identifiant, Nsondage) VALUES ('phephe', '3');
INSERT INTO PARTICIPANTS_SONDAGE (Identifiant, Nsondage) VALUES ('gegedu78', '3');

-- Table : POSSIBILITE
BEGIN TABLE IF EXISTS POSSIBILITE;
create table POSSIBILITE
(Npossibilites int(50) not null unique primary key,
Nsondage int (50) not null,
Texte char(255),
Image char(255),
Ordre int(50) not null,
foreign key (Nsondage) references SONDAGE);
INSERT INTO POSSIBILITE (Npossibilites, Nsondage, Texte, Image, Ordre) VALUES ('1', '1', 'Cinéma', NULL, '1');
INSERT INTO POSSIBILITE (Npossibilites, Nsondage, Texte, Image, Ordre) VALUES ('2', '1', 'Piscine', NULL, '2');
INSERT INTO POSSIBILITE (Npossibilites, Nsondage, Texte, Image, Ordre) VALUES ('3', '1', 'Resto', NULL, '3');
INSERT INTO POSSIBILITE (Npossibilites, Nsondage, Texte, Image, Ordre) VALUES ('4', '1', 'Sport', NULL, '4');
INSERT INTO POSSIBILITE (Npossibilites, Nsondage, Texte, Image, Ordre) VALUES ('5', '2', 'Pates carbo', 'patescarbo.png', '1');
INSERT INTO POSSIBILITE (Npossibilites, Nsondage, Texte, Image, Ordre) VALUES ('6', '2', 'Spaghetti', NULL, '2');
INSERT INTO POSSIBILITE (Npossibilites, Nsondage, Texte, Image, Ordre) VALUES ('7', '2', 'Lasagne', NULL, '3');
INSERT INTO POSSIBILITE (Npossibilites, Nsondage, Texte, Image, Ordre) VALUES ('8', '2', 'Wok', NULL, '4');
INSERT INTO POSSIBILITE (Npossibilites, Nsondage, Texte, Image, Ordre) VALUES ('9', '2', 'Tartiflette', NULL, '5');
INSERT INTO POSSIBILITE (Npossibilites, Nsondage, Texte, Image, Ordre) VALUES ('10', '2', 'Raclette', NULL, '6');
INSERT INTO POSSIBILITE (Npossibilites, Nsondage, Texte, Image, Ordre) VALUES ('11', '3', 'Espagne', NULL, '1');
INSERT INTO POSSIBILITE (Npossibilites, Nsondage, Texte, Image, Ordre) VALUES ('12', '3', 'Egypte', NULL, '2');
INSERT INTO POSSIBILITE (Npossibilites, Nsondage, Texte, Image, Ordre) VALUES ('13', '3', 'Turquie', NULL, '3');
INSERT INTO POSSIBILITE (Npossibilites, Nsondage, Texte, Image, Ordre) VALUES ('14', '3', 'Japon', NULL, '4');
INSERT INTO POSSIBILITE (Npossibilites, Nsondage, Texte, Image, Ordre) VALUES ('15', '3', 'Etats-Unis', NULL, '5');

-- Table : QUESTION
BEGIN TABLE IF EXISTS QUESTION;
create table QUESTION
(Nquestions int(50) not null unique primary key,
Nquestionnaire int(50) not null,
Texte char(255) not null,
Ordre int(50) not null,
foreign key (Nquestionnaire) references QUESTIONNAIRE);
INSERT INTO QUESTION (Nquestions, Nquestionnaire, Texte, Ordre) VALUES ('1', '1', 'Quelle est la couleur du cheval de Napoléon ?', '1');
INSERT INTO QUESTION (Nquestions, Nquestionnaire, Texte, Ordre) VALUES ('2', '1', 'Quelle est la race du cheval de Napoléon ?', '2');
INSERT INTO QUESTION (Nquestions, Nquestionnaire, Texte, Ordre) VALUES ('3', '1', 'Quel est le nom du cheval de Napoléon ?', '3');
INSERT INTO QUESTION (Nquestions, Nquestionnaire, Texte, Ordre) VALUES ('4', '1', 'Quel est le maitre du cheval de Napoléon ?', '4');
INSERT INTO QUESTION (Nquestions, Nquestionnaire, Texte, Ordre) VALUES ('5', '1', 'Quelle est la taille du cheval de Napoléon ?', '5');
INSERT INTO QUESTION (Nquestions, Nquestionnaire, Texte, Ordre) VALUES ('6', '2', 'Combien de roues ?', '1');
INSERT INTO QUESTION (Nquestions, Nquestionnaire, Texte, Ordre) VALUES ('7', '2', 'Combien de fois Eddy Merckx a-t-il remporté le tour de France ?', '2');
INSERT INTO QUESTION (Nquestions, Nquestionnaire, Texte, Ordre) VALUES ('8', '2', 'Quel est le record de vitesse à vélo ?', '3');
INSERT INTO QUESTION (Nquestions, Nquestionnaire, Texte, Ordre) VALUES ('9', '2', 'Combien de vélos ont été vendus en Belgique en 2017?', '4');
INSERT INTO QUESTION (Nquestions, Nquestionnaire, Texte, Ordre) VALUES ('10', '2', 'Quelle est la préposition adéquate ?', '5');

-- Table : QUESTIONNAIRE
BEGIN TABLE IF EXISTS QUESTIONNAIRE;
create table QUESTIONNAIRE
(Nquestionnaire int(50) not null unique primary key,
Identifiant char (255) not null,
Description char(255) not null,
Activite boolean not null,
foreign key (Identifiant) references UTILISATEUR);
INSERT INTO QUESTIONNAIRE (Nquestionnaire, Identifiant, Description, Activite) VALUES ('1', 'Eriko99', 'Napoléon', '1');
INSERT INTO QUESTIONNAIRE (Nquestionnaire, Identifiant, Description, Activite) VALUES ('2', 'Hiboule', 'Vélo', '0');

-- Table : RELATION
BEGIN TABLE IF EXISTS RELATION;
create table RELATION
(Emetteur char(255) not null,
Recepteur char (255) not null,
Relation boolean not null,
unique (Recepteur,Emetteur),
primary key (Emetteur,Recepteur),
foreign key (Emetteur,Recepteur) references UTILISATEUR);
INSERT INTO RELATION (Emetteur, Recepteur, Relation) VALUES ('Eriko99', 'fel98', '1');
INSERT INTO RELATION (Emetteur, Recepteur, Relation) VALUES ('Eriko99', 'margauxgerard', '0');
INSERT INTO RELATION (Emetteur, Recepteur, Relation) VALUES ('fel98', 'margauxgerard', '1');
INSERT INTO RELATION (Emetteur, Recepteur, Relation) VALUES ('Hiboule', 'Moustikman', '1');
INSERT INTO RELATION (Emetteur, Recepteur, Relation) VALUES ('Hiboule', 'gegedu78', '0');
INSERT INTO RELATION (Emetteur, Recepteur, Relation) VALUES ('Hiboule', 'phephe', '1');
INSERT INTO RELATION (Emetteur, Recepteur, Relation) VALUES ('margauxgerard', 'phephe', '1');
INSERT INTO RELATION (Emetteur, Recepteur, Relation) VALUES ('gegedu78', 'phephe', '1');
INSERT INTO RELATION (Emetteur, Recepteur, Relation) VALUES ('gegedu78', 'margauxgerard', '0');
INSERT INTO RELATION (Emetteur, Recepteur, Relation) VALUES ('Moustikman', 'margauxgerard', '0');

-- Table : REPONSE
BEGIN TABLE IF EXISTS REPONSE;
create table REPONSE
(Identifiant char(255) not null,
Nquestions int(50) not null,
Noptions int(50) not null,
unique (Identifiant, Nquestions),
primary key (Identifiant, Nquestions),
foreign key (Nquestions) references QUESTION,
foreign key (Identifiant) references UTILISATEUR);
INSERT INTO REPONSE (Identifiant, Nquestions, Noptions) VALUES ('fel98', '1', '3');
INSERT INTO REPONSE (Identifiant, Nquestions, Noptions) VALUES ('Eriko99', '1', '3');
INSERT INTO REPONSE (Identifiant, Nquestions, Noptions) VALUES ('margauxgerard', '1', '2');

-- Table : SCORE
BEGIN TABLE IF EXISTS SCORE;
create table SCORE
(Identifiant char(255) not null,
Npossibilites int (50) not null,
Score int(50) not null,
unique(Identifiant,Npossibilites),
primary key (Identifiant, Npossibilites),
foreign key (Identifiant, Npossibilites) references POSSIBILITES);
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('fel98', '2', '4');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('margauxgerard', '4', '4');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('fel98', '3', '3');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('margauxgerard', '3', '3');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('margauxgerard', '1', '2');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('margauxgerard', '2', '1');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('fel98', '4', '2');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('fel98', '1', '1');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('gegedu78', '5', '6');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('gegedu78', '7', '5');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('gegedu78', '9', '4');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('gegedu78', '6', '3');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('gegedu78', '8', '2');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('gegedu78', '10', '1');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('Moustikman', '6', '6');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('Moustikman', '8', '5');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('Moustikman', '9', '4');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('Moustikman', '5', '3');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('Moustikman', '7', '2');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('Moustikman', '10', '1');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('phephe', '10', '6');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('phephe', '5', '5');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('phephe', '6', '4');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('phephe', '7', '3');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('phephe', '9', '2');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('phephe', '8', '1');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('Hiboule', '8', '6');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('Hiboule', '5', '5');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('Hiboule', '10', '4');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('Hiboule', '7', '3');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('Hiboule', '9', '2');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('Hiboule', '6', '1');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('fel98', '15', '5');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('fel98', '12', '4');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('fel98', '14', '3');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('fel98', '11', '2');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('fel98', '13', '1');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('Moustikman', '13', '5');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('Moustikman', '11', '4');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('Moustikman', '14', '3');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('Moustikman', '12', '2');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('Moustikman', '15', '1');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('phephe', '15', '5');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('phephe', '11', '4');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('phephe', '13', '3');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('phephe', '14', '2');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('phephe', '12', '1');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('gegedu78', '14', '5');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('gegedu78', '11', '4');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('gegedu78', '15', '3');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('gegedu78', '13', '2');
INSERT INTO SCORE (Identifiant, Npossibilites, Score) VALUES ('gegedu78', '12', '1');

-- Table : SONDAGE
BEGIN TABLE IF EXISTS SONDAGE;
create table SONDAGE 
(Nsondage int(50) not null unique primary key,
Identifiant not null unique ,
Nbrechoix int(50) not null,
Description char(255) not null,
Activite boolean not null,
foreign key (Identifiant) references UTILISATEUR);
INSERT INTO SONDAGE (Nsondage, Identifiant, Nbrechoix, Description, Activite) VALUES ('1', 'fel98', '4', 'On fait quoi ce soir ?', '0');
INSERT INTO SONDAGE (Nsondage, Identifiant, Nbrechoix, Description, Activite) VALUES ('2', 'margauxgerard', '6', 'On mange quoi au souper commu de demain soir ?', '1');
INSERT INTO SONDAGE (Nsondage, Identifiant, Nbrechoix, Description, Activite) VALUES ('3', 'Hiboule', '5', 'On part ou en vacances?', '1');

-- Table : UTILISATEUR
BEGIN TABLE IF EXISTS UTILISATEUR;
create table UTILISATEUR
(Identifiant char(255) not null unique primary key, 
Nom char(255) not null, 
Prénom char(255) not null, 
MDP char(255) not null, 
Mail char(255) not null unique, 
Photo char(255), 
Meilleur_ami char(255),
foreign key (Meilleur_ami) references UTILISATEUR);
INSERT INTO UTILISATEUR (Identifiant, Nom, Prénom, MDP, Mail, Photo, Meilleur_ami) VALUES ('Gerard','margauxgerard','Louvainlaneuve','Margaux', 'margaux.gerard@student.uclouvain.be', 'photodeprofil.png', 'fel98');
INSERT INTO UTILISATEUR (Identifiant, Nom, Prénom, MDP, Mail, Photo, Meilleur_ami) VALUES ('de Patoul', 'fel98', 'Banane', 'Félix', 'felix.depatoul@student.uclouvain.be', NULL, NULL);
INSERT INTO UTILISATEUR (Identifiant, Nom, Prénom, MDP, Mail, Photo, Meilleur_ami) VALUES ('Vandewerve', 'Eriko99', 'Fraise45', 'Eric', 'eric.vandewerve@student.uclouvain.be', NULL, 'fel98');
INSERT INTO UTILISATEUR (Identifiant, Nom, Prénom, MDP, Mail, Photo, Meilleur_ami) VALUES ('Neimry', 'Hiboule', 'Motdepasse', 'Emile', 'emile.neimry@student.uclouvain.be', NULL, NULL);
INSERT INTO UTILISATEUR (Identifiant, Nom, Prénom, MDP, Mail, Photo, Meilleur_ami) VALUES ('Chanteux', 'gegedu78','touxopharm','Géraldine', 'geraldine.chanteux@student.uclouvain.be', NULL, NULL);
INSERT INTO UTILISATEUR (Identifiant, Nom, Prénom, MDP, Mail, Photo, Meilleur_ami) VALUES ('Goffinet','phephe', '#presqueparfaite', 'Ophélie','ophelie.goffinet@student.uclouvain.be', NULL, NULL);
INSERT INTO UTILISATEUR (Identifiant, Nom, Prénom, MDP, Mail, Photo, Meilleur_ami) VALUES ('Dupont','Moustikman', '5140Ligny','Jean', 'jean.dupont@student.uclouvain.be', NULL, NULL);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;

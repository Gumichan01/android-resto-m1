
/*

	Script de création de table

	Utile our le projet

*/


-- Suppression des tables
drop table if exists Ouvrir;
drop table if exists Restaurant;
drop table if exists Periode;

.print "\n======== Creation des tables ========\n"


-- Creation de la table Periode
.print "CREATE TABLE Periode"
create table Periode (

	jour text,
	heure_ouverture_matinale integer,
	heure_fermeture_matinale integer,
	
	heure_ouverture_aprem integer,
	heure_fermeture_aprem integer,

	check(jour in ("Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi",
		"Dimanche"))
);


-- Création de la table Restaurant
.print "CREATE TABLE Restaurant"

create table Restaurant (

	nom text,
	adresse text,
	tel text,
	web text,
	photo text,
	cout integer,
	note integer,
	type_cuisine text,
	latitude double,
	longitude double,
	check(type_cuisine in ("Classique","Végétarien","Italien","Chinois","Japonais","Fast food")),
	check(note between 0 and 5)
);


-- Création de la table Ouvrir
.print "CREATE TABLE Ouvrir"
create table if not exists Ouvrir (

	idresto integer,
	idperiode integer,
	foreign key(idresto) references Restaurant(ROW_ID),
	foreign key(idperiode) references Periode(ROW_ID)
);


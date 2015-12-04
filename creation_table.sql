
-- Suppression des tables
drop table if exists Note;
drop table if exists Periode;

-- Creation de la table Note
create table if not exists Note (

	idnote integer primary key autoincrement,
	valeur integer not null, check(valeur between 0 and 5)
);

-- Creation de la table Periode
create table if not exists Periode (

	idperiode integer primary key autoincrement,
	jour text not null,
	heure_ouverture integer not null,
	heure_fermeture integer not null,
	check(jour in ("Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi",
		"Dimanche")),
check(heure_ouverture between 0 and 23),
check(heure_fermeture between 0 and 23)
);


-- Tests

.print "Test Note negatif"
insert into Note(valeur) values(null);
.print "Test Note positif"
insert into Note(valeur) values(3);
insert into Note(valeur) values(1);
insert into Note(valeur) values(4);
select * from Note;

.print "Test Note negatif"
insert into Periode(jour,heure_ouverture,heure_fermeture) values(null,9,19);
insert into Periode(jour,heure_ouverture,heure_fermeture) values("Lundi",null,22);
insert into Periode(jour,heure_ouverture,heure_fermeture) values("Lundi",8,null);
insert into Periode(jour,heure_ouverture,heure_fermeture) values("Lundi",null,null);
insert into Periode(jour,heure_ouverture,heure_fermeture) values(null,null,0);
insert into Periode(jour,heure_ouverture,heure_fermeture) values(null,8,null);
insert into Periode(jour,heure_ouverture,heure_fermeture) values(null,null,null);
insert into Periode(jour,heure_ouverture,heure_fermeture) values("STOP",8,null);
insert into Periode(jour,heure_ouverture,heure_fermeture) values("Lundi",64,null);
insert into Periode(jour,heure_ouverture,heure_fermeture) values("Lundi",null,64);
.print "Test Note positif"
insert into Periode(jour,heure_ouverture,heure_fermeture) values("Vendredi",10,23);
insert into Periode(jour,heure_ouverture,heure_fermeture) values("Samedi",15,2);
select * from Periode;







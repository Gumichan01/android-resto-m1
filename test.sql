

-- Tests
.print "\n======== Tests ========\n"

insert into Note(valeur) values(3);
insert into Note(valeur) values(1);
insert into Note(valeur) values(4);
select * from Note;

.print "Test Note negatif"
insert or ignore into Periode(jour,heure_ouverture,heure_fermeture) values(null,9,19);
insert or ignore into Periode(jour,heure_ouverture,heure_fermeture) values("Lundi",null,22);
insert or ignore into Periode(jour,heure_ouverture,heure_fermeture) values("Lundi",8,null);
insert or ignore into Periode(jour,heure_ouverture,heure_fermeture) values("Lundi",null,null);
insert or ignore into Periode(jour,heure_ouverture,heure_fermeture) values(null,null,0);
insert or ignore into Periode(jour,heure_ouverture,heure_fermeture) values(null,8,null);
insert or ignore into Periode(jour,heure_ouverture,heure_fermeture) values(null,null,null);
insert or ignore into Periode(jour,heure_ouverture,heure_fermeture) values("STOP",8,null);
insert or ignore into Periode(jour,heure_ouverture,heure_fermeture) values("Lundi",64,null);
insert or ignore into Periode(jour,heure_ouverture,heure_fermeture) values("Lundi",null,64);
.print "Test Note positif"
insert into Periode(jour,heure_ouverture,heure_fermeture) values("Vendredi",10,23);
insert into Periode(jour,heure_ouverture,heure_fermeture) values("Samedi",15,2);
.print ""
.print "SELECT Periode"
select * from Periode;
.print ""

.print "Test GPS negatif"
insert or ignore into gps(latitude,longitude) values(null,0.256);
insert or ignore into gps(latitude,longitude) values(0.256,null);
insert or ignore into gps(latitude,longitude) values(null,null);
.print "Test GPS positif"
insert into gps(latitude,longitude) values(48.949685,72.256147);
insert into gps(latitude,longitude) values(48.902229, 2.302833);
insert into gps(latitude,longitude) values(48.829372,2.381068);
.print ""
.print "SELECT gps"
select * from gps;

insert into Restaurant(nom,adresse,ville,tel,web,photo,cout,type_cuisine,idgps) 
values("Lunch time","Rue de Neuilly","Clichy","01xxxxxxxx","www.toto.dump","photo",6,"Fast food",2);
insert into Restaurant(nom,adresse,ville,tel,web,photo,cout,type_cuisine,idgps) 
values("Pomme de Pain","9-13, Rue Marie André Lagroua Weill Halle","Paris","01xxxxxxxx",
		"www.pommedepain.fr","photo",9,"Classique",3);
.print ""
.print "SELECT Restaurant"
select * from Restaurant;

insert into Avoir(idresto,idnote) values(2,3);
insert into Avoir(idresto,idnote) values(1,3);
insert into Avoir(idresto,idnote) values(1,2);
insert into Avoir(idresto,idnote) values(2,1);
.print ""
.print "SELECT Avoir"
select * from Avoir;

insert into Ouvrir(idresto,idperiode) values(2,2);
insert into Ouvrir(idresto,idperiode) values(1,1);
insert into Ouvrir(idresto,idperiode) values(1,2);
insert into Ouvrir(idresto,idperiode) values(2,1);
.print ""
.print "SELECT Ouvrir"
select * from Ouvrir;
# Projet Backend Java – Test Technique Matawan

Ce projet est une application Java (Spring Boot) développée dans le cadre du test technique.  
Elle permet de créer une équipe avec ses joueurs, la mettre à jour, et retouner une liste de toutes les équipes de la ligue 1.

## Technologies
- Java 17
- Spring Boot 3.3.0
- Maven
- Base de données : H2 embarquée (base légère et rapide pour ce genre de tests)
- JPA / Hibernate (simplifie la gestion des entités Java et des tables SQL)
- Lombok (pour reduire le code)
- JUnit  tests unitaires et intégrations (tests automatisés fiables)


## Installation
- Installer le jar envoyé en attach
- Le démarrer en java 17
- L'application va se démarrer avec le port 8081
- Pour tester les differents 3 services, accéder au swagger http://localhost:8081/swagger-ui/index.html

## Api
- 3 apis sont disponibles:
	création équipe => /equipe (post)
	création équipe => /patch (post) le nom et l'acronym ne peuvent pas être modifier
	retourner la liste de toutes les équipe avec leurs joueurs => equipe (get) avec des param de pagination et ordre

## Code source
- Cloner le code depuis https://github.com/ralphsa95/matwanservices.git
- Vous pouvez aussi dézipper le code source envoyé en zip dans le mail

## Acces à la base
- Après avoir lancer l'appli, accéder à http://localhost:8081/h2-console/
- Pour login: user sa/ sans mot de passe 
              admin/pass123!
- 2 tables sont créées: equipe et joueur
- Les équipes suivantes sont déjà créées:
		OGC Nice	           | OGCN	
		AS Monaco	           | ASM
		Olympique de Marseille | OM	
		Paris Saint-Germain    | PSG
		Olympique Lyonnais	   | OL		
		Lille OSC	           | LOSC	
		
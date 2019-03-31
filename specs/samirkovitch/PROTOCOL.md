## What transport protocol do we use?

TCP

## How does the client find the server (addresses and ports)?

adresse IP : fournie par le serveur

port : 6259

## Who speaks first?

Lors de la connection, le serveur envoie un message de bienvenu. Puis le client envoie une requête au serveur.

## What is the sequence of messages exchanged by the client and the server?

Une fois que le client se connecte sur le serveur, ce dernier envoie un message de bienvenu ainsi que une description de ce que peut faire le client. Puis le serveur se met en attente d'une requête.

Le client envoie un message sous la forme opération 1erNombre 2èmeNombre\n

Le serveur envoie soit le résultat soit une erreur

## What happens when a message is received from the other party?

Le serveur reçoit le message du client. Il doit vérifier l'opération ainsi que les nombres pour effectuer le calcul

Le client affiche l'opération avec son résultat

## What is the syntax of the messages? How we generate and parse them?

Message du serveur : 

 - Si le message du client est correct, envoie : Resultat: 1erNombre opération 2èmeNombre = résultat\n
 
 - Si le message du client est incorrecte      : Erreur de saisie\n

Message du client : opération, 1erNombre, 2èmeNombre\n

opérations: +, -, *, / (d'autres seront ajoutées si le temps le permet)

## Who closes the connection and when?

le serveur ferme la connection lorsqu'il a envoyé le résultat ou une erreur
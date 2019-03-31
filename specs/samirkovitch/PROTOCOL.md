## What transport protocol do we use?

TCP

## How does the client find the server (addresses and ports)?

adresse IP : à définir

port : 6259

## Who speaks first?

Le client envoie une requête au serveur en premier

## What is the sequence of messages exchanged by the client and the server?

Le client envoie un message sous la forme opération, 1erNombre, 2èmeNombre\n

Le serveur envoie soit le résultat soit un erreur

## What happens when a message is received from the other party?

Le serveur reçoit le message du client. Il doit vérifier l'opération ainsi que les nombres pour effectuer le calcul

## What is the syntax of the messages? How we generate and parse them?

Message du serveur : 

 - Si le message du client est correct, envoie : Résultat de 1erNombre opération 2èmeNombre = résultat\n
 
 - Si le message du client est incorrecte      : Erreur de saisie\n

Message du client : opération, 1erNombre, 2èmeNombre\n

opérations: +, -, *, / (d'autres seront ajoutées si le temps le permet)

## Who closes the connection and when?

le serveur ferme la connection lorsqu'il a envoyé le résultat ou une erreur
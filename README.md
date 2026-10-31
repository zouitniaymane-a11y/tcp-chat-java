# tcp-chat-java

Petit projet réalisé en cours de réseau : chat en TCP entre un téléphone et un serveur Java qui tourne dans une machine virtuelle Ubuntu.

## Structure du projet

```
tcp-chat-java/
├── chat-telephone/
│   └── ChatServeur.java
├── .gitignore
└── README.md
```

## Principe

Le serveur tourne dans la VM Ubuntu. Le téléphone s'y connecte à travers le PC, grâce à une redirection de port.

```
Téléphone --Wi-Fi--> PC (port 8080) --NAT VMware--> VM Ubuntu (port 8080)
```

## Prérequis

- Java JDK 11 ou plus récent (`sudo apt install default-jdk` sous Ubuntu)
- VMware avec une VM Ubuntu
- Un téléphone connecté au même Wi-Fi que le PC

## Configuration réseau

1. Dans VMware, ouvrir *Virtual Network Editor*, choisir VMnet8 (NAT), puis *NAT Settings* et ajouter une redirection : port hôte 8080, protocole TCP, adresse IP de la VM, port 8080.
2. Autoriser le port 8080 dans le pare-feu Windows (PowerShell en administrateur) :

```powershell
New-NetFirewallRule -DisplayName "Chat8080" -Direction Inbound -Protocol TCP -LocalPort 8080 -Action Allow
```

3. Relever l'adresse IP du PC avec `ipconfig` (adresse IPv4 de la carte Wi-Fi).

## Lancer le serveur

Dans la VM :

```bash
cd chat-telephone
javac ChatServeur.java
java ChatServeur
```

Le serveur accepte un client à la fois et attend le suivant quand il se déconnecte.

## Connecter le téléphone

Sur iPhone, avec l'application iSH :

```sh
apk add netcat-openbsd
nc <ip-du-pc> 8080
```

Ce qui est tapé sur le téléphone s'affiche dans le terminal de la VM, et inversement.

## Limites

- Un seul client à la fois.
- Pas de chiffrement : les messages passent en clair.
- L'adresse IP du PC peut changer, il faut alors adapter la commande `nc`.

## Auteur

zouitni

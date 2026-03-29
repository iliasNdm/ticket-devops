# TicketFlow — Backend API

> Plateforme de gestion de tickets avec pipeline CI/CD, monitoring et observabilité complète.

---

## Stack technique

**Backend** : Spring Boot 4 · PostgreSQL · Docker · Kubernetes  
**DevOps** : GitHub Actions · ArgoCD · Prometheus · Grafana · Loki

---

## Prérequis

- [Docker](https://www.docker.com/) + Docker Compose
- [Java 21](https://adoptium.net/) (pour lancer sans Docker)
- [Maven](https://maven.apache.org/) (pour lancer sans Docker)

---

## Lancer le projet avec Docker

### 1. Cloner le repo

```bash
git clone https://github.com/iliasNdm/ticketflow.git
cd ticketflow/backend/ticketflow
```

### 2. Configurer les variables d'environnement

```bash
cp .env.example .env
```

Modifier `.env` avec tes valeurs :

```bash
POSTGRES_DB=
POSTGRES_USER=
POSTGRES_PASSWORD=
POSTGRES_PORT=
SPRING_PORT=
SPRING_DATASOURCE_URL=
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=
```

### 3. Lancer l'application

```bash
docker compose up --build
```

L'application démarre automatiquement quand PostgreSQL est prêt.

---

## Lancer en local sans Docker

```bash
mvn clean package -DskipTests
java -jar target/*.jar
```

> Assure-toi d'avoir PostgreSQL installé et configuré localement.

---

## Lancer les tests

```bash
mvn test
```

Les tests utilisent H2 en mémoire — pas besoin de PostgreSQL.

---

## Endpoints disponibles

| Méthode | URL | Description |
|---|---|---|
| `POST` | `/api/tickets` | Créer un ticket |
| `GET` | `/api/tickets` | Lister tous les tickets |
| `GET` | `/api/tickets/{id}` | Détail d'un ticket |
| `PUT` | `/api/tickets/{id}` | Modifier un ticket |
| `PATCH` | `/api/tickets/{id}/status` | Changer le statut |
| `DELETE` | `/api/tickets/{id}` | Supprimer un ticket |

---

## Documentation API

Swagger UI accessible sur :

```
http://localhost:YourRunningPort/swagger-ui.html
```

---

## Health Check

```
http://localhost:YourRunningPort/actuator/health
```

---

## Commandes utiles

```bash
# Lancer en arrière-plan
docker compose up --build -d

# Voir les logs
docker compose logs -f

# Voir les logs d'un service
docker compose logs -f spring-boot

# Arrêter tout
docker compose down

# Arrêter et supprimer les volumes
docker compose down -v
```

---

## Architecture

```
ticketFlow/
├── backend/
│   └── ticketflow/
│       ├── src/
│       │   ├── main/java/com/example/ticketflow/
│       │   │   ├── controllers/
│       │   │   ├── services/
│       │   │   ├── dao/
│       │   │   ├── dtos/
│       │   │   ├── mappers/
│       │   │   └── exceptions/
│       │   └── test/
│       ├── Dockerfile
│       ├── docker-compose.yml
│       └── pom.xml
├── .github/
│   └── workflows/
│       └── backend.yml
└── README.md
```

---

## CI/CD

Chaque push sur `main` déclenche automatiquement :

1. Compilation du projet
2. Exécution des tests unitaires
3. Build et push de l'image Docker

---

## Variables d'environnement

| Variable | Description |
|---|---|
| `POSTGRES_DB` | Nom de la base de données |
| `POSTGRES_USER` | Utilisateur PostgreSQL |
| `POSTGRES_PASSWORD` | Mot de passe PostgreSQL |
| `POSTGRES_PORT` | Port exposé PostgreSQL |
| `SPRING_PORT` | Port exposé Spring Boot |
| `SPRING_DATASOURCE_URL` | URL de connexion à la DB |
| `SPRING_DATASOURCE_USERNAME` | Utilisateur Spring Boot |
| `SPRING_DATASOURCE_PASSWORD` | Mot de passe Spring Boot |

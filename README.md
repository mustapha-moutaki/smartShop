# 🛒 SmartShop - Gestion Commerciale B2B

## 📋 Description du Projet

**SmartShop** est une application web backend REST API de gestion commerciale développée pour **MicroTech Maroc**, un distributeur B2B de matériel informatique basé à Casablaba.

L'application permet de gérer un portefeuille de **650 clients actifs** avec :
- ✅ Un système de fidélité à remises progressives
- 💳 Des paiements fractionnés multi-moyens par facture
- 📊 Une traçabilité complète via historique immuable
- 💰 Une optimisation de la gestion de trésorerie

> **Note importante** : Cette application est **purement backend** (API REST uniquement). Aucune interface graphique n'est fournie. Les tests se font via **Postman** ou **Swagger**.

---

## 🚀 Technologies Utilisées

### Backend
- **Java** 8+
- **Spring Boot** (Framework principal)
- **Spring Data JPA** (ORM / Hibernate)
- **PostgreSQL** / MySQL (Base de données)

### Outils & Bibliothèques
- **Lombok** : Réduction du code boilerplate
- **MapStruct** : Conversion automatique entre entités et DTOs
- **JUnit 5 & Mockito** : Tests unitaires
- **Swagger** : Documentation API
- **Maven** : Gestion des dépendances

### Concepts Java Utilisés
- Stream API
- Lambda Expressions
- Java Time API
- Builder Pattern
- Architecture en couches

---

## 📦 Architecture du Projet

```
src/main/java/com/smartshop/
│
├── controller/          # Endpoints REST
├── service/             # Logique métier
├── repository/          # Accès données (JPA)
├── entity/              # Entités JPA
├── dto/                 # Data Transfer Objects
├── mapper/              # MapStruct mappers
├── enums/               # Enums (Roles, Status, Tiers...)
├── exception/           # Gestion centralisée des erreurs
└── config/              # Configuration Spring
```

### Architecture en Couches
```
Controller → Service → Repository → Database
     ↕          ↕
   DTO      Entity
```

---

## 🗄️ Modèle de Données

### Entités Principales

#### 👤 User
- `id`, `username`, `password`, `role` (ADMIN/CLIENT)

#### 🏢 Client
- `id`, `nom`, `email`, `niveau_fidelite`
- `total_orders`, `total_spent`
- `first_order_date`, `last_order_date`

#### 📦 Product
- `id`, `nom`, `prix_unitaire`, `stock_disponible`

#### 🛍️ Order (Commande)
- `id`, `client`, `date`, `status`
- `sous_total`, `remise`, `tva`, `total`
- `code_promo`, `montant_restant`

#### 📄 OrderItem
- `id`, `produit`, `quantite`, `prix_unitaire`, `total_ligne`

#### 💳 Payment (Paiement)
- `id`, `commande`, `numero_paiement`
- `montant`, `type_paiement`, `statut`
- `date_paiement`, `date_encaissement`

---

## 🎯 Fonctionnalités Principales

### 1️⃣ Gestion des Clients
- ✅ Création, consultation, mise à jour
- 📊 Suivi automatique : nombre commandes, montant cumulé
- 📅 Dates première/dernière commande
- 📜 Historique complet des commandes

### 2️⃣ Système de Fidélité Automatique

| Niveau | Conditions | Remise | Montant Minimum |
|--------|------------|--------|-----------------|
| **BASIC** | Par défaut | 0% | - |
| **SILVER** | 3 commandes OU 1,000 DH | 5% | ≥ 500 DH |
| **GOLD** | 10 commandes OU 5,000 DH | 10% | ≥ 800 DH |
| **PLATINUM** | 20 commandes OU 15,000 DH | 15% | ≥ 1,200 DH |

### 3️⃣ Gestion des Produits
- ➕ Ajout, modification, suppression (soft delete)
- 🔍 Consultation avec filtres et pagination
- 📦 Gestion du stock

### 4️⃣ Gestion des Commandes
- 🛒 Création multi-produits avec quantités
- ✔️ Validation stock disponible
- 💰 Application remises cumulatives (fidélité + promo)
- 🧮 Calcul automatique : Sous-total → Remise → TVA (20%) → Total TTC
- 🔄 Gestion des statuts : PENDING → CONFIRMED / CANCELED / REJECTED

### 5️⃣ Paiements Multi-Moyens

| Moyen | Limite | Particularités |
|-------|--------|----------------|
| **ESPÈCES** | 20,000 DH max | Immédiat |
| **CHÈQUE** | Illimité | Différé (échéance) |
| **VIREMENT** | Illimité | Référence banque |

> Une commande peut être payée en **plusieurs fois** avec différents moyens. Elle doit être **totalement payée** (montant_restant = 0) avant validation par l'ADMIN.

---

## 🔐 Authentification & Autorisations

### Type d'Authentification
- **HTTP Session** (login/logout)
- ❌ Pas de JWT
- ❌ Pas de Spring Security

### Rôles & Permissions

#### 👨‍💼 ADMIN (Employé MicroTech)
- ✅ CRUD complet sur toutes les ressources
- ✅ Voir tous les clients
- ✅ Créer commandes pour n'importe quel client
- ✅ Valider/Annuler les commandes

#### 🏢 CLIENT (Entreprise cliente)
- ✅ Consulter son propre profil
- ✅ Voir son historique de commandes
- ✅ Consulter la liste des produits (lecture seule)
- ❌ Aucune modification possible
- ❌ Ne peut pas voir les données des autres clients

---

## 📊 Enums du Système

### UserRole
- `ADMIN` : Employé MicroTech
- `CLIENT` : Entreprise cliente

### CustomerTier (Niveau Fidélité)
- `BASIC` : Pas de remise
- `SILVER` : 5% à partir de 500 DH
- `GOLD` : 10% à partir de 800 DH
- `PLATINUM` : 15% à partir de 1,200 DH

### OrderStatus
- `PENDING` : En attente validation
- `CONFIRMED` : Validée par ADMIN
- `CANCELED` : Annulée manuellement
- `REJECTED` : Refusée (stock insuffisant)

### PaymentStatus
- `EN_ATTENTE` : Non encaissé
- `ENCAISSÉ` : Reçu
- `REJETÉ` : Chèque sans provision

---

## 🔧 Installation & Configuration

### Prérequis
- Java 8+
- Maven 3.6+
- PostgreSQL 12+ ou MySQL 8+
- Postman ou Swagger UI

### 1. Cloner le projet
```bash
git clone https://github.com/votre-username/smartshop.git
cd smartshop
```

### 2. Configuration Base de Données

Modifier `src/main/resources/application.properties` :

```properties
# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/smartshop_db
spring.datasource.username=votre_user
spring.datasource.password=votre_password

# MySQL (alternative)
# spring.datasource.url=jdbc:mysql://localhost:3306/smartshop_db
# spring.datasource.username=root
# spring.datasource.password=password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Session Configuration
server.servlet.session.timeout=30m
```

### 3. Build & Run
```bash
# Compiler le projet
mvn clean install

# Lancer l'application
mvn spring-boot:run
```

L'application démarre sur : `http://localhost:8080`

---

## 📡 Endpoints API Principaux

### 🔐 Authentification
```http
POST   /api/v1/auth/login
POST   /api/v1/auth/logout
GET    /api/v1/auth/current-user
```

### 👥 Clients
```http
GET    /api/v1/clients
GET    /api/v1/clients/{id}
POST   /api/v1/clients
PUT    /api/v1/clients/{id}
GET    /api/v1/clients/{id}/orders
```

### 📦 Produits
```http
GET    /api/v1/products
GET    /api/v1/products/{id}
POST   /api/v1/products
PUT    /api/v1/products/{id}
DELETE /api/v1/products/{id}
```

### 🛍️ Commandes
```http
GET    /api/v1/orders
GET    /api/v1/orders/{id}
POST   /api/v1/orders
PUT    /api/v1/orders/{id}/confirm
PUT    /api/v1/orders/{id}/cancel
GET    /api/v1/orders/my-history
```

### 💳 Paiements
```http
POST   /api/v1/payments
GET    /api/v1/payments/order/{orderId}
PUT    /api/v1/payments/{id}/encaisse
```

> **Documentation complète** : Accédez à Swagger UI sur `http://localhost:8080/swagger-ui.html`

---

## 🧪 Tests

### Exécuter les tests
```bash
mvn test
```

### Couverture des tests
- Tests unitaires avec **JUnit 5**
- Mock des dépendances avec **Mockito**
- Tests des services et repositories

---

## ⚠️ Règles Métier Critiques

### Validation Stock
```
quantité_demandée ≤ stock_disponible
```

### Calcul Commande
```
Sous-total HT = Σ (prix × quantité)
Montant HT après remise = Sous-total - Remise totale
TVA (20%) = Montant HT après remise × 0.20
Total TTC = Montant HT après remise + TVA
```

### Codes Promo
- Format : `PROMO-XXXX` (4 caractères alphanumériques)
- Remise additionnelle de **5%**
- Usage unique possible

### Paiements
- Limite ESPÈCES : **20,000 DH max** (Art. 193 CGI)
- Une commande doit être **totalement payée** avant validation

---

## 🚨 Gestion des Erreurs

### Codes HTTP Retournés
| Code | Signification |
|------|---------------|
| `200` | Succès |
| `201` | Créé |
| `400` | Erreur de validation |
| `401` | Non authentifié |
| `403` | Accès refusé |
| `404` | Ressource inexistante |
| `422` | Règle métier violée |
| `500` | Erreur interne |

### Format de Réponse d'Erreur
```json
{
  "timestamp": "2025-12-02T10:30:00",
  "status": 422,
  "error": "Unprocessable Entity",
  "message": "Stock insuffisant pour le produit X",
  "path": "/api/v1/orders"
}
```

---

## 📝 Exemple d'Utilisation

### Scénario : Créer une commande avec paiement fractionné

#### 1. Login ADMIN
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

#### 2. Créer une commande
```http
POST /api/v1/orders
Content-Type: application/json

{
  "clientId": 1,
  "items": [
    { "productId": 5, "quantity": 2 },
    { "productId": 8, "quantity": 1 }
  ],
  "promoCode": "PROMO-2025"
}
```

#### 3. Ajouter un paiement partiel
```http
POST /api/v1/payments
Content-Type: application/json

{
  "orderId": 15,
  "amount": 5000,
  "paymentType": "ESPECES",
  "reference": "RECU-001"
}
```

#### 4. Valider la commande (quand totalement payée)
```http
PUT /api/v1/orders/15/confirm
```

---

## 👥 Contributeurs

- **Nom Prénom** - Développeur Backend
- **MicroTech Maroc** - Client

---

## 📄 Licence

Ce projet est développé dans un cadre pédagogique pour **YouCode**.

---

## 📞 Contact & Support

Pour toute question ou support :
- 📧 Email : votre.email@example.com
- 🔗 GitHub : [github.com/mustapha-moutaki](https://github.com/mutapha-moutaki)

---

## 🎯 Roadmap Future

- [ ] Ajout de rapports statistiques avancés
- [ ] Notifications email automatiques
- [ ] Export des factures en PDF
- [ ] Tableau de bord analytics
- [ ] API webhooks pour intégrations tierces

---

**Développé avec ☕ et 💻 par [Mustapha MOUTAKI]**
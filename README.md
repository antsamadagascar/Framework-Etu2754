# Framework MVC Java

Un framework MVC léger et simple pour le développement d'applications web Java, offrant une approche moderne basée sur les annotations.

## 🚀 Fonctionnalités

- **Front Controller** : Point d'entrée unique pour toutes les requêtes
- **Annotations** : Configuration basée sur les annotations (@Controller, @Url, @Get, @Post, etc.)
- **Injection de dépendances** : Gestion automatique des paramètres et objets
- **Gestion de session** : Sessions simplifiées avec MySession
- **Validation** : Validation automatique des formulaires avec annotations
- **Authentification** : Système d'authentification flexible par rôles
- **API REST** : Support JSON pour les API REST
- **Upload de fichiers** : Gestion native des uploads
- **Gestion d'erreurs** : Système complet de gestion d'exceptions

## 📦 Installation

1. Ajoutez le framework.jar à votre projet Java
2. Configurez le FrontController dans votre `web.xml`
3. Créez vos contrôleurs avec les annotations appropriées

## 🔧 Configuration

### web.xml
```xml
<servlet>
    <servlet-name>FrontController</servlet-name>
    <servlet-class>controller.FrontController</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>FrontController</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

2. Créez vos contrôleurs avec les annotations

## 📖 Utilisation

### 1. Contrôleur simple

```java
@Controller("HomeController")
public class HomeController {
    
    @Url("/accueil")
    @Get
    public ModelView accueil() {
        ModelView mv = new ModelView();
        mv.setUrl("WEB-INF/pages/accueil.jsp");
        mv.add("titre", "Bienvenue");
        mv.add("message", "Hello World!");
        return mv;
    }
}
```

### 2. Utilisation de ModelView

```java
@Controller("ProductController")
public class ProductController {
    
    @Url("/produits")
    @Get
    public ModelView listeProduits() {
        ModelView mv = new ModelView();
        
        // Données à envoyer à la vue
        mv.add("titre", "Liste des produits");
        mv.add("produits", Arrays.asList("Laptop", "Souris", "Clavier"));
        mv.add("nombre", 3);
        
        // Page à afficher
        mv.setUrl("WEB-INF/pages/produits.jsp");
        return mv;
    }
}
```

### 3. Récupérer des paramètres

```java
@Controller("UserController")
public class UserController {
    
    @Url("/utilisateur")
    @Get
    public ModelView voirUtilisateur(@Param(name = "id") int id) {
        ModelView mv = new ModelView();
        mv.add("userId", id);
        mv.add("nom", "Jean Dupont");
        mv.setUrl("WEB-INF/pages/user.jsp");
        return mv;
    }
    
    @Url("/recherche")
    @Post
    public ModelView rechercher(@Param(name = "mot") String motCle,
                              @Param(name = "age") int age) {
        ModelView mv = new ModelView();
        mv.add("recherche", motCle);
        mv.add("age", age);
        mv.setUrl("WEB-INF/pages/resultats.jsp");
        return mv;
    }
}
```

### 4. Utilisation de MySession

```java
@Controller("SessionController")
public class SessionController {
    
    @Url("/sauvegarder")
    @Post
    public ModelView sauvegarder(@Param(name = "nom") String nom, MySession session) {
        // Ajouter en session
        session.add("nomUtilisateur", nom);
        session.add("dateConnexion", new Date());
        
        ModelView mv = new ModelView();
        mv.add("message", "Données sauvegardées !");
        mv.setUrl("WEB-INF/pages/confirmation.jsp");
        return mv;
    }
    
    @Url("/profil")
    @Get
    public ModelView profil(MySession session) {
        // Récupérer depuis la session
        String nom = (String) session.get("nomUtilisateur");
        Date dateConnexion = (Date) session.get("dateConnexion");
        
        ModelView mv = new ModelView();
        mv.add("nom", nom);
        mv.add("dateConnexion", dateConnexion);
        mv.setUrl("WEB-INF/pages/profil.jsp");
        return mv;
    }
    
    @Url("/deconnexion")
    @Get
    public ModelView deconnexion(MySession session) {
        // Supprimer de la session
        session.delete("nomUtilisateur");
        session.delete("dateConnexion");
        
        ModelView mv = new ModelView();
        mv.setUrl("WEB-INF/pages/accueil.jsp");
        return mv;
    }
}
```

### 5. Formulaires avec @ModelParam

```java
// Modèle
public class Utilisateur {
    private String nom;
    private String email;
    private int age;
    
    // getters et setters...
}

@Controller("FormController")
public class FormController {
    
    @Url("/inscription")
    @Get
    public ModelView formulaireInscription() {
        ModelView mv = new ModelView();
        mv.setUrl("WEB-INF/pages/inscription.jsp");
        return mv;
    }
    
    @Url("/inscription")
    @Post
    public ModelView traiterInscription(@ModelParam Utilisateur user) {
        ModelView mv = new ModelView();
        
        // L'objet user est automatiquement rempli !
        mv.add("nom", user.getNom());
        mv.add("email", user.getEmail());
        mv.add("age", user.getAge());
        
        mv.setUrl("WEB-INF/pages/confirmation.jsp");
        return mv;
    }
}
```

### 6. API REST (JSON)

```java
@Controller("ApiController")
public class ApiController {
    
    @Url("/api/users")
    @Get
    @RestApi
    public List<String> getUsers() {
        return Arrays.asList("Alice", "Bob", "Charlie");
    }
    
    @Url("/api/user")
    @Post
    @RestApi
    public Map<String, Object> createUser(@Param(name = "nom") String nom) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("nom", nom);
        result.put("id", 123);
        return result;
    }
}
```

### 7. Authentification simple

```java
@Controller("AuthController")
@Authentification  // Toute la classe nécessite une authentification
public class AuthController {
    
    @Url("/dashboard")
    @Get
    public ModelView dashboard(MySession session) {
        Utilisateur user = (Utilisateur) session.get("authUser");
        
        ModelView mv = new ModelView();
        mv.add("nomUtilisateur", user.getNom());
        mv.setUrl("WEB-INF/pages/dashboard.jsp");
        return mv;
    }
}

@Controller("AdminController")
@Authentification("admin")  // Nécessite le rôle "admin"
public class AdminController {
    
    @Url("/admin")
    @Get
    public ModelView panneauAdmin() {
        ModelView mv = new ModelView();
        mv.setUrl("WEB-INF/pages/admin.jsp");
        return mv;
    }
}
```

### 8. Validation des formulaires

```java
// Modèle avec validation
public class Contact {
    @NotNull(message = "Le nom est obligatoire")
    private String nom;
    
    @ValidEmail(message = "Email invalide")
    private String email;
    
    // getters et setters...
}

@Controller("ContactController")
public class ContactController {
    
    @Url("/contact")
    @Post
    public ModelView envoyerMessage(@ModelParam Contact contact, MySession session) {
        ModelView mv = new ModelView();
        
        try {
            // Validation automatique
            validator.validateObject(contact);
            
            // Si pas d'erreur
            session.add("success", "Message envoyé !");
            mv.setUrl("WEB-INF/pages/contact.jsp");
            
        } catch (ValidationException e) {
            // En cas d'erreur de validation
            e.getValidationErrors().forEach((field, error) -> {
                mv.add(field + "Error", error);
            });
            mv.add("contact", contact);  // Garder les données
            mv.setUrl("WEB-INF/pages/contact.jsp");
        }
        
        return mv;
    }
}
```

## 🔧 Annotations principales

- **@Controller("nom")** : Marque une classe comme contrôleur
- **@Url("/chemin")** : Définit l'URL d'accès
- **@Get / @Post** : Méthode HTTP
- **@Param(name = "nom")** : Récupère un paramètre
- **@ModelParam** : Remplit automatiquement un objet
- **@RestApi** : Retourne du JSON
- **@Authentification** : Nécessite une connexion
- **@Authentification("role")** : Nécessite un rôle spécifique

## ✅ Classes utiles

- **ModelView** : Pour envoyer des données à la vue
  - `mv.setUrl("page.jsp")` : Définit la page
  - `mv.add("cle", valeur)` : Ajoute des données

- **MySession** : Pour gérer la session
  - `session.add("cle", valeur)` : Ajouter
  - `session.get("cle")` : Récupérer
  - `session.delete("cle")` : Supprimer

Le framework gère automatiquement le routage, l'injection des paramètres, et la navigation.

## 🔨 Développé avec

Le framework a été développé en suivant une approche agile avec 16 sprints :

- **Sprint 0** : FrontController et configuration web.xml
- **Sprint 1** : Annotations et scan automatique
- **Sprint 2-3** : Routing et invocation automatique
- **Sprint 4** : Communication Controller-View
- **Sprint 5** : Gestion d'exceptions
- **Sprint 6-7** : Gestion des formulaires et injection d'objets
- **Sprint 8** : Gestion de session
- **Sprint 9** : API REST JSON
- **Sprint 10** : Annotations d'URL avancées
- **Sprint 11** : Gestion d'erreurs HTTP
- **Sprint 12** : Upload de fichiers
- **Sprint 13-14** : Validation de formulaires
- **Sprint 15-16** : Système d'authentification complet

## 🤝 Contribution

Ce framework est conçu pour être extensible. N'hésitez pas à contribuer en ajoutant de nouvelles fonctionnalités ou en améliorant l'existant.

## 📄 Licence

Ce projet est sous licence libre pour usage éducatif et commercial.
# Modern Tetris Android

Application Android native complète du jeu Modern Tetris avec toutes les fonctionnalités de la version web.

## 🎮 Fonctionnalités

### Modes de Jeu (9 modes)
- **Classic** : Mode Tetris traditionnel sans fin
- **Sprint** : Complétez 40 lignes le plus rapidement possible
- **Marathon** : Survivez 150 lignes avec difficulté croissante
- **Zen** : Mode relaxant avec statistiques détaillées
- **Puzzle** : 150 puzzles avec système de hints AI
- **Battle** : Affrontez une IA avec 5 niveaux de difficulté
- **Power-Up** : Mode classique avec 8 power-ups uniques
- **Battle 2P** : Mode 2 joueurs local en split-screen
- **Daily Challenge** : Défis quotidiens avec modificateurs

### Système de Progression
- 100 niveaux avec système XP
- 10 rangs (Novice à Eternal)
- Déblocables : thèmes, musiques, styles de pièces, effets visuels

### Achievements & Trophées
- 30+ achievements dans 8 catégories
- Système de trophées Bronze/Argent/Or/Platine
- Récompenses XP pour la progression

### Features Android
- **Google Play Games** : Classements et achievements en ligne
- **Mode hors-ligne** : Jouez sans connexion internet
- **Sauvegarde cloud** : Synchronisation entre appareils
- **Widgets** : Affichage du score sur l'écran d'accueil
- **Android TV** : Support des téléviseurs et manettes
- **Notifications** : Rappels pour les défis quotidiens
- **Thème dynamique** : Material You avec couleurs du wallpaper

## 📱 Configuration Requise

- Android 7.0 (API 24) ou supérieur
- 100 MB d'espace disponible
- RAM : 2 GB minimum recommandé
- Connexion internet pour les fonctionnalités en ligne

## 🚀 Installation

### Depuis Google Play Store
```
Recherchez "Modern Tetris" sur le Play Store
Installez l'application
```

### Installation APK manuelle
1. Téléchargez le fichier APK depuis les releases GitHub
2. Activez "Sources inconnues" dans les paramètres Android
3. Installez l'APK

### Build depuis les sources

#### Prérequis
- Android Studio Arctic Fox ou plus récent
- JDK 17
- Android SDK 34
- Gradle 8.2

#### Étapes
```bash
# Cloner le repository
git clone https://github.com/your-repo/tetris-android.git
cd tetris-android/android-app

# Build debug
./gradlew assembleDebug

# Build release
./gradlew assembleRelease

# Installer sur un appareil connecté
./gradlew installDebug
```

## 🎯 Architecture

### Stack Technique
- **Langage** : Kotlin
- **UI** : Jetpack Compose
- **Architecture** : MVVM avec Clean Architecture
- **DI** : Hilt
- **Database** : Room
- **Réseau** : Retrofit + OkHttp
- **Coroutines** : Pour l'asynchrone
- **Navigation** : Navigation Component

### Structure du Projet
```
app/
├── src/main/java/com/tetris/modern/
│   ├── game/          # Moteur de jeu
│   ├── modes/         # Modes de jeu
│   ├── ui/            # Composants UI
│   ├── data/          # Room entities et DAOs
│   ├── network/       # API REST
│   ├── audio/         # Gestion audio
│   └── utils/         # Utilitaires
└── src/main/res/      # Resources
```

## 🎨 Personnalisation

### Thèmes Déblocables
- Cyberpunk (défaut)
- Rétro 8-bit
- Nature
- Minimal
- Galaxy
- Matrix
- Rainbow

### Musiques
- Chiptune classique
- Synthwave
- Orchestral
- Jazz
- Metal
- Lo-fi

### Styles de Pièces
- Glass
- Pixel
- Hologram
- Crystal
- Animated

## 🔧 Configuration API Backend

Le jeu se connecte au backend PHP existant. Configurez l'URL dans `NetworkConfig.kt`:

```kotlin
object NetworkConfig {
    const val BASE_URL = "https://your-server.com/api/"
}
```

## 🎮 Contrôles

### Touch Controls
- **Swipe gauche/droite** : Déplacer la pièce
- **Swipe bas** : Soft drop
- **Swipe haut rapide** : Hard drop
- **Tap** : Rotation horaire
- **Double tap** : Rotation anti-horaire
- **Long press** : Hold piece

### Manette de Jeu
- **D-Pad** : Déplacements
- **A** : Rotation horaire
- **B** : Rotation anti-horaire
- **X** : Hold
- **Y** : Hard drop

## 📊 Performance

### Optimisations
- Rendu 60 FPS stable
- Gestion mémoire optimisée
- Battery-friendly avec suspension intelligente
- Cache des assets pour chargement rapide
- Compression des données sauvegardées

### Tests
```bash
# Tests unitaires
./gradlew test

# Tests instrumentés
./gradlew connectedAndroidTest

# Analyse de code
./gradlew lint
```

## 🐛 Debug

### Logs
Les logs sont gérés par Timber. En mode debug, tous les logs sont affichés dans Logcat.

### ProGuard
Le fichier `proguard-rules.pro` est configuré pour préserver les classes nécessaires en release.

## 📝 Licences

- Code source : MIT License
- Assets graphiques : Creative Commons
- Musiques : Licences individuelles

## 🤝 Contribution

1. Fork le projet
2. Créez une branche feature (`git checkout -b feature/AmazingFeature`)
3. Commit vos changements (`git commit -m 'Add AmazingFeature'`)
4. Push la branche (`git push origin feature/AmazingFeature`)
5. Ouvrez une Pull Request

## 📧 Support

- Issues GitHub : https://github.com/your-repo/tetris-android/issues
- Email : support@moderntetris.com

## 🎯 Roadmap

### Version 3.2.0 (Q1 2025)
- [ ] Multijoueur en ligne temps réel
- [ ] Mode AR avec ARCore
- [ ] Tournois hebdomadaires
- [ ] Replay system

### Version 3.3.0 (Q2 2025)
- [ ] Éditeur de puzzles communautaire
- [ ] Intégration Twitch
- [ ] Mode Marathon infini
- [ ] Support Wear OS

## 📈 Analytics

L'application collecte des données anonymes pour améliorer l'expérience :
- Modes joués
- Scores moyens
- Temps de jeu
- Crashes (via Crashlytics)

Ces données peuvent être désactivées dans les paramètres.

## ⚡ Commandes Utiles

```bash
# Clean build
./gradlew clean build

# Generate signed APK
./gradlew bundleRelease

# Check dependencies updates
./gradlew dependencyUpdates

# Profile app performance
./gradlew :app:assembleDebug --profile
```

---

**Version actuelle : 3.1.1**  
**Dernière mise à jour : Septembre 2025**
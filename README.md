# Explorateurs Autonomes

La portée de ce projet se concentre sur la mise en oeuvre d'une application permettant de simuler l'exploration en temps réel d'une carte par des explorateurs intelligents et communiquants. 

L'utilisateur pourra préparer une équipe d'explorateurs en définissant une stratégie d'exploration et un équipement avant le démarrage de la simulation.

La configuration optimale de l'équipe doit permettre de trouver rapidement tous les trésors en minimisant les pertes humaines. En fin de simulation, l'utilisateur aura accès à diverses statistiques telles que le nombres de trésors découverts ou encore le nombre d'explorateurs n'ayant pas survécu.


## Fonctionnalités du projet:

**Menu graphique de configuration:**
  Ce menu de configuration offrira à l'utilisateur une interface conviviale et visuellement intuitive pour paramétrer divers éléments avant le lancement de l'exploration. Il englobera la sélection de l'équipement, la détermination de la stratégie d'exploration à adopter et la sélection du nombre d'explorateurs, en respectant un budget prédéfini. Cette diversité d'équipements permettra une personnalisation poussée, accordant à l'utilisateur un contrôle accru sur la composition et les compétences de son équipe.

**Règles:**
Des règles doivent être définies pour établir des stratégies d'exploration efficaces. Ces règles régissent les agissements des explorateurs face à certaines situations. Elles auront trait à l'exploration, à la récupération des trésors et aux combats contre les ennemis qui les protègent.

**Protocole de communication:**
L'intégration d'un protocole de communication offre une solution adéquate pour transmettre des informations entre les explorateurs. Dans le contexte d'une attaque imminente, un explorateur peut émettre un signal sonore distinctif, alertant ses coéquipiers. Ce signal sonore déclenche une réaction immédiate de l'équipe. En parallèle, l'utilisation de signaux visuels, tels que des feux ou des dispositifs lumineux, peut renforcer cette communication d'urgence.

**Stratégies:**
Une stratégie est un ensemble cohérent de règles que les explorateurs doivent suivre pour mener à bien leur mission. 

**Fenêtre de statistiques:**
L'intégration d'une fenêtre de statistiques en fin de simulation offre une rétroaction détaillée sur la performance de l'équipe à la fin de chaque exploration. La fenêtre affiche des données clés telles que le nombre de trésors amassés, le nombre de survivants, le nombre de pas effectués et le nombre d'ennemis tués. Ces données permettent d'évaluer l'efficacité des stratégies d'exploration et de l'équipement. Des graphiques peuvent également être inclus pour une représentation plus visuelle des performances. Cette rétroaction offre aux utilisateurs la possibilité d'optimiser leurs approches et d'ajuster leurs choix.

**Interactions des explorateurs et équipement:**
Au cours de la simulation, les explorateurs seront amenés à interagir avec leur environnement. Leur progression s'opérera différemment en fonction des obstacles qu'ils rencontrent. Par ailleurs, il utiliserons divers équipements tels qu'une arme pour faciliter les phases de combat ou des talkies-walkies pour leur permettre de communiquer plus rapidement entre eux, accélérant ainsi l'exécution des protocoles de communication.

\subsubsection*{Protection des trésors par des ennemis:}
Les trésors seront protégés par des ennemis qui attaqueront les explorateurs. Lorsque ces derniers rencontrerons un ennemi, ils entreront dans une phase de combat autonome au cours de laquelle ils prennent le risque de perdre de la vie. Si l'explorateur sort victorieux du combat, il peut récupérer le trésor. En cas de défaite, il n'est plus en capacité de continuer l'exploration. Les ennemis peuvent être plus ou moins fort.

**Génération d'une carte aux trésors:**
La fonctionnalité de génération de carte de trésors vise à créer des environnements variés pour les explorateurs autonomes. La carte intégrera différents types de terrain tels que des forêts, des plaines et des zones rocheuses, tout en introduisant des obstacles variés comme des arbres et des rivières. La diversité d'environnement crée un terrain où les explorateurs doivent constamment ajuster leurs approches pour surmonter des défis variés.

**Informations en temps réel:**
Il sera possible de suivre en temps réel l'état de l'équipe et l'avancée de l'exploration. Un encart indiquera tout au long de la simulation la santé des explorateurs, le nombre de trésors en leur possession, les zones explorées etc. Ces divers informations permettront de savoir si la stratégie choisie est efficace mais aussi de connaître la progression des explorateurs.

**Multithreading:**
 Le multithreading constitue un aspect technique crucial du projet. En utilisant Java SE avec une approche de programmation multithreading, l'objectif est d'exploiter efficacement les ressources du système pour gérer des processus parallèles. Cette approche accroît la réactivité de l'application pendant l'exploration en équipe, permettant par exemple la gestion simultanée de l'exécution des stratégies des explorateurs, de l'accès à des données partagées entre les explorateurs et de la mise à jour en temps réel des statistiques de l'exploration. L'utilisation du multithreading garantit une exécution fluide et efficiente de l'application, offrant ainsi une expérience utilisateur optimale.

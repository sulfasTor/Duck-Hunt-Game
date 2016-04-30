**************************************************************************
*       __           ___                                    _____        *
*      |  \  |    | |   |   |   /    |     | |     | |\   |   |          *
*      |   | |    | |       |  /     |_____| |     | | \  |   |          *
*      |   | |    | |       |  \     |     | |     | |  \ |   |          *
*      |__/  |____| |___|   |   \    |     | |_____| |   \|   |          *
*                                                                        *
**************************************************************************
By sulfasTor.


le fichier Archivo.txt contient les donnees des joueurs et les scores.

=====Definition des classes=====
Cible:
-Classe de base pour les cibles
-les dessiner, savoir si ca deborde et tirer dessus

CibleMobile: extends Cible
-Ajoute du mouvement

CursorPanel:
-Classe qui dessine les armes, donne le nom des armes, indique la nature de l'arme(automatique ou non)

GunChangerListener:
-Ecouteur du clavier pour changer les armes 

Index: MAIN
-Classe du menu

LogIn:
-Classe qui authentifie l'utilisateur déjà enregistré et donne les scores

SaveScore:
-Classe qui recupere le score et l'ecrit sur le fichier Archivo.txt

Valida:
-Classe qui valide les donnees rentrees dans les formulaires

NeWindow:
-Classe avec l fenetre du jeu et contient aussi le coeur du jeu

nextClass:
-Ecouteur du timer second de la SurfaceJeu

nextMillisClass:
-Idem en milliseconds

OpenIndex:
-Ecouteur du bouton retour pour quitter la fenetre de niveau et aller au menu

OpenIndexIn:
-Ecouteur du bouton retour pour quitter la fenetre de scoreu et aller au menu

OpenIndexUp:
-Ecouteur du bouton retour pour quitter la fenetre d'enregistrement et aller au menu

OpenRegister:
-Ecouteur du bouton 'Sign Up' du menu

OpenWindow:
-Ecouteur du bouton "QuickPlay"

Register:
-Class qui fait l'enregistrement d'utilisateur (username, mail, password)

Score:
-ecouteur du bouton de renvoi à la fenêtre principale

ShootListener:
-Ecouteur de la click de la souris

SignIn:
-Fenetre pour se logger et voir son score

SignUp:
-Fenetre pour s'enregistrer

SourisListener:
-Ecouteur des click maintenus de la souris

SurfaceJeu:
-Classe qui dessine les cibles, les armes et tout qui est en lien avec cela

TypeMouvement:
-Enum qui decrit les constantes des mouvements





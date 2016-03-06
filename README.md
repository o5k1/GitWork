## Introduzione
Questo repository contiene una serie di tool creati per migliorare l'integrazione Teamwork-GitHub. Tali tool sono stati creati in tempi ristretti per agevolare il lavoro di gruppo nell'ambito del corso di Ingegneria del software, previsto dal CdS in Informatica dall'Università degli Studi di Padova.
Nessuna garanzia sull'affidabilità del contenuto di questo repository.

## GitWork
Si tratta di un programma JAVA da riga di comando che permette di scegliere un task di Teamwork e trasformare tutti i suoi sub-task in issue di GitHub.
Dato un task di Teamwork, viene creata una issue sul repository GitHub (hard-coded) avente:
- titolo: corrisponde al titolo del task da cui deriva;
- descrizione: corrisponde alla descrizione del task da cui deriva;
- label *ToDo*;
- Commento: contiende l'id del task da cui deriva, utile per gli *hook*.

### Per iniziare
Aprire il file GitWork.java e modificare le seguenti variabili: 
- **GH_user**;
- **GH_psw**;
- **GH_target_repo_username**; 
- **GH_target_repo_name** .

## Hooks
### Git Hooks
post-commit.sample è un [Git hook](https://git-scm.com/book/it/v2/Customizing-Git-Git-Hooks) che, dato un messaggio di commit che contenga ```#n_issue``` (dove *n_issue* è il numero di una issue presente nel repository) e ```£toVerify```, cacella automaticamente la label 'Working' della issue *n_issue* ed aggiunge la label 'toVerify'. 

### Per iniziare
Aprire il file post-commit.sample e modificare le seguenti variabili: 
- **usr**;
- **psw**;
- **target_repo_owner**; 
- **target_repo_name** .

Successivamente rimuovere l'estensione .sample ed inserire post-commit nella cartella **.git/hooks** del repository locale che si vuole dotare di tale funzionalità.

Questo hook utilizza la libreria cURL per effettuare chiamate HTTP, effettuare il [download](https://curl.haxx.se/download.html) ed installarla prima di eseguire l'hook.

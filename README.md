## Introduzione
Questo repository contiene una serie di tool creati per migliorare l'integrazione Teamwork-GitHub. Tali tool sono stati creati in tempi ristretti per agevolare il lavoro di gruppo nell'ambito del corso di Ingegneria del software, previsto dal CdS in Informatica dall'Università degli Studi di Padova.
Nessuna garanzia sull'affidabilità del contenuto di questo repository.

## GitWork
Si tratta di un programma JAVA da riga di comando che permette di scegliere un task di Teamwork e trasformare tutti i suoi sub-task in issue di GitHub.

Utilizzate le issue per richieste di funzionalità e segnalazione di errori.

### Per iniziare
Aprire il file GitWork.java e modificare le seguenti variabili: 
- **GH_user**;
- **GH_psw**;
- **GH_target_repo_username**; 
- **GH_target_repo_name** .

### Il codice
Attenzione: questo applicativo è stato scritto in tempi ristretti, guardare il codice per un periodo troppo prolungato potrebbe farvi sanguinare gli occhi.

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

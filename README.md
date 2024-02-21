===================================README======================================
Clasa MyDispatcher
In metoda addTask(), aleg de fiecare data host-ul catre care trimit task-ul, in
functie de algoritmul dorit. Pentru Round Robin, am folosit formula data, (i+1)%
(nr noduri). Pentru Shortest Queue, se alege mereu nodul care are lungimea cozii
cea mai mica. Pentru ca algoritmul tine cont si de task-ul running, tin o variabila
minQ, care contine valoarea lungimii + 1 daca are un task running in acel moment.
Pentru SITA, dau task-urile de tip SHORT la primul nod, MEDIUM la al doilea si
LONG la al treilea. In caz de egalitate, aleg nodul cu cel mai mic id. Pentru Least
Work Left, folosesc atributul getWorkLeft si aleg mereu nodul care are valoarea acestui
atribut minima. In caz de egalitate, aleg nodul cu cel mai mic id.Metoda addTask este
declarată ca synchronized, asigurând că un singur thread poate executa această metodă
la un moment dat. Astfel, se previn race conditions și se asigură consistența datelor
când sarcinile sunt adăugate în sistem.

Clasa MyHost
Am adaugat syncronized pentru addTask, getQueueSize, și getWorkLeft, pentru a asigura accesul 
si modificarea cozii de prioritate intr-un mod thread-safe. 
Am folosit un priority queue pe care o ordonez in functie de prioritate, iar in cazul in care sunt
egale, ordonez in functie de start. In run, verific daca este un task running, daca este, atunci
verific daca este preemtibil si daca task-ul care asteapta in coada este mai prioritar, atunci trec
in running acel task, si il bag pe celalalt in coada. Daca nu exista nimic running, atunci bag in
running primul task ce asteapta in coada. Cand pornesc un task, scad timpul pe care il petrece in
running din getLeft(). atunci cand timpul ajunge la 0, apelez finish(). Pentru a implementa shutdown,
am folosit o variabila care este initiata cu true, si este false in cazul apelarii lui shutdown.
In run, parcurg toata logica descrisa mai sus doar daca nu se da shutdown.

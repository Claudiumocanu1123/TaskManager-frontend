# Task Manager Frontend

Frontend pentru aplicația Task Manager.
Interfață simplă pentru gestionarea task-urilor, conectată la backend-ul construit cu Spring Boot.

## Features

* afișare listă de task-uri
* adăugare task nou
* editare task
* ștergere task
* interfață clean și ușor de folosit

## Tech Stack

* JavaFX
* Java
* REST API (pentru comunicarea cu backend-ul)

## Cum rulezi proiectul

1. Clonează repo-ul:

```id="c9q2x1"
git clone https://github.com/claudiumocanu1123/TaskManager-frontend.git
```

2. Deschide proiectul în IntelliJ IDEA (sau alt IDE)

3. Rulează aplicația din clasa principală (ex: `Main.java`)

## Conectare la backend

Asigură-te că backend-ul rulează pe:

```id="p1z8ra"
http://localhost:8080
```

Endpoint folosit:

```id="y6w0dl"
/api/tasks
```

Dacă schimbi portul sau URL-ul, modifică request-urile din cod.

## Structura proiectului

* `view` – interfața (FXML)
* `controller` – logica UI
* `service` – apeluri către backend
* `model` – structura datelor

## Notes

Proiectul este în dezvoltare. Posibile îmbunătățiri:

* design mai modern (CSS)
* validare input
* gestionare erori mai bună
* suport pentru autentificare

---

Scopul principal al proiectului este să exersez conectarea unui UI la un backend REST și organizarea codului într-un mod clar.

# Social-Network
Am creat o aplicație de social network în cadrul unui proiect de facultate, folosind JavaFX pentru interfața grafică, Gradle pentru gestionarea dependențelor și Postgres pentru baza de date. Clasele de bază din proiect sunt:

  -Entity
  
  -Friendship
  
  -FriendRequest 
  
  -User

Clasa de bază Entity ce este extinsa de toate celelalte entitati include un id, clasa User conține informații despre utilizator, cum ar fi numele, prenumele, numele de utilizator și parola, iar fiecare utilizator are o listă de prieteni. Friendship este o entitate care reprezintă legătura de prietenie dintre doi utilizatori, în timp ce FriendRequest este o entitate care reprezintă o cerere de prietenie trimisă de un utilizator către altul.

Am implementat o interfață de repository, care este implementată în clasa Repository. Această clasă este apoi moștenită de clasa Service, care se ocupă de adăugarea, ștergerea, filtrarea și căutarea datelor în baza de date. Aceste operații sunt realizate în mod transparent pentru utilizator, prin intermediul interfeței grafice.

Folosind pattern-ul Observer, am implementat o actualizare live a informațiilor din aplicație. Atunci când un utilizator adaugă un nou prieten, interfața grafică se actualizează automat.

La deschiderea aplicației, utilizatorii sunt întâmpinați cu o fereastră de autentificare. După autentificare, aceștia sunt redirecționați către fereastra principală a aplicației, care conține trei butoane în partea stângă: unul pentru listarea prietenilor, unul pentru afișarea cererilor de prietenie și unul pentru afișarea tuturor utilizatorilor din aplicație. Utilizatorii au opțiunea de a-și actualiza profilul sau de a-l șterge.

Elementele grafice ale aplicației au fost create cu ajutorul SceneBuilder și au fost îmbunătățite cu CSS personalizat.

În concluzie, aplicația mea de social network  demonstrează abilitățile mele avansate în OOP, dezvoltarea de interfețe utilizator atrăgătoare și gestionarea bazelor de date. Această aplicație reprezintă un exemplu elocvent al capacității mele de a dezvolta soluții sofisticate, ușor de utilizat și estetice, care să răspundă nevoilor și așteptărilor utilizatorilor.

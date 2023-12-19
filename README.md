### Uruchamianie w profiliu 'test'

- otwórz consolę w katalogu głównym projektu
- uruchom komendę: `.\gradlew bootRun`
- aplikacja dostępna lokalnie na porcie 8080
- profil `test` uruchamia aplikację w opraciu o wbudowaną bazę 
danych in-memory H2 oraz predefiniowanymi kontami testowymi:
  - login: admin@company.com, hasło: password, rola: ADMINISTRATOR
  - login: jan.nowak@company.com, hasło: password, rola: TECHNICIAN
  - login: ewa.kaczmarek@company.com, hasło: password, rola: REPORTER
### Running in the 'test' profile

- open console in the root directory of the project
- run the command: `.\gradlew bootRun`
- application available locally on port 8080
- the `test` profile runs the application based on the built-in database
  in-memory H2 data and predefined test accounts:
- login: admin@company.com, password: password, role: ADMINISTRATOR
- login: jan.nowak@company.com, password: password, role: TECHNICIAN
- login: ewa.kaczmarek@company.com, password: password, role: REPORTER
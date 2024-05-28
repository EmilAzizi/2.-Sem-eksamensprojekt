# Projektkalkuleringsværktøj

Projektkalkuleringsværktøjet er et webbaseret system, der hjælper med at administrere projekter, opgaver og brugere. Det giver brugerne mulighed for at oprette og slette projekter, tilføje og redigere opgaver.

## Funktioner

- **Brugerhåndtering**: Opret, log ind og slet brugere.
- **Projektadministration**: Opret, vælg og slet projekter.
- **Opgavestyring**: Tilføj, rediger og slet opgaver inden for projekter.

## Teknologier

- **Backend**: Spring Boot
- **Frontend**: Thymeleaf, HTML, CSS
- **Database**: JDBC
- **Byggeværktøj**: Maven

## Installation

1. Klon dette repository:
    ```sh
    git clone https://github.com/yourusername/projektkalkuleringsvaerktoej.git
    cd projektkalkuleringsvaerktoej
    ```

2. Byg projektet med Maven:
    ```sh
    mvn clean install
    ```

3. Start applikationen:
    ```sh
    mvn spring-boot:run
    ```

4. Åbn din browser og gå til `http://localhost:8080/projectManagement`.

## Brugerroller

- **Administrator**: Kan oprette, redigere og slette brugere og projekter.
- **Bruger**: Kan oprette, redigere og slette opgaver inden for deres projekter.

## Bidrag

Se `CONTRIBUTING.md` for detaljer om, hvordan du kan bidrage til projektet.

## Licens

Dette projekt er licenseret under MIT-licensen - se `LICENSE.md` for flere detaljer.

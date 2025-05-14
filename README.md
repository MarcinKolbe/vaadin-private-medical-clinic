# Vaadin Private Medical Clinic

A Vaadin-based front-end client for the Private Medical Clinic REST API.

---

## Features

* View doctors, patients, and appointments
* Create and cancel appointments
* Add diagnoses and ratings through basic UI forms
* Responsive design using Vaadin components

> ⚠️ This frontend is actively developed and currently supports limited functionality. More features are planned.

---

## Prerequisites

* Java 17+
* Maven 3.6+ (or Gradle)
* Backend API running (see [https://github.com/MarcinKolbe/private-medical-clinic](https://github.com/MarcinKolbe/private-medical-clinic))

---

## Running Locally

1. **Clone the repository**

   ```bash
   git clone https://github.com/MarcinKolbe/vaadin-private-medical-clinic.git
   cd vaadin-private-medical-clinic
   ```

2. **Configure backend URL**

   * Edit `application.properties` (or `application.yml`) to point to your API:

     ```properties
     backend.url=http://localhost:8080/api
     ```

3. **Build & Run**

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Open in Browser**
   Navigate to `http://localhost:8081` (or the port configured in `application.properties`).

---

## Feedback & Contributions

Issues and pull requests are welcome! Feel free to open an issue for feature requests or bug reports.

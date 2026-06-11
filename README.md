# Equipment Management System

A robust desktop inventory management system built with **Java Swing (GUI)** and backed by a relational **MySQL Database**. This application features an administrative dashboard designed to track campus equipment items, catalog their physical deployment locations, monitor operational availability states, and manage inventory lifecycles with direct database synchronization.

---

## 🚀 Key Features

* **Dynamic Inventory Dashboard:** Interactive, real-world data table rendering with customized asset state badges (Operational, Under Maintenance, Out of Service).
* **Live Text Filtering & Searching:** Instantly filter equipment rows dynamically via the search bar query parameters or the status selection dropdown.
* **Inline Table Actions Panel:** Embedded custom cell renderers and editors hosting action triggers to edit or delete database asset tuples safely.
* **Relational Database Persistence:** Powered by an active JDBC wrapper using transactions (`commit` & `rollback`) to preserve database normalization boundaries.
* **Input Validation Handling:** Comprehensive modal error screening prompts blocking null inputs or incorrect cast types during layout saving states.

---

## 🛠️ Tech Stack & Dependencies

* **Language:** Java 8 or higher
* **UI Framework:** Java Swing / AWT (NetBeans GUI Form Builder Builder Layout)
* **Database:** MySQL Server
* **Drivers:** MySQL Connector/J JDBC Driver
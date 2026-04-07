# 🌸 FlowerShop Management System (MVVM)

## 📌 Overview

This project is a desktop application designed for managing a chain of flower shops.
It was developed using the **MVVM (Model-View-ViewModel)** architectural pattern and follows clean design principles such as **SOLID** and the **Command pattern**.

The application allows efficient management of flower shops, flowers, and stock availability.

---

## 🏗️ Architecture

The application is structured based on the MVVM pattern:

* **Model**

  * Contains domain entities: `Florarie`, `Floare`, `Stoc`
  * Includes repository classes for database interaction

* **ViewModel**

  * Acts as the intermediary between UI and data
  * Contains application logic, state management, and commands
  * Implements `INotifyPropertyChanged`

* **View**

  * Graphical user interface (GUI)
  * Uses data binding to interact with the ViewModel

* **Commands**

  * Implements the Command design pattern (`ICommand`)
  * Handles user actions such as Add, Update, Delete

---

## ⚙️ Features

* Add / Update / Delete flower shops
* Add / Update / Delete flowers
* Manage stock (availability and color per shop)
* View all flower shops
* View all flowers (without stock details)
* Filter flowers by:

  * availability
  * color
* Search flowers by name

---

## 🗄️ Database Design

The application uses a relational database with the following main entities:

* **Florarie**

  * `id`, `denumire`, `adresa`

* **Floare**

  * `id`, `denumire`, `imagine`

* **Stoc**

  * `idStoc`, `idFlorarie`, `idFloare`, `culoare`, `stoc`

The `Stoc` entity models the relationship between flower shops and flowers, including stock quantity and color variations.

---

## 🧠 Technologies Used

* Programming Language: *Java*
* GUI Framework: *JavaFX*
* Database: *MySQL*
* Design Patterns:

  * MVVM
  * Command
  * Repository

---

## 📚 Academic Context

This project was developed as part of the **Software Design** course assignment, focusing on:

* MVVM architecture
* UML diagrams (package, class, ER)
* Clean code principles

---

## ✨ Author

* **Bianca Ilies**

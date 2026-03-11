# Spring Boot Blog Application

A full-stack **Blog Application** built using **Java Spring Boot**.  
This application allows users to create, update, delete, and view blog posts with category support and search functionality.

---

##  Features

- User Registration & Login
- Create Blog Posts
- Update Blog Posts
- Delete Blog Posts
- View All Blog Posts
- Category-wise Posts
- Search Blog Posts
- Pagination Support
- Clean MVC Architecture

---

## 🛠 Tech Stack

**Backend**
- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate

**Frontend**
- Thymeleaf
- HTML
- CSS
- Bootstrap

**Database**
- MySQL

**Build Tool**
- Maven

---

##  Project Structure
src/main/java/com/project
│
├── controller
├── service
├── serviceImpl
├── repository
├── entity
├── config
└── BlogApplication.java

src/main/resources
│
├── templates
├── static
└── application.properties


---

##  Installation & Setup

###  Clone the Repository


git clone  https://github.com/nikhilrajput666444/springboot-blog-app


###  Open the Project

Open the project in any IDE:

- IntelliJ IDEA
- Eclipse
- VS Code

###  Configure Database

Update `application.properties`:


spring.datasource.url=jdbc:mysql://localhost:3306/blog_app
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


###  Run the Application


mvn spring-boot:run


Application will start on:


http://localhost:8080


---

##  Application Screenshots

### Home Page
![Home Page](screenshorts/Homee.png)

### Login Page
![Login Page](screenshorts/loginn.png)

### Create Blog Post Page
![Create Post](screenshorts/addpost.png)

### signup page 
![signup page](screenshorts/signup.png)

### add post category 
![add post category](screenshorts/addpostcategory.png)

#admin login page 
![admin login page](screenshorts/adminlogin.png)

# all post  page 
![all post ](screenshorts/allpost.png)

# guest   page 
![guest page ](screenshorts/guestlogin.png)

# search by category page 
![search by category page ](screenshorts/searchbycategory.png)

# view all comments page 
![viewall comments ](screenshorts/viewallcomments.png)







##  Author

Nikhil singh

GitHub:  
https://github.com/nikhilrajput666444


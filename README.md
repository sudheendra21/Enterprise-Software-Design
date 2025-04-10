# E-Learning Platform

A secure and scalable E-Learning Platform built using **Spring Boot**, **Thymeleaf**, and **Spring Security**, designed to provide interactive learning experiences through video-based content delivery.

## 🚀 Features

- 🔐 **Authentication & Authorization**  
  Robust user and admin login system implemented using **Spring Security**. Ensures secure access control based on user roles.

- 🎥 **Video Upload & Streaming**  
  Admins can upload educational videos. Users can stream videos seamlessly via a user-friendly interface.

- 📚 **Course Enrollment & Session Management**  
  Registered users can enroll in courses. Sessions persist securely across the platform using session management strategies.

- 🌐 **MVC Architecture**  
  Utilizes Spring Boot MVC architecture for clean separation of concerns. **Thymeleaf** is used for dynamic and responsive view rendering.

- ☁️ **AWS S3 Integration**  
  All video content is stored securely in **AWS S3**. Buckets are encrypted with **Customer-Managed Encryption Keys (CMEK)** for data at rest and in transit.

---

## 🛠️ Tech Stack

- **Backend**: Spring Boot, Spring MVC, Spring Security  
- **Frontend**: Thymeleaf, HTML5, CSS3  
- **Storage**: AWS S3 with CMEK Encryption  
- **Build Tool**: Maven  
- **Database**: MySQL / PostgreSQL (choose the one you used)  
- **Authentication**: Spring Security with role-based access

---

## 📦 Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/e-learning-platform.git
   cd e-learning-platform

# ⬡ HireHub — Full-Stack Job Portal

A **production-ready Job Portal** built with **Java 17 + Spring Boot 3.2**, featuring full authentication, job posting, applications management, and role-based dashboards — all styled with a sleek dark UI.

---

## 🚀 What's Inside

| Feature | Details |
|---|---|
| **Auth** | Spring Security — Login, Register, Logout |
| **Roles** | Job Seeker, Employer, Admin |
| **Jobs** | Post, search, filter, paginate, toggle active/closed |
| **Applications** | Apply with cover letter, track status, employer review |
| **Dashboards** | Separate dashboards per role |
| **Database** | H2 (in-memory, zero setup) or MySQL |
| **Demo Data** | Auto-seeded on first run |
| **UI** | Thymeleaf + custom dark CSS (no Bootstrap needed) |

---

## 📋 Prerequisites — What You Need to Install

### 1. Java 17 (Required)

**Windows:**
1. Download from https://adoptium.net (Eclipse Temurin 17 LTS)
2. Run the installer, check "Set JAVA_HOME" and "Add to PATH"
3. Verify: open CMD and run `java -version`

**macOS (with Homebrew):**
```bash
brew install openjdk@17
echo 'export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
java -version
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install openjdk-17-jdk -y
java -version
```

---

### 2. Maven 3.9+ (Required)

**Windows:**
1. Download from https://maven.apache.org/download.cgi
2. Extract to `C:\maven`
3. Add `C:\maven\bin` to your system PATH
4. Verify: `mvn -version`

**macOS:**
```bash
brew install maven
mvn -version
```

**Linux:**
```bash
sudo apt install maven -y
mvn -version
```

> **Tip:** If you don't want to install Maven globally, use the included Maven Wrapper: replace `mvn` with `./mvnw` (Mac/Linux) or `mvnw.cmd` (Windows) in all commands below.

---

### 3. Git (Optional, for cloning)
Download from https://git-scm.com/downloads

---

### 4. MySQL (Optional — only for production)
The app runs perfectly with **H2 (in-memory)** by default — no database install needed!

To switch to MySQL:
1. Install MySQL 8+ from https://dev.mysql.com/downloads/
2. Uncomment the MySQL section in `application.properties`
3. Comment out the H2 section
4. Set your username/password

---

### 5. IDE (Recommended)
- **IntelliJ IDEA** (Community is free): https://www.jetbrains.com/idea/
- **VS Code** with Java Extension Pack
- **Eclipse IDE for Java EE**

---

## ▶️ Running the Project

### Option A — Command Line (Recommended)

```bash
# 1. Navigate to project folder
cd jobportal

# 2. Build and run
mvn spring-boot:run

# Or on Mac/Linux with wrapper:
./mvnw spring-boot:run
```

### Option B — IntelliJ IDEA
1. Open IntelliJ → File → Open → select the `jobportal` folder
2. Wait for Maven to download dependencies (first run takes 2-3 min)
3. Find `JobPortalApplication.java` → right-click → Run

### Option C — Build a JAR and run
```bash
mvn clean package -DskipTests
java -jar target/jobportal-1.0.0.jar
```

---

## 🌐 Access the App

Once running (you'll see `Started JobPortalApplication`):

| URL | Description |
|---|---|
| http://localhost:8080 | Homepage |
| http://localhost:8080/jobs | Browse all jobs |
| http://localhost:8080/register | Create account |
| http://localhost:8080/login | Login |
| http://localhost:8080/h2-console | Database browser (dev only) |

---

## 🔑 Demo Accounts (Auto-Created on First Run)

| Role | Email | Password |
|---|---|---|
| **Admin** | admin@jobportal.com | admin123 |
| **Employer** | employer@techcorp.com | employer123 |
| **Employer** | hr@innovatex.com | employer123 |
| **Job Seeker** | priya@gmail.com | seeker123 |
| **Job Seeker** | amit@gmail.com | seeker123 |

---

## 📁 Project Structure

```
jobportal/
├── pom.xml                          # Maven dependencies
└── src/main/
    ├── java/com/jobportal/
    │   ├── JobPortalApplication.java    # Main entry point
    │   ├── config/
    │   │   ├── SecurityConfig.java      # Spring Security setup
    │   │   └── DataInitializer.java     # Demo data seeder
    │   ├── controller/
    │   │   ├── AuthController.java      # Login / Register
    │   │   ├── HomeController.java      # Home + Dashboard routing
    │   │   └── JobController.java       # Jobs + Applications
    │   ├── model/
    │   │   ├── User.java                # User entity (JPA)
    │   │   ├── Job.java                 # Job entity
    │   │   └── Application.java         # Application entity
    │   ├── repository/
    │   │   ├── UserRepository.java
    │   │   ├── JobRepository.java       # Custom search queries
    │   │   └── ApplicationRepository.java
    │   ├── service/
    │   │   ├── UserService.java
    │   │   ├── JobService.java
    │   │   ├── ApplicationService.java
    │   │   └── CustomUserDetailsService.java
    │   └── dto/
    │       └── RegisterDTO.java
    └── resources/
        ├── application.properties       # DB, server config
        ├── static/
        │   ├── css/style.css            # Full custom dark UI
        │   └── js/main.js
        └── templates/
            ├── index.html               # Homepage
            ├── profile.html             # Profile editor
            ├── auth/
            │   ├── login.html
            │   └── register.html
            ├── jobs/
            │   ├── list.html            # Browse + search
            │   └── detail.html          # Job detail + apply
            ├── dashboard/
            │   ├── employer.html
            │   ├── seeker.html
            │   └── admin.html
            ├── employer/
            │   ├── job-form.html        # Post new job
            │   └── applications.html    # Review applicants
            └── seeker/
                └── applications.html    # Track my applications
```

---

## 🔧 Common Troubleshooting

| Problem | Fix |
|---|---|
| `java: error: release version 17 not supported` | Install Java 17, set JAVA_HOME |
| `Port 8080 already in use` | Change `server.port=8081` in `application.properties` |
| Maven not found | Add Maven/bin to PATH, or use `./mvnw` |
| H2 console blank | Make sure you're using JDBC URL `jdbc:h2:mem:jobportaldb` |
| Dependency download fails | Check internet connection; corporate proxy may need config |

---

## 🛠️ Switching to MySQL (Production)

1. Open `src/main/resources/application.properties`
2. Comment out the H2 block
3. Uncomment and edit the MySQL block:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/jobportaldb?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```
4. Restart the app — it creates the schema automatically.

---

## 📦 Key Dependencies

| Dependency | Purpose |
|---|---|
| `spring-boot-starter-web` | MVC web layer |
| `spring-boot-starter-security` | Authentication & authorization |
| `spring-boot-starter-thymeleaf` | Server-side HTML templates |
| `thymeleaf-extras-springsecurity6` | `sec:authorize` in templates |
| `spring-boot-starter-data-jpa` | ORM / database layer |
| `spring-boot-starter-validation` | Bean validation (@Valid) |
| `h2` | In-memory database (dev) |
| `mysql-connector-j` | MySQL driver (prod) |
| `lombok` | Boilerplate reduction (@Getter etc.) |

---

## ✨ Feature Highlights

- **Role-based access control** — Employers can only manage their own jobs; seekers see seeker-only UI
- **Search & filter** — Full-text search across title, description, company with location + category + type filters
- **Pagination** — Jobs list paginates (9 per page)
- **One-click apply** — Job seekers apply with cover letter and resume link
- **Live status updates** — Employers update application status (Pending → Reviewing → Shortlisted → Hired/Rejected)
- **Demo data** — Realistic seed data so you can explore all features immediately

---

*Built with Spring Boot 3.2 · Java 17 · Thymeleaf · Spring Security · JPA/Hibernate · H2/MySQL*

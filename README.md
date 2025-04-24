# 🏓 PingPong Player Communication (Hexagonal Architecture)

This project demonstrates two communicating `Player` components, implemented in **pure Java**, using **Hexagonal Architecture** principles.

It includes:

- ✅ Thread-based communication in a single Java process
- ✅ Socket-based communication in separate processes
- ✅ Pluggable message strategies (Simple, JSON, Base64)
- ✅ Clean architecture (ports & adapters)
- ✅ Unit tests with JUnit 5

---
pingpong/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── example/
│   │               └── pingpong/
│   │                   ├── adapters/
│   │                   │   ├── cli/
│   │                   │   │   ├── InitiatorMain.java          # Socket client entry
│   │                   │   │   ├── ResponderMain.java          # Socket server entry
│   │                   │   │   └── Main.java                   # Single-process in-memory version
│   │                   │   └── memory/
│   │                   ├── domain/
│   │                   │   ├── Player.java                     # Core logic
│   │                   │   ├── Base64EncodingStrategy.java     # Strategy: Base64
│   │                   │   ├── JsonMessageStrategy.java        # Strategy: JSON
│   │                   │   ├── SimpleMessageStrategy.java      # Strategy: Simple text
│   │                   │   └── ports/
│   │                   │       ├── MessagePort.java            # Output port interface
│   │                   │       └── MessageStrategy.java        # Strategy interface
│   │                   └── SocketMessageAdapter.java           # Socket adapter implementing MessagePort
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── pingpong/
│                       └── domain/
│                           └── PlayerTest.java                # Unit tests for Player + Strategies
├── pom.xml                                                  # Maven config
└── README.md                                                 # Documentation





---

## 🚀 How to Run

### ✅ 1. Single-Process (In-Memory Threaded Version)

```bash
mvn compile exec:java "-Dexec.mainClass=com.example.pingpong.adapters.cli.ResponderMain"
mvn compile exec:java "-Dexec.mainClass=com.example.pingpong.adapters.cli.InitiatorMain"
mvn compile exec:java "-Dexec.mainClass=com.example.pingpong.adapters.cli.Main"

⚙️ Message Strategies
## The Player logic is decoupled from message formatting via MessageStrategy interface.

### Available strategies:

  SimpleMessageStrategy

  JsonMessageStrategy

   Base64EncodingStrategy

You can inject different strategies in ResponderMain easily.

🧠 Design Principles
🟩 Hexagonal Architecture (Ports & Adapters)

🧩 Pluggable behavior via Strategy Pattern

🧪 Testable core logic with no dependency on frameworks

🔁 Scalable from single-process to multi-process

📄 Author Notes
This project was developed as part of a pre-interview exercise, focusing on:

Clean design

Extensibility

Functional correctness

Real-world readiness (multi-process, CLI, testability)




### ✅ 2. Multi-Process (WebSocket Version) 

> 🧠 **Bonus implementation** using Java WebSockets (JSR 356) — modern, asynchronous, and browser-compatible communication.

This version uses:
- A WebSocket **server** to represent the `Responder`
- A WebSocket **client** to represent the `Initiator`
- Communication over `ws://localhost:8080/pingpong`



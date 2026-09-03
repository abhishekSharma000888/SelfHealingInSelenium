# SelfHealingInSelenium
A Selenium-based test automation framework that uses a three-level locator strategy to make automated tests more resilient to UI changes.
## Basic Framework
SelfHealingSelenium
│
├── pom.xml
│
└── src
├── main
│   └── java
│       ├── elements
│       │   └── Elements.java
│       │
│       └── utilities
│           └── Common.java
│
└── test
└── java
├── ParentClass.java
│
└── tests
└── EndToEnd.java
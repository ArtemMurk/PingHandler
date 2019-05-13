# Ping Handler

| Technology     | Badge |
|:--------------:|:-----:|
| License        | [![LICENSE](https://img.shields.io/badge/LICENSE-Apache%202.0-blue.svg)](LICENSE) |
| Travis CI      | [![Build Status](https://travis-ci.com/ArtemMurk/TelegramPingHandlerCore.svg?branch=master)](https://travis-ci.com/ArtemMurk/TelegramPingHandlerCore) |
| Codacy         | [![Codacy Badge](https://api.codacy.com/project/badge/Grade/eeb38bbb83b34b99b0ce1488db2aee75)](https://www.codacy.com/app/ArtemMurk/PingHandlerCore?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ArtemMurk/PingHandlerCore&amp;utm_campaign=Badge_Grade) |

## Project Information
This project help to catch your project heartbeat. 
It's simple Spring REST application catch pings from your module and save them in RDBMS. 
As main DB use Postgres, for test hsqlDb.

**WARN -> You need ALARM system that send notification if something wrong**

## Build

Use Maven for build this project. 
This project package in **war** file, you need some Servlet Container to deploy it.
 
Build command.
> mvn clean package

## Database

#### Db scheme that use in project

![db scheme](https://github.com/ArtemMurk/TelegramPingHandlerCore/blob/master/DBScheme.png)

## Technologies

#### Serialization
* Jackson

#### Logging
* Slf4j
* Logback

#### Test
* JUnit
* AssertJ
* Mockito
* Hamcrest
* Spring Test
* Jsonpath

#### Spring
* Spring MVC

#### ORM
* Hibernate

#### Other
* Lombok

## Contact Information
If you have any questions I'd be happy to answer it.
 
* **Author**: Артём Муравлёв
* **Linkeidn**: [My Linkeidn](https://linkedin.com/in/artem-muravlov)
* **Telegram**: @artem_murk
* **Mail**: muravlevars@gmail.com



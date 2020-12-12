## Spring Boot and Integration with JMS and Apache ActiveMQ

### Spring Integration:
Extends the Spring programming model to support the well-known Enterprise Integration Patterns. Spring Integration enables lightweight messaging within Spring-based applications and supports integration with external systems via declarative adapters. Those adapters provide a higher-level of abstraction over Spring’s support for remoting, messaging, and scheduling. Spring Integration’s primary goal is to provide a simple model for building enterprise integration solutions while maintaining the separation of concerns that is essential for producing maintainable, testable code.

### Java Message Service (JMS):
Java Messaging Service(JMS) is an application programming interface that is used for exchange of loosely coupled, reliable and asynchronous messages between different software application components (clients).

### ActiveMQ:
There are quite a few JMS implementations out there. Most of them are provided by the Middle Oriented Middleware providers, example: WebSphere MQ, Oracle EMS, JBoss AP, SwiftMQ, TIBCO EMS, SonicMQ, ActiveMQ, WebLogic JMS. Some are open source, some are not.
ActiveMQ is an open-source, message oriented middleware written in Java with a full fledged implementation of JMS 1.1 as part of J2EE 1.4 specification ( as per ActiveMQ website).
It provides messaging software with enterprise features that can serve as a backbone for a distributed application built upon messaging model.
Apache ActiveMQ has the following characteristics:
1. the most popular and powerful open source messaging and Integration Patterns server
2. accepts non-Java clients
3. can be used standalone in production environments
4. supports pluggable transport protocols such as in-VM, TCP, SSL, NIO, UDP, multicast, JGroups, and JXTA transports
5. can be used as an in-memory JMS provider, using an embedded broker, to avoid the overhead of running separate processes when doing unit testing JMS5
6. the ActiveMQ executable starts with a default configuration
7. can also be used embedded in an application
8. can be configured using ActiveMQ or Spring configuration (XML or Java Configuration)
9. provides advanced messaging features such as message groups, virtual and composite destinations, and wildcards
10. provides support for Enterprise Integration Patterns when used with Spring Integration or Apache Camel

### Queues and Topics:
JMS messages sent by an application are targeted to a specific destination just like postal mail boxes, and the messages are placed in the mailbox until someone picks them up.
There are two types of destination in JMS: queues and topics.

### Queue:
Queues are based on point-to-point messaging model (messages are sent to queue) or p2p model which allows users to send messages both synchronously or asynchronously using different channels.

### Topic:
Topics are based on publish-subscribe model where messages are sent to a particular topic.
Publish/Subscribe messaging model allows publishers to send messages to many clients/users at the same time.
Consumers can subscribe to a topic and when a message arrives each consumer gets a copy of that message.

further references:     
- https://spring.io/projects/spring-integration
- https://www.oracle.com/java/technologies/java-message-service.html
- http://activemq.apache.org/
- https://www.devglan.com/spring-boot/spring-boot-jms-activemq-example


### Project Descriptions:
please see application.properties files in resources folder and select a active profile "dev" or "com" to run project. you can check test methods too.  

Maven Dependencies:
- spring-boot-starter-activemq :It provides all the required dependencies to integrate JMS and activemq with spring boot.
- activemq-broker: This provides embedded activemq in spring boot application. But since, we will be configuring our activemq outside the application we have commented it for time being.
- spring-boot-maven-plugin : It will collect all the jar files present in the classpath and create a single executable jar.

current project steps:
1. Download one of apache activemq types "ActiveMQ 5 Classic" or "ActiveMQ Artemis" as per your operating system:
-ActiveMQ 5 Classic: "http://activemq.apache.org/components/classic/download/"
-ActiveMQ Artemis: "http://activemq.apache.org/components/artemis/download/"
2. Extract under "D:\projects\tools\apache-activemq-5.16.0". you can use [tools/apache-activemq-5.16.0-bin.zip](tools/apache-activemq-5.16.0-bin.zip) file in the repository instead.
3. Now traverse to "D:\projects\tools\apache-activemq-5.16.0\bin\win64" and execute the "acivemq.bat" file.
4. Open the browser and hit - "http://localhost:8161/admin/" and Enter userId/password as admin/admin (ActiveMq by default exposes a broker url tcp://localhost:61616 and an admin console on tcp://localhost:61616 with userId and password as "admin" and "admin". )
5. Now click on the queues option present in menu bar and you can notice there are no queues available as below image.
6. Run Application.java as a java applicaton and you can see one queue automatically created with name inbound.queue which we had configured in our Listener.class. Refresh the page after application restart



### IntellliJ IDEA Configurations:
- IntelijIDEA: Help -> Edit Custom Vm Options -> add these two line:
    - -Dfile.encoding=UTF-8
    - -Dconsole.encoding=UTF-8
- IntelijIDEA: File -> Settings -> Editor -> File Encodings-> Project Encoding: form "System default" to UTF-8. May be it affected somehow.
- IntelijIDEA: File -> Settings -> Editor -> General -> Code Completion -> check "show the documentation popup in 500 ms"
- IntelijIDEA: File -> Settings -> Editor -> General -> Auto Import -> check "Optimize imports on the fly (for current project)"
- IntelijIDEA: File -> Settings -> Editor -> Color Scheme -> Color Scheme Font -> Scheme: Default -> uncheck "Show only monospaced fonts" and set font to "Tahoma"
- IntelijIDEA: Run -> Edit Configuration -> Spring Boot -> XXXApplication -> Configuration -> Environment -> VM Options: -Dspring.profiles.active=dev
- IntelijIDEA: Run -> Edit Configuration -> Spring Boot -> XXXApplication -> Code Coverage -> Fix the package in include box

<hr/>
<a href="mailto:eng.motahari@gmail.com?"><img src="https://img.shields.io/badge/gmail-%23DD0031.svg?&style=for-the-badge&logo=gmail&logoColor=white"/></a>


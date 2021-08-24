# Scheduling-Application
Appointment Scheduling Desktop Application, v1.0

You are working for a software company that has been contracted to develop a GUI-based scheduling desktop application. 
The contract is with a global consulting organization that conducts business in multiple languages and has main offices in Phoenix, Arizona; White Plains, New York; Montreal, Canada; and London, England. 
The consulting organization has provided a MySQL database that the application must pull data from. 
The database is used for other systems, so its structure cannot be modified.

The organization outlined specific business requirements that must be met as part of the application. 
From these requirements, a system analyst at your company created solution statements for you to implement in developing the application. 
These statements are listed in the requirements section.

Your company acquires Country and First-Level-Division data from a third party that is updated once per year. 
These tables are prepopulated with read-only data. 
Please use the attachment “Locale Codes for Region and Language” to review division data. 
Your company also supplies a list of contacts, which are prepopulated in the Contacts table; however, administrative functions such as adding users are beyond the scope of the application and done by your company’s IT support staff. 
Your application should be organized logically using one or more design patterns and generously commented using Javadoc so your code can be read and maintained by other programmers.

By Nicholas Hasty | Nicholas.hasty1@gmail.com | 2 July 2021

IntelliJ IDEA 2020.3 (Community Edition)
Build #IC-203.5981.155, built on November 30, 2020
Runtime version: 11.0.9+11-b1145.21 amd64
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
Windows 10 10.0
GC: ParNew, ConcurrentMarkSweep
Memory: 1490M
Cores: 4

Java SDK 11.0.11

To run program download program zip files including the MySQL JDBC driver.
Use the login information options to access the application:
Username: test, user1, user2
Password: test, letmein1, letmein2
Program will iterate through all usernames in database to check what user enters and give alert for each.
Set computer system language to "French" and the application login screen and notifications will be in French language.

Additional reports are number of customers added this month.

mysql:mysql-connector-java:8.0.22



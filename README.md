# XMLtoSQL

Web app for parsing users' data from XML and inserting it to MySQL database.

**Used technologies:**
* Tomcat 9.0 
* JDK 1.8
* jQuery
* HTML/CSS

## Configuration and installation

1. Download .war file (available in releases) or compile the project by yourself and drop the file into {Tomcat Installation Path}/webapps

2. Remember to edit `WEB-INF/db.properties` file to match your MySQL server settings
```
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/java
jdbc.username=java
jdbc.password=java
```

## Index page 🌎
This is where you start. Choose either one of options. Links will take you to corresponding subpages.

![1](https://user-images.githubusercontent.com/29582159/203868201-c61fd0d3-fea5-468d-85d4-480b1f271a71.PNG)

## File import 📁
On this site you can upload file of your own, that contains user data, formatted like so:
```
<users>
  <user>
	<name>user1</name>
	<surname>surname1</surname>
	<login>login1</login>
  </user>
  <user>
	<name>user2</name>
	<surname>surname2</surname>
	<login>login2</login>
  </user>
  <user>
	<name>usern</name>
	<surname>surnamen</surname>
	<login>loginn</login>
  </user>
</users>
```
For now the application parses only basic data, however it is a subject to change.

## Users' data 👨
![2](https://user-images.githubusercontent.com/29582159/203868678-184f3093-762a-4235-a30e-adda0e14e8d6.PNG)

Here we can display, search or sort users' data that our database holds. 

The MD5 hashcode appended to surname is just `surname + _ + (MD5)name`.

Used pagination to avoid downloading all of database's content everytime.

set JAVA_HOME="C:\Program Files (x86)\Compil\jdk-11.0.2"
mvn -f ../pom.xml clean package -DskipTests

docker-compose up
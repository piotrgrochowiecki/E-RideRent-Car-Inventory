# Car Inventory service for E-RideRent application
RESTful web service that provides car inventory capabilities.

## Used technologies:
* Java 17
* MySQL
* Spring Boot
* Hibernate
* Spring Data
* Maven
* Docker

## Set up a database using Docker
<p>
This part assumes that you have Docker engine already installed and running.
To set up a database, use terminal and navigate to main directory of the project. If opened in IntelliJ, you can use "Terminal"
card there.
</p>
<p>
Run following commands in order:
</p>

````
docker run --name=CarInventoryServiceDB -p 3312:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=1 -e MYSQL_DATABASE=car_inventory_service_db -d mysql:latest
````

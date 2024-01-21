FROM openjdk:17-jdk-alpine@sha256:a996cdcc040704ec6badaf5fecf1e144c096e00231a29188596c784bcf858d05
RUN apk update && apk upgrade && apk add bash
WORKDIR /home/ERideRent_Car_Inventory
ADD target/E-RideRent-Car-Inventory.jar /home/ERideRent_Car_Inventory/
CMD java -jar /home/ERideRent_Car_Inventory/E-RideRent-Car-Inventory.jar
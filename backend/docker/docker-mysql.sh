docker run -d \
--name shopping-mysql \
-e MYSQL_ROOT_PASSWORD="shopping" \
-e MYSQL_USER="shopping" \
-e MYSQL_PASSWORD="shopping" \
-e MYSQL_DATABASE="shopping" \
-p 3306:3306 \
mysql:latest
FROM mongo:4.0.4

COPY init.json /init.json

CMD mongoimport --host chat_mongo --db chat_db --collection messages --type json --file /init.json --jsonArray
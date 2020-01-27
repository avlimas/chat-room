FROM mongo:4.0.4

COPY messages.json /messages.json

CMD mongoimport --host chat_mongo --db chat_db --collection messages --type json --file /messages.json --jsonArray
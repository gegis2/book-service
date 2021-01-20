# book-service

API service for storing book data

## usage

/retrieve?barcode={barcode}
returns book json
returns status code if unsuccefull
-404 if doesnt exists
-400 if barcode contains non alphabetical/numerical characters

###/put --data {"name":"book","author":"author","barCode":"barrrdcdode","price":5.5,"quantity":5,"releaseYear":1500,"scienceindex":1}
###/put --data {"name":"book","author":"author","barCode":"barrrdcdode","price":5.5,"quantity":5,"releaseYear":1500}
###/put --data {"name":"book","author":"author","barCode":"barrrdcdode","price":5.5,"quantity":5,"scienceindex":1}
###/put --data {"name":"book","author":"author","barCode":"barrrdcdode","price":5.5,"quantity":5}

    returns status code
        -200 if succesfull
        -400 if some required data missing
        -403 if already exists
        -406 if writing to file failed

/update --data {"barCode":{barcode},"collumn":"name","data":{new data}}
/update --data {"barCode":{barcode},"collumn":"author","data":{new data}}
/update --data {"barCode":{barcode},"collumn":"barcode","data":{new data}}
/update --data {"barCode":{barcode},"collumn":"price","data":{new data}}
/update --data {"barCode":{barcode},"collumn":"quantity","data":{new data}}
/update --data {"barCode":{barcode},"collumn":"releaseyear","data":{new data}}
/update --data {"barCode":{barcode},"collumn":"scienceindex","data":{new data}}

    returns status code
        -200 if succesfull
        -400 if some required data missing or field name is wrong
        -403 if data format does not fit the field
        -404 if book does not exist

/totalPrice?barcode={barcode}
returns price and barcode json if succesfull
returns status code if unsuccefull
-400 if barcode contains non alphabetical/numerical characters
-404 if book with given barcode doesnt exist

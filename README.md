Books related APIs

/api/books/{pageNumber}
Purpose: Gets all books (no filter). This is for homepage just on page load of the app. Since the homepage is paginated, and because the API crashes trying to get millions of rows of books from cluster, pageNumber is to be specified during call from frontend.
Method: GET
Response structure:
[
    {
        "title": "title1",
        "subjects": "subjects of 1, all subjects1",
        "type": "book",
        "bib_num": "bibnum1"
    },
    {
        "title": "title2",
        "subjects": "subjects of 2, all subjects2",
        "type": "cd",
        "bib_num": "bibnum2"
    }
.
.
.
]


/api/books/getTypes
Purpose: To get the top 10 most frequently occurring types of books (to display in “type” dropdown menu). Call in the beginning on homepage 
Method: GET
Usage: /api/books/getTypes
Response Structure: 
[
    "microfilm",
    "book",
    "dvd",
    "cd-rom",
    "cd"
]




/api/books/getDetails/{bibNum}
Purpose: To get subject, type and details of a book by bibNum. USed on 2nd page (book details page) after user clicks on one book from homepage
Method: GET
Usage: /api/books/getDetails/bibnum1
Response Structure: 
{
    "title": "title1",
    "subjects": "subjects of 1, all subjects1",
    "type": "book",
    "bib_num": "bibnum1"
}
PS: NOT A LIST (ONE BOOK)

If a wrong bibNum is given as parameter, spring boot returns an error like:
{
    "timestamp": "2019-09-28T20:30:25.994+0000",
    "status": 404,
    "error": "Not Found",
    "message": "Book not found with bibNum : ‘wrong_bibnum",
    "path": "/api/books/getDetails/aish"
} 



api/books/filterBooks?bookTitle={title}&bookType={type}&bookCondition={condition}&pageNumber={pageNumber}
Purpose: To filter out books based on either/all of the criteria - title, type or condition. Also paginated. Initial filter request has a page number of 1 (by default). Page number is not an optional parameter
Method: GET
Usage: Type the values of bookTitle, bookType and bookCondition without quotes as follows - api/books/filterBooks?bookTitle=title1&bookType=dvd&bookCondition=3&pageNumber=1

If any filter is not chosen by user, leave that URL parameter empty (except for condition. If condition is not selected by user, put 0 and then make API call). Examples:

If only title is selected:
api/books/filterBooks?bookTitle=some title&bookType=&bookCondition=0&pageNumber=1

If only type is selected:
api/books/filterBooks?bookTitle=&bookType=cd-rom&bookCondition=0&pageNumber=1

If only condition is selected:
api/books/filterBooks?bookTitle=&bookType=&bookCondition=3&pageNumber=1

If title and type are selected:
api/books/filterBooks?bookTitle=some_title&bookType=dvd&bookCondition=0&pageNumber=1

If type and condition are selected:
api/books/filterBooks?bookTitle=&bookType=dvd&bookCondition=3&pageNumber=1

If title and condition are selected:
api/books/filterBooks?bookTitle=some_title&bookType=&bookCondition=3&pageNumber=1

If all are selected:
api/books/filterBooks?bookTitle=titl&bookType=cd-rom&bookCondition=2&pageNumber=1

NOTE: Condition is denoted by numbers in database. UI should do the following mapping and then make API call:

Drop down value (book condition)
Value to send to API
Very Bad
1
Bad
2
Okay
3
Good
4
Very Good
5


Response Structure:
[
    {
        "title": "title1",
        "subjects": "subjects of 1, all subjects1",
        "type": "book",
        "bib_num": "bibnum1"
    },
    {
        "title": "title2",
        "subjects": "subjects of 2, all subjects2",
        "type": "cd",
        "bib_num": "bibnum2"
    }
.
.
.
]

If the search criteria does not match any results in the database, an empty array is returned:
[ ]
//Appropriate message has to be displayed on the UI


Book Condition related APIs

api/conditions/get/{bibNum}
Purpose: Gets the condition of all copies of a given book. Should be called when user clicks on any book and navigates to second (book) page
Method: GET
Usage: api/conditions/get/bibnum1
Response structure:
[
    {
        "barcode": "barcode1",
        "bibNum": "bibnum1",
        "bookCondition":   4,
        "userId": "user1"
    },
    {
        "barcode": "barcode14",
        "bibNum": "bibnum1",
        "bookCondition": 2,
        "userId": "user4
    }
]

If a book does not have any conditions inserted by users, an empty list is returned:
[ ]
Appropriate message has to be displayed on the UI



api/conditions/insert
Purpose: Insert a condition for a particular book
Method: POST
POST request body Structure:
{
        "barcode": "barcode1",
        "bibNum": "bibnum1",
        "bookCondition": 4,
        "userId": "user1"
}
NOTE: Barcode can be anything. BibNum and UserId should be existing in the database
Usage: api/conditions/insert
Response structure:
True (is insert successful)
OR
False (is insert unsuccessful)

In case a call to this API is made with a userId or bibNum that does not exist in the database, a foreign key constraint violation pops up:
{
    "timestamp": "2019-09-28T21:16:18.573+0000",
    "status": 400,
    "error": "Bad Request",
    "message": "UserId or BibNum is invalid! Foreign key violation at the database! Insert unsuccessful!",
    "path": "/api/reviews/insert"
}
	NOTE: Front end has to ensure POST request body is correct. 


api/conditions/getReport/{condition}
Purpose: Gets the condition of all books (irrespective of bibNum) - this is when library admin staff want to generate reports
Method: GET
Usage: api/conditions/getReport/4
(where user wants to get all books with condition ‘bad’, ie, 4)
Response structure:
{
    "bookConditions": [
        {
            "barcode": "barcode3",
            "bibNum": "bibnum3",
            "bookCondition": 1,
            "userId": "user5"
        },
        {
            "barcode": "barcode4",
            "bibNum": "bibnum5",
            "bookCondition": 1,
            "userId": "user3"
        }
    ],
    "count": 2
}

NOTE: a) Do not use bibNum or UserID or any of the fields being returned if not needed in UI. Did not make sense to create new POJO just for this API, so used existing BookCondition POJO. 
b) This API also returns count of books in a particular condition. Thought it would be useful to display  it in the report.

If a condition does not have any books associated with it or if condition number is wrong (>5), then an empty result like this is obtained:

{
    "bookConditions": [ ],
    "count": 0
}

Appropriate message has to be displayed on the UI


Reviews related APIs:

api/reviews/get/{bibNum}
Purpose: Gets all reviews for a book. Should be called when user clicks on any book and navigates to second (book) page 
Method: GET
Usage: api/conditions/get/bibnum3
Response structure:
[
    {
        "reviewId": 1,
        "userId": "user4",
        "bibNum": "bibnum3",
        "reviewHeading": "good book",
        "reviewRating": 4,
        "reviewDescription": "rev des 1",
        "recommend": "yes"
    }
]
NOTE: Result is list of Reviews

If a book does not have any reviews associated with it, an empty list is returned:
[ ]
Appropriate message has to be displayed on the UI

	
api/reviews/getRating/{bibNum}
Purpose: Gets the average rating given by users for a book. Should be called when user clicks on any book and navigates to second (book) page. Just a good-to-have feature for 2nd page
Method: GET
Usage: api/conditions/getRating/bibnum5
Response structure:
2.6667
NOTE: Result is just a float - the average rating. Use this number to make stars or something on the UI - or just display as plain text - whatever is easy.

If no ratings have been given by any user to the book yet, then the following error message is the response from this API:
{
    "timestamp": "2019-09-28T20:49:37.518+0000",
    "status": 404,
    "error": "Not Found",
    "message": "No ratings yet!",
    "path": "/api/reviews/getRating/aish"
}

Display appropriate message on the UI


api/reviews/insert
Purpose: Used to insert a review by a user
Method: POST
POST Request Body Structure:
{
        "userId": "user4",
        "bibNum": "bibnum3",
        "reviewHeading": "good book",
        "reviewRating": 4,
        "reviewDescription": "rev des 1",
        "recommend": "yes"
    }
Usage: api/conditions/insert
Response structure:
True (is insert successful)
OR
False (is insert unsuccessful)

In case a call to this API is made with a userId or bibNum that does not exist in the database, a foreign key constraint violation pops up:
{
    "timestamp": "2019-09-28T21:16:18.573+0000",
    "status": 400,
    "error": "Bad Request",
    "message": "UserId or BibNum is invalid! Foreign key violation at the database! Insert unsuccessful!",
    "path": "/api/reviews/insert"
}
	NOTE: Front end has to ensure POST request body is correct. 



The front end for this project was built using Angular 7, using Angular’s internal http libraries to make API calls to the frontend. The UI is built using bootstrap 4.0.0, and consists of a home page- displaying two buttons - 

**To start the application:**

1- Clone the repo into your local machine.

`git clone https://github.com/mohamed-elmahdi/jumia-task`

2- Navigate to the  project root directory and build the docker image.

`docker build --build-arg JAR_FILE=build/libs/jumiatask-0.0.1-SNAPSHOT.jar -t jumiataskbe .`
 
3- Run the docker image.

`docker run -p 8088:8088 jumiataskbe`

4- The application is now running on port 8088.

**API:**
 
Description: Phone number listing.

Path: GET `/api/v1/phone-numbers`

Params:

String countryName, QueryParam, Optional

Boolean isValid, QueryParam, Optional
